(ns guestbook.core
  (:require [reagent.dom :as dom :refer [render]]
            [guestbook.message-form :as message-form]
            [ajax.core :refer [GET]]
            [reagent.core :as reagent]))

(defn get-messages [messages]
  (GET "/messages"
       {:headers {"Accept" "application/transit+json"}
        :handler #(reset! messages (vec %))}))

(defn messages-list [messages]
  [:ul.content
   (for [{:keys [id name message timestamp]} @messages]
     ^{:key id}
     [:li
      [:time (.toLocaleString timestamp)]
      [:p message]
      [:p "-" name]])])

(defn home []
  (let [messages (reagent/atom {})]
    (get-messages messages)
    (fn []
      [:div
       [:div.row
        [:div.span12
         [messages-list messages]]]
       [:div.row
        [:div.span12
         [message-form/form]]]])))

(dom/render [home]
            (.getElementById js/document "content"))

