# Arena Developer Guide #

## Requirements and General Notes ##

* **JDK 1.7**: currently it doesn't work out-of-the-box with Java8
* **Scala 2.11**  <<<< recently migrated from 2.10 on Oct 4 2014.

**Note:** *We are currently working on it, but we still don't have a C.I. in order to deploy new versions of artifacts every time someone pushes a change to master.
So, this means that, every time you push something, you'll also need to manually deploy it with "mvn deploy".
This also means that, probably, for now, if you want to have it all working straight right after checkout, then, go ahead and check out ALL projects listed below, instead of just picking some of them.*

## Checkout Projects/Repos.

It's useful to create a folder to hold all the repos (projects), but well, optional:

```
mkdir arena
cd arena
```
##### Main Arena Projects

* **arena-core:** contains all the components and Arena API for arena users. Although it doesn't have any implementation, so you need to use an implementation for this. Currently we only have one: arena-jface. 
* **arena-jface:** Implementation of Arena on top of SWT, JFace, and JFace Databindings.
* **arena-examples:** Contains some examples small apps for internal manual testing.

In order to check them out:
```
hg clone https://bitbucket.org/uqbar-project/arena-core
hg clone https://bitbucket.org/uqbar-project/arena-jface
hg clone https://bitbucket.org/uqbar-project/arena-examples
```

#### Additional Utilities

* **Uqbar Domain:** Utility to model domains. Allows to annotate objects as @Observables. Also contains some API for persistence without coupling it to any implementation. 
* **Arena-xtend:** Extensions to use arena with [xtend](http://www.eclipse.org/xtend/). Provides kind of an internal DSL for building bindings easily and in a declarative way.

Check-out:
``` 
hg clone https://bitbucket.org/uqbar-project/uqbar-domain
hg clone https://bitbucket.org/uqbar-project/arena-xtend
```

## Foundational Components for Observability / Transactional Aspects (not needed unless you'll change this foundations)

Then If you'll work on the transactional or observability aspects behind Arena, you must download the following projects:

* **apo-core: (Aspects for Pure Objects):** Small AOP framework on top of [Javassist](http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/). It creates a more abstract general layer on top of javassist in order to express all AOP concepts: pointcuts, join-points, aspects, etc.
* **apo-poo (Pure Observable Objects):** implements transparent objects observability over AOP. Based on apo-core.
* **apo-pot (Pure Objects Transactions):** Implements a transparent transactions mechanism for objects through AOP. (Transactional objects).
* **apo-parent:** Additional configuration for apo-core, apo-poo, apo-pot

Checkout:
```
hg clone https://bitbucket.org/uqbar-project/apo-core
hg clone https://bitbucket.org/uqbar-project/apo-poo
hg clone https://bitbucket.org/uqbar-project/apo-pot
hg clone https://bitbucket.org/uqbar-project/apo-parent
```

In addition optionally you can checkout some **parent projects** if you need to change them:

```
hg clone https://bitbucket.org/uqbar-project/uqbar-parent-project
hg clone https://bitbucket.org/uqbar-project/uqbar-scala-parent
```

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