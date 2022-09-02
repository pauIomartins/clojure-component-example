(defproject hellocomponent/todo "0.1.0-SNAPSHOT"
  :description "TODO"
  :url "TODO"
  :license {:name "TODO: Choose a license"
            :url "http://choosealicense.com/"}
  :dependencies [
                 [org.clojure/clojure "1.11.0"]
                 [prismatic/schema "1.1.12"]
                 [com.stuartsierra/component "1.1.0"]
                 [io.pedestal/pedestal.jetty "0.5.10"]
                 [io.pedestal/pedestal.route "0.5.10"]
                 [io.pedestal/pedestal.service "0.5.10"]
                 [org.clojure/data.json "2.4.0"]
                 [org.slf4j/slf4j-nop "1.7.36"]
                 [com.github.seancorfield/next.jdbc "1.2.796"]
                 [com.h2database/h2 "2.1.214"]
                 ]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "1.2.0"]
                                  [com.stuartsierra/component.repl "1.0.0"]]
                   :source-paths ["dev"]}})
