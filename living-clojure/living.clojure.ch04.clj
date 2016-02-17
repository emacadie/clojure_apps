;; page 70
(class "Caterpillar")
(. "caterpillar" toUpperCase)
(.toUpperCase "caterpillar")
;; method object args
(.indexOf "caterpillar" "pillar")

;; page 71
(ns caterpillar.network
  (:import (java.net InetAddress)))
;; / slash for static methods
(InetAddress/getByName "localhost")
(.getHostName (InetAddress/getByName "localhost"))
(def l-host (InetAddress/getByName "localhost"))
(class l-host)
(.getHostName l-host)
;; so she created an object and called getHostName on it
;; fully qualified name
(java.net.InetAddress/getByName "localhost")

(def sb (doto (StringBuffer. "Who ")
          (.append "are ")
          (.append "you?")))
(.toString sb)

;; page 73
(defn who-are-you [input]
  (cond
    (= java.lang.String (class input)) "String - Who are you?"
    (= clojure.lang.Keyword (class input)) "Keyword - Who are you?"
    (= java.lang.Long (class input)) "Number - Who are you?"))
(who-are-you :alice)
(who-are-you "Alice")
(who-are-you 123)
(who-are-you true)

;; page 74
(defmulti who-are-you class)
(defmethod who-are-you java.lang.String [input]
  (str "String - who are you? " input))
(defmethod who-are-you clojure.lang.Keyword [input]
  (str "Keyword - who are you? " input))
(defmethod who-are-you java.lang.Number [input]
  (str "Number - who are you? " input))
(defmethod who-are-you :default [input]
  (str "I don't know - who are you? " input))

(who-are-you :alice)
(who-are-you "Alice")
(who-are-you 123)
(who-are-you true)

;; page 75
(defmulti eat-mushroom (fn [height]
                         (if (< height 3)
                           :grow
                           :shrink )))
(defmethod eat-mushroom :grow [_]
  "eat the right side to grow")
;; page 76
(defmethod eat-mushroom :shrink [_]
  "eat the left side to shrink")
(eat-mushroom 1)
(eat-mushroom 9)

(defprotocol BigMushroom
  (eat-mushroom [this]))

(extend-protocol BigMushroom
  java.lang.String
  (eat-mushroom [this]
    (str (.toUpperCase this) " mmmm tasty!"))

  clojure.lang.Keyword
  (eat-mushroom [this]
    (case this
      :grow "Eat the right side"
      :shrink "Eat the left side"))

  java.lang.Number
  (eat-mushroom [this]
    (if (< this 3)
      "Eat the right side to grow"
      "Eat the left side to shrink")))

;; page 77
(eat-mushroom "Big mushroom")
(eat-mushroom :grow)
(eat-mushroom 1)

(defrecord Mushroom [color height])
(def regular-mushroom (Mushroom. "white with blue lines" "9 inches"))
(class regular-mushroom)
(.color regular-mushroom)
(.height regular-mushroom)
; either way works
(.-color regular-mushroom)
(.-height regular-mushroom)

;; page 78
(defprotocol Edible
  (bite-right-side [this])
  (bite-left-side [this]))
(defrecord WonderlandMushroom [color height]
  Edible
  (bite-right-side [this]
    (str "The " color " bite makes you grow bigger"))
  (bite-left-side [this]
    (str "The " color " bite makes you grow smaller")))
(defrecord RegularMushroom [color height]
  Edible
  (bite-right-side [this]
    (str "The " color " bite tastes bad"))
  (bite-left-side [this]
    (str "The " color " bite tastes bad as well")))

(def alice-mushroom (WonderlandMushroom. "blue dots" "3 inches"))
(def reg-mushroom (RegularMushroom. "brown" "1 inch"))
(bite-right-side alice-mushroom)
(bite-left-side alice-mushroom)
;; page 79
(bite-right-side reg-mushroom)
(bite-left-side reg-mushroom)

(defprotocol Edible
  (bite-right-side [this])
  (bite-left-side [this]))

(deftype WonderlandMushroom []
  Edible
  (bite-right-side [this]
    (str "The bite makes you grow bigger"))
  (bite-left-side [this]
    (str "The bite makes you grow smaller")))
;; page 80
(defrecord RegularMushroom []
  Edible
  (bite-right-side [this]
    (str "The bite tastes bad"))
  (bite-left-side [this]
    (str "The bite tastes bad as well")))
  
(def alice-mushroom (WonderlandMushroom. ))
(def reg-mushroom (RegularMushroom. ))
(bite-right-side alice-mushroom)
(bite-left-side alice-mushroom)
(bite-right-side reg-mushroom)
(bite-left-side reg-mushroom)

;; page 81
(defn bite-right-side [mushroom]
  (if (= (:type mushroom) "wonderland")
    "The bite makes you grow bigger"
    "The bite tastes bad"))

(defn bite-left-side  [mushroom]
  (if (= (:type mushroom) "wonderland")
    "The bite makes you grow smaller"
    "The bite tastes bad"))

(bite-right-side {:type "wonderland"})
(bite-left-side {:type "wonderland"})
(bite-right-side {:type "regular"})
(bite-left-side {:type "regular"})
