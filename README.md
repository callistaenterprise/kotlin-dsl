# Kotlin DSL demo

A simple Kotlin DSL demonstrating how such DSLs could be constructed. Furthermore two Spring Boot implementations, one in Kotlin, one in Java,
using the DSL.

The Spring Boot applications are configured to persist the file `src/main/resources/dsl/demodata.orderdsl.kts` to the database at start up. The
applications also monitor the `/tmp` directory on the local file system for files ending in `orderdsl.kts`, and persist the files if found.

Since the file suffix `orderdsl.kts` is registered in the configuration, these files can be edited directly in IntelliJ IDEA with full
editor support.


## Instructions

- From the root directory, build the whole project: `./gradlew build`.

- Import the project into IntelliJ. When the import is done, open one of the files `demodata.orderdsl.kts`. The IDE should recognize this
  file as of the right type, and should give you editing support. If this doesn't work out of the box, check if the association between file
  suffix and script can be configured in the IntelliJ settings: Languages & Frameworks -> Kotlin -> Kotlin scripting.
  
- Start docker compose in the root directory to get a postgres database running on port 15432: `docker compose up -d`.

- Choose to go down into either the Kotlin (`kotlin-boot`) or the Java version (`java-boot`) of Spring Boot, and start it with `../gradlew
  bootRun`. The application should start, and the contents of `src/main/resources/dsl/demodata.orderdsl.kts` should be persisted to the
  database.
  
- Make a copy of `src/main/resources/dsl/demodata.orderdsl.kts` and call it something else, such as `demodata2.orderdsl.kts`. Copy the new
  file to the `/tmp/` folder on your file system. The contents of the file should be persisted in the database.


## Some notes

- The file
  `order-dsl/src/main/resources/META-INF/kotlin/script/templates/se.callistaenterprise.kotlindsl.dsl.scriptdef.OrderDslScript.classname` is
  empty, but is the way Kotlin registers the start of the DSL interpreting logic. In other words, the class name mentioned in this file's
  name makes the Kotlin compiler (as well as IntelliJ) register files with suffix `orderdsl.kts` to be interpreted with OrderDsl as an
  implicit receiver.
  
- Postgres, run through Docker compose, is set up to create files in the directory `orderdsl_data` in the project root. This directory can be
  deleted to start anew with a clean database. The running database can be accessed with `psql -h localhost -p 15432 -U orderdsl orderdsl`,
  password `secret`.
