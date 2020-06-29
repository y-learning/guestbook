(ns guestbook.core
  (:require [reagent.dom :as dom :refer [render]]
            [guestbook.message-form :as message-form]
            [ajax.core :refer [GET]]
            [reagent.core :as reagent]
            [clojure.core.async :refer [>! <! go chan]]))

(defn get-messages [messages]
  (GET "/messages"
       {:headers {"Accept" "application/transit+json"}
        :handler #(reset! messages (vec %))}))

(defn messages-list [messages]
  [:ul.content
   (for [{:keys [name message timestamp]} @messages]
     ^{:key timestamp}
     [:div [:li
            [:time (.toLocaleString timestamp)]
            [:p message [:br] [:b "-" name]]]
      [:br]])])

(defn append-msg [messages new-msg]
  (swap! messages conj (assoc new-msg :timestamp (js/Date.))))

(defn messages-chan-events [messages-chan messages]
  (go
    (while true
      (if-let [new-event (<! messages-chan)]
        (let [message (:append new-event)]
          (cond message
                (append-msg messages message)

                (:clear new-event)
                (reset! messages nil)))))))

(defn home []
  (let [messages (reagent/atom nil) messages-chan (chan)]
    (messages-chan-events messages-chan messages)
    (get-messages messages)
    (fn []
      [:div
       [:div.row
        [:div.span12
         [messages-list messages]]]
       [:div.row
        [:div.span12
         [message-form/form messages-chan]]]])))

(dom/render [home]
            (.getElementById js/document "content"))

