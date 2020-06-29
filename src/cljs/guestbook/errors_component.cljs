(ns guestbook.errors-component)

(defn errors-alert? [field-id errors]
  (when-let [error (field-id @errors)]
    [:div.alert.alert-danger (clojure.string/join error)]))
