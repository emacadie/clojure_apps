;; Difficulty:Elementary
;; Sets are collections of unique values.

(def __ #{:a :b :c :d})

(= __ (set '(:a :a :b :c :c :c :c :d :d)))

(= __ (clojure.set/union #{:a :b :c} #{:b :c :d}))
