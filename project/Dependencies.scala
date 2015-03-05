import sbt._

object Version {

  val akka      = "2.4-SNAPSHOT"

}

object Library {
  val akkaActor   =  "com.typesafe.akka" % "akka-actor_2.11" % Version.akka
  val akkasl4j    = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  val akkatestkit = "com.typesafe.akka" %% "akka-testkit" % Version.akka
  val akkaRemote  = "com.typesafe.akka" %% "akka-remote" % Version.akka

}


object Dependencies {

    import Library._

    val libraries = List(
      akkaActor,
      akkasl4j,
      akkatestkit,
      akkaRemote
    )
}