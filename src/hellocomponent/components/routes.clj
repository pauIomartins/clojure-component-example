(ns hellocomponent.components.routes
  (:require [clojure.set :as set]
            [io.pedestal.http.route :as route]
            [io.pedestal.interceptor :refer [interceptor]])
  (:import (com.stuartsierra.component Lifecycle)))

(def ^:private all-controllers [:hello-controller
                                :greetings-controller
                                :todo-controller])

(defn- inject-controller
  "Returns a Pedestal Interceptor that injects the component into the
  request context. Use `controller` to retrieve it."
  [controller-component]
  (interceptor
    {:name  ::inject-component
     :enter (fn [context]
              (assoc-in context [:request ::controller] controller-component))}))

(defn- prepend-interceptor
  "Adds an Interceptor to the beginning of the interceptor chain associated with a Pedestal route.
  The route should already be in Pedestal's verbose form, after calling `expand-routes`."
  [expanded-route interceptor]
  (update expanded-route :interceptors (fn [interceptors]
                                         (vec (cons interceptor interceptors)))))

(defn- set-interceptor-on-route
  [route component]
  (prepend-interceptor route (inject-controller component)))

(defn- all-routes-with-interceptor
  [routes-component]
  (->> all-controllers
       (map #(let [controller      (get routes-component %)
                   routes          (get controller :routes)
                   expanded-routes (route/expand-routes routes)]
               (map (fn [route]
                      (set-interceptor-on-route route controller))
                    expanded-routes)))
       (reduce set/union)))

(defrecord Routes []
  Lifecycle
  (start [component]
    (assoc component :all-routes (all-routes-with-interceptor component)))
  (stop [component]
    (assoc component :all-routes nil)))

(defn new-routes []
  (map->Routes {}))