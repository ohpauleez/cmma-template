(ns leiningen.new.cmma-base
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files sanitize-ns project-name]]
            [leiningen.core.main :as main]
            [clojure.java.io :as io]
            [clojure.string :as string]))

(def render (renderer "cmma-base"))

(defn binary [file]
  (io/input-stream (io/resource (string/join "/" ["leiningen" "new" "cmma_base" file]))))

(defn cmma-base
  "Generate a new CMMA-based project"
  [name]
  (let [sanitized-ns (sanitize-ns name)
        data {:raw-name name
              :name (project-name name)
              :namespace sanitized-ns
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' cmma-base project.")
    (->files data
             ["README.md" (render "README.md" data)]
             ["project.edn" (render "project.edn" data)]
             ["project.clj" (render "project.clj" data)]
             ["build.boot" (render "build.boot" data)]
             ["Dockerfile" (render "Dockerfile" data)]
             ["Capstanfile" (render "Capstanfile" data)]
             ["Makefile" (render "Makefile" data)]
             ["Makefile.cmma" (render "Makefile.cmma" data)]
             ["bin/cmma-0.1.0-standalone.jar" (binary "cmma-0.1.0-standalone.jar")]
             [".gitignore" (render ".gitignore" data)]
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)])))
