(ns guestbook.controllers.get-messages-controller
  (:require [guestbook.db.core :as db]
            [ring.util.http-response :as response]
            [guestbook.controllers.controller :as ctrl]
            [guestbook.layout :as l]))

(defrecord GetMessagesController []
  ctrl/Controller
  (handle [_ _]
    (response/ok (db/get-messages))))