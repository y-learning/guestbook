(ns guestbook.test.db.core
  (:require
    [clojure.test :refer :all]
    [guestbook.db.core :refer [*db*] :as db]
    [java-time.pre-java8]
    [luminus-migrations.core :as migrations]
    [next.jdbc :as jdbc]
    [guestbook.config :refer [env]]
    [mount.core :as mount])
  (:import (java.util Date)))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'guestbook.config/env
      #'guestbook.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-save-get-messages
  (jdbc/with-transaction
    [t-conn *db* {:rollback-only true}]
    (let [message {:name "Bob" :message "Hello there!" :timestamp (Date.)}]
      (is (= 1 (db/save-message!
                 t-conn
                 message
                 {:connection t-conn})))
      (is (= message
             (-> (db/get-messages t-conn {:id "1"})
                 (first)
                 (select-keys [:name :message :timestamp])))))))
