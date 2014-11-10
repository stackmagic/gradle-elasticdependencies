gradle-elasticdependencies
==========================

gradle plugin to

* create ProjectDependency from `projectName` in a multimodule build
* create ExternalDependency if no matching project is found
  * default group is the same as the project this plugin is used from
  * default version is '+'
  * group/version can be overridden optionally for the external dependency

this plugin makes it easy and convenient to build only a single module without
having gradle load/configure the root and all other modules (see gradle's "-u"
commandline switch). in that case, your dependencies must be available from a
remote repository.

this plugin was created to allow devs to build projects like they're used to
but build individual modules on the build server completely isolated from the
rest of the project.

[ ![TravicCI](https://travis-ci.org/stackmagic/gradle-elasticdependencies.svg?branch=master) ](https://travis-ci.org/stackmagic/gradle-elasticdependencies)
[ ![Download](https://api.bintray.com/packages/stackmagic/maven/gradle-elasticdependencies/images/download.svg) ](https://bintray.com/stackmagic/maven/gradle-elasticdependencies/_latestVersion)

related work
============

* [elastic-deps](https://github.com/pniederw/elastic-deps): elastic-deps by Peter Niederwieser

setup (gradle 2.1 or higher)
============================

```groovy
plugins {
  id 'net.swisstech.elasticdependencies'
}
```

setup (gradle-1.7 or higher)
============================

```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        compile 'net.swisstech:gradle-elasticdependencies:+'
    }
}

apply plugin: 'net.swisstech.elasticdependencies'
```

usage
=====

```groovy
dependencies {
  // if you run just gradle, and if there's an 'other-module' available,
  // this will create a ProjectDependency('other-module')
  // if this 'other-module' isn't available, or if you run "gradle -u"
  // this will create an ExternalDependency("${project.group}:other-module:+")
  compile elastic('other-module')

  // override the version, this will create
  // a ProjectDependency('other-module')
  // or ExternalDependency("${project.group}:other-module:1.33.7")
  compile elastic('other-module', '1.33.7')

  // override the group too
  // a ProjectDependency('other-module')
  // or ExternalDependency('org.other:other-module:1.33.7')
  compile elastic('other-module', '1.33.7', 'org.other')
}
```

todo
====

* TODO add more 'elastic' methods for other ways of referencing projects and/or supplying the group/version
* TODO integration/verification tests with an actual gradle instance loading a project and us verifying everything. see if gradle's own integration tests are any help to making this happen in a clever way with little effort
