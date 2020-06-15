(ns guestbook.routes.home
  (:require
    [guestbook.layout :as layout]
    [guestbook.db.core :as db]
    [clojure.java.io :as io]
    [guestbook.middleware :as middleware]
    [ring.util.response]
    [ring.util.http-response :as response]
    [struct.core :as st])
  (:import (java.util Date)))

(defn validate-message [params]
  (st/validate
    params
    [[:name st/required]
     [:message st/required [st/min-count 10]]]))

(defn home-page [request]
  (let [{flash :flash} request]
    (println flash)
    (layout/render request
                   "home.html"
                   (merge {:messages (db/get-messages)}
                          (select-keys flash [:name :message :errors])))))

(defn about-page [request]
  (layout/render request "about.html"))

(defn save-message! [{:keys [params]}]
  (if-let [errors (first (validate-message params))]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-message! (assoc params :timestamp (Date.)))
      (response/found "/"))))

(defn home-routes []
  [""
   {:middleware [middleware/wrap-csrf middleware/wrap-formats]}
   ["/" {:get (fn [request] (home-page request))}]
   ["/about" {:get about-page}]
   ["/message" {:post (fn [request] (save-message! request))}]])



