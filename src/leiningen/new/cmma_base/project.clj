;; There are no Git Dependencies allowed in `project.edn` for this compatibility to work
(let [proj (read-string (slurp "project.edn"))
      ;; `project` and `version` can't be used, sadly
      {:keys [project version description url license]} (:app proj)
      {:keys [dependencies dev-dependencies boot-dependencies repositories source-paths resource-paths]} proj
      lein-repositories (mapv vec repositories)
      {:keys [java-source-paths javac-options main]} (:external proj)]
  (defproject {{raw-name}} "0.1.0-SNAPSHOT"
    :description description
    :url ~url
    :license ~license
    :dependencies ~dependencies
    :min-lein-version "2.5.0"
    :source-paths ~source-paths
    :resource-paths ~resource-paths
    ;:main ^{:skip-aot true} ~main
    :main ~main
    ;:aot []
    :java-source-paths ~java-source-paths
    :javac-options ~javac-options
    :global-vars  {*warn-on-reflection* true
                   *unchecked-math* :warn-on-boxed
                   ;*compiler-options* {:disable-locals-clearing true}
                   *assert* true}
    :pedantic? :abort
    :aliases {"build" ["uberjar"]}
    :profiles {:uberjar {:aot [~main]}
               :dev {:aliases {"dumbrepl" ["trampoline" "run" "-m" "clojure.main/main"]}
                     :dependencies ~dev-dependencies
                     :source-paths ["src/dev"] ;; Override this so we can keep the aleph code out of the JAR
                     :codox {:output-path "docs/api-docs"}
                     :plugins [[lein-marginalia "0.8.0" :exclusions [[org.clojure/clojure]
                                                    ;; Use the tools.reader from `cljfmt`
                                                    [org.clojure/tools.reader]]]
                               [lein-codox "0.9.0" :exclusions [[org.clojure/clojure]]]
                               ;; Requires lein 2.5.0 or higher
                               [lein-cljfmt "0.3.0" :exclusions [[org.clojure/clojure]]]]}}
    :repositories ~lein-repositories
    ;:plugins [[info.sunng/lein-bootclasspath-deps "0.2.0"]]
    ;:boot-dependencies ~boot-dependencies
    :jvm-opts ^:replace ["-d64" "-server"
                         ;"-Xms1g" ;"-Xmx1g"
                         ;"-XX:+UnlockCommercialFeatures" ;"-XX:+FlightRecorder"
                         ;"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8030"
                         ;"-XX:+UseG1GC"
                         ;"-XX:+UseConcMarkSweepGC" "-XX:+UseParNewGC" "-XX:+CMSParallelRemarkEnabled"
                         ;"-XX:+ExplicitGCInvokesConcurrent"
                         "-XX:+AggressiveOpts"
                         ;-XX:+UseLargePages
                         "-XX:+UseCompressedOops"]))

