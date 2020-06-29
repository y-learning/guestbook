(ns guestbook.controllers.clear-messages-controller
  (:require [guestbook.db.core :as db]
            [guestbook.controllers.controller :as ctrl]
            [ring.util.http-response :as response]))

(defrecord ClearMessagesController []
  ctrl/Controller
  (handle [_ _] (try
                  (db/delete-messages!)
                  (response/ok {:status :ok})
                  (catch Exception e
                    (println "Error:: " e)
                    (response/internal-server-error
                      {:errors {:server-error ["Failed to delete
                             messages"]}})))))