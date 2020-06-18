(ns guestbook.routes.home
  (:require
    [guestbook.middleware :as middleware]
    [guestbook.controllers.controller :as ctrl]))

(defn home-routes [controllers]
  ["" {:middleware [middleware/wrap-csrf middleware/wrap-formats]}
   ["/" {:get #(ctrl/handle (:gmc controllers) %)}]
   ["/message" {:post #(ctrl/handle (:smc controllers) %)}]
   ["/about" {:get #(ctrl/handle (:ac controllers) %)}]])
