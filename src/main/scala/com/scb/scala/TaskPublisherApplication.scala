package com.scb.scala

import akka.actor.{ActorLogging, Actor, Props, ActorSystem}
import com.scb.scala.PublisherProtocol.TaskCreated

import com.typesafe.config.ConfigFactory
import scala.collection.immutable.List

/**
 * Created by balab on 12/2/2015.
 */
object TaskPublisherApplication {

  def main(args: Array[String]) {

    import PublisherProtocol.TaskCreated
    val taskActorSystem =  ActorSystem("PublisherSystem", ConfigFactory.load("publisher") )
    val taskActor = taskActorSystem.actorOf(Props[PublishActor], "PublishActor")
    val list = List( List("fileName1.txt","fileName2.txt"), List("fileName3.txt", "fileName4.txt"),List( "fileName5.txt", "fileName6.txt"), List( "fileName7.txt", "fileName8.txt") )

    taskActor ! TaskCreated(list)

  }


  class PublishActor extends Actor with ActorLogging{


    val master = context.actorSelection("akka.tcp://TaskDistributorActorSystem@10.5.3.35:2552/user/TaskDistributorActor")

    def receive: Receive = {

      case TaskCreated(taskList) =>  master ! taskList
      case _ =>

    }
  }
}


object PublisherProtocol {

  //Messages to Master
  case class TaskCreated(taskList:Any)


}

