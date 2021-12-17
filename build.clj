(ns build
  (:require
   [clojure.tools.build.api :as b]))

(def ^:private class-dir  "target/classes")
(def ^:private uber-file  "target/clj-xmas.jar")
(def ^:private main       'xmas.core)
(def ^:private basis      (b/create-basis {:project "deps.edn"}))

(defn uberjar
  "clojure -T:build uberjar"
  [_]
  (b/copy-dir {:src-dirs (:paths basis)
               :target-dir class-dir})
  (b/compile-clj {:basis basis
                  :src-dirs ["src"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis basis
           :main main}))
