

organization := "com.scb.core"

name :="scb-core"

version := "1.0"

scalaVersion := "2.11.5"

assemblyJarName in assembly :=  name +".jar"

libraryDependencies ++= Dependencies.libraries

resolvers ++= Seq(
  "akka" at "http://repo.akka.io/snapshots",
  "sonatype" at "https://oss.sonatype.org/content/repositories/releases",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/",
  "typesafe releases" at "http://repo.typesafe.com/typesafe/releases",
  "sbt-assembly-resolver-0" at "http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases",
  "sbt-plugin-snapshots" at "http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/" ,
  "sbt-plugin-release"  at  "http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/" ,
  "typesafe-release"  at  "http://repo.typesafe.com/typesafe/releases/" ,
  "neo4j-release"  at  "http://m2.neo4j.org/content/repositories/releases" ,
  "neo4j-public-repository" at  "http://m2.neo4j.org/content/groups/public" ,
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases" ,
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots" ,
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/" ,
  "Maven2 Repo"  at "http://download.java.net/maven/2/"
)

