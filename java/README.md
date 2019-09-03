This directory contains JAVA implementation of solutions to TSP problem.

# Where to Begin
This project does not use any IDEs to manage the code. Instead it relies on mannually organising the files. To make things easer, a few shell scripts have been created to off-load most daily maintainance tasks.

For a quick start, use `run-all-tests` to compile all classes in the project. (Unit tests will automatically be launched at the end of the script).

There are a few other scripts to facilitate productivity:
* `compile-class` to compile individual class files under `src` directory. For ecample, to compile xing.core.Solution use `compile-class core.Solution`. The prefix xing is automatically added.
* `compile-test` compiles JUnit tests under `test` directory
* `compile-demo` compiles GUI related demos under `demo` directory
* `run-demo` and `run-test` can be used to launch classes
* Use `add-class` and `add-demo` to create new class files organised in appropriate directory and fill-in stub class declaration, check the script for details
* `make-doc` calls javadoc command and organise the output in the `doc` directory

# Documentation
Check javadoc for documentation of the project
