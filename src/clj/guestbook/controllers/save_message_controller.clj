(ns guestbook.controllers.save-message-controller
  (:require [ring.util.http-response :as response]
            [guestbook.db.core :as db]
            [ring.util.response]
            [ring.util.http-response :as response]
            [struct.core :as st]
            [guestbook.controllers.controller :as ctrl])
  (:import (java.util Date)))

(defn validate-message [params]
  (st/validate
    params
    [[:name st/required]
     [:message st/required [st/min-count 10]]]))

(defn save-message! [{:keys [params]}]
  (if-let [errors (first (validate-message params))]
    (-> (response/found "/")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-message! (assoc params :timestamp (Date.)))
      (response/found "/"))))

(defrecord SaveMessageController []
  ctrl/Controller
  (handle [_ request] (save-message! request)))