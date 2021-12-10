(ns xmas.core
  (:gen-class)
  (:require
   [clojure.string :as str]
   [clojure.tools.cli :as cli])
  (:import
   java.util.Random))

(defn color [code s] (str \u001b "[" code "m" s \u001b "[m"))
(defn strs [n s & more] (apply str (concat (repeat n s) more)))
(defn cursor-up [n] (print (str \u001b "[" n "F")))
(defn parse-long [s] (Long/parseLong s))
(defn rand-nth' [^Random r coll] (nth coll (int (* (.nextDouble r) (count coll)))))

(def star   (color 33 \u2605))
(def left   (color 32 \uFF0F))
(def right  (color 32 \uFF3C))
(def bottom (color 32 \u005e))
(def trunk  (color 31 "| |"))

(def objects [\u0069 \u0020 \u0020 \u0020 \u0020 \u0020
              \u0020 \u0020 \u0020 \u0020 \u0020 \u0020
              \u0020 \u2E1B \u2042 \u2E2E "&" "@" \uFF61])
(def object-colors [21 33 34 35 36 37])

(defn generate-tree [^Random r size]
  (concat
   [(strs (inc size) " " star (strs (inc size) " "))]
   (for [l (range 1 (inc size))]
     (str (strs (- size l) " " left)
          (apply str (repeatedly (dec (* l 2)) #(color (rand-nth' r object-colors)
                                                       (rand-nth' r objects))))
          right
          (strs (- size l) " ")))
   [(strs size bottom trunk (strs size bottom))
    (strs size " " trunk (strs size " "))]))

(defn print-tree [^Random r size n]
  (doseq [line (apply map #(str/join " " %&) (repeatedly n #(generate-tree r size)))]
    (println line)))

(def cli-options
  [["-s" "--size SIZE" "Tree size" :default 5 :parse-fn parse-long :validate [#(< 0 % 100)]]
   ["-n" "--number NUMBER" "Number of trees" :default 1 :parse-fn parse-long]
   ["-a" "--animation"]
   ["-i" "--interval INTERVAL" :default 1 :parse-fn parse-long]
   ["-h" "--help"]])

(defn -main
  [& args]
  (let [{:keys [options summary errors]} (cli/parse-opts args cli-options)
        {:keys [size number animation interval help]} options
        r (Random. (System/currentTimeMillis))]
    (cond
      errors (doseq [e errors] (println e))
      help (println (str "Usage:\n" summary))

      animation
      (while true
        (print-tree r size number)
        (cursor-up (+ 3 size))
        (Thread/sleep (* interval 1000)))

      :else
      (print-tree r size number))))
