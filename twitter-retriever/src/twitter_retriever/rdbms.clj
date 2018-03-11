(ns twitter-retriever.rdbms
  (:require [hugsql.core :as hugsql]
            ; [ted.db :refer [db-con]]
            ; [hikari-cp.core :refer :all]
            [mount.core :refer [defstate]]
            [environ.core :refer [env]]
            [conman.core :as conman]
            [clojure.java.jdbc]))

;; (hugsql/def-db-fns "twitter_retriever/sql/statements.sql" {:quoting :ansi})
;; (hugsql/def-sqlvec-fns "twitter_retriever/sql/statements.sql" {:quoting :ansi})



(def pool-spec
  {
   :jdbc-url (env :database-url) ;; "postgres://localhost:5433/musicdb?user=music_lover&password=this-is-music"
   :driver-class-name (env :db-driver) ;; "org.postgresql.Driver"
   })

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "twitter_retriever/sql/statements.sql")
(mount.core/start)


