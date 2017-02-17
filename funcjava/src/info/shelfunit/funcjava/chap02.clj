(ns info.shelfunit.funcjava.chap02)
(require '[clojure.string :as cs])
; this is in ns:  (:require [clojure.string :as cs]))

(defn transform-list-to-uppercase []
  (println "In transform-list-to-uppercase")
  (def names [ "Brian", "Nate", "Neil", "Raju", "Sara", "Scott" ])
  (println "here is names: " names)
  (def names-upper (map #(cs/upper-case %1 ) names))
  (println "here is names-upper: " names-upper)
  (println "Done with transform-list-to-uppercase"))

(defn get-count-of-names []
  (println "In get-count-of-names")
  (def names [ "Brian", "Nate", "Neil", "Raju", "Sara", "Scott" ])
  (println "Count of names: " (count names))
  (println "Done with get-count-of-names"))

(defn reverse-upper-case []
  (println "In reverse-upper-case []")
  (def names [ "Brian", "Nate", "Neil", "Raju", "Sara", "Scott" ])
  (def first-transform (map #(cs/reverse %1) (map #(cs/upper-case %1) names)))
  (println "first-transform: " first-transform)
  (def second-transform (->> names
                             (map #(cs/upper-case %1))
                             (map #(cs/reverse %1))))
  (println "second-transform: " second-transform)
  (println "Done with reverse-upper-case"))
  
  

(defn -main [& args]
  (println "in info.shelfunit.funcjava.chap02.-main"))
