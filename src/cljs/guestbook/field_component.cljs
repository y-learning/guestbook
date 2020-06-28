(ns guestbook.field-component)

(defn- common-attr [name value on-change]
  {:name      name
   :value     value
   :on-change on-change})

(defn- input [input-type title name value on-change attributes]
  [:p title
   [input-type
    (merge (common-attr name value on-change) attributes)]])

(defn label-text [title name value on-change]
  [input :input.form-control title name value on-change {:type :text}])

(defn label-textarea [title name value on-change]
  [input :textarea.form-control title name value on-change {:rows 4 :cols 50}])
