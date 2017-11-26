(ns ted-hugs-mysql.sql
  (:require [hugsql.core :as hugsql]
            [ted-hugs-mysql.db :refer [db-con]]
            [hikari-cp.core :refer :all]
            [clojure.java.jdbc]))

;; calling these functions in this namespace will give all the
;; resulting functions this namespace as well
(hugsql/def-db-fns "ted_hugs_mysql/sql/festival.sql" {:quoting :ansi})
(hugsql/def-sqlvec-fns "ted_hugs_mysql/sql/festival.sql" {:quoting :ansi})

;; functions using HugSQL that wrap them in transactions

(defn tx-new-user [arg-map]
  "Calls new-user wrapped in a transaction. arg-map needs 
keys :email, :pass and :role"
  (clojure.java.jdbc/with-db-transaction [tx db-con]  
    (new-user tx {:email (:email arg-map)  
                  :pass (:pass arg-map) 
                  :role (:role arg-map)})))

(defn tx-update-rating-type [arg-map]
"Calls update-rating-type wrapped in a transaction. arg-map needs keys :description and :name
The name is used to update the description"
  (clojure.java.jdbc/with-db-transaction [tx db-con]
    (update-rating-type tx {:description (:description arg-map)
                    :name (:name arg-map) })))

(defn tx-new-rating-type [arg-map]
"Calls update-rating-type wrapped in a transaction. arg-map needs keys :description and :name"
  (clojure.java.jdbc/with-db-transaction [tx db-con]
    (new-rating-type tx {:description (:description arg-map)
                            :name (:name arg-map)})))

(defn tx-delete-user [arg-map]
  (clojure.java.jdbc/with-db-transaction [tx db-con]
    (delete-user tx {:email (:email arg-map)})))

;; fields for Hikari Connection Pool
(def datasource-options {:auto-commit        true
                         :read-only          false
                         :connection-timeout 30000
                         :validation-timeout 5000
                         :idle-timeout       600000
                         :max-lifetime       1800000
                         :minimum-idle       10
                         :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            "mysql"
                         :jdbc-url           "mysql://music_lover:this-is-music@localhost:3306/musicdb"
                                        ; "jdbc:mysql://localhost:3306/musicdb" ; "//localhost:3306/musicdb"
                         :username           "music_lover"
                         :password           "this-is-music"
                         :database-name      "musicdb"
                         :server-name        "localhost"
                         :port-number        3306
                         :register-mbeans    false})

; :classname "com.mysql.jdbc.Driver"
; :subprotocol "mysql"

(def datasource
  (make-datasource datasource-options))



