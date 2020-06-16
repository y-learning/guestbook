(ns guestbook.controllers.controller)

(defprotocol Controller
          (handle [this, request]))