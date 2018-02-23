(defproject ted-mysql-migrations "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [migratus "0.9.9"]
                 [mysql/mysql-connector-java "5.1.44"]]
  
  :plugins [[migratus-lein "0.5.2"]]
  :migratus {:store :database
           :migration-dir "migrations"
           :db {:classname "com.mysql.jdbc.Driver"
                :subprotocol "mysql"
                ;; I think the "jdbc:mysql://" is kind of implied
                ;; :subname "localhost/musicdb" 
                ;; "localhost:3306/musicdb" 
                ;; "jdbc:mysql://localhost:3306/musicdb"  ;; "//localhost/migratus"
                :subname "//localhost:3306/musicdb"
                :user "music_lover"
                :password "this-is-music"}}
)

