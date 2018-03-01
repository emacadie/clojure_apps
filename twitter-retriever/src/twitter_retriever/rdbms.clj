(ns twitter-retriever.rdbms
  (:require [hugsql.core :as hugsql]
            [ted.db :refer [db-con]]
            ; [hikari-cp.core :refer :all]
            [mount.core :refer [defstate]]
            [conman.core :as conman]
            [clojure.java.jdbc]))

;; (hugsql/def-db-fns "twitter_retriever/sql/statements.sql" {:quoting :ansi})
;; (hugsql/def-sqlvec-fns "twitter_retriever/sql/statements.sql" {:quoting :ansi})



(def pool-spec
  {
   :jdbc-url "postgres://localhost:5432/musicdb?user=music_lover&password=this-is-music"
   :driver-class-name "org.postgresql.Driver"
   })

(defstate ^:dynamic *db*
          :start (conman/connect! pool-spec)
          :stop (conman/disconnect! *db*))

(conman/bind-connection *db* "twitter_retriever/sql/statements.sql")
(mount.core/start)


