# {{name}}

TODO: Description

## TODO: Template Notes

The generated project uses Make and [CMMA](https://github.com/ohpauleez/cmma).
You can optionally use Boot or Leiningen, both fed from the shared `project.edn` file.

You can redefine CMMA's behaviors (compilation, running namespaces, etc.) with
environment variables, or redefining the Makefile variables (in either `Makefile` or `Makefile.cmma`)

CMMA ships with bundled dependency management, but you can use any tool (Boot, Lein, Maven)
to resolve dependencies - just [follow the examples](https://github.com/ohpauleez/cmma/tree/master/cmma-clj/examples).
When using another tool, you can redefine the `nrepl` task in `Makefile.cmma` and remove the CMMA JAR from the project.

This template also ships with Docker and OSv support.


## Tasks

| Task                   |     CMMA       |   Leiningen      |     Boot     |
|------------------------|----------------|------------------|--------------|
| Launch a REPL          | `make repl`    |  `lein dumbrepl` |              |
| Launch a Socket REPL   | `make srepl`   |                  |              |
| Launch a SREPL Client  | `make srepl-client`   |           |              |
| Launch nREPL           | `make nrepl`   |  `lein repl`     | `boot repl`  |
| Run tests              | Use Repl       |  `lein test`     | `boot test`  |
| Build a deployable JAR | Use Java tools |  `lein build`    | `boot build` |
| Compile Clojure files  | `make compile` |  `lein compile`  |              |
| Run `clojure.main`     | `make clj`     |                  |              |
| Print classpath        | `make classpath` |  `lein classpath` | `boot show -c`  |
| Run a namespace        | `make ns`      |  `lein run`      |              |
| Generate API docs      |                |  `lein codox`    |              |
| Generate literate docs |                |  `lein marg`     |              |


## License

Copyright © 2014 FIXME
