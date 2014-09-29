(ns monkeyballs.greet
  (:gen-class)
  (:require [monkeyballs.log :as log]))

(defn -main [& message]
  (log/log "GREET" (clojure.string/join " " message)))