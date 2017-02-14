(ns funcjava.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println "Here are args: " args)
  (println "What is args: " (class args))
  (println "Here is beginning of args: " (first args))
  (println "Here are the rest of args: " (rest args))
  (println "Here is split args: " (str/split (str args) #" "))
  (println "Number of args: " (count args)))
