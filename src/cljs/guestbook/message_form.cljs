(ns guestbook.message-form
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]
            [guestbook.errors-component :refer [errors-alert?]]
            [guestbook.button-component :refer [submit-btn]]
            [guestbook.field-component :refer [label-text label-textarea]]
            [clojure.core.async :refer [>! <! go]]))

(defn get-value [synthetic-event]
  (-> synthetic-event .-target .-value))

(defn get-csrf-token []
  (.-value (.getElementById js/document "csrf-token")))

(defn send-message! [fields errors messages-chan]
  (POST "/message"
        {:format        :json
         :headers       {"Accept"       "application/transit+json"
                         "x-csrf-token" (get-csrf-token)}
         :params        @fields
         :handler       #(do
                           (.log js/console (str "response: " %))
                           (reset! errors nil)
                           (go (>! messages-chan {:append @fields})))
         :error-handler #(do
                           (.error js/console (str "Errors::" %))
                           (reset! errors (get-in % [:response :errors])))}))

(defn update-field [id fields synthetic-event]
  (swap! fields assoc id (get-value synthetic-event)))

(defn- input [input-fn title id fields]
  [input-fn title id (id @fields) #(update-field id fields %)])

(defn user-confirmed? []
  (js/confirm "Are you sure you wanna delete all messages?"))

(defn delete-messages! [messages-chan]
  (if (user-confirmed?)
    (POST "/messages/clear"
          {:headers {"Accept"       "application/transit+json"
                     "x-csrf-token" (get-csrf-token)}
           :handler #(go (>! messages-chan {:clear true}))})))

(defn form [messages-chan]
  (let [fields (reagent/atom {}) errors (reagent/atom nil)]
    (fn []
      [:div.content
       [:div.form-group
        [errors-alert? :name errors]
        [input label-text "Name" :name fields]
        [errors-alert? :message errors]
        [input label-textarea "Message" :message fields]
        [submit-btn "Comment" #(send-message! fields errors messages-chan)]
        [:input.btn.btn-secondary {:type     :submit
                                   :on-click #(delete-messages! messages-chan)
                                   :value    "Clear"}]]])))