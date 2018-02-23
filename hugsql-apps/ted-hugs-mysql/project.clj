(defproject ted-hugs-mysql "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [;; [org.clojure/clojure "1.8.0"]
                 [org.clojure/clojure "1.9.0"]
                 [mysql/mysql-connector-java "5.1.44"]
                 [hikari-cp "1.8.3"]
                 [com.layerware/hugsql "0.4.5"]]
  :main ^:skip-aot ted-hugs-mysql.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

