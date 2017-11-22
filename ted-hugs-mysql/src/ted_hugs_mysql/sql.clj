(ns ted-hugs-mysql.sql
  (:require [hugsql.core :as hugsql]
            [ted-hugs-mysql.db :refer [db-con]]
            [clojure.java.jdbc]))

(hugsql/def-db-fns "ted_hugs_mysql/sql/festival.sql" {:quoting :ansi})
(hugsql/def-sqlvec-fns "ted_hugs_mysql/sql/festival.sql" {:quoting :ansi})

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


