(ns dev
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application.

  Call `(reset)` to reload modified code and (re)start the system.

  The system under development is `system`, referred from
  `com.stuartsierra.component.repl/system`.

  See also https://github.com/stuartsierra/component.repl"
  #_{:clj-kondo/ignore [:unused-namespace :unused-referred-var]}
  (:require
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer [javadoc]]
   [clojure.pprint :refer [pprint]]
   [clojure.reflect :refer [reflect]]
   [clojure.repl :refer [apropos dir doc find-doc pst source]]
   [clojure.set :as set]
   [clojure.string :as string]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer [refresh refresh-all clear]]
   [com.stuartsierra.component :as component]
   [com.stuartsierra.component.repl :refer [reset set-init start stop system]]
   [hellocomponent.system :as system]
   [hellocomponent.components.http-server :as http-server]
   [hellocomponent.hello-component]
   [io.pedestal.test :as pedestal.test]))

;; Do not try to load source code from 'resources' directory
(clojure.tools.namespace.repl/set-refresh-dirs "dev" "src" "test")

(defn dev-system
  "Constructs a system map suitable for interactive development."
  []
  (system/new-system))

(set-init (fn [_] (dev-system)))

(defn run-tests
  "Runs all tests in the project."
  []
  (test/run-all-tests #"^(demo|shared)\..+-test$"))

(defn response-for
  "Returns the response from the Pedestal HTTP service.
   Arguments are like `io.pedestal.test/response-for` but omit the service-fn."
  [verb url & options]
  (apply pedestal.test/response-for
         (http-server/get-service-fn (:http-server system))
         verb
         url
         options))
