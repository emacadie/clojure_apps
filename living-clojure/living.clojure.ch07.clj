;; chapter 07 - web stuff
;; page 112: lein new compojure cheshire-cat
;; page 111: compojure build on top of ring
lein ring server
;; that opens a browser pointing to http://localhost:3000/
;; look up require again, what does :refer :all mean?
;; page 116: to start ring without starting browser:
lein ring server-headless
;; page 121
lein trampoline cljsbuild repl-rhino
;; page 124
lein ring server
;; in another window/terminal
lein cljsbuild auto
;; up to page 125
lein trampoline cljsbuild repl-listen

