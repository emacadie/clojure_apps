(defproject fwpd "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot fwpd.core
  :target-path "target/%s"
  ; :repl-options {:init (require 'clojure.tools.namespace.repl)}
  :profiles {:uberjar {:aot :all}
             :dev [:project/dev ] ; :profiles/dev
             :project/dev  {:dependencies [; [prone "1.1.2"]
                                           [org.clojure/tools.namespace "0.2.11"]
                                 ;[ring/ring-mock "0.3.0"]
                                 ;[ring/ring-devel "1.5.0"]
                                 ;[pjstadig/humane-test-output "0.8.1"]
                                           ]
                  ;:plugins      [[com.jakemccrary/lein-test-refresh "0.14.0"]]
                  
                  ;:source-paths ["env/dev/clj" "test/clj"]
                  ;:resource-paths ["env/dev/resources"]
                  ;:repl-options {:init-ns user}
                            
                            }})
