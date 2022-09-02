(ns hellocomponent.components.http-server
  (:require [io.pedestal.http :as http])
  (:import (com.stuartsierra.component Lifecycle)))

(defrecord HTTPServer []
  Lifecycle
  (start [component]
    (assoc component :service
                     (-> {::http/routes (-> component :routes :all-routes)
                          ::http/type   :jetty
                          ::http/port   8080
                          ::http/join?  false}
                         http/default-interceptors
                         http/create-server
                         http/start)))
  (stop [component]
    (when (::http/stop-fn (:service component))
      (http/stop (:service component)))
    component))

(defn get-service-fn
  "Returns the Pedestal service function for use with `io.pedestal.test/response-for`."
  [http-server-component]
  (some-> http-server-component
          :service
          ::http/service-fn))

(defn new-http-server
  []
  (map->HTTPServer {}))