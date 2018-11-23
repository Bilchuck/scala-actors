package ua.ucu.edu.basic_work.part2

import akka.actor.{Actor, ActorRef, Terminated}
import akka.event.Logging
import ua.ucu.edu.basic_work.work.RiskyWork


class ClientActor(computeSupervisor: ActorRef, resultActor: ActorRef, work: List[RiskyWork]) extends Actor {
  val log = Logging(context.system, this)

  def receive = {
    //TODO
    case _ => {}
  }
}
