(ns ted.sql
  (:require [hugsql.core :as hugsql]
            [ted.db :refer [db-con]]
            [hikari-cp.core :refer :all]
            [mount.core :refer [defstate]]
            [conman.core :as conman]
            [clojure.java.jdbc]))

;; :quoting did off
;; now try :ansi
(hugsql/def-db-fns "ted/sql/festival.sql" {:quoting :ansi})
(hugsql/def-sqlvec-fns "ted/sql/festival.sql" {:quoting :ansi})

(defn tx-new-user [arg-map]
  "Calls new-user wrapped in a transaction. arg-map needs 
keys :email, :pass and :role"
  (clojure.java.jdbc/with-db-transaction [tx db-con]  
    (new-user tx {:email (:email arg-map)  
                  :pass (:pass arg-map) 
                  :role (:role arg-map)})))

(defn tx-update-rating-type [arg-map]
"Calls update-rating-type wrapped in a transaction. arg-map needs keys :description and :name"
  (clojure.java.jdbc/with-db-transaction [tx db-con]
    (update-rating-type tx {:description (:description arg-map)
                            :name (:name arg-map)})))

(defn tx-delete-user [arg-map]
  (clojure.java.jdbc/with-db-transaction [tx db-con]
    (delete-user tx {:email (:email arg-map)})))

(def pool-spec
  {
   :jdbc-url "postgres://localhost:5433/musicdb?user=music_lover&password=this-is-music"
   :driver-class-name "org.postgresql.Driver"
   })

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "ted/sql/festival.sql")
(mount.core/start)


