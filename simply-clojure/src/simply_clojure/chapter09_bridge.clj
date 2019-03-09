(ns simply-clojure.chapter09-bridge
  (:require [clojure.string :as str]
            [simply-clojure.helper :as helper]))

;; the bridge program after chapter 9

;; Write a procedure card-val that takes a single card as its argument and returns the value of that card.
;;  Each ace in the hand is worth four points, each king is worth three points, each queen two points, and each jack one. 
;; The other cards, twos through tens, have no point value.
(defn card-symbol-to-str [card-sym]
  (str/lower-case (subs (str card-sym) 1 (.length (str card-sym)))))

(defn card-val [card]
  (let [card-string (card-symbol-to-str card)
        last-card-char (helper/last-string card-string)]
    (case last-card-char
      "a" 4
      "k" 3
      "q" 2
      "j" 1
      0)))

;; Write a procedure high-card-points that takes a hand as its argument and returns the total number of points from high cards in the hand. 
;; (This procedure does not count distribution points.)
(defn high-card-points [card-list]
  (reduce + (map card-val card-list)))

; Write a procedure count-suit that takes a suit and a hand as arguments and returns the number of cards in the hand with the given suit.
(defn count-suit [suit card-list]
  (count (filter #(= suit %1)
                 (map #(helper/first-string (card-symbol-to-str %1))
                      card-list))))

;; Write a procedure suit-counts that takes a hand as its argument and returns a sentence containing the number of spades, 
;; the number of hearts, the number of clubs, and the number of diamonds in the hand.
(defn suit-counts [suit-sentence]
  (map #(count-suit %1 suit-sentence) ["s" "h" "c" "d"]))

; Write suit-dist-points that takes a number as its argument, 
; interpreting it as the number of cards in a suit. 
; The procedure should return the number of distribution points your hand gets for having that number of cards in a particular suit.
; from text: A bridge hand might also have some "distribution" points, which are points having to do with the distribution of the thirteen cards among the four suits. 
; If your hand has only two cards of a particular suit, then it is worth an extra point. 
; If it has a "singleton," only one card of a particular suit, that's worth two extra points. 
; A "void," no cards in a particular suit, is worth three points.
;; probably a way to do this with maps, but the book doesn't cover maps
(defn suit-dist-points [num-suit] 
  (case num-suit
    0 3
    1 2
    2 1
    0))

;; Write hand-dist-points, which takes a hand as its argument and returns the number of distribution points the hand is worth.
(defn hand-dist-points [hand]
  (reduce + (map suit-dist-points (suit-counts hand))))

;; Write a procedure bridge-val that takes a hand as its argument and returns the total number of points that the hand is worth.
(defn bridge-val [hand]
  (+ (high-card-points hand) (hand-dist-points hand)))

