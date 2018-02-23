 (ns ted.core
     (:require [ted.db :refer [db-con]]
               [ted.sql :as sql]
               [clojure.pprint :as pprint])
     ;; (:gen-class)
     )


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
