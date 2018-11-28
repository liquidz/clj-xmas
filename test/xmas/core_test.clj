(ns xmas.core-test
  (:require [clojure.string :as str]
            [clojure.test :as t]
            [xmas.core :as sut]))

(t/deftest write-tree-test
  (let [size 5
        w (doto (java.io.StringWriter.)
            (sut/write-tree size))
        res (.toString w)]
    (t/is (not (str/blank? res)))
    (t/is (= (+ 1    ; star
                size ; leaves
                2)   ; trunks
             (count (str/split res #"\n"))))))
