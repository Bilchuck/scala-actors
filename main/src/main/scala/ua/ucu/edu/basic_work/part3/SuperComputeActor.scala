package ua.ucu.edu.basic_work.part3

import scala.language.postfixOps
import akka.actor.{OneForOneStrategy, ActorRef, Props, Actor}
import akka.actor.SupervisorStrategy.{Stop}
import akka.event.Logging
import scala.concurrent.duration._

class SuperComputeActor() extends Actor {

  val log = Logging(context.system, this)

  def receive = {
    //TODO
    case _ => {}
  }
}
