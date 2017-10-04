# NumberWords

A small utility for converting numbers into their english language equivalents.

This implementation is composed of two primary components:
 - A simple rope data structure for efficiently constructing strings.
 - The translator implementation that breaks numbers into segments before translating
   those segments into english.

## Building, Testing & Running

This project uses [SBT](http://www.scala-sbt.org/) for building. Some of the commands you can use are:

`sbt package` - Builds the JAR file.

`sbt test` - Runs the unit tests.

`sbt run NUMBER+` - Runs the application, translating any numbers you supply.

`sbt clean coverage test coverageReport` - Generates a report detailing test case coverage.

`sbt assembly` - Generates an uber-JAR that can be run directly with `java -jar ...`