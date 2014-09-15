(defproject {{raw-name}} "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :licenses [{:name "MIT License"
              :url "http://opensource.org/licenses/MIT"
              :distribution :repo}
             {:name "Eclipse Public License - v 1.0"
              :url "http://www.eclipse.org/legal/epl-v10.html"
              :distribution :repo}]
  
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2322"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [com.facebook/react "0.11.1"]
                 [om "0.7.3"]
                 [prismatic/om-tools "0.3.3"]
                 [prismatic/plumbing "0.3.4-SNAPSHOT"]
                 [prismatic/schema "0.2.6"]
                 [racehub/om-bootstrap "0.2.8"]
                 [rm-hull/monet "0.2.1"]
                 [sablono "0.2.22"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]]
  
  :source-paths ["src"]
  
  :cljsbuild { 
              :builds [{:id "{{sanitized}}"
                        :source-paths ["src"]
                        :compiler {
                                   :output-to "out/{{sanitized}}.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map "out/{{sanitized}}.map"}}]})
