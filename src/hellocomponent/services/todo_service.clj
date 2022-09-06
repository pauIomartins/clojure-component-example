(ns hellocomponent.services.todo-service
  (:require [com.stuartsierra.component :as component]
            [hellocomponent.repositories.todo-repository :as repositories.todo-repository]
            [hellocomponent.domain.logic.to-do :as logic.to-do]
            [hellocomponent.domain.models.to-do :as models.to-do]
            [schema.core :as s]))

(s/defn create-todo :- models.to-do/Todo
  [title :- s/Str
   description :- s/Str
   {:keys [todo-repository]}]
  (repositories.todo-repository/create-todo (logic.to-do/new-todo title description) todo-repository))

(s/defn update-todo :- models.to-do/Todo
  [todo :- models.to-do/Todo
   new-title :- s/Str
   new-description :- s/Str
   {:keys [todo-repository]}]
  (let [updated-todo (logic.to-do/updated-todo todo new-title new-description)]
    (repositories.todo-repository/update-todo updated-todo todo-repository)))

(s/defn delete-todo
  [todo :- models.to-do/Todo
   {:keys [todo-repository]}]
  (repositories.todo-repository/delete-todo todo todo-repository))

(s/defn todo-by-id :- models.to-do/Todo
  [id :- s/Uuid
   {:keys [todo-repository]}]
  (repositories.todo-repository/todo-by-id id todo-repository))

(s/defn list-all :- [models.to-do/Todo]
  [{:keys [todo-repository]}]
  (repositories.todo-repository/list-all todo-repository))

(defrecord TodoService []
  component/Lifecycle
  (start [component] component)
  (stop [component] component))

(defn new-todo-service []
  (map->TodoService {}))
