(ns xmas.core
  (:gen-class))

(defn color [code s] (str \u001b "[" code "m" s \u001b "[m"))
(defn strs  [n s] (apply str (repeat n s)))

(def default-size 5)
(def star   (color 33 \u2605))
(def left   (color 32 \uFF0F))
(def right  (color 32 \uFF3C))
(def bottom (color 32 \u005e))
(def trunk  (color 31 "| |"))

(def objects [\u0069 \u0020 \u0020 \u0020 \u0020 \u0020
              \u0020 \u0020 \u0020 \u0020 \u0020 \u0020
              \u0020 \u2E1B \u2042 \u2E2E "&" "@" \uFF61])
(def object-colors [21 33 34 35 36 37])

(defn write-tree [writer size]
  (letfn [(write [s] (.write writer s))]
    (write (str (strs (inc size) " ") star "\n"))
    (doseq [l (map inc (range size))]
      (write (str (strs (- size l) " ") left))
      (dotimes [_ (dec (* l 2))]
        (write (color (rand-nth object-colors) (rand-nth objects))))
      (write (str right "\n")))
    (write (str (strs size bottom) trunk (strs size bottom) "\n"))
    (write (str (strs size " ") trunk "\n"))))

(defn -main [& [size]]
  (let [size (or (and size (Long/parseLong size)) default-size)]
    (write-tree *out* size)))
