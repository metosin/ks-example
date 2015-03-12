(ns ks-example.db
  (:require [monger.core :as m]
            [monger.collection :as mc]
            [monger.json]
            [monger.joda-time]
            [ks-example.config :as config]))

(defonce conn (m/connect))
(defonce db  (m/get-db conn (get-in config/config [:db :db-name])))

(defn create-id ^String []
  (str (org.bson.types.ObjectId.)))
