# om-bower

A Leiningen template for creating
[OM](https://github.com/swannodette/om) projects using
[Bower](http://bower.io) to manage JavaScript libraries.  By default
Twitter Bootstrap is included in the project, as are a number of
ClojureScript libraries that I find useful.

The created project is by default dual-licensed under the MIT license
and the EPL.

## Quick start

To create a new OM project based on the `om-bower` lein-template.

```bash
lein new om-bower my-om-project
cd my-om-project
bower install
lein cljsbuild once
```

For development, run `lein cljsbuild auto` in a terminal and open the
`index.html` file in a browser of your choice.  This works rather well
with IntelliJ/Cursive, but unfortunately not so well with Emacs/Cider,
since you don't get REPL support/completion, etc.

A previous version of the template contained support for nREPL based
development.  However, recent upgrades broke this setup in a number of
ways, and I could find no way to make the current Cider/nREPL and
ClojureScript versions work together in a reasonable manner.


## License

Copyright © 2014 Matthias Hölzl <tc@xantira.com>

Dual-licensed under the MIT license and the Eclipse Public License,
either version 1.0 or (at your option) any later version.
