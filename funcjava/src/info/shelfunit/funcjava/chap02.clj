(ns info.shelfunit.funcjava.chap02)
(require '[clojure.string :as cs])
; this is in ns:  (:require [clojure.string :as cs]))

(def friends [ "Brian", "Nate", "Neil", "Raju", "Sara", "Scott" ])
(def editors [ "Brian", "Jackie", "John", "Mike" ])
(def comrades [ "Kate", "Ken", "Nick", "Paula", "Zack" ])

(defn transform-list-to-uppercase []
  (println "In transform-list-to-uppercase")
  (println "here is friends: " friends)
  (def friends-upper (map #(cs/upper-case %1 ) friends))
  (println "here is friends-upper: " friends-upper)
  (println "Done with transform-list-to-uppercase"))

(defn get-count-of-friends []
  (println "In get-count-of-friends")
  (println "Count of friends: " (count friends))
  (println "Done with get-count-of-friends"))

(defn reverse-upper-case []
  (println "In reverse-upper-case []")
  (def first-transform (map #(cs/reverse %1) (map #(cs/upper-case %1) friends)))
  (println "first-transform: " first-transform)
  (def second-transform (->> friends
                             (map #(cs/upper-case %1))
                             (map #(cs/reverse %1))))
  (println "second-transform: " second-transform)
  (println "Done with reverse-upper-case"))
  
(defn reuse-lambda
  ([letter]
   (println "in reuse-lambda with arg " letter)
   (println "Here is friends: " friends "; Here is joined friends: " (cs/join ", " friends) )
   (println "friends that start with letter: " (filter #(cs/starts-with? %1 letter) friends))
   (println "Here is editors: " editors "; Here is joined editors: " (cs/join ", " editors) )
   (println "editors that start with letter: " (filter #(cs/starts-with? %1 letter) editors))
   (println "Here is comrades: " comrades "; Here is joined comrades: " (cs/join ", " comrades) )
   (println "comrades that start with letter: " (filter #(cs/starts-with? %1 letter) comrades)))
  ([] (reuse-lambda "N"))) 

(defn -main [& args]
  (println "in info.shelfunit.funcjava.chap02.-main"))
