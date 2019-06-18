(ns simply-clojure.chapter17-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter17 :refer :all]
            [simply-clojure.helper    :as helper]))

(test/deftest test-my-max
  (test/testing "Testing my-max"
    (test/is (= 4 (my-max 1 2 3 4)))
    (test/is (= 4 (my-max 1 2 4 3 4)))
    (test/is (= 4 (my-max 4 3 2 1)))
    (test/is (= 4 (my-max 4 3 4 2 1)))
    (test/is (= 5 (my-max 4 3 5 2 1)))))

(test/deftest test-my-max-loop-recur
  (test/testing "Testing my-max-loop-recur"
    (test/is (= 4 (my-max-loop-recur 1 2 3 4)))
    (test/is (= 4 (my-max-loop-recur 1 2 4 3 4)))
    (test/is (= 4 (my-max-loop-recur 4 3 2 1)))
    (test/is (= 4 (my-max-loop-recur 4 3 4 2 1)))
    (test/is (= 5 (my-max-loop-recur 4 3 5 2 1)))))

(test/deftest test-my-max-reduce
  (test/testing "Testing my-max-reduce"
    (test/is (= 4 (my-max-reduce 1 2 3 4)))
    (test/is (= 4 (my-max-reduce 1 2 4 3 4)))
    (test/is (= 4 (my-max-reduce 4 3 2 1)))
    (test/is (= 4 (my-max-reduce 4 3 4 2 1)))
    (test/is (= 5 (my-max-reduce 4 3 5 2 1)))))

(test/deftest test-my-append
  (test/testing "Testing my-append"
    (test/is (= '(yellow sub the word) (my-append '(yellow sub) '(the word))))
    (test/is (= '(yellow sub the word) (my-append '(yellow sub) (seq '(the word)))))
    (test/is (= '(yellow sub the word) (my-append (seq '(yellow sub)) (seq '(the word)))))
    (test/is (= '(yellow sub the word) (my-append (seq '(yellow sub)) '(the word))))
    (test/is (= '(i am the walrus) (my-append '(i am) '(the walrus))))
    (test/is (= '(Rod Argent Chris White) (my-append '(Rod Argent) '(Chris White))))
    ;; from Husk R7RS docs
    (test/is (= '(x y) (my-append '(x) '(y))))
    (test/is (= '(a b c d) (my-append '(a) '(b c d))))
    (test/is (= '(a (b) (c)) (my-append '(a (b)) '((c)))))
    (test/is (= '(a) (my-append '() 'a)))
    (test/is (= '(1 2 3 4 5 '(10 11 12)) (my-append '(1 2 3) '(4 5 '(10 11 12)))))))

(test/deftest test-list-append
  (test/testing "Testing list-append"
    (test/is (= '(1 2 3 4 5 6 7 8 9) (list-append '(1 2 3) '(4 5 6) '(7 8 9))))
    (test/is (= '(1 2 3 4) (list-append '(1 2 3) 4)))
    (test/is (= '(1 2 3 4 5 6 7 8 9) (list-append '(1 2 3) 4 5 6 '(7 8 9))))
    (test/is (= '(1 2 3 4 5 6 7 8 9) (list-append '(1 2 3) '(4 5 6) 7 8 9)))))

(test/deftest test-my-vec-append
  (test/testing "Testing my-vec-append"
    (test/is (= '(yellow sub the word) (my-vec-append '(yellow sub) '(the word))))
    (test/is (= '(yellow sub the word) (my-vec-append '(yellow sub) (seq '(the word)))))
    (test/is (= '(yellow sub the word) (my-vec-append (seq '(yellow sub)) (seq '(the word)))))
    (test/is (= '(yellow sub the word) (my-vec-append (seq '(yellow sub)) '(the word))))
    (test/is (= '(i am the walrus) (my-vec-append '(i am) '(the walrus))))
    (test/is (= '(Rod Argent Chris White) (my-vec-append '(Rod Argent) '(Chris White))))
    ;; from Husk R7RS docs
    (test/is (= '(x y) (my-vec-append '(x) '(y))))
    (test/is (= '(a b c d) (my-vec-append '(a) '(b c d))))
    (test/is (= '(a (b) (c)) (my-vec-append '(a (b)) '((c)))))
    (test/is (= '(a) (my-vec-append '() 'a)))
    (test/is (= '(1 2 3 4 5 '(10 11 12)) (my-vec-append '(1 2 3) '(4 5 '(10 11 12)))))))

(test/deftest test-list-vec-append
  (test/testing "Testing list-vec-append"
    (test/is (= '(1 2 3 4 5 6 7 8 9) (list-vec-append '(1 2 3) '(4 5 6) '(7 8 9))))
    (test/is (= '(1 2 3 4) (list-vec-append '(1 2 3) 4)))
    (test/is (= '(1 2 3 4 5 6 7 8 9) (list-vec-append '(1 2 3) 4 5 6 '(7 8 9))))
    (test/is (= '(1 2 3 4 5 6 7 8 9) (list-vec-append '(1 2 3) '(4 5 6) 7 8 9)))))

(test/deftest test-sentence-via-append
  (test/testing "Testing sentence-via-append"
    (test/is (= '(hello world) (sentence-via-append 'hello 'world)))
    (test/is (= '(hello this is a ren) (sentence-via-append 'hello '(this is a ren))))
    (test/is (= '(what is this this is a ren) (sentence-via-append '(what is this) '(this is a ren))))
    (test/is (= '(what is this re) (sentence-via-append '(what is this) 're)))
    (test/is (= '(what is this re) (sentence-via-append '(what is this) (list 're))))
    (test/is (= '(hello nil) (sentence-via-append 'hello '(this is a '(deep list) bro))))
    (test/is (= '(hello) (sentence-via-append '(this is a '(deep list) bro) 'hello)))
    (test/is (= '(hello) (sentence-via-append 'hello '())))
    (test/is (= '(hello) (sentence-via-append '() 'hello)))
    (test/is (= '(hello 4) (sentence-via-append 'hello 4)))
    (test/is (= '(hello 4) (sentence-via-append 'hello '(4))))))

(test/deftest testing-multi-sentence-append
  (test/testing "Testing multi-sentence-append"
    (test/is (= '(hello there bug guy) (multi-sentence-append 'hello 'there 'bug 'guy)))
    (test/is (= '(hello there bug guy) (multi-sentence-append 'hello 'there '(bug guy))))
    (test/is (= '(hello there bug guy) (multi-sentence-append 'hello '(there bug) 'guy)))
    (test/is (= '(hello there bug guy) (multi-sentence-append '(hello there) 'bug 'guy)))
    (test/is (= '(hello world this is a list okay) (multi-sentence-append 'hello 'world '(this is a list) 'okay '(this is a '(deep) list)) ))
    (test/is (= '(hello world this is a list okay this is fine) (multi-sentence-append 'hello 'world '(this is a list) 'okay '(this is a '(deep) list) '(this is fine))))))

(test/deftest testing-multi-sentence-reduce
  (test/testing "Testing multi-sentence-reduce"
    (test/is (= '(hello there bug guy) (multi-sentence-reduce 'hello 'there 'bug 'guy)))
    (test/is (= '(hello there bug guy) (multi-sentence-reduce 'hello 'there '(bug guy))))
    (test/is (= '(hello there bug guy) (multi-sentence-reduce 'hello '(there bug) 'guy)))
    (test/is (= '(hello there bug guy) (multi-sentence-reduce '(hello there) 'bug 'guy)))
    (test/is (= '(hello world this is a list okay) (multi-sentence-reduce 'hello 'world '(this is a list) 'okay '(this is a '(deep) list)) ))
    (test/is (= '(hello world this is a list okay this is fine) (multi-sentence-reduce 'hello 'world '(this is a list) 'okay '(this is a '(deep) list) '(this is fine))))))

(test/deftest test-new-member
  (test/testing "Testing new-member"
    (test/is (= '(d e f g) (new-member 'd '(a b c d e f g))))
    (test/is (= '(e f g) (new-member 'e '(a b c d e f g))))
    (test/is (= false (new-member 'h '(a b c d e f g))))
    (test/is (= false (new-member 4 '(a b c d e f g))))))

(test/deftest test-my-list-ref
  (test/testing "Testing my-list-ref"
    (test/is (= 'best (my-list-ref '(Lisp is the best language) 3)))
    (test/is (= 'c (my-list-ref '(a b c d) 2)))
    (test/is (= false (my-list-ref '(a b c d) 5)))
    (test/is (= false (my-list-ref '(a b c d) -5)))))

(test/deftest test-my-length
  (test/testing "Testing my-length"
    (test/is (= 3 (count '(a b c)) (my-length '(a b c)))) 
    (test/is (= 3 (count '(a (b) (c d e)))  (my-length '(a (b) (c d e)))))
    (test/is (= 3 (count '(a (b) (c d '(f g h) e)))  (my-length '(a (b) (c d '(f g h) e)))))
    (test/is (= 0 (count '()) (my-length '())))))

(test/deftest test-length-reduce
  (test/testing "Testing length-reduce"
    (test/is (= 3 (count '(a b c)) (length-reduce '(a b c)))) 
    (test/is (= 3 (count '(a (b) (c d e)))  (length-reduce '(a (b) (c d e)))))
    (test/is (= 3 (count '(a (b) (c d '(f g h) e)))  (length-reduce '(a (b) (c d '(f g h) e)))))
    (test/is (= 0 (count '()) (length-reduce '())))))

(test/deftest test-before-in-list?
  (test/testing "Testing before-in-list"
    (test/is (= true (before-in-list? '(back in the ussr) 'in 'ussr)))
    (test/is (= false (before-in-list? '(back in the ussr) 'the 'back)))
    (test/is (= false (before-in-list? '(back in the ussr) 'in 'usa)))))

(test/deftest test-flatten2
  (test/testing "Testing flatten2"
    (test/is (= '(a b c d e f g h i j k) 
                (flatten2 '(((a b) c (d e)) (f g) ((((h))) (i j) k)) '()) ))
    (test/is (= '(the man in the moon ate the potstickers) 
                (flatten2 '(((the man) in ((the) moon)) ate (the) potstickers) '()) ))
    (test/is (= '(the man in the moon ate the potstickers) 
                             (flatten2 '(the man (in ((the) moon)) ate (the) potstickers) '())))))

(test/deftest test-flatten-reduce
  (test/testing "Testing flatten-reduce"
    (test/is (= '(a b c d e f g h i j k) 
                (flatten-reduce '(((a b) c (d e)) (f g) ((((h))) (i j) k)) )))
    (test/is (= '(the man in the moon ate the potstickers) 
                (flatten-reduce '(((the man) in ((the) moon)) ate (the) potstickers) )))
    (test/is (= '(the man in the moon ate the potstickers) 
                             (flatten-reduce '(the man (in ((the) moon)) ate (the) potstickers))))))

(test/deftest test-deep-count
  (test/testing "Testing deep-count"
    (test/is (= 11 (deep-count '(((a b) c (d e)) (f g) ((((h))) (i j) k)))))
    (test/is (= 8 (deep-count '(((the man) in ((the) moon)) ate (the) potstickers))))
    (test/is (= 8 (deep-count '(the man (in ((the) moon)) ate (the) potstickers))))))

(test/deftest test-branch
  (test/testing "Testing branch"
    (test/is (= '(e f) (branch '(3) '((a b) (c d) (e f) (g h))) ) )
    (test/is (= 'f (branch '(3 2) '((a b) (c d) (e f) (g h)))) )
    (test/is (= 'h (branch '(2 3 1 2) '((a b) ((c d) (e f) ((g h) (i j)) k) (l m)))))
    (test/is (= 'h (branch '(2 3 1 2) '((a b) ((c d) (e f) ((g h z) (i j)) k) (l m)))))))

 


