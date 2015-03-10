(ns ks-example.integration
  (:require [schema.core :as s]
            [schema.coerce :as sc]))

(def Message {:source   s/Str
              :message  s/Str})
