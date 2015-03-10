(ns ks-example.config
  (:require [clojure.java.io :as io]
            [potpuri.core :refer [deep-merge]]))

(def mode (-> (System/getProperty "mode") (or "dev") (keyword)))

(if-not (#{:dev :prod} mode)
  (throw (ex-info (format "Illegal mode: [%s]" mode) {:mode mode})))

(def dev?  (= mode :dev))
(def prod? (= mode :prod))

(def config
  (->> (concat (keep io/resource ["ks-example.edn"
                                  (format "ks-example-%s.edn" (name mode))])
               (filter #(.exists %) [(io/file "ks-example.edn")]))
       (map (comp read-string slurp))
       (apply deep-merge)))
