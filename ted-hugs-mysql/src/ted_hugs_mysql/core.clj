(ns ted-hugs-mysql.core
  (:require [ted-hugs-mysql.db :refer [db-con]]
            [ted-hugs-mysql.sql :as sql]
            [clojure.pprint :as pprint])

)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
