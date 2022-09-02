(ns hellocomponent.domain.logic.to-do
  (:require [schema.core :as s]
            [hellocomponent.domain.models.to-do :as models.to-do]))

(s/defn new-todo :- models.to-do/Todo
  [title :- s/Str
   description :- s/Str]
  {:id          (random-uuid)
   :title       title
   :description description})