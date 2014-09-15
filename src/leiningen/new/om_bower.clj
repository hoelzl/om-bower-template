(ns leiningen.new.om-bower
  "Generate an OM project for nREPL compliant editors/IDEs"
  (:require [leiningen.new.templates :refer [renderer
                                             multi-segment
                                             sanitize-ns
                                             project-name
                                             name-to-path 
                                             year
                                             sanitize
                                             ->files]]
            [leiningen.core.main :as main]))

(defn om-bower
  "A lein template for creating nREPL compliant OM project"
  [name]
  (let [render (renderer "om-bower")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :sanitized (sanitize (project-name name))
              :year (year)}]
    (main/info "Generating a project called " name "based on the 'om-bower' template")
    (main/info "To see other templates (app, lein plugin, etc), try `lein help new`.")
    (->files data
             [".gitignore" (render "gitignore" data)]
             [".bowerrc" (render "bowerrc" data)]
             ["bower.json" (render "bower.json" data)]
             ["LICENSE.EPL" (render "LICENSE.EPL" data)]
             ["LICENSE.MIT" (render "LICENSE.MIT" data)]
             ["README.md" (render "README.md" data)]
             ["index.html" (render "index.html" data)]
             ["doc/intro.md" (render "intro.md" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/{{nested-dirs}}.cljs" (render "core.cljs" data)])))
