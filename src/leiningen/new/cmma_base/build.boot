(require '[clojure.edn :as edn])

(def proj (edn/read-string (slurp "project.edn")))

(set-env! :source-paths   (set (:source-paths proj))
          :test-paths     (set (:test-paths proj))
          :resource-paths (set (:resource-paths proj))
          :dependencies   (:dependencies proj)
          :repositories (:repositories proj))

(task-options! pom {:project (get-in proj [:app :project])
                    :version (str (get-in proj [:app :version]) "-standalone")
                    :description (get-in proj [:app :description])
                    :license (get-in proj [:app :license])})

(load-data-readers!)

;; == Testing tasks ========================================

(deftask with-test
  "Add test to source paths"
  []
  (set-env! :source-paths #(clojure.set/union % (get-env :test-paths)))
  (set-env! :dependencies #(into % (:dev-dependencies proj)))
  identity)

;; Include test/ in REPL sources
(replace-task!
  [r repl] (fn [& xs] (with-test) (apply r xs)))

(require '[clojure.test :refer [run-tests]])

(deftask test
  "Run project tests"
  []
  (with-test) ;; test source paths and test/dev deps added
  (require '[clojure.tools.namespace.find :refer [find-namespaces-in-dir]])
  (let [find-namespaces-in-dir (resolve 'clojure.tools.namespace.find/find-namespaces-in-dir)
        test-nses              (->> (get-env :test-paths)
                                    (mapcat #(find-namespaces-in-dir (clojure.java.io/file %)))
                                    distinct)]
    (doall (map require test-nses))
    (apply clojure.test/run-tests test-nses)))

;; == Server Tasks =========================================

(deftask build
  "Build my project."
  []
  (set-env! :source-paths #(clojure.set/union % (set (get-in proj [:external :java-source-paths]))))
  (comp (javac)
        (aot :namespace #{(get-in proj [:external :main])})
        (pom)
        (uber)
        (jar :main (get-in proj [:external :main]))))

