package ua.ucu.edu.handson.workshop.part3

import scala.language.postfixOps
import akka.actor.{Actor, ActorRef, OneForOneStrategy, Props}
import akka.actor.SupervisorStrategy.Stop
import akka.event.Logging
import ua.ucu.edu.basic_work.work.RiskyWork

import scala.concurrent.duration._

class SuperComputeActor() extends Actor {
  val log = Logging(context.system, this)

  override val supervisorStrategy =
      OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute, loggingEnabled = false) {
        case _ => {
          log.info("Stopping problem actor!")
          Stop
        }
      }

  def receive = {
    case riskyWork : RiskyWork => {
      val worker: ActorRef = context.actorOf(Props[Worker])
      worker.tell(riskyWork, sender())
    }
  }
}
