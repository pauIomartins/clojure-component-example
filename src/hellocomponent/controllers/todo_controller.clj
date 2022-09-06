(ns hellocomponent.controllers.todo-controller
  (:require [com.stuartsierra.component :as component]
            [hellocomponent.services.todo-service :as todo-service]
            [hellocomponent.components.routes :as routes]
            [io.pedestal.http.body-params :as body-params])
  (:import [java.util UUID]))

(defn ping [_]
  {:status 200
   :body   {:message "pong"}})

(defn todo-by-id
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)
        todo-id                (-> request :path-params :id UUID/fromString)
        todo                   (todo-service/todo-by-id todo-id todo-service-component)]
    (if todo
      {:status 200 :body todo}
      {:status 404 :body {:message "Not found!"}})))

(defn update-todo
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)
        body-params            (:json-params request)
        todo-id                (-> request :path-params :id UUID/fromString)
        todo                   (todo-service/todo-by-id todo-id todo-service-component)]
    (if todo
      {:status 200
       :body   (todo-service/update-todo todo (:title body-params) (:description body-params) todo-service-component)}
      {:status 400 :body {:message "To do do not exists!"}})))

(defn delete-todo
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)
        todo-id                (-> request :path-params :id UUID/fromString)
        todo                   (todo-service/todo-by-id todo-id todo-service-component)]
    (todo-service/delete-todo todo todo-service-component)
    {:status 204 :body {}}))

(defn create-todo
  [request]
  (let [controller-component   (::routes/controller request)
        todo-service-component (:todo-service controller-component)
        params                 (:json-params request)]
    {:status 201
     :body   (todo-service/create-todo (:title params) (:description params) todo-service-component)}))

(defn list-all
  [request]
  (let [controller-comp   (::routes/controller request)
        todo-service-comp (:todo-service controller-comp)]
    {:status 200
     :body   (todo-service/list-all todo-service-comp)}))

(def routes #{["/todos/:id" :get [todo-by-id] :route-name :get-todo]
              ["/todos/:id" :put [(body-params/body-params) update-todo] :route-name :update-todo]
              ["/todos/:id" :delete [delete-todo] :route-name :delete-todo]
              ["/todos" :get [list-all] :route-name :list-all-todo]
              ["/todos" :post [(body-params/body-params) create-todo] :route-name :create-todo]
              ["/ping" :get [ping] :route-name :ping-todo]})

(defrecord TodoController []
  component/Lifecycle
  (start [component]
    (assoc component :routes routes))
  (stop [component]
    component))

(defn new-todo-controller []
  (map->TodoController {}))