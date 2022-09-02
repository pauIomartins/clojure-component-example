(ns hellocomponent.services.greetings-service
  (:require [com.stuartsierra.component :as component]))

(defrecord GreetingsService []
  component/Lifecycle
  (start [component]
    (assoc component :greetings-list ["Hey!" "Hello!" "Hi!" "¡Hola!" "Oi!" "Olá!"]))
  (stop [component]
    (assoc component :greetings-list nil)))

(defn any-greeting
  [component]
  (rand-nth (:greetings-list component)))

(defn new-greetings
  []
  (map->GreetingsService {}))

