(ns xmas.tool
  (:require
   [xmas.core :as core]))

(def xmas
  "Plant christmas trees to your terminal
  Options:
  - :size       <Number>   Tree size
  - :number     <Number>   Number of trees
  - :animation  <Boolean>  Flag for animation
  - :interval   <Number>   Animation interval seconds"
  core/run)

(def christmas
  "Alias for `xmas`"
  xmas)

(def help
  "Navigate to the right help command"
  (fn [_]
    (println "Use `clojure -A:deps -Tmerry help/doc` instead")))
