(ns brave_clojure.chapter05)

;; sum with recur
(defn sum 
  ([vals]
   (sum vals 0))
  ([vals accumulating-total]
   (println "sum w/recur and vals: ", vals, " and accumulating-total: ", accumulating-total)
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))

(sum [11 5 2])

;; sum with loop-recur
(defn sum-loop 
  ([vals]
   (sum-loop vals 0))
  ([vals accumulating-total]
   (println "sum-loop w/recur and vals: ", vals, " and accumulating-total: ", accumulating-total)
   (loop [loop-vals  vals
          loop-accum accumulating-total]
     (println "In loop with loop-vals: ", loop-vals, " and loop-accum: ", loop-accum)
       (if (empty? loop-vals)
         loop-accum
         (recur (rest loop-vals) (+ (first loop-vals) loop-accum))))))

;; exercises
;; 1. You used (comp :intelligence :attributes) to create a function that returns a character’s intelligence.
;; Create a new function, attr, that you can call like (attr :intelligence) and that does the same thing.
;; I don't think you can do this without also sending the map as an argument

(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(def character2
  {:name "Grandma Boozyface"
   :attributes {:intelligence 5
                :strength 2
                :dexterity 2}})

(def c-int (comp :intelligence :attributes))

(c-int character)
;; returns 10
(c-int character2)
;; returns 5
(defn attr [the-attr the-map]
  ((comp the-attr :attributes) the-map))
(attr :intelligence character)
; returns 10
(attr :dexterity character2)
; returns 2

;; 2. Implement the comp function.
(comment (defn ekm-comp [f1 f2 & args]
   (f1 (apply f2 args))))
;; I am bailing on this one
;; see https://github.com/dancrumb/clojure-brave-and-true for one that works
;; I just looked at the source of comp. It uses "apply", "reduce", and there is a recursive call to "comp"


;; 3. Implement the assoc-in function. 
;; Hint: use the assoc function and define its parameters as [m [k & ks] v]
(defn ekm-assoc-in [m [k & ks] v])



