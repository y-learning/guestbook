(ns guestbook.controllers.save-message-controller
  (:require [ring.util.http-response :as response]
            [guestbook.db.core :as db]
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
    (response/bad-request {:errors errors})
    (try
      (db/save-message! (assoc params :timestamp (Date.)))
      (response/ok {:status :ok})
      (catch Exception e
        (println "save-message! failed: " e)
        (response/internal-server-error
          {:errors {:server-error ["Failed to save the message!"]}})))))

(defrecord SaveMessageController []
  ctrl/Controller
  (handle [_ request] (save-message! request)))