;; lein new async-tea-party
;; page 101 - create a channel in the repl
(def tea-channel (async/chan))
;; name space: Could do:
(load "clojure/core/async")
;; this worked at the repl:
(ns async-tea-party.core
  (:require [clojure.core.async :as async]))
;; then I could do this:
(def tea-channel (async/chan))
;; then this:
(def tea-channel (async/chan 10))
;; synchronous/blocking put
(async/>!! tea-channel :cup-of-tea)
;; get it back with a blocking take:
(async/<!! tea-channel)
;; page 102
;; async get: >! async put: <!
(let [tea-channel (async/chan)]
  (async/go (async/>! tea-channel :cup-of-tea-1))
  (async/go (println "Thanks for the " (async/<! tea-channel))))

;; page 103
(def tea-channel (async/chan 10))

(async/go-loop []
  (println "Thanks for the " (async/<! tea-channel))
  (recur))

(async/>!! tea-channel :hot-cup-of-tea)
(async/>!! tea-channel :tea-with-milk)

;; page 104
(def tea-channel (async/chan 10))
(def milk-channel (async/chan 10))
(def sugar-channel (async/chan 10))
(async/go-loop []
  (let[[v ch] (async/alts! [tea-channel
                             milk-channel
                             sugar-channel])]
    (println "Got " v " from " ch)
    (recur)))
(async/>!! sugar-channel :sugar)
(async/>!! milk-channel :milk)
(async/>!! tea-channel :tea)


;;
(let [c (+ 1 2)
      [d e] [5 6]]
  (println "starting with c is " c " d is " d " e is " e)
  (-> (+ d e) (- c)))

;; for page 107
;; this worked at the repl:
(ns async-tea-party.core
  (:require [clojure.core.async :as async]))

(request-tea)
;; I got things to work at repl by adding this to project.clj:
;; :main ^:skip-aot async-tea-party.core
;; to get to repl in emacs: M-x cider-jack-in

