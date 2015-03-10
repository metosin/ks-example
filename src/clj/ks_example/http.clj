(ns ks-example.http
  (:require [clojure.tools.nrepl.server :as nrepl]
            [org.httpkit.server :as http-kit]
            [ks-example.log]
            [ks-example.routes :as routes]))

(defn start-server [config]
  (if-let [nrepl-port (get-in config [:nrepl :port])]
    (nrepl/start-server :port nrepl-port))
  (http-kit/run-server #'routes/app (:http config)))
