(ns guestbook.db.core
  (:require
    [conman.core :as conman]
    [clojure.java.jdbc :as jdbc]
    [mount.core :refer [defstate]]
    [guestbook.config :refer [env]]))

(defstate ^:dynamic *db*
           :start (conman/connect! {:jdbc-url (env :database-url)})
           :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "sql/queries.sql")

(defn to-date [sql-date]
  (-> sql-date (.getTime) (java.util.Date.)))
  
(extend-protocol jdbc/IResultSetReadColumn
  java.sql.Date
  (result-set-read-column [value metadata index]
    (to-date value)))

(extend-type java.util.Date
  jdbc/ISQLParameter
  (set-parameter [value ^java.sql.PreparedStatement stmt idx]
    (.setTimestamp stmt idx (java.sql.Timestamp. (.getTime value)))))


