(ns twitter-retriever.core)

(defn print-stuff []
  (println "Here is the first line")
  (println "here is the second line")
  (println "here is the third line"))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  (println "Let's write some clojure")
  (println "Here are your args: ", args)
  (println "args is a ", (class args))
  (println "Let's make the args a map")
  (def arg-map (apply hash-map args))
  (println "here is the map:", arg-map)
  (println "Here are the keys: ", (keys arg-map))
  (println "here is map value for filepath: ", (:filepath arg-map), ", and it's a ", (class (:filepath arg-map))))

