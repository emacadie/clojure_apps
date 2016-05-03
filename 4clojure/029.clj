;; Get the Caps
;; Difficulty:Easy
;; Topics:strings
;; Write a function which takes a string and returns a new string containing only the capital letters.
;; other solutions are better
(def __ (fn [x]
          (let [y (clojure.string/replace
                   (print-str (re-seq #"\p{Upper}" x)) #"[\s()]" "")]
            (if (= y "nil")
              '()
              y))) )

(= (__ "HeLlO, WoRlD!") "HLOWRD")

(empty? (__ "nothing"))

(= (__ "$#A(*&987Zf") "AZ")


