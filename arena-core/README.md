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

##### arena-core
Whole Arena api
```
hg clone https://bitbucket.org/uqbar-project/arena-core
```

##### arena-jface
Implementation of Arena over the framework jface
```
hg clone https://bitbucket.org/uqbar-project/arena-jface
```

##### Arena Examples
```
hg clone https://bitbucket.org/uqbar-project/arena-examples
```

####Aditional utilities

#####uqbar-domain
Utility to modelate domains. Allows anotate objects as observables. 
``` 
hg clone https://bitbucket.org/uqbar-project/uqbar-domain
```

#####arena-xtend
Extension to use arena with [xtend](http://www.eclipse.org/xtend/)
```
hg clone https://bitbucket.org/uqbar-project/arena-xtend
```

## Foundational Components for Observability / Transactional Aspects (not needed unless you'll change this foundations)


Then If you'll work on the transactional or observability aspects behind Arena, you must download the following projects:

#####apo-core

Small framework of aspects buildt on to javassists and made in scala.
This model the idea of point cut , joint point , and stuff

```
hg clone https://bitbucket.org/uqbar-project/apo-core
```

#####apo-poo
Over apo-core implements transparent observability over aspects

```
 hg clone https://bitbucket.org/uqbar-project/apo-poo
```
 
#####apo-pot
Implements transacsionality aspects over objects. (Transactional objects)

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

The proven environment is based on Eclipse Kepler version.

#### Based on Eclipse Kepler ####

// TODO: someone needs to write this down

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