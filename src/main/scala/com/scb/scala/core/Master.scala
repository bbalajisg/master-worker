package com.scb.scala.core

import akka.actor.{Actor, ActorLogging, ActorRef, Terminated}

/**
 * Created by balab on 10/2/2015.
 */
class Master extends Actor with ActorLogging {
  import com.scb.scala.core.MasterWorkerProtocol._

  import scala.collection.mutable.{Map, Queue}
 
  // Holds known workers and what they may be working on
  val workers = Map.empty[ActorRef, Option[Tuple2[ActorRef, Any]]]

  // Holds the incoming list of task to be done as well as the memory of who asked for it
  val workQ = Queue.empty[Tuple2[ActorRef, Any]]

  def receive = {
    // Worker is alive. Add him to the list, watch him for  death and let him know if there's task to be done
    case WorkerCreated(worker) => workerCreated(worker)

    // A worker wants more task. If we know about him, he's not currently doing anything, and we've got something to do, give it to him.
    case WorkerRequestsWork(worker) => workerRequestsWork(worker)

    // Worker has completed its work and we can clear it out
    case WorkIsDone(worker) => workIsDone(worker)

    // A worker died.  If he was doing anything then we need
    // to give it to someone else so we just add it back to the
    // master and let things progress as usual
    case Terminated(worker) => terminated(worker)

    // Anything other than our own protocol is "work to be done"
    case work =>  addWork(work)

    case  MasterStarted => println("Master Started...")
  }

  // Notifies workers that there's task available, provided they're not already working on something
  def notifyWorkers(): Unit = {
    if (!workQ.isEmpty) {

      workers.foreach {
        case (worker, m) if (m.isEmpty) => worker ! WorkIsReady
        case _ =>   println
      }
    }
  }

  // nodes register with master
  def workerCreated(worker: ActorRef) = {
    context.watch(worker)
    workers += (worker -> None)
    notifyWorkers()
  }

  //Node request for a work
  def workerRequestsWork(worker: ActorRef) = {

    if (workers.contains(worker)) {
      if (workQ.isEmpty) {
        worker ! NoWorkToBeDone
      } else if (workers(worker) == None) {
        val (workSender, work) = workQ.dequeue()
        workers += (worker -> Some(workSender -> work))
        worker.tell(WorkToBeDone(work), workSender)
      }
    }
  }

  def terminated(worker: ActorRef) = {

    if (workers.contains(worker) && workers(worker) != None) {
      log.error("Died while processing {}", worker, workers(worker))
      // Send the work that it was doing back to ourselves for processing
      val (workSender, work) = workers(worker).get
      self.tell(work, workSender)
    }
    workers -= worker
  }

   def workIsDone(worker: ActorRef) = {

     if (!workers.contains(worker))
       log.error("Says it's done work but we didn't know about him", worker)
     else
       workers += (worker -> None)

   }


  def addWork(work:Any) = {
    log.info("Queueing..... {}", work)
    workQ.enqueue(sender -> work)
    notifyWorkers()
  }


}