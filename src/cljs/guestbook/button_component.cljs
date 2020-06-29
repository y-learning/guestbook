(ns guestbook.button-component)

(defn submit-btn [label on-click]
  [:input.btn.btn-primary {:type     :submit
                           :on-click on-click
                           :value    label
                           :style    {:margin "5px"}}])


