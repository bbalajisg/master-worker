package com.scb.scala.core

import akka.actor.ActorRef


/**
 * Created by balab on 10/2/2015.
 */
object MasterWorkerProtocol {
  //Messages from Workers
  case class WorkerCreated(worker: ActorRef)
  case class WorkerRequestsWork(worker: ActorRef)
  case class WorkIsDone(worker: ActorRef)
 
  //Messages to Workers
  case class WorkToBeDone(work: Any)
  case object WorkIsReady
  case object NoWorkToBeDone

  //Messasge on start
  case object WorkerStarted
  case object MasterStarted

}