(ns brave-clojure.chapter03)

;; 1. Use the str, vector, list, hash-map, and hash-set functions.
;; done elsewhere

;; 2. Write a function that takes a number and adds 100 to it.
(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))

(def inc100 (inc-maker 100))

;; 3. Write a function, dec-maker, that works exactly like the function inc-maker except with subtraction: 
(defn dec-maker
  "Create a custom incrementor"
  [dec-by]
  #(- % dec-by))

(def dec9 (dec-maker 9))
(dec9 10)

;; 4. Write a function, mapset, that works like map except the return value is a set
(defn mapset [func coll]
  (set (map func coll)))

(mapset inc [1 1 2 2])

;; 5. Create a function that's similar to symmetrize-body-parts except that it has to work with weird space aliens with radial symmetry. 
;; Instead of two eyes, arms, legs, and so on, they have five.
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts 
         final-body-parts []]
    (if (empty? remaining-asym-parts) 
      final-body-parts
      (let [[part & remaining] remaining-asym-parts] 
        (recur remaining 
               (into final-body-parts
                     (set [part (matching-part part)])))))))


