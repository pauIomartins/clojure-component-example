(ns hellocomponent.controllers.greetings-controller
  (:require [com.stuartsierra.component :as component]
            [hellocomponent.services.greetings-service :as greetings-service]
            [hellocomponent.components.routes :as routes]))

(defn greetings
  [request]
  (let [controller-comp (::routes/controller request)
        greetings-comp  (:greetings-service controller-comp)]
    {:status 200
     :body   {:message (greetings-service/any-greeting greetings-comp)}}))

(defrecord GreetingsController []
  component/Lifecycle
  (start [component]
    (assoc component :routes #{["/greetings" :get greetings :route-name :greetings]}))
  (stop [component]
    component))

(defn new-greetings-controller []
  (map->GreetingsController {}))