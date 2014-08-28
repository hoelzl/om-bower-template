# om-bower

A Leiningen template for creating
[OM](https://github.com/swannodette/om) projects compatible with nREPL
compliant editors/IDEs, and using [Bower](http://bower.io) to manage
JavaScript libraries.

This template (including the text of the README.md file) is based on
Mimmo Cosenza's
[om-start](https://github.com/magomimmo/om-start-template) template
and was modified by me (Matthias Hölzl, <tc@xantira.com>) to better
suit the setup I use for my projects.

In addition to adding a Bower configuration, newly created projects
are by default dual-licensed under the MIT license and the EPL.  I
have also included the
[double-check](https://github.com/cemerick/double-check) library for
property-based testing.

## Quick start

### Emacs/cider quick start

Start by creating a new OM project based on `om-bower` lein-template.

```bash
lein new om-bower my-om-project
cd my-om-project
bower install
lein cljsbuild once
```

Then open the generated `core.cljs` file in Emacs from the
`src/cljs/my-om-project` directory and run the `C-c M-j` shortcut (or `M-x
cider-jack-in`).

The command needs some time to download dependencies and plugins, to
compile the ClojureScript code and to run the nREPL server and
client. So, be patient the first time.

Once the nREPL is ready, evaluate the following Clojure forms:

```clj
(run) ; to run the included http server
```

and then

```clj
(browser-repl) ; to run the browser-connected REPL
```

Finally, visit the URL `http://localhost:3000` in your browser to
activate the browser-connected REPL.  If your REPL remains
unresponsive after visiting this page, you may have to disable your ad
blocker for `localhost`; both Adblock Plus and AdGuard seem to prevent
cider from connecting to the browser.

You're now ready to start hacking on your Om application.  To check
whether everything works as expected, modify the `core.cljs` file,
compile it into your REPL (`C-c C-k`) and check that your browser
updates as expected.

The following instructions for other development environments are
taken verbatim from the `om-start` template; I don't know if they
still work since I don't use these environments myself.

### Eclipse/CCW  quick start

Start [CCW][5] by [Laurent Petit][6] and create a new `Clojure
Project` based on the `om-bower` lein-template.

```bash
New > Clojure Project

Project Name: my-om-project
Leiningen teamplate: om-bower
```

From the contextual menu of the project select `Run as > Clojure Application`.

The command needs sometime to compile the ClojureScript code and to
run the nREPL server and client. So, be patient.

Once the nREPL is ready, evaluate the `(run)` form to start the
included http server.

Then start the Browser Connected REPL by evaluating the
`(browser-repl)` form.

When ready, visit the `http://localhost:3000` URL to activate the
ClojureScript REPL and you are ready to follow the [David Nolen][2]
[Tutorial on OM][3].

Open the `core.cljs` source code in the editor and evaluate it form by
form starting from the namespace declaration.

### Vim/fireplace quick start

Requires [vim-fireplace][8].

Start by creating a new OM project based on `om-bower` lein-template and
launching the repl.

```bash
lein new om-bower my-om-project
cd my-om-project
lein repl
```

`lein repl` will require some time to download dependencies and compile the
ClojureScript code.  Once the nREPL is ready, evaluate the following Clojure
forms:

```clj
(run) ; to run the included http server server.
```

Then open the generated `core.cljs` file in Vim from the
`src/cljs/my-om-project` directory and enter the command

```vim
:Piggieback (browser-repl-env)
```
Finally, visit the `http://localhost:3000` URL to activate the Browser
Connected REPL.

You're now ready to follow the [David Nolen][2] [Tutorial on OM][3]
with the same kind of `live` experience he reached with
[Light Table][4].

Evaluate `core.cljs` file form by form starting from the namespace
declaration. To do that just position your cursor at the end of each
top-level form and type `cpp`.

> ATTENTION NOTE: `om-bower` does not use `:none` optimization and
> this is because [austin][7] does not support it. 

## License

Copyright © 2014 Mimmo Cosenza (aka @magomimmo)

Copyright © 2014 Matthias Hölzl <tc@xantira.com>


Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

[1]: https://github.com/swannodette/om
[2]: https://github.com/swannodette
[3]: https://github.com/swannodette/om/wiki/Basic-Tutorial
[4]: http://www.lighttable.com/
[5]: https://github.com/laurentpetit/ccw
[6]: https://github.com/laurentpetit
[7]: https://github.com/cemerick/austin
[8]: https://github.com/tpope/vim-fireplace

