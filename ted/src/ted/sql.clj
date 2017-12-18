(ns ted.sql
  (:require [hugsql.core :as hugsql]
            [ted.db :refer [db-con]]
            ; [hikari-cp.core :refer :all]
            [clojure.java.jdbc]))

;; :quoting did off
;; now try :ansi
(hugsql/def-db-fns "ted/sql/festival.sql" {:quoting :off})
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

(defn tx-new-user-and-rating-type [arg-map]
  (clojure.java.jdbc/with-db-transaction [tx db-con]
    (new-user tx {:email (:email (:user arg-map))
                  :pass (:pass (:user arg-map))
                  :role (:role (:user arg-map))})
    (new-rating-type tx {:name (:name (:rating-type arg-map))
                         :description (:description (:rating-type arg-map))})))

(defn new-user-and-rating-type [arg-map]
  (new-user db-con {:email (:email (:user arg-map))
                    :pass (:pass (:user arg-map))
                    :role (:role (:user arg-map))})
  (new-rating-type db-con {:name (:name (:rating-type arg-map))
                           :description (:description (:rating-type arg-map))})
  )

(comment
(def datasource-options {; :auto-commit        true
                         ; :read-only          false
                         ; :connection-timeout 30000
                         ; :validation-timeout 5000
                         ; :idle-timeout       600000
                         ; :max-lifetime       1800000
                         ; :minimum-idle       10
                         ; :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            "postgresql"
                         ; :jdbc-url           "jdbc:postgresql://localhost:5433/musicdb"
                         ; :jdbc-url "jdbc:postgresql://localhost:5433/musicdb?user=music_lover&password=this-is-music"
                         :username           "music_lover"
                         :password           "this-is-music"
                         :database-name      "musicdb"
                         :server-name        "localhost"
                         :port-number        5433
                         ; :register-mbeans    false
                         ; :driver-class-name "org.postgresql.ds.PGSimpleDataSource"
                         ; :class-name "org.postgresql.ds.PGSimpleDataSource"
                         ; :classname "org.postgresql.Driver"
                         ; :subprotocol "postgresql"
                         ; :subname "//localhost:5433/musicdb"
                         ; :dbtype "postgres"
                         ; :dbname "musicdb"
                         
;:jdbc-url 	Yes¹ 	None 	This property sets the JDBC connection URL. Please note the h2 adapter expects :url instead of :jdbc-url.
;:driver-class-name 	No 	None 	This property sets the JDBC driver class.
;:adapter 	Yes¹ 	None 	This property sets the database adapter. Please check Adapters and corresponding datasource class names for the full list of supported adapters and their datasource class names.
;:connection-test-query 	No 	None 	If your driver supports JDBC4 we strongly recommend not setting this property. This is for "legacy" databases that do not support the JDBC4 Connection.isValid() API. This is the query that will be executed just before a connection is given to you from the pool to validate that the connection to the database is still alive.
;:leak-detection-threshold 	No 	0 	This property controls the amount of time that a connection can be out of the pool before a message is logged indicating a possible connection leak. A value of 0 means leak detection is disabled, minimum accepted value is 2000 (ms). ( ps: it's rarely needed option, use only for debugging )
;:register-mbeans 	No 	false 	This property register mbeans which can be used in jmx to monitor hikari-cp.
;:connection-init-sql 	No 	None 	This property sets a SQL statement that will be executed after every new connection creation before adding it to the pool.
;:datasource 	No 	None 	This property allows you to directly set the instance of the DataSource to be wrapped by the pool.
;:datasource-classname 	No 	None 	This is the name of the DataSource class provided by the JDBC driver.
;:metric-registry 	No 	None 	This is an instance of a Dropwizard metrics MetricsRegistry.
;:health-check-registry 	No 	None 	This is an instance of a Dropwizard metrics HealthCheckRegistry.
                         ; :classname "org.postgresql.Driver"
                         ; :subprotocol "postgresql"
                         ; :subname "//localhost:5433/musicdb"
})

(def datasource
  (make-datasource datasource-options))

;:classname "org.postgresql.Driver"
;   :subprotocol "postgresql"
;   :subname "//localhost:5433/musicdb"
;   :user "music_lover"
;   :password "this-is-music"
;   :sslmode "require"

) ;; end comment







