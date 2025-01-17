import sbt.Keys.resolvers

name := "spark-cassandra-tests"
version := "1.0"

scalaVersion := "2.11.11"

val sparkVersion = "2.4.3"
val sparkTestingVersion = sparkVersion + "_0.12.0"
val sparkCassandraConnectorVersion = "2.4.0"
val cassandraVersion = "3.2"

updateOptions := updateOptions.value.withCachedResolution(true)
parallelExecution in test := false
//Forking is required for the Embedded Cassandra
fork in Test := true

resolvers ++= Seq(
  "cloudera" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Hortonworks" at "http://repo.hortonworks.com/content/repositories/releases/",
  "Hortonworks Groups" at "http://repo.hortonworks.com/content/groups/public/",
  "Apache Snapshots" at "https://repository.apache.org/content/repositories/releases/"
)

libraryDependencies ++= Seq(
  "com.holdenkarau" %% "spark-testing-base" % sparkTestingVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.10.0" % "test",
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-mllib" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-hive" % sparkVersion  % "test",
  "com.datastax.spark" %% "spark-cassandra-connector-unshaded" % sparkCassandraConnectorVersion % Provided,
  "com.datastax.spark" %% "spark-cassandra-connector-embedded" % sparkCassandraConnectorVersion % "test",
  "org.apache.cassandra" % "cassandra-all" % cassandraVersion % "test"
).map(_.exclude("org.slf4j", "log4j-over-slf4j")) // Excluded to allow Cassandra to run embedded
  .map(_.exclude("org.slf4j", "slf4j-log4j12"))
