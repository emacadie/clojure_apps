;; Palindrome Detector
;; Difficulty:Easy
;; Topics:seqs
;; Write a function which returns true if the given sequence is a palindrome.
;; Hint: "racecar" does not equal '(\r \a \c \e \c \a \r)

(def __ (fn [x]
          (= (reverse (into '() x))
             (into '() x))))

(false? (__ '(1 2 3 4 5)))

(true? (__ "racecar"))

(true? (__ [:foo :bar :foo]))

(true? (__ '(1 1 3 3 1 1)))

(false? (__ '(:a :b :c)))


