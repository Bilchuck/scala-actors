package ua.ucu.edu.handson.workshop.part2

import akka.actor.{Actor, ActorRef, Terminated}
import akka.event.Logging
import ua.ucu.edu.handson.basic_work.work.{RiskyWork, RiskyWorkResult}


class ClientActor(computeSupervisor: ActorRef, resultActor: ActorRef, work: List[RiskyWork]) extends Actor {
  val log = Logging(context.system, this)

  override def preStart() = {
    computeSupervisor ! CreateComputeActor("computeActor")
  }

  def receive = {
    case computeActor: ActorRef => {
      context.watch(computeActor)
      work.foreach(w => computeActor ! w)
    }
    case result: RiskyWorkResult => {
      resultActor ! result
    }
    case Terminated => {
      log.error("Compute actor terminated, terminating self")
      context.stop(self)
    }
  }
}
