(ns ks-example.ui.main
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as reagent :refer [atom]]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]))

(defonce main-data (atom {:clicks 0}))

(defn clicked [_]
  (swap! main-data update-in [:clicks] inc))

(defn yell-to-backend [_]
  (go
    (let [response (<! (http/post "/api/integration/incoming" {:edn-params {:source "foo"
                                                                            :message "bar"}}))]
      (js/console.log (pr-str response))
      (swap! main-data assoc-in [:response] response))))

(defn main-view []
  (let [{:keys [clicks response]} @main-data]
    [:div#main-view
     [:div.jumbotron
      [:div.container
       [:h1 "Greetings, earthlings!"]
       [:p
        [:span "Clicks:"]
        [:span.text-info clicks]]
       [:button.btn.btn-primary.btn-lg
        {:on-click clicked}
        "Click me"]
       [:button.btn.btn-default.btn-lg
        {:on-click yell-to-backend}
        "Yell to backend"]]]
     (if-let [{:keys [status body]} response]
       [:div.response
        [:div.alert {:class (if (= status 200) "alert-success" "alert-danger")}
         [:strong (if (= status 200) "Successful:" "Oh no:")]
         (pr-str body)
         [:button.btn.btn-default.btn-xs.pull-right
          {:on-click (fn [_] (swap! main-data assoc-in [:response] nil))}
          "Clear"]]])]))

(defn init! []
  (js/console.log "Here we go!")
  (reagent/render [main-view] (js/document.getElementById "app")))

(init!)
