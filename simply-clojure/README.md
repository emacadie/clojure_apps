# simply-clojure

"Simply Scheme" in Clojure.

## Usage

FIXME
```clojure
(require '[clojure.tools.namespace.repl :as repl])
(repl/refresh)
(require '[simply-clojure.helper])
(require '[simply-clojure.chapter14-numbers])
```

Link to numeric tower: https://github.com/clojure/math.numeric-tower  

lein test simply-clojure.chapter15-test

lein test :only simply-clojure.chapter15-test/test-to-binary

To use file in emacs:
* M-x cider-jack-in  
* Go to REPL (C-x o)  
* (ns simply-clojure.chapter17)  ; (or whichever namespace you wish to work on)
* Go back to emacs (C-x o)
* M-x cider-load-buffer (you can call this again to reload further changes)

## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
