(ns guestbook.controllers.get-messages-controller
  (:require [guestbook.db.core :as db]
            [ring.util.response]
            [guestbook.controllers.controller :as ctrl]
            [guestbook.layout :as l]))

(defrecord GetMessagesController []
  ctrl/Controller
  (handle [_ request]
    (let [{flash :flash} request]
      (l/render request
                "home.html"
                (merge {:messages (db/get-messages)}
                       (select-keys flash [:name :message :errors]))))))