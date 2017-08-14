
# Application Context

Arena's **ApplicationContext** is an implementation of [Service Locator](https://msdn.microsoft.com/en-us/library/ff648968.aspx) architectural pattern.
 
## Motivation

You have a Singleton object (like a [Repository / Home](./repositories.html)), or a configuration object and you want to use different implementations for testing and running an application.

## Implementation

An Application Context has the following services:
 
![appContext](../../images/architecture/appContext-class.png)

* Use *configureSingleton* and *getSingleton* to store and retrieve singleton objects
 

## Examples

### Instantiation

At the beginning of an application, we must create repository objects and let application context reference them:

1. In `*Application` or `Main Window`

```xtend
// Xtend
override protected Window<?> createMainWindow() {
    ApplicationContext.instance.configureSingleton(typeof(Whatever), RepoWhatever.instance)
    return new WhateverWindow(this)
}

// Java
@Override
protected Window<?> createMainWindow() {
    ApplicationContext.getInstance()
              .configureSingleton(Whatever.class, RepoWhatever.getInstance());
    return new BuscarCelularesWindow(this);
}
```

2. In a `Bootstrap` class:

```xtend
// Xtend
class CelularesBootstrap extends CollectionBasedBootstrap {
    new() {
       ApplicationContext.instance => [
           configureSingleton(typeof(Whatever), RepoWhatever.instance)
       ]
    }
```

3. When testing your application, you can define a *mocked* repository (if you don't want to save your changes to a database, you can use a CollectionBasedRepo):

```xtend
// Java
>>TestWhatever
@Before
@Override 
public void init() {
    ...
    ApplicationContext.getInstance().configureSingleton(Whatever.class, new MockRepoWhatever());

// Xtend
>>TestWhatever
@Before
override init() {
    ...
    ApplicationContext.instance.configureSingleton(typeof(Whatever), new MockRepoWhatever)

```
    
## Use  
    
[Application model](../state.html) uses the properly configured repo:

```xtend
def getRepoCustomers() {
    ApplicationContext.instance.getSingleton(typeof(Customer))
}
 
def void search() {
    results = repoCustomers().search(name)
    ...

```

So application model doesn't know if it is using a real or a mocked repository, as shown in this picture:  
      
![appContext](../../images/architecture/appContext.png)
