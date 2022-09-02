(ns hellocomponent.repositories.todo-repository
  (:require [com.stuartsierra.component :as component]
            [schema.core :as s]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [hellocomponent.domain.models.to-do :as models.to-do]))

(s/defn create-todo :- models.to-do/Todo
  [{:keys [id title description] :as todo} :- models.to-do/Todo
   {:keys [db]}]
  (let [ds (:datasource db)]
    (jdbc/execute! ds ["INSERT INTO to_do (id, title, description) VALUES (?, ?, ?)" id title description])
    todo))

(s/defn update-todo :- models.to-do/Todo
  [{:keys [id title description] :as todo} :- models.to-do/Todo
   {:keys [db]}]
  (let [ds (:datasource db)]
    (jdbc/execute! ds ["UPDATE to_do SET title = ?, description = ? WHERE id = ?" title description id])
    todo))

(s/defn delete-todo
  [{:keys [id]} :- models.to-do/Todo
   {:keys [db]}]
  (let [ds (:datasource db)]
    (jdbc/execute! ds ["DELETE FROM to_do WHERE id = ?" id])
    nil))

(s/defn todo-by-id :- models.to-do/Todo
  [id :- s/Uuid
   {:keys [db]}]
  (let [ds (:datasource db)]
    (jdbc/execute! ds ["SELECT * FROM to_do WHERE id = ?" id] {:builder-fn rs/as-unqualified-lower-maps})))

(s/defn list-all :- [models.to-do/Todo]
  [component]
  (let [ds (:datasource (:db component))]
    (jdbc/execute! ds ["SELECT * FROM to_do"] {:builder-fn rs/as-unqualified-lower-maps})))

(defrecord TodoRepository []
  component/Lifecycle
  (start [component] component)
  (stop [component] component))

(defn new-todo-repository []
  (map->TodoRepository {}))

