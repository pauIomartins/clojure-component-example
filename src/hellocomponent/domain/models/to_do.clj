(ns hellocomponent.domain.models.to-do
  (:require [schema.core :as s]))

(s/defschema Todo {:id          s/Uuid
                   :title       s/Str
                   :description s/Str})