(ns ks-example.routes
  (:require [schema.core :as s]
            [compojure.api.sweet :refer :all]
            [compojure.route :refer [resources]]
            [ring.util.http-response :refer [ok] :as resp]
            [ks-example.index :refer [index-page]]
            [ks-example.integration :as integration]))

(defapi app
  (swagger-ui "/docs")
  (swagger-docs
    :title "KS example API")

  (GET* "/" []
    (-> (ok index-page)
        (resp/content-type "text/html; charset=\"UTF-8\"")))

  (context "/static" []
    (context "/fonts" []
      (resources "" {:root "META-INF/resources/webjars/bootstrap/3.3.2/fonts"})
      (resources "" {:root "META-INF/resources/webjars/font-awesome/4.3.0/fonts"}))
    (resources "/shim" {:root "META-INF/resources/webjars/es5-shim/4.0.6"})
    (resources "" {:root "static"}))

  (swaggered "integration" :description "Integration API"
    (context "/api/integration" []

      (POST* "/incoming" []
        :body [message integration/IncomingMessage]
        (ok (integration/save-incoming! message)))

      (GET* "/incoming" []
        :query-params [{source :- s/Str "foo"}]
        (ok (integration/find-by-source source))))))

(app {:request-method :get :uri "/api/integration/incoming" :headers {"Accept" "application/edn"}})



