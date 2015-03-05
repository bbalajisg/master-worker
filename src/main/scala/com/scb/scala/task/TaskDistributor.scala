package com.scb.scala.task

import com.scb.scala.core.Master
import scala.collection.immutable.List

/**
 * Created by balab on 12/2/2015.
 */
class TaskDistributor extends Master{


  override def addWork(work:Any) = {

    // implement logic here.
    //with each actor a named parameter
    super.addWork(work)
  }

}
