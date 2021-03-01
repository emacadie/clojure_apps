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
You have to load the namespace file and the test file before you can use them in the buffer and before you can run the tests.


To run tests for a particular namespace:
lein test simply-clojure.chapter04-test

lein test simply-clojure.chapter05-test

lein test simply-clojure.chapter06-test

lein test simply-clojure.chapter08-test

lein test simply-clojure.chapter09-bridge-test

lein test simply-clojure.chapter09-test

lein test simply-clojure.chapter11-test

lein test simply-clojure.chapter12-test

lein test simply-clojure.chapter14-numbers-test

lein test simply-clojure.chapter14-test

lein test simply-clojure.chapter14-vector-test

lein test simply-clojure.chapter15-phone-test

lein test simply-clojure.chapter15-test

lein test simply-clojure.chapter17-test

lein test simply-clojure.helper-test


## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
