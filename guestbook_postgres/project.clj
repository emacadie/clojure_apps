(defproject guestbook "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[bouncer "1.0.1"]
                 ; [com.h2database/h2 "1.4.192"]
                 [org.postgresql/postgresql "42.1.4"]
                 [compojure "1.6.0"]
                 [conman "0.7.4"]
                 [cprop "0.1.11"]
                 [luminus-immutant "0.2.4"]
                 [luminus-migrations "0.4.3"]
                 [luminus-nrepl "0.1.4"]
                 [markdown-clj "1.0.1"]
                 [metosin/ring-http-response "0.9.0"]
                 [mount "0.1.11"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.webjars.bower/tether "1.3.7"]
                 [org.webjars/bootstrap "4.0.0-alpha.3"]
                 [org.webjars/font-awesome "4.6.3"]
                 [org.webjars/jquery "3.1.1"]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [ring-middleware-format "0.7.0"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.1"]
                 [selmer "1.11.3"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main guestbook.core
  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-cprop "1.0.1"]
            [migratus-lein "0.4.3"]
            [lein-immutant "2.1.0"]]

  :profiles
  {:uberjar {:omit-source true
             :aot :all
             :uberjar-name "guestbook.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]

   :project/dev  {:dependencies [[prone "1.1.2"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.5.0"]
                                 [pjstadig/humane-test-output "0.8.1"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.14.0"]]
                  
                  :source-paths ["env/dev/clj" "test/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev { :env { :DATABASE_URL "jdbc:h2:./guestbook.db" 
                         ; :database-url "jdbc:h2:./guestbook.db" 
                         ; :database-url "jdbc:h2:/home/ericm/tmp/clojure/examples/guestbook/guestbook.db.mv.db"
                         ; :database-url "jdbc:postgres://localhost:5433/musicdb?user=music_lover&password=this-is-music"
                         :database-url "postgres://localhost:5433/musicdb?user=music_lover&password=this-is-music"
                         ; :driver-class-name "org.postgresql.Driver"
                         ; :driver "org.postgresql.Driver"
                         :classname "org.postgresql.Driver"
                         ; :driver-class-name "org.postgresql.ds.PGSimpleDataSource"
                         } }
   :profiles/test {}})
