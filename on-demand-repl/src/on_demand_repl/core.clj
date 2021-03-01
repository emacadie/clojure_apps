(ns on-demand-repl.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(println "cleanliness is next to godliness")
(println "Hello, this is a new line")

(require  '[clojure.core.reducers :as r])

(require  '[clojure.spec.alpha :as s])

(defn p-even [x]
  (println "x is " x ", and is it even? " (even? x))
  (even? x))



(s/def ::username string?)
(s/def ::password string?)

(s/def ::last-login number?)
(s/def ::comment string?)
(s/def ::age number?)

(s/def ::user
  (s/keys
   :req [::username ::password]
   :opt [::comment ::last-login]))

(s/def :user2
  (s/keys
   :req [::username ::password]
   :opt [::comment ::last-login]))

(println
 (s/valid?
  ::user
  {::username   "rich"
   ::password   "zegure"
   ::comment    "this is a user"
   ::last-login 11000
   ::age        26}))

(println
 (s/valid?
  ::user
  {:username   "rich"
   :password   "zegure"
   :comment    "this is a user"
   :last-login 11000
   :age        26}))

(defn inc-p [x]
  (println "x is " x " (inc x) is " (inc x))
  (inc x))

(defn +-with-print [x y]
  (println "x is " x "y is " y  "(+ x y) is " (+ x y))
  (+ x y))




