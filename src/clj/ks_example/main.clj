(ns ks-example.main
  (:gen-class))

(defn -main [& args]
  (require 'ks-example.http)
  (require 'ks-example.config)
  ((resolve 'ks-example.http/start) (resolve 'ks-example.config/config)))
