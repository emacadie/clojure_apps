(ns monkeyballs.log
  (:require [io.aviso.ansi :as ansi]))

(defn log [category message]
  (println (ansi/green category)
           (ansi/blue message)))