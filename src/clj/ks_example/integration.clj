(ns ks-example.integration
  (:require [schema.core :as s]
            [schema.coerce :as sc]
            [monger.collection :as mc]
            [ks-example.db :as db])
  (:import [org.joda.time DateTime]))

(def IncomingMessage {:source   s/Str
                      :message  s/Str})

(def MessageData (assoc IncomingMessage
                        :_id  s/Str
                        :ts   DateTime))

(s/defn ^:always-validate incoming->data :- MessageData [incoming-message :- IncomingMessage]
  (assoc incoming-message
         :_id (db/create-id)
         :ts  (DateTime/now)))

(s/defn ^:always-validate save-incoming! :- MessageData [incoming-message :- IncomingMessage]
  (let [data-message (incoming->data incoming-message)]
    (mc/insert db/db :incoming data-message)
    data-message))

(s/defn ^:always-validate find-by-source :- [MessageData] [source]
  (mc/find-maps db/db :incoming {:source source}))
