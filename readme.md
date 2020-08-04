# PhEMA ELM Util

[![Build Status](https://travis-ci.org/PheMA/elm-utils.svg?branch=master)](https://travis-ci.org/PheMA/elm-utils)

Various utility methods for working with the Clinical Quality Language
([CQL](https://cql.hl7.org/)) Expression Logical Model
([ELM](https://cql.hl7.org/04-logicalspecification.html)).

## Deployment

To publish a new version, simply push a new tag to this repository. Importantly,
make sure the tag does not have `SNAPSHOT` in [`pom.xml`](pom.xml), because
Bintray does not support snapshot releases.