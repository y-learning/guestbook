(ns guestbook.routes.home
  (:require
    [guestbook.middleware :as middleware]
    [guestbook.controllers.controller :as ctrl]
    [guestbook.layout :as layout]))

(defn home-routes [controllers]
  ["" {:middleware [middleware/wrap-csrf middleware/wrap-formats]}
   ["/" #(layout/render % "home.html")]
   ["/about" #(layout/render % "about.html")]
   ["/messages" {:get #(ctrl/handle (:gmc controllers) %)}]
   ["/message" {:post #(ctrl/handle (:smc controllers) %)}]
   ["/messages/clear" {:post #(ctrl/handle (:cmc controllers) %)}]])
