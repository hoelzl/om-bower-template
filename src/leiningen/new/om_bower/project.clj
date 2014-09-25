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

  :dependencies [[org.clojure/clojure "1.7.0-alpha2"]
                 [org.clojure/clojurescript "0.0-2342"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [com.facebook/react "0.11.2"]
                 [om "0.7.3"]
                 [prismatic/om-tools "0.3.3"]
                 [prismatic/plumbing "0.3.3"]
                 [prismatic/schema "0.3.0"]
                 [racehub/om-bootstrap "0.2.9"]
                 [sablono "0.2.22"]
                 [rm-hull/monet "0.2.1"]]
    
  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :hooks [leiningen.cljsbuild]

  :cljsbuild
  {:builds {:{{name}}
            {:source-paths ["src/cljs"]
             :compiler
             {:output-to "dev-resources/public/js/{{sanitized}}.js"
              :output-dir "dev-resources/public/js"
              :optimizations :advanced
              :pretty-print false
              :source-map "dev-resources/public/js/{{sanitized}}.map"}}}})
