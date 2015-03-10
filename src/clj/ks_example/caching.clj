(ns ks-example.caching
  (:require [ks-example.config :as config]))

(def no-cache     "max-age=0,no-cache,no-store")
(def cache-30d    (if config/dev?
                    no-cache
                    "public,max-age=2592000,s-maxage=2592000"))

(defn add-cache-header [response value]
  (if response
    (assoc-in response [:headers "cache-control"] value)))

(defn cache-middleware [handler value]
  (fn [request]
    (-> request handler (add-cache-header value))))
