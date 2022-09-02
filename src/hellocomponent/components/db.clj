(ns hellocomponent.components.db
  (:require [next.jdbc :as jdbc])
  (:import (com.stuartsierra.component Lifecycle)))

(def db {:dbtype "h2" :dbname "example"})

(defrecord Db []
  Lifecycle
  (start [component]
    (let [ds (jdbc/get-datasource db)]
      (jdbc/execute! ds ["CREATE TABLE IF NOT EXISTS to_do (
                                 id          VARCHAR(40) PRIMARY KEY,
                                 title       VARCHAR(60),
                                 description varchar(255))"])
      (assoc component :datasource ds)))
  (stop [component]
    (assoc component :datasource nil)))

(defn new-Db
  []
  (map->Db {}))