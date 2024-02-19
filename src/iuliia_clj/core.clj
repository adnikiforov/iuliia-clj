(ns iuliia-clj.core
  (:require [iuliia-clj.translator :as translator])
  (:require [iuliia-clj.schema :as schema]))

(defn translate [string schema] (translator/translate string schema))
(defn load_schema [name] (schema/load_schema name))
(defn all_schemas [] (schema/schemas))
