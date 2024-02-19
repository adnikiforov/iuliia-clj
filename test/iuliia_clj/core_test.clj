(ns iuliia-clj.core-test
  (:require [clojure.test :refer :all]
            [iuliia-clj.core :refer :all]))

;(deftest a-test
;  (testing "FIXME, I fail."
;    (is (= 0 1))))

;(defn test_schema [schema_name]
;  (let [schema (schema/load_schema schema_name)]
;    (doseq [[input output] (.get_samples schema)]
;      (let
;       [test_output (translate input schema)]
;
;        (if (not= test_output output)
;          (do
;            (println test_output)
;            (println output))
;          ())))))
;
;(defn test_schemas []
;  (doseq [schema_name (schema/schemas)]
;    (println "---")
;    (println schema_name)
;    (test_schema schema_name)
;    (println "---")))
;
;(test_schemas)