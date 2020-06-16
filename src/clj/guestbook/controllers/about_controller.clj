(ns guestbook.controllers.about-controller
  (:require
    [guestbook.layout :as layout]
    [ring.util.response]
    [guestbook.controllers.controller :as ctrl]))

(defrecord AboutController []
  ctrl/Controller
  (handle [_ request]
    (layout/render request "about.html")))
