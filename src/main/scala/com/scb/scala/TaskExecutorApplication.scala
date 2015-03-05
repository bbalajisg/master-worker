package com.scb.scala

import akka.actor.{ActorSystem, Props}
import com.scb.scala.task.TaskExecutors
import com.typesafe.config.ConfigFactory


/**
 * Created by balab on 11/2/2015.
 */

/**
 * Assuming that each task will be running on a node / PC / workstation
 */
object TaskExecutorApplication {


  def main(args: Array[String]) {
    startTaskExecutor()
  }


  def startTaskExecutor() = {

    val taskActorSystem = ActorSystem("TaskSystem", ConfigFactory.load("worker") )
    val masterActorPath =  "akka.tcp://MasterActorSystem@10.5.3.35:2552/user/MasterActor"
    val taskActor = taskActorSystem.actorOf(Props(new TaskExecutors(masterActorPath )), "taskActor")

  }

}
