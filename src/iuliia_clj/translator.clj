(ns iuliia-clj.translator
  (:require [clojure.string :as str]))

(def ENDING_LENGTH 2)

(defn- upcase? [char] (boolean (re-find #"\p{Lu}" (str char))))

(defn- translate_prev [chars index schema]
  (.prev_mapping schema
                 (apply str
                        (str/lower-case
                         (if (> index 0)
                           (subvec (into [] (seq chars)) index (+ index 1))
                           (into [] (str (get (into [] chars) index))))))))

(defn- translate_next [chars index schema]
  (.next_mapping schema
                 (apply str
                        (str/lower-case
                         (subvec (into [] (seq chars)) index (+ index 1))))))

(defn- split_word [word]
  (if (<= (count word) ENDING_LENGTH)
    [word, ""]
    (let [ending (if (> (count word) ENDING_LENGTH)
                   (apply str (take-last ENDING_LENGTH word))
                   "")
          stem (if (zero? (- (count word) ENDING_LENGTH))
                 word
                 (apply str (take (- (count word) ENDING_LENGTH) word)))]

      [stem ending])))

(defn- camelcase [string]
  (if (upcase? string)
    (let [list (seq (str/lower-case string))]
      (str
       (str/upper-case
        (first list))
       (apply str (next list))))
    string))

(defn- translate_char [chars char index schema]
  (if (boolean (re-find #"[А-Яа-яёЁ]" (str char)))
    (let [t_char (or
                  (translate_prev chars index schema)
                  (translate_next chars index schema)
                  (.mapping schema (str/lower-case char)))]
      (if (upcase? char)
        (camelcase (str/upper-case t_char))
        t_char))
    char))

(defn- translate_stem [stem schema]
  (let [t_stem (apply str (map-indexed
                           (fn [index char] (translate_char stem char index schema))
                           stem))]
    (if (upcase? t_stem)
      t_stem
      (camelcase t_stem))))

(defn- translate_chunk [chunk schema]
  (if (= (int (first chunk)) 32)
    ; space group, return as is
    chunk
    (let [[stem ending] (split_word chunk)]
      ; extract ending
      (if (zero? (count ending))
        (translate_stem chunk schema)
        ; translate ending if possible
        (let [t_ending (.ending_mapping schema ending)]
          ; translate stem if there is ending, otherwise translate chunk
          (if (nil? t_ending)
            (translate_stem chunk schema)
            (str
             (translate_stem stem schema)
             t_ending)))))))

; takes string and splits into groups of symbols and spaces
(defn- split_chunks [string]
  (reduce
   (fn [acc elem]
     (cond
        ; no groups yet — open new group
       (empty? acc) (conj acc [elem])
        ; last group and elem are both non-spaces — append to group
       (and
        (not= (int (last (last acc))) 32)
        (not= (int elem) 32))
       (assoc acc
              (- (count acc) 1)
              (conj (last acc) elem))
        ; last group and elem are both spaces — append to group
       (= (last (last acc)) elem)
       (assoc acc
              (- (count acc) 1)
              (conj (last acc) elem))
        ; last group mismatches elem — open new group
       :else (conj acc [elem])))
   []
   (seq string)))

;; public api
(defn translate [string schema]
  (apply str
         (for [chunk (split_chunks string)]
           (apply str (translate_chunk chunk schema)))))
