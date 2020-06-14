-- :name save-message! :! :n
-- :doc creates a new message using the name, message, and timestamp keys.
INSERT INTO guestbook
    (name, message, timestamp)
VALUES (:name, :message, :timestamp);

-- :name get-messages :? :*
-- :doc selects all available messages
SELECT *
FROM guestbook;

-- :name get-message :? :1
-- :doc retrieves a message by the given id.
SELECT *
FROM guestbook
WHERE id = :id;