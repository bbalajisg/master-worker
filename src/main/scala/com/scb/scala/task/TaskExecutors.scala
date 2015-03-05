package com.scb.scala.task

import akka.actor.ActorRef
import com.scb.scala.core.Worker

/**
 * Created by balab on 11/2/2015.
 */
class TaskExecutors(masterLocation: String) extends Worker(masterLocation){

  //Required to be implemented
  override def doWork(workSender: ActorRef, work: Any): Unit = {

  }
}
