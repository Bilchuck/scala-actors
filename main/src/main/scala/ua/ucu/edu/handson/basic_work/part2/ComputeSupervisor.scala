package ua.ucu.edu.handson.basic_work.part2

import scala.language.postfixOps
import akka.actor.SupervisorStrategy.{Restart, Resume, Stop}
import akka.actor.{OneForOneStrategy, ActorRef, Actor}
import akka.event.Logging
import scala.concurrent.duration._


case class CreateComputeActor(actorName: String)

class ComputeSupervisor(computeActorFactory: ComputeActorFactory) extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    //TODO
    case _ => {}
  }
}
