(ns guestbook.message-form
  (:require [reagent.core :as reagent]))

(defn get-value [synthetic-event]
  (-> synthetic-event .-target .-value))

(defn common-input-atr [name state]
  {:name      name
   :value     (name @state)
   :on-change #(swap! state assoc name (get-value %))})

(defn name-input [state]
  [:p "Name"
   [:input.form-control (assoc (common-input-atr :name state) :type :text)]])

(defn message-input [state]
  [:p "Message"
   [:textarea.form-control
    (assoc (common-input-atr :message state) :rows 4 :cols 50)]])

(defn submit-btn [label]
  [:input.btn.btn-primary {:type :submit :value label}])

(defn form []
  (let [fields (reagent/atom {})]
    (fn []
      [:div.content
       [:div.form-group
        [name-input fields]
        [message-input fields]
        [submit-btn "Comment"]]])))