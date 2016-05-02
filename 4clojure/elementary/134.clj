;; A nil key
;; Difficulty:Elementary
;; Write a function which, given a key and map, returns true iff the map contains an entry with that key and its value is nil.

(def __ (fn [k m] (let [x (get m k)]
                    (if (and (= x nil) (contains? m k))
                      true
                      false))))

(true?  (__ :a {:a nil :b 2}))

(false? (__ :b {:a nil :b 2}))

(false? (__ :c {:a nil :b 2}))
