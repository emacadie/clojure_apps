# funcjava

This is a Clojure app using boot that will have Clojure equivalents to the Java code in Venkat S' Functional Programming in Java.   
I already have a Gradle-based repo that has the Java code and equivalent Groovy code. I found a Gradle plugin that let you use Clojure in a Gradle app,
but I wasn't able to do as much as with lein or boot or Gradle for Java and Groovy (like only run some of the tests).   


For Chapter two: I will not do anything for the iteration section. There does not seem to be a way to simply iterate in Clojure (even though there is a function called "iterate"). It seems like in Clojure, you don't just go through a sequence/collection/list, but you do something with each item in the list (more that just print to console).    

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

Run the project directly:

    $ boot run

Run the project's tests (they'll fail until you edit them):

    $ boot test

Build an uberjar from the project:

    $ boot build

Run the uberjar:

    $ java -jar target/funcjava-0.1.0-SNAPSHOT-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

boot test --namespaces info.shelfunit.funcjava.chap01-test   

boot run --args "hello world"   
To use multiple args:    
boot run -a "hello" -a "world"    
### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
