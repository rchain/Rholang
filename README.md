Rholang is a concurrency-oriented programming language, with a focus on message-passing and asynchrony. It is designed to be used to implement protocols and "smart contracts" on a general-purpose blockchain, but could be used in other settings as well. It is based on the ρ-calculus, a reflective and higher-order extension of the π-calculus.

The language is still in the early stages of development, but for those who are interested, more information can be found in the [RChain Platform Architecture](http://rchain-architecture.readthedocs.io/en/latest/).

## Dependencies:
To run on Ubuntu Linux pre-install
* [sbt](http://www.scala-sbt.org/0.13/docs/Installing-sbt-on-Linux.html)
* [CUP](http://www2.cs.tum.edu/projects/cup/install.php) - can be installed using apt
* JLex - install using apt

Then clone this repository and run `sbt` from inside the repository folder.
`compile` builds the project.
`run` allows you to select which parser to run.
`runMain <mainclass> <rhofile>` will actually run parsing on the file.

For example try `runMain rholang.parsing.rholang2.Test examples/sugar/Cell1.rho`
