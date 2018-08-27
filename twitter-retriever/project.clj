(defproject twitter-retriever "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [twitter-api "1.8.0"]
                 [clojurewerkz/propertied "1.3.0"]
                 ; [org.postgresql/postgresql "42.1.4"]
                 [org.postgresql/postgresql "42.2.4"]
                 [conman "0.8.2"]
                 [mount "0.1.12"]
                 [environ "1.1.0"]
                 [clj-time "0.14.4"]
                 [clj-http "3.8.0"]
                 ]
   :plugins [; [lein-autoreload "0.1.1"]
             [lein-environ "1.1.0"]
             ]
  ; directories use underscores, namespaces use hyphens
  ; :main ^:skip-aot twitter-retriever.core
  :main ^:skip-aot twitter-retriever.work
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

