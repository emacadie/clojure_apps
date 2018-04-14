(ns brave_clojure.chapter04)

;; reduce doing map with map (from the book)
(reduce (fn [new-map [key val]]
          ; (println "here is new-map: ",new-map, ", key: ", key, ", val: ", val)
          (assoc new-map key (inc val)))
        {}
        {:max 30 :min 10 :avg 22 :temp 44})

;; from the book: filter with reduce
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}
        {:human 4.1
         :critter 3.9})

;; reduce doing map with a vector
;; based on this from cheat-sheet:
;; (map #(str "Hello " % "!" ) ["Ford" "Arthur" "Tricia"])
(reduce (fn [name-vec name]
          ; (println "Here is name-vec: ", name-vec, "; name: ", name)
          (conj name-vec  (str "Hello, " name)))
        []
        ["Ford" "Arthur" "Tricia"])

;; he uses map to get values from one key from a vector
(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Spider-Man" :real "Peter Parker"}
   {:alias "Santa" :real "Your mom"}
   {:alias "Easter Bunny" :real "Your dad"}])

(map :real identities)
;; gives: ("Bruce Wayne" "Peter Parker" "Your mom" "Your dad")
;; better than doseq

(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))

(stats [3 4 10])
; => (17 3 17/3)

(stats [80 1 44 13 6])
; => (144 5 144/5)
;; good way to do calculations on a vector of numbers

;; take-while and drop-while are higher-order functions

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 4)
            (drop-while #(< (:month %) 2) food-journal))

;; book says that looks inefficient, but filter will go through the entire thing
;; so if you know you can skip part of it, use take-while and drop-while

;; plug these in and see what you get
(concat [1 2 3 4] [5])
(conj [1 2 3 4] 5)
(vector (concat [1 2 3 4] [5]))
(vec (concat [1 2 3 4] [5]))
(vector 1 2 3)
(vec 1 2 3)
(cons 0 [1 2 3 4]) ;; puts 0 at front, returns list
(vec (cons 0 [1 2 3 4])) ;; puts 0 at front, converts to vector
(conj  [1 2 3 4] 0) ;; puts 0 at the end
(cons 0 '(1 2 3 4))
(conj '(1 2 3 4) 0)
;; so cons for list, conj for vectors
;; cons, arg is in front and added to front
;; conj, arg is in back, and added to back

