(ns simply-clojure.chapter11-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter11 :refer :all]
            [simply-clojure.helper      :as helper]
            ; [clojure.math.numeric-tower :as math-nt]
            )
)

(test/deftest test-count-ums
  (test/testing "Testing count-ums"
    (test/is (= 3 (count-ums "today um we are going to um talk about the combining um method")))))

(test/deftest test-if-count-ums
  (test/testing "Testing if-count-ums"
    (test/is (= 3 (if-count-ums "today um we are going to um talk about the combining um method")))))

(test/deftest test-if-count-ums-with-func
  (test/testing "Testing if-count-ums-with-func"
    (test/is (= 3 (if-count-ums-with-func "today um we are going to um talk about the combining um method")))))

(test/deftest test-phone-unspell
  (test/testing "Testing phone-unspell"
    (test/is (= [7 6 7 2 6 7 6] (phone-unspell "popcorn")))))

(test/deftest test-initials
  (test/testing test-initials
    (test/is (= "I I N S") (initials "If I Needed Someone"))
    (test/is (= "S T O P") (initials "Some Tunisians Only Pause"))))

(test/deftest test-countdown
  (test/testing "Testing countdown"
    (test/is (= "10 9 8 7 6 5 4 3 2 1 BLASTOFF!" (countdown 10)))
    (test/is (= "3 2 1 BLASTOFF!" (countdown 3)))))

(test/deftest test-copies
  (test/testing "Testing copies"
    (test/is (= "SPAM SPAM SPAM SPAM SPAM SPAM SPAM SPAM" (copies 8 "SPAM")))
)
)

