(ns simply-clojure.chapter15-test
  (:require [clojure.test :as test]
            [simply-clojure.chapter15 :refer :all]
            [simply-clojure.helper    :as helper]))

(test/deftest test-closest-power-of-2
  (test/testing "Testing closest-power-of-2"
    (test/is (= 4 (closest-power-of-2 22 0)))))

(test/deftest test-get-all-powers-of-2
  (test/testing "Test get-all-powers-of-2"
    (test/is (= [1 6] (get-all-powers-of-2 33)))))

(test/deftest test-to-binary
  (test/testing "Test to-binary"
    (test/is (= [1 0 0 1]   (to-binary-r 9)))
    (test/is (= [1 0 1 1 1] (to-binary-r 23)))))

(test/deftest test-palindrome-r
  (test/testing "Testing palindrome-r"
    (test/is (= true  (palindrome-r "flee to me remote elf")))
    (test/is (= false (palindrome-r "flee to ma remote elf")))
    (test/is (= false (palindrome-r "flee to me remote control")))))

(test/deftest test-substrings-r
  (test/testing "Testing substrings-r"
    (test/is (= ["h" "he" "hel" "hell" "hello"] (substrings-r "hello")))
    (test/is (= ["t" "th" "the" "ther" "there"] (substrings-r "there")))))

(test/deftest test-sub-whole-word-r
  (test/testing "Testing sub-whole-word-r"
    (test/is (= '("t" "th" "the" "ther" "there" "h" "he" "her" "here" "e" "er" "ere" "r" "re" "e") (sub-whole-word-r "there")))
    (test/is (= '("h" "he" "hel" "hell" "hello" "e" "el" "ell" "ello" "l" "ll" "llo" "l" "lo" "o") (sub-whole-word-r "hello")))
    (test/is (= '("m" "mi" "mis" "miss" "missi" "missis" "mississ" "mississi" "mississip" "mississipp" 
                  "mississippi" "i" "is" "iss" "issi" "issis" "ississ" "ississi" "ississip" "ississipp" 
                  "ississippi" "s" "ss" "ssi" "ssis" "ssiss" "ssissi" "ssissip" "ssissipp" "ssissippi" 
                  "s" "si" "sis" "siss" "sissi" "sissip" "sissipp" "sissippi" "i" "is" "iss" "issi" "issip" 
                  "issipp" "issippi" "s" "ss" "ssi" "ssip" "ssipp" "ssippi" "s" "si" "sip" "sipp" "sippi" "i" 
                  "ip" "ipp" "ippi" "p" "pp" "ppi" "p" "pi" "i")
                (sub-whole-word-r "mississippi")))))




