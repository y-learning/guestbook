(ns guestbook.routes.home
  (:require
    [guestbook.layout :as layout]
    [guestbook.db.core :as db]
    [clojure.java.io :as io]
    [guestbook.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response])
  (:import (java.util Date)))

(defn home-page [request]
  (layout/render request
                 "home.html"
                 {:messages (db/get-messages)}))

(defn about-page [request]
  (layout/render request "about.html"))

(defn save-message! [{:keys [params]}]
  (db/save-message!
    (assoc params :timestamp (Date.)))
  (response/found "/"))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]
   ["/message" {:post (fn [request] (save-message! request))}]])

