name := """quetzal-cassandra-integration"""

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.knoldus"

//JVM Options
parallelExecution in Test := false
fork in run := true

//Source
val cassandraConnector = "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0"
val sparkSql = "org.apache.spark" % "spark-sql_2.11" % "2.2.0"
val typeSafeConfig = "com.typesafe" % "config" % "1.3.2"
val jena = "org.apache.jena" % "apache-jena-libs" % "3.5.0" pomOnly()
val akkaHttpCore = "com.typesafe.akka" % "akka-http-core_2.11" % "10.0.5"
val akkaHttpExperimental = "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.11.1"
val akkaHttpSpray = "com.typesafe.akka" % "akka-http-spray-json-experimental_2.11" % "2.4.11.1"
val logger = "org.slf4j" % "slf4j-log4j12" % "1.7.16"

val sourceDependencies = Seq(cassandraConnector, sparkSql, typeSafeConfig,
  jena, akkaHttpCore, akkaHttpExperimental, akkaHttpSpray)
  .map(_.exclude("org.slf4j", "slf4j-log4j12"))

//Test
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % "test"
val mockito = "org.mockito" % "mockito-all" % "1.10.19" % "test"
val cassandraUnit = "org.cassandraunit" % "cassandra-unit" % "3.1.3.2" % "test"
val logBackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"

val testDependencies = Seq(scalaTest, mockito, cassandraUnit, logBackClassic)

//Overrides
val thrift = "org.apache.thrift" % "libthrift" % "0.9.2" pomOnly()
val guava = "com.google.guava" % "guava" % "19.0"
val netty = "io.netty" % "netty-all" % "4.0.44.Final"
val jacksonCore = "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
val jacksonDataBind = "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
val jacksonModule = "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.8.7"

val overridesDependencies = Seq(netty, thrift, guava, jacksonCore, jacksonDataBind, jacksonModule)
  .map(_.exclude("org.slf4j", "slf4j-log4j12"))
libraryDependencies ++= (sourceDependencies ++ testDependencies ++ Seq(logger))
dependencyOverrides ++= overridesDependencies
