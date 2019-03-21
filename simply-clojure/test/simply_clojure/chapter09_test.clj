(ns simply-clojure.chapter09-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter09 :refer :all]
            [simply-clojure.helper      :as helper]
            [clojure.math.numeric-tower :as math-nt]))

(test/deftest test-prepend-every
  (test/testing "Testing prepend-every"
    (test/is (= "she said she said" (prepend-every "s" ["he" "aid" "he" "aid"])))
    (test/is (= "antidote antipasto antigone antibody" (prepend-every "anti" ["dote" "pasto" "gone" "body"])))))

(test/deftest test-sentence-version
  (test/testing "Test sentence-version"
    (test/is (= '(64 4 16 36) ((sentence-version helper/square) [8 2 4 6])))
    (test/is (= '("i" "i" "f") ((sentence-version helper/first-word) (helper/split-string-to-words "if i fell"))))))

(test/deftest test-letterwords
  (test/testing "Test letterwords"
    (test/is (= '("got" "to" "you" "into") (letterwords "o" "got to get you into my life")))))

(test/deftest test-hang
  (test/testing "Test hang"
    (test/is (= '("_" "o" "t" "_" "t" "i" "_" "_" "e" "_") (hang "potsticker" "etaoi")))))

(test/deftest test-common-words
  (test/testing "Test common-words"
    (test/is (= '("good" "this" "thing") (common-words "this is good thing" "what good can this little thing do")))))

(test/deftest test-appearances
  (test/testing "Testing appearances"
    (test/is (= 2 (appearances-string "e" "feelings")))
    (test/is (= 2 (appearances-string "hello" "hello goodbye hello again goodbye again nothing again")))))

(test/deftest test-my-appearances
  (test/testing "Testing my-appearances"
    (test/is (= 2 (my-appearances {:type :word, :part "e" :whole "feelings"})))
    (test/is (= 2 (my-appearances {:type :sentence :part "hello" :whole "hello goodbye hello again goodbye again nothing again" })))
    (test/is (= 3 (my-appearances {:type :vector :part 2 :whole [1 2 3 2 4 2 5]})))))

(test/deftest test-unabbrev
  (test/testing "Testing unabbrev"
    (test/is (= "John Bill Wayne Fred Joey" (unabbrev "John 1 Wayne Fred 4" "Bill Hank Kermit Joey")))
    (test/is (= "I want to tell you" (unabbrev "I 3 4 tell 2" "Do you want to know a secret?")))))

(test/deftest test-first-last
  (test/testing "Testing first-last"
    (test/is (= ["ohio" "alabama" "alaska"] (first-last ["california" "ohio" "nebraska" "alabama" "alaska" "delaware"])))))

(test/deftest test-compose
  (test/testing "Testing compose"
    (let [inc-square (compose inc helper/square)]
      (test/is (= 17 (inc-square 4))))))

(test/deftest test-substitute
  (test/testing "Testing substitute"
    (test/is (= "She loves you yeah yeah yeah" (substitute "yeah" "maybe" "She loves you maybe maybe maybe")))))

(test/deftest test-type-check
  (test/testing "Testing type-check"
    (let [safe-square (type-check helper/square number?)]
      (test/is (= false (safe-square "D")))
      (test/is (= false (safe-square :4)))
      (test/is (= 16 (safe-square 4)))
      (test/is (= 16.0 (safe-square 4.0))))
    (let [safe-sqrt (type-check math-nt/sqrt number?)]
      (test/is (= false (safe-sqrt :hello)))
      (test/is (= 4 (safe-sqrt 16))))))

(test/deftest test-aplize
  (test/testing "Testing aplize"
    (let [apl-sqrt (aplize math-nt/sqrt)]
      (test/is (= 6 (apl-sqrt 36)))
      (test/is (= '(1 10 5 4) (apl-sqrt '(1 100 25 16))))
      (test/is (= '(1 10 5 4) (apl-sqrt [1 100 25 16])))
      (test/is (= '(1 10 5 4) (apl-sqrt #{1 100 25 16}))))))


(test/deftest test-my-keep
  (test/testing "testing my-keep"
    (test/is (= '(2 4 6) (my-keep even? '(1 2 3 4 5 6 7))))
    (test/is (= '(2 4 6) (my-keep even? [1 2 3 4 5 6 7])))))

