[![Build Status](https://travis-ci.org/uqbar-project/arena.svg?branch=master)](https://travis-ci.org/uqbar-project/arena)
[![Coverage Status](https://coveralls.io/repos/uqbar-project/arena/badge.svg?branch=master&service=github)](https://coveralls.io/github/uqbar-project/arena?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.uqbar-project/arena-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.uqbar-project/arena-core/)

Arena
=====

# Documentation

* https://sites.google.com/site/programacionui/material/herramientas/arena
* http://arena.uqbar-project.org/

# How to deploy to Maven Central?

#### Snapshots
Automatically deployed by Travis, after a successful build of the _master_ branch.

#### Releases
Travis will perform a release each time a version tag (i.e. a tag named following [Semantic Versioning spec](http://semver.org/)) is found, so the developer should only prepare the release on his/her machine. The steps are:

```bash
mvn --batch-mode release:clean release:prepare
# If you want to manually set the version, remove the --batch-mode option

git push --follow-tags
# This will publish both the commits and the new tag
```
