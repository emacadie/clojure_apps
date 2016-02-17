;; page 84
lein new serpent-talk
;; rename src/serpent_talk/core.clj to src/serpent_talk/talk.clj
;; and core_test.clj to talk_test.clj
(require '[camel-snake-kebab.core :as csk])
(csk/->snake_case "Hello repl")
(require '[clojure.set :as set])
;; up to page 94
;; page 96
lein run -m serpent-talk.talk "Hello pigeon"
;; add this to project.clj
:main serpent-talk.talk
;; then you can to "lein run"
