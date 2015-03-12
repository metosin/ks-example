(ns ks-example.main
  (:gen-class))

(defn -main [& args]
  (require 'ks-example.http)
  (require 'ks-example.config)
  ((resolve 'ks-example.http/start-server) @(resolve 'ks-example.config/config)))
