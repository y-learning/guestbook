(ns guestbook.handler
  (:require
    [guestbook.middleware :as middleware]
    [guestbook.layout :refer [error-page]]
    [guestbook.routes.home :refer [home-routes]]
    [reitit.ring :as ring]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.middleware.webjars :refer [wrap-webjars]]
    [guestbook.env :refer [defaults]]
    [mount.core :as mount]))

(mount/defstate init-app
                :start ((or (:init defaults) (fn [])))
                :stop ((or (:stop defaults) (fn []))))

(defn- page [error-code title]
  (constantly (error-page {:status error-code, :title title})))

(def default-handler
  {:not-found          (page 404 "404 - Page not found")
   :method-not-allowed (page 405 "405 - Not allowed")
   :not-acceptable     (page 406 "406 - Not acceptable")})

(mount/defstate app-routes
                :start
                (ring/ring-handler
                  (ring/router [(home-routes)])
                  (ring/routes
                    (ring/create-resource-handler {:path "/"})
                    (wrap-content-type (wrap-webjars (constantly nil)))
                    (ring/create-default-handler default-handler))))

(defn app []
  (middleware/wrap-base #'app-routes))
