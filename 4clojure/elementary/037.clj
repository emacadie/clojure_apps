;; Regular Expressions
;; Difficulty:Elementary
;; Topics:regex syntax
;; Regex patterns are supported with a special reader macro.

(def __ "ABC")

(= __ (apply str (re-seq #"[A-Z]+" "bA1B3Ce ")))


  
