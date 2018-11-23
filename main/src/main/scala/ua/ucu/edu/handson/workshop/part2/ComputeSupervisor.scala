package ua.ucu.edu.handson.workshop.part2

import scala.language.postfixOps
import akka.actor.SupervisorStrategy.{Restart, Resume, Stop}
import akka.actor.{Actor, ActorRef, OneForOneStrategy}
import akka.event.Logging
import ua.ucu.edu.handson.basic_work.work.RiskyWorkException

import scala.concurrent.duration._


case class CreateComputeActor(actorName: String)

class ComputeSupervisor(computeActorFactory: ComputeActorFactory) extends Actor {
  val log = Logging(context.system, this)

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute, loggingEnabled = false) {
      case _: ArithmeticException => {
        log.error("Resuming compute actor due to arithmetic exception")
        Resume
      }
      case e: RiskyWorkException => {
        log.error("Restarting compute actor due to risky work exception. Reason: {}", e.msg)
        Restart
      }
      case e: Exception => {
        log.error("Stopping compute actor due to exception. Reason: {}", e.getMessage)
        Stop
      }
    }

  def receive = {
    case startComputeActor : CreateComputeActor => {
      val computeActor: ActorRef = computeActorFactory.create(context, startComputeActor.actorName)

      sender ! computeActor

      log.info("Started compute actor with name {}", startComputeActor.actorName)
    }
  }
}
