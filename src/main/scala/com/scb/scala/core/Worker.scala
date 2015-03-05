package com.scb.scala.core

import akka.actor.{Actor, ActorLogging, ActorPath, ActorRef}


/**
 * Created by balab on 10/2/2015.
 */

abstract class Worker(masterLocation: String)  extends Actor with ActorLogging {
  import com.scb.scala.core.MasterWorkerProtocol._
 
  //We need to know where the master is
  val master = context.actorSelection(masterLocation)
 
  //This is how our derivations will interact with us.  It allows derivations to complete work asynchronously
  case class WorkComplete(result: Any)
 
  //Required to be implemented
  def doWork(workSender: ActorRef, work: Any): Unit
 
  //Notify the Master that  I am born & alive  :)
  override def preStart() = master ! WorkerCreated(self)


  def working(work: Any): Receive = {
    // Pass... we're already working
    case WorkIsReady => println("work is ready")
    // Pass... we're already working
    case NoWorkToBeDone =>  println("No work to be done")
    // Pass... we shouldn't even get this
    case WorkToBeDone(_) =>
      log.error("Yikes. Master told me to do work, while I'm working.")
    // Our derivation has completed its task
    case WorkComplete(result) =>
      log.info("Work is complete.  Result {}.", result)
      master ! WorkIsDone(self)
      master ! WorkerRequestsWork(self)
      // We're idle now
      context.become(idle)
  }

  def idle: Receive = {
    // Master says there's work to be done, let's ask for it
    case WorkIsReady =>
      log.info("Requesting work")
      master ! WorkerRequestsWork(self)
    // Send the work off to the implementation
    case WorkToBeDone(work) =>
      log.info("Got work {}", work)
      doWork(sender, work)
      context.become(working(work))
    // We asked for it, but either someone else got it first, or there's literally no work to be done
    case NoWorkToBeDone =>

    case  _ => println("Worker .........")
  }
 
  def receive = idle
}