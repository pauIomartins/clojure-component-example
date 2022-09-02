(ns hellocomponent.system
  (:require [com.stuartsierra.component :as component]
            [hellocomponent.controllers.hello-controller :as controllers.hello]
            [hellocomponent.controllers.greetings-controller :as controllers.greetings]
            [hellocomponent.controllers.todo-controller :as controllers.todo]
            [hellocomponent.components.routes :as components.routes]
            [hellocomponent.components.http-server :as components.http-server]
            [hellocomponent.components.db :as components.db]
            [hellocomponent.repositories.todo-repository :as repositories.todo-repository]
            [hellocomponent.services.greetings-service :as services.greetings-service]
            [hellocomponent.services.todo-service :as services.todo-service]))

(defn new-system []
  (component/system-map
    :db                   (components.db/new-Db)
    :todo-repository      (component/using (repositories.todo-repository/new-todo-repository) [:db])
    :greetings-service    (services.greetings-service/new-greetings)
    :todo-service         (component/using (services.todo-service/new-todo-service) [:todo-repository])
    :hello-controller     (controllers.hello/new-hello-controller)
    :greetings-controller (component/using (controllers.greetings/new-greetings-controller) [:greetings-service])
    :todo-controller      (component/using (controllers.todo/new-todo-controller) [:todo-service])
    :routes               (component/using (components.routes/new-routes) [:hello-controller :greetings-controller :todo-controller])
    :http-server          (component/using (components.http-server/new-http-server) [:routes])))
