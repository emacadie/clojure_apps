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
;; extra parts: {:name "neck" :size 2}, {:name "left-shoulder" :size 3}
;; {:name "left-upper-arm" :size 3} {:name "chest" :size 10}
;; {:name "back" :size 10} {:name "left-forearm" :size 3}
;; {:name "abdomen" :size 6}  {:name "left-kidney" :size 1}
;; {:name "left-hand" :size 2} {:name "left-knee" :size 2}
;; {:name "left-thigh" :size 4} {:name "left-lower-leg" :size 3}
;; {:name "left-achilles" :size 1} {:name "left-foot" :size 2}

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             ])

;; add a parameter for other parts
(defn matching-part
  [part other]
  {:name (clojure.string/replace (:name part) #"^left-" other)
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

;; make a call to the other part
(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts other]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part 
                                         (matching-part part "right-") 
                                         (matching-part part other)])))
          []
          asym-body-parts))


(symmetrize-body-parts asym-hobbit-body-parts )
(better-symmetrize-body-parts asym-hobbit-body-parts "other-")

;; 6. Create a function that generalizes symmetrize-body-parts and the function you created in Exercise 5. 
;; The new function should take a collection of body parts and the number of matching body parts to add. 
;; If you’re completely new to Lisp languages and functional programming, it probably won’t be obvious how to do this. 
;; If you get stuck, just move on to the next chapter and revisit the problem later.
;; this kind of sort of works
(defn varied-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts other-vec]
  (set (flatten (reduce (fn [final-body-parts part]
                      (into final-body-parts (set [part
                                                   (for [other-part other-vec]
                                                     (matching-part part other-part) )
                                                   ])))
                    []
                    asym-body-parts))))

(varied-symmetrize-body-parts asym-hobbit-body-parts ["up-", "down-", "front-"])
(def part "left-elbow")
(def other-vec ["up-", "down-", "front-"])
(doseq [other-part other-vec]
  (matching-part part other-part) )


