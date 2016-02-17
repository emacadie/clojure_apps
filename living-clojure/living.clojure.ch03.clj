; page 55
(def who-atom (atom :caterpillar))

; page 56
(reset! who-atom :chrysalis)

(defn change [state]
  (case state
    :caterpillar :chysalis
    :chrysalis :butterfly
    :butterfly))

; page 57
(swap! who-atom change)

; page 58
(def counter (atom 0))
@counter
(dotimes [_ 5] (swap! counter inc))

(let [n 5]
  (future (dotimes [_ n] (swap! counter inc)))
  (future (dotimes [_ n] (swap! counter inc)))
  (future (dotimes [_ n] (swap! counter inc))))

(defn inc-print [val]
  (println val)
  (inc val))
(swap! counter inc-print)

; page 59
(let [n 2]
  (future (dotimes [_ n] (swap! counter inc-print)))
  (future (dotimes [_ n] (swap! counter inc-print)))
  (future (dotimes [_ n] (swap! counter inc-print))))

; page 60
(def alice-height (ref 3))
(def right-hand-bites (ref 10))

(defn eat-from-right-hand []
  (when (pos? @right-hand-bites)
    (alter right-hand-bites dec)
    (alter alice-height #(+ %1 24))))

(eat-from-right-hand)

(dosync (eat-from-right-hand))

; page 61
(def alice-height (ref 3))
(def right-hand-bites (ref 10))
(defn eat-from-right-hand []
  (dosync (when (pos? @right-hand-bites)
            (alter right-hand-bites dec)
            (alter alice-height #(+ %1 24)))))

(let [n 2]
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand))))

; page 62
(def alice-height (ref 3))
(def right-hand-bites (ref 10))

(defn eat-from-right-hand[]
  (dosync (when (pos? @right-hand-bites)
            (commute right-hand-bites dec)
            (commute alice-height #(+ %1 24)))))

(let [n 2]
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand)))
  (future (dotimes [_ n] (eat-from-right-hand))))

; page 63
(def x (ref 1))
(def y (ref 1))

(defn new-values []
  (dosync
   (alter x inc)
   (ref-set y (+ 2 @x))))

(let [n 2]
  (future (dotimes [_ n] (new-values)))
  (future (dotimes [_ n] (new-values))))

; page 64
(def who-agent (agent :caterpillar))

@who-agent

(defn change [state]
  (case state
    :caterpillar :chrysalis
    :chrysalis :butterfly
    :butterfly))

(send who-agent change)

@who-agent
; page 65
(defn change-error [state]
  (throw (Exception. "Boom")))

(send who-agent change-error)

; page 66
(defn err-handler-fn [a ex]
  (println "error " ex " value is " @a))

(def who-agent (agent :caterpillar))
(set-error-handler! who-agent err-handler-fn)
(set-error-mode! who-agent :continue)
(send who-agent change-error)
