# pom2xml plug-in for [Kobalt](http://beust.com/kobalt/home/index.html)

[![License (3-Clause BSD)](https://img.shields.io/badge/license-BSD%203--Clause-blue.svg?style=flat-square)](http://opensource.org/licenses/BSD-3-Clause) [![release](http://github-release-version.herokuapp.com/github/ethauvin/kobalt-pom2xml/release.svg?style=flat)](https://github.com/ethauvin/kobalt-pom2xml/releases/latest) [![Build Status](https://travis-ci.org/ethauvin/kobalt-pom2xml.svg?branch=master)](https://travis-ci.org/ethauvin/kobalt-pom2xml) [![CircleCI](https://circleci.com/gh/ethauvin/kobalt-pom2xml/tree/master.svg?style=shield)](https://circleci.com/gh/ethauvin/kobalt-pom2xml/tree/master) [![Download](https://api.bintray.com/packages/ethauvin/maven/kobalt-pom2xml/images/download.svg) ](https://bintray.com/ethauvin/maven/kobalt-pom2xml/_latestVersion)


The plug-in will generate a [Project Object Model](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html) (POM) XML file for your project.

To use the plug-in include the following in `Build.kt`:

```kotlin
import net.thauvin.erik.kobalt.plugin.pom2xml.*

val bs = buildScript {
    plugins("net.thauvin.erik:kobalt-pom2xml:")
}

val p = project {
    name = "example"
    group = "com.example"
    artifactId = name
    version = "0.1"

    pom2xml {

    }
}
```

To invoke the `pom2xml` task:

```sh
./kobaltw pom2xml
```

The `pom.xml` file will be created in the project's directory.

[View Example](https://github.com/ethauvin/kobalt-pom2xml/blob/master/example/kobalt/src/Build.kt)

## Parameters

The following optional configuration parameters are available.

Attribute       | Description
:---------------|:----------------------------------------------------------------------------------
`loc`          | The directory location to save the POM file to, defaults to the project directory.
`name`           | The name of the POM file, defaults to `pom.xml`.

For example:

```kotlin
    pom2xml {
        name = "pom-test.xml"
        loc = "foo/bar"
    }
```