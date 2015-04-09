(ns ks-example.routes
  (:require [schema.core :as s]
            [compojure.api.sweet :refer :all]
            [compojure.route :refer [resources]]
            [ring.util.http-response :refer [ok] :as resp]
            [ks-example.index :refer [index-page]]
            [ks-example.session :as session]
            [ks-example.integration :as integration]))

(defapi app-routes
  {:format {:formats [:edn :json-kw]}}

  (swagger-ui "/docs")
  (swagger-docs
    :title "KS example API")

  (GET* "/" []
    (-> (ok index-page)
        (resp/content-type "text/html; charset=\"UTF-8\"")))

  (swaggered "integration" :description "Integration API"

    (context "/api/session" []
      (GET* "/" [] session/status)
      (POST* "/login" [] session/login)
      (GET* "/logout" [] session/logout))

    (context "/api/integration" []
      (middlewares [session/wrap-session-required]
        (POST* "/incoming" [] integration/save-incoming!)
        (GET* "/incoming" [] integration/find-by-source)))))
