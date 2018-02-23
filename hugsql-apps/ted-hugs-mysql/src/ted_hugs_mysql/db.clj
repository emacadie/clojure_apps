(ns ted-hugs-mysql.db)

(def db-con
  {:classname "com.mysql.jdbc.Driver"
   :subprotocol "mysql"
   :subname "//localhost:3306/musicdb"
   :user "music_lover"
   :password "this-is-music"
   ;; :sslmode "require"
   })

