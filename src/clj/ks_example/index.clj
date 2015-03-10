(ns ks-example.index
  (:require [clojure.java.io :as io]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [html5 include-css include-js]]))


(def index-page
  (html
    (html5
      [:head
       [:title "KS Example"]
       [:meta {:charset "utf-8"}]
       [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
       [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
       (include-css "static/css/ks-example.css")]
      [:body
       [:div#app]
       "<!--[if lt IE 10]>"
       (include-js "static/shim/es5-shim.js" "static/shim/es5-sham.js")
       "<![endif]-->"
       (include-js "static/js/ks-example.js")])))
