(ns hellocomponent.controllers.hello-controller
  (:import (com.stuartsierra.component Lifecycle)))

(defn hello [_]
  {:status 200
   :body   {:message "Hello world!"}})

(defrecord HelloController []
  Lifecycle
  (start [component]
    (assoc component :routes #{["/hello" :get hello :route-name :hello]}))
  (stop [component]
    component))

(defn new-hello-controller []
  (map->HelloController {}))