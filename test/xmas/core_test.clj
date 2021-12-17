(ns xmas.core-test
  (:require
   [clojure.test :as t]
   [xmas.core :as sut]))

(t/deftest generate-tree-test
  (let [size 5
        r (java.util.Random.)
        tree (sut/generate-tree r size)]
    (t/is (= (+ 1    ; star
                size ; leaves
                2)   ; trunks
             (count tree)))))
