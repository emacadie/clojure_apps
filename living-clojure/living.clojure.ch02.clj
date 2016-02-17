;; page 34
(let [bottle "drinkme"]
  (cond
    (= bottle "poison") "don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"))

(let [x 5]
  (cond
    (> x 10) "Bigger than 10"
    (> x 4) "bigger than 4"
    (> x 3) "bigger than 3"))

(let [x 5]
  (cond
    (> x 3) "bigger than 3"
    (> x 10) "Bigger than 10"
    (> x 4) "bigger than 4"))

(let [x 1]
  (cond
    (> x 10) "bigger than 10"
    (> x 4) "bigger than 4"
    (> x 3) "bigger than 3"))

(let [bottle "mystery"]
  (cond
    (= bottle "poison") "Don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"
    :else "unknown"))

;; page 35
(let [bottle "mystery"]
  (cond
    (= bottle "poison") "Don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"
    "default" "unknown"))

(let [bottle "mystery"]
  (cond
    (= bottle "poison") "Don't touch"
    (= bottle "drinkme") "grow smaller"
    (= bottle "empty") "all gone"
    "other" "unknown"))

(let [bottle "drinkme"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"))

(let [bottle "mystery"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"))

;; page 36
(let [bottle "mystery"]
  (case bottle
    "poison" "don't touch"
    "drinkme" "grow smaller"
    "empty" "all gone"
    "unknown"))
;; page 37
(defn grow [name direction]
  (if (= direction :small)
    (str name " is growing smaller")
    (str name " is growing bigger")))

(partial grow "Alice")

(def partA (partial grow "Alice"))
(partA :small)

(defn toggle-grow [direction]
  (if (= direction :small) :big :small))

(toggle-grow :big)
(toggle-grow :small)

(defn oh-my [direction]
  (str "Oh my! You are growing " direction))

;; page 38
(oh-my (toggle-grow :small))

(defn surprise [direction]
  ((comp oh-my toggle-grow) direction))

(defn adder [x y]
  (+ x y))

(adder 3 4)

(def adder-5 (partial adder 5))

(adder-5 10)

(let [[color size] ["blue" "small"]]
  (str "The " color " door is " size))

; page 39
(let [x ["blue" "small"]
      color (first x)
      size (last x )]
  (str "The " color " door is " size))

(let [[color [size]] ["blue" ["very small"]]]
  (str "The  " color " door is " size))

(let [[color [size] :as original] ["blue" ["small"]]]
  {:color color :size size :original original})

(let [{flower1 :flower1 flower2 :flower2}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))

(let [{flower1 :flower1 flower2 :flower2 :or {flower2 "missing"}}
      {:flower1 "red"}]
  (str "The flowers are " flower1 " and " flower2))

(let [{flower1 :flower1 :as all-flowers}
      {:flower1 "red"}]
  [flower1 all-flowers])

;; page 40

(let [{:keys [flower1 flower2]}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))
; this also works
(let [{:keys [:flower1 :flower2]}
      {:flower1 "red" :flower2 "blue"}]
  (str "The flowers are " flower1 " and " flower2))

(defn flower-colors [colors]
  (str "The flowers are "
       (:flower1 colors)
       " and "
       (:flower2 colors)))

(flower-colors {:flower1 "red" :flower2 "blue"})

; better version
(defn flower-colors [{:keys [flower1 flower2]}]
  (str "The flowers are " flower1 " and " flower2))

; page 41
(take 5 (range))

; page 44
(def adjs ["normal" "too small" "too big" "is swimming"])

(defn alice-is [in out]
  (if (empty? in)
    out
    (alice-is
     (rest in)
     (conj out
           (str "Alice is " (first in))))))

(defn alice-is [in out]
  (println "Here is in: " in)
  (if (empty? in)
    out
    (alice-is
     (rest in)
     (conj out
           (str "Alice is " (first in))))))

; page 45
(defn alice-is [input]
  (loop [in input
         out []]
    (if (empty? in)
      out
      (recur (rest in)
             (conj out
                   (str "Alice is " (first in)))))))

(defn countdown [n]
  (if (= n 0)
    n
    (countdown (- n 1))))

; page 46
(defn countdown [n]
  (if (= n 0)
    n
    (recur (- n 1))))
; page 47
(def animals [:mouse :duck :dodo :lory :eaglet])
(map #(str %1) animals)

; page 48
(def animal-print (doall (map #(println %1) animals)))

(def colors ["brown" "black" "blue" "pink" "gold"])

; page 49
(defn gen-animal-string [animal color]
  (str color "-" animal))

(map gen-animal-string animals colors)

(map gen-animal-string animals (cycle ["brown" "black"]))

(reduce (fn [r x] (+ r (* x x))) [1 2 3])

; page 50
(reduce (fn [r x] (if (nil? x) r (conj r x)))
        []
        [:mouse nil :duck nil nil :lory])


(filter (complement nil?) [:mouse nil :duck nil])

(filter keyword? [:mouse nil :duck nil])

(defn return-string [x]
  (if (string? x)
    x
    nil))

(defn return-string [x]
  (when (string? x)
    x))

(map #(return-string %1) ["aaa" 33 "bbb" :keyword])

(remove nil? (map #(return-string %1) ["aaa" 33 "bbb" :keyword]))

; page 51
(for [animal [:mouse :duck :lory]]
  (str (name animal)))

(for [animal [:mouse :duck :lory]
      color [:red :blue]]
  (str (name color) (name animal)))

(for [animal [:mouse :duck :lory]
      color [:red :blue]
      :let [animal-str (str "animal-"(name animal))
            color-str (str "color-"(name color))
            display-str (str animal-str "-" color-str)]]
  display-str)

(for [animal [:mouse :duck :lory]
      color [:red :blue]
      :let [animal-str (str "animal-"(name animal))
            color-str (str "color-"(name color))
            display-str (str animal-str "-" color-str)]
      :when (= color :blue)]
  display-str)

; page 52
(flatten [[:duck [:mouse] [[:lory]]]])

(vec '(1 2 3))
(into [] '(1 2 3))

(sorted-map :b 2 :a 1 :c 3)

(into (sorted-map) {:b 2 :a 1 :c 3})

(into {} [[:a 1] [:b 2] [:c 3]])

(into [] {:a 1, :b 2, :c 3})

(partition 3 [1 2 3 4 5 6 7 8 9])

(partition 3 [1 2 3 4 5 6 7 8 9 10])

(partition-all 3 [1 2 3 4 5 6 7 8 9 10])

; page 53
(partition-by #(= 6 %1) [1 2 3 4 5 6 7 8 9 10])
