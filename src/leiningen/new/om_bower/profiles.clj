{:shared {:clean-targets ["out" :target-path]}

 :tdd [:shared 
       {:cljsbuild
        {:builds {:{{name}}
                  {:compiler
                   {:optimizations :whitespace
                    :pretty-print true}}}}}]

 :dev [:shared
       {:resources-paths ["dev-resources"]
        :source-paths ["dev-resources/tools/http" "dev-resources/tools/repl"]
        :dependencies [[ring "1.3.1"]
                       [compojure "1.1.9"]
                       [cheshire "5.3.1"]
                       [hickory "0.5.4"]]
        :plugins [[com.cemerick/austin "0.2.0-SNAPSHOT"]
                  [com.cemerick/clojurescript.test "0.3.1"]]
        :cljsbuild
        {:builds {:{{name}}
                  {:source-paths ["dev-resources/tools/repl"]
                   :compiler
                   {:optimizations :whitespace
                    :pretty-print true}}}}

        :injections [(require '[ring.server :as http :refer [run]]
                              'cemerick.austin.repls)
                     (defn browser-repl-env []
                       (reset! cemerick.austin.repls/browser-repl-env
                                (cemerick.austin/repl-env)))
                     (defn browser-repl []
                       (cemerick.austin.repls/cljs-repl
                         (browser-repl-env)))]}]}
