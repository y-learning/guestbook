(ns guestbook.core)

(-> js/document
    (. getElementById "content")
    (. -innerHTML)
    (set! "Hello, World!"))