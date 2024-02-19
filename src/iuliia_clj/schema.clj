(ns iuliia-clj.schema
  (:require
   [clojure.data.json :as json]
   [clojure.string :as str])
  (:use [clojure.java.io :as io]))

(definterface ISchema
  (mapping [char])
  (next_mapping [char])
  (prev_mapping [char])
  (ending_mapping [char])
  (get_samples []))

(deftype Schema [name description url mapping prev_mapping next_mapping ending_mapping samples]
  ISchema
  (mapping [_this char] (get mapping char))
  (next_mapping [_this char] (get next_mapping char))
  (prev_mapping [_this char] (get next_mapping char))
  (ending_mapping [_this char] (get ending_mapping char))
  (get_samples [_this] samples))

(defn schemas []
  ; TODO add resource loader if possible instead of hardcoded path
  (for [file (filter #(str/ends-with? % ".json") (.list (io/file "../../resources/schema")))]
    (get (str/split file #"\.") 0)))

(defn load_schema [schema_name]
  (let
   [raw_schema
    (json/read-str (slurp (io/resource (format "schema/%s.json" schema_name))))]

    (->Schema
     (get raw_schema "name")
     (get raw_schema "description")
     (get raw_schema "url")
     (get raw_schema "mapping")
     (get raw_schema "prev_mapping")
     (get raw_schema "next_mapping")
     (get raw_schema "ending_mapping")
     (get raw_schema "samples"))))
