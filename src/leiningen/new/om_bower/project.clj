(defproject {{raw-name}} "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :licenses [{:name "MIT License"
              :url "http://opensource.org/licenses/MIT"
              :distribution :repo}
             {:name "Eclipse Public License - v 1.0"
              :url "http://www.eclipse.org/legal/epl-v10.html"
              :distribution :repo}]

  :min-lein-version "2.3.4"

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [om "0.7.1"]
                 [com.facebook/react "0.11.1"]]
  
  :plugins [[lein-cljsbuild "1.0.3"]]

  :hooks [leiningen.cljsbuild]

  :cljsbuild
  {:builds {:{{name}}
            {:source-paths ["src/cljs"]
             :compiler
             {:output-to "dev-resources/public/js/{{sanitized}}.js"
              :optimizations :advanced
              :pretty-print false}}}})