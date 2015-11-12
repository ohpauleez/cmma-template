# cmma-base

A Leiningen template for CMMA-based projects.

## Usage

Install by hand with: `lein install`

Create new CMMA-based projects with `lein new cmma-base myproject`

Then you should be able to `make repl` from within the new project.


From there you can manage dependencies with any tool you like (including CMMA).
You can also remove the `Makefile.cmma` file and work directly in the main Makefile, if you prefer.

If you use another tool (Boot, Leiningen, Maven) for dependency management and launching nREPL sessions,
you can chop up the Makefile a bit and remove the CMMA JAR from your generated project.

## License

Copyright © 2015 Paul deGrandis

Distributed under the Eclipse Public License either version 1.0
