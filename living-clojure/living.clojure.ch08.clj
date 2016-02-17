;; page 145
(defn hi-queen [phrase]
  (str phrase ", so give me some lovin' baby"))
  

;; page 147
(defmacro def-hi-queen [name phrase]
  (list 'defn
        (symbol name)
        []
        (list 'hi-queen phrase)))

(macroexpand-1 '(def-hi-queen alice-hi-queen "My name is Alice"))

;; up to page 148
'(first [1 2 3])
`(first [1 2 3])

(let [x 5]
  `(first [x 2 3]))

(let [x 5]
  `(first [~x 2 3]))

(defmacro def-hi-queen [name phrase]
  `(defn ~(symbol name)[]
     (hi-queen ~phrase)))

