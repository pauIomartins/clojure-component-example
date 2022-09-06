(ns hellocomponent.domain.logic.to-do
  (:require [schema.core :as s]
            [hellocomponent.domain.models.to-do :as models.to-do]))

(s/defn new-todo :- models.to-do/Todo
  [title :- s/Str
   description :- s/Str]
  {:id          (random-uuid)
   :title       title
   :description description})

(s/defn updated-todo :- models.to-do/Todo
  [todo :- models.to-do/Todo
   new-title :- s/Str
   new-description :- s/Str]
  (assoc todo :title new-title :description new-description))
