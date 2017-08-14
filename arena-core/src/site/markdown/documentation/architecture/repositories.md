
# Repositories / Homes

A Repository stores objects of type T. The term **Home** in Arena stands for a place where objects live.
 
T must be a subclass of Entity, which defines the following behavior:

* a unique identifier (property id)

* hashCode() and equals() method are based on id property
 
* isNew() message to identify new objects 
 
* *template methods* validateCreate() and validateDelete() you can override to add validations
 
 
## Services 

* **create**: adds an object to that repository, so it can be retrieved afterwards
 
* **delete**: removes an object
 
* **update**: modify an object in position x or remove old object/add a new one in position x
 
* different kinds of search. Arena provides
 
    - **searchById**: retrieves an object T by unique identifier
   
    - **searchByExample**: passing a prototype object you retrieve all objects that match the criteria
   
    - **allInstances**: returns all objects in the repository    

## Arena Implementations

> *org.uqbar.commons.model.CollectionBasedRepo*

It is a memory-based collection, without persistence capabilities. Once application is closed,
all data is lost. It is intended for educational purposes, or in developments where persistence is not mandatory (like games).
 
<img src="../../images/architecture/collectionBasedHome.png" align="right" />


## Neo4J Implementation
 
[Arena Persistence](http://arena-pers.uqbar.org) defines an abstract class **PersistentRepo** that persists data into a Neo4J graph database.

* Usage

 Add the following dependency in your pom.xml
 
```xml
<dependency>
    <groupId>org.uqbar-project</groupId>
    <artifactId>arena-pers</artifactId>
    <version>3.6.1</version>
</dependency>
```

And check out this examples:

* [Cellphone customers - Xtend](https://github.com/uqbar-project/eg-celulares-arenapers-xtend)
   
* [Students - Xtend](https://github.com/uqbar-project/eg-alumnos-arenapers-xtend)   
 
* [Cellphone customers - Java](https://github.com/uqbar-project/eg-celulares-arenapers-java)

* [Cellphone customers - Scala](https://github.com/uqbar-project/eg-celulares-arenapers-scala) 

* [Students - Groovy](https://github.com/uqbar-project/eg-alumnos-arenapers-groovy)


# Things to do when defining a repo
 
* it is usually defined as a Singleton, because we want to provide a global point of access, so all updates and retrieves are centralized. You can use an [Application context](./applicationContext.html) object to configure different homes for testing and runtime environments. 
 
* you can simply define your own Repository implementation, or subclass any available implementation
 
  
## If you subclass CollectionBasedRepo:
  

* it is recommended to create a fixture or data set
 
* you can define your own search() method, or use a searchByExample() implementing getCriterio() method
 
* methods you must override 

    - **createExample()**: creates a prototypical T object, used for a search by example

    - **getEntityType()**: returns T class (mandatory because of Java erasures)

    - **Predicate\<T\> getCriterio(T example)**: defines a criteria to be used in a search by example. If you don't want to use it simply return a null value, otherwise you create a Predicate that returns a boolean indicating whether object matches the search.

 
# Example: Definition

 Example of a Repository for Customers defining a special search.
 
```java
// Java
@Override
public Class<Celular> getEntityType() {
    return Customer.class;
}

@Override
public Celular createExample() {
    return new Customer();
}

// Xtend
override def getEntityType() {
    typeof(Customer)
}
 
override def createExample() {
    new Customer
}
```
 
Defining a criteria for a search by example:
  
```xtend
override def Predicate<Customer> getCriterio(Customer example) {
    var result = this.allCriteria
    if (example.number != null) {
        result = new AndPredicate(result, this.getCriteriaByNumber(example.number))
    }
    if (example.name != null) {
        result = new AndPredicate(result, this.getCriteriaByName(example.name))
    }
    result
}
    
override getAllCriteria() {
    [ Customer customer | true ] as Predicate<Customer>
}
    
def getCriteriaByNumber(Integer number) {
    [ Customer customer | customer.number.equals(number) ] as Predicate<Customer>
}
 
def getCriteriaByName(String name) {
    [ Customer customer | customer.name.toLowerCase.contains(name.toLowerCase) ] as Predicate<Customer>
}
```

## Specific search implementation

Defining an ad-hoc search method:

```xtend
def search(Integer number, String name) {
    allInstances.filter [customer | this.match(number, customer.number) && 
        this.match(name, customer.name)].toList
}
```

Note that if you are using a persistent implementation, this is not a good idea, since you are bringing all database into memory before filtering elements.


## Bootstrap: Creating a fixture

You can create the data

* in an init() method of a repository object

* or you can use Arena *Bootstrap* interface, to decouple data initialization from create / retrieve / update / delete methods.

![bootstrap](../../images/architecture/arena-bootstrap.png)

* run() method contains initialization code for repository objects. Here is an example:

```xtend
// Xtend
class CelularesBootstrap extends CollectionBasedBootstrap {

    /**
     * Inits cellphone models repository outside its definition
     * as a decoupling strategy
     */
    override run() {
        val repoModels = RepoModels.instance
        ...
        val nokiaAsha = repoModels.create("NOKIA ASHA 501", 700f, true)
        val lgOptimusL5 = repoModels.create("LG OPTIMUS L5 II", 920f, false)
        ...
```

* isPending() method indicates whether run() method should be executed:

    - if using a memory-based repository, isPending() should always return true (because every time application stops all data is lost)

    - if using a persistent repository, we should avoid creating data twice


## Repository Usage    

(usually from an [Application Model](../state.html) or Panel) 

You can fire a search-by-example

```java
// Java
Cellphone searchedCellphone = new Cellphone(number, name);
List<Celular> results = repoCellPhones.searchByExample(searchedCellphone);

// Xtend
val searchedCellphone = new Cellphone => [
    number = _number
    name = _name
]
results = repoCellphones.searchByExample(searchedCellphone)
```

or a direct search:

```java
// Java
List<Cellphone> results = repoCellphones.search(number, name);

// Xtend
results = repoCellphones.search(number, name)
```

Updating / creating an object example:

```java
// Java
if (getModelObject().isNew()) {
    getRepoCellphones().create(getModelObject());
} else {
    getRepoCellphones().update(getModelObject());
}

// Xtend
if (modelObject.isNew) {
    repoCellphones.create(modelObject)
} else {
    repoCellphones.update(modelObject)
}
```
