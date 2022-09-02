(ns hellocomponent.controllers.todo-controller
  (:require [com.stuartsierra.component :as component]
            [hellocomponent.services.todo-service :as todo-service]
            [hellocomponent.components.routes :as routes]))

(defn ping [_]
  {:status 200
   :body   {:message "pong"}})

(defn todo-by-id
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)]
    {:status 201
     :body   (todo-service/todo-by-id (random-uuid) todo-service-component)}))

(defn update-todo
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)]
    {:status 201
     :body   (todo-service/update-todo {} "title" "description" todo-service-component)}))

(defn delete-todo
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)]
    (todo-service/delete-todo {} todo-service-component)
    {:status 204 :body nil}))

(defn create-todo
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)]
    {:status 201
     :body   (todo-service/create-todo "Some Title" "A short description" todo-service-component)}))

(defn list-all
  [request]
  (let [controller-comp   (::routes/controller request)
        todo-service-comp (:todo-service controller-comp)]
    {:status 200
     :body   (todo-service/list-all todo-service-comp)}))

(def routes #{["/todos/:id" :get todo-by-id :route-name :get-todo]
              ["/todos/:id" :put update-todo :route-name :update-todo]
              ["/todos/:id" :delete delete-todo :route-name :delete-todo]
              ["/todos" :get list-all :route-name :list-all-todo]
              ["/todos" :post create-todo :route-name :create-todo]
              ["/ping" :get ping :route-name :ping-todo]})

(defrecord TodoController []
  component/Lifecycle
  (start [component]
    (assoc component :routes routes))
  (stop [component]
    component))

(defn new-todo-controller []
  (map->TodoController {}))