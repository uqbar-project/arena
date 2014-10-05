# Arena Developer Guide #

## Requirements and General Notes ##

* **JDK 1.7**: currently it doesn't work out-of-the-box with Java8
* **Scala 2.11**  <<<< recently migrated from 2.10 on Oct 4 2014.

## Checkout Projects/Repos.

It's useful to create a folder to hold all the repos (projects), but well, optional:

```
mkdir arena
cd arena
```

##### Arena (core)
Whole Arena api
```
hg clone https://bitbucket.org/uqbar-project/arena-core
```

##### Arena JFace
Implementation of Arena over the framework jface
```
hg clone https://bitbucket.org/uqbar-project/arena-jface
```

##### Arena Examples
```
hg clone https://bitbucket.org/uqbar-project/arena-examples
```

#### Additional Utilities

##### Uqbar Domain
Utility to model domains. Allows to annotate objects as @Observables. Also contains some API for persistence without coupling it to any implementation. 
``` 
hg clone https://bitbucket.org/uqbar-project/uqbar-domain
```

##### Arena-xtend
Extensions to use arena with [xtend](http://www.eclipse.org/xtend/). Provides kind of an internal DSL for building bindings easily and in a declarative way.
```
hg clone https://bitbucket.org/uqbar-project/arena-xtend
```

## Foundational Components for Observability / Transactional Aspects (not needed unless you'll change this foundations)

Then If you'll work on the transactional or observability aspects behind Arena, you must download the following projects:

##### apo-core

**Aspects for Pure Objects:** Small AOP framework on top of [Javassist](http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/). It creates a more abstract general layer on top of javassist in order to express all AOP concepts: pointcuts, join-points, aspects, etc.

```
hg clone https://bitbucket.org/uqbar-project/apo-core
```

#####apo-poo

Pure Observable Objects: implements transparent objects observability over AOP. Based on apo-core.

```
 hg clone https://bitbucket.org/uqbar-project/apo-poo
```
 
#####apo-pot
Implements a transparent transactions mechanism for objects through AOP. (Transactional objects)

``` 
hg clone https://bitbucket.org/uqbar-project/apo-pot
```

#####apo-parent

Aditional configuration for apo-core, apo-poo, apo-pot
 
```
hg clone https://bitbucket.org/uqbar-project/apo-parent
``` 
In addition optionally you can checkout some **parent projects** if you need to change them:

* hg clone https://bitbucket.org/uqbar-project/uqbar-parent-project
* hg clone https://bitbucket.org/uqbar-project/uqbar-scala-parent


### IDE ###

As we use maven, you can use whatever IDE you want.
Although, we can suggest some proven Environment:

* Eclipse Kepler with ScalaIDE
* Eclipse Luna with ScalaIDE
* IntelliJIDEA with Scala plugin

The proven environment is based on Eclipse Kepler version.
Here's a sample environment

#### Based on latest Eclipse Version (LUNA) #####

* Install eclipse Luna
* **Scalaide**:
* + Install scalaide from the following update site: http://download.scala-ide.org/nightly-scala-ide-luna-211x
* + Install m2e-scala from update site: http://alchim31.free.fr/m2e-scala/update-site

#### Maven configuration ####

You need to **configure your maven settings** in order to connect to Uqbar's repositories, both to retrieve artifacts, but also to deploy new versions:

* vi ~/.m2/settings.xml

(if .m2 folder is not already created, then create it)

And put this:

```
#!xml

 <settings xmlns="http://maven.apache.org/POM/4.0.0"  
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

   <servers>
        <server>
            <id>uqbar-releases</id>
            <username>mvn@uqbar-wiki.org</username>
            <password>PASSWORD_HERE</password>
        </server>
        <server>
            <id>uqbar-snapshots</id>
            <username>mvn@uqbar-wiki.org</username>
            <password>repomaven88</password>
        </server>
        <server>
            <id>sites-uqbar-project.org</id>
            <username>sites@uqbar-wiki.org</username>
            <password>PASSWORD_HERE</password>
        </server>
    </servers>
   <profiles>
     <profile>
       <id>uqbar-wiki</id>
       <repositories>
         <repository>
           <id>uqbar-wiki.org-releases</id>
           <name>uqbar-wiki.org-releases</name>
           <url>http://uqbar-wiki.org/mvn/releases</url>
         </repository>
         <repository>
           <snapshots/>
           <id>uqbar-wiki.org-snapshots</id>
           <name>uqbar-wiki.org-snapshots</name>
           <url>http://uqbar-wiki.org/mvn/snapshots</url>
         </repository>
       </repositories>
     </profile>
   </profiles>
   <activeProfiles>
     <activeProfile>uqbar-wiki</activeProfile>
   </activeProfiles>
 </settings>
```

**NOTE THAT YOU MUST ASK SOME UQBAR MEMBER FOR THE PASSWORD AND REPLACE THERE THE "PASSWORD_HERE".
**