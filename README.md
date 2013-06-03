# Geophile/Erdo Adapter

This library implements
[Geophile](https://github.com/geophile/geophile)'s Index abstraction
using the [Erdo](https://github.com/geophile/erdo) key/value store. In
other words, it allows Erdo to be used as a spatial index, and
participate in spatial joins.

## Installation

Geophile-Erdo can be built from source using [maven](http://maven.apache.org):

        mvn install

This creates `target/geophile-erdo-1.0.jar`.

(Geophile-Erdo depends on both
[Geophile](https://github.com/geophile/geophile) and
[Erdo](https://github.com/geophile/erdo). These dependencies are
expressed in this module's pom.xml.)

To create Javadoc files:

        mvn javadoc:javadoc

These files are located in `target/site/apidocs`.

## Usage

Geophile-Erdo is an adapter between Geophile and Erdo. The only direct
usage of this library by an application would be to allocate an
Erdo-based index. There are two APIs for doing this.

To create an Erdo index doing blind updates:

        OrderedMap map;  // An Erdo ordered map
        Index index;     // A Geophile index
        ...
        index = ErdoIndex.withBlindUpdates(map);

To create an Erdo index doing non-blind updates:

        index = ErdoIndex.withOrdinaryUpdates(map);

Erdo can usually do blind updates faster than non-blind
updates. However, blind updates are less informative about the
pre-update state of the database. See Geophile documentation for more
details on blind vs. non-blind updates.

Geophile-Erdo includes two implementations of Geophile's Index
behavior test,
`com.geophile.z.index.IndexTestBase`:

* com.geophile.erdoindex.ErdoIndexBlindUpdateTest tests blind updates
* com.geophile.erdoindex.ErdoIndexOrdinaryUpdateTest tests ordinary updates
