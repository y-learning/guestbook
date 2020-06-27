(ns guestbook.core
  (:require [reagent.dom :as dom :refer [render]]
            [guestbook.message-form :as message-form]))

(defn home []
  [:div.row
   [:div.span12
    [message-form/form]]])

(dom/render [home]
            (.getElementById js/document "content"))

