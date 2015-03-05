package com.scb.scala

import com.scb.scala.task.TaskDistributor


/**
 * Created by balab on 11/2/2015.
 */
object TaskDistributorApplication {

  import akka.actor.{Props, ActorSystem}
  import com.typesafe.config.ConfigFactory

  def main(args: Array[String]) = {
     startTaskDistributorApplication()
  }

  def startTaskDistributorApplication() : Unit = {

    val taskDistributorActorSystem = ActorSystem("TaskDistributorActorSystem", ConfigFactory.load("taskmaster"))
    val taskDistributorActor = taskDistributorActorSystem.actorOf(Props[TaskDistributor], "TaskDistributorActor")
  }
}
