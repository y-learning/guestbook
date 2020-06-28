(ns guestbook.message-form
  (:require [reagent.core :as reagent]
            [guestbook.errors-component :refer [errors-alert?]]
            [guestbook.button-component :refer [submit-btn]]
            [guestbook.field-component :refer [label-text label-textarea]]))

(defn get-value [synthetic-event]
  (-> synthetic-event .-target .-value))

(defn update-field [id fields synthetic-event]
  (swap! fields assoc id (get-value synthetic-event)))

(defn- input [input-fn title id fields]
  [input-fn title id (id @fields) #(update-field id fields %)])

(defn form []
  (let [fields (reagent/atom {}) errors (reagent/atom nil)]
    (fn []
      [:div.content
       [:div.form-group
        [errors-alert? :name errors]
        [input label-text "Name" :name fields]
        [errors-alert? :message errors]
        [input label-textarea "Message" :message fields]
        [submit-btn "Comment" #(println "")]]])))