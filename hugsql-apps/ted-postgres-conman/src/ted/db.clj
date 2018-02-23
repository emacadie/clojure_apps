(ns ted.db)

(def db-con
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "//localhost:5432/musicdb"
   :user "music_lover"
   :password "this-is-music"
   :sslmode "require"
   })

