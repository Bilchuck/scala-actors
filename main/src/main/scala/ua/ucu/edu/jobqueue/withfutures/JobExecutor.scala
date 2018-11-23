package ua.ucu.edu.jobqueue.withfutures

import java.util.UUID

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props}
import akka.routing.SmallestMailboxPool

import scala.concurrent.duration._

object JobExecutor {
  sealed trait JobActions
  case object Download extends JobActions

  case class DownloadRequest(
    url: String,
    path: String
  )

  case class Job(
    id: UUID,
    action: JobActions,
    url: String,
    path: String,
    requester: ActorRef
  )

  def props() = Props(classOf[JobExecutor])
}

class JobExecutor extends Actor with ActorLogging {
  import JobExecutor._
  import JobWorker._

  val poolSupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 20, withinTimeRange = 1 minute) {
      case _ => Restart
    }

  val pool = context.actorOf(SmallestMailboxPool(5, supervisorStrategy = poolSupervisorStrategy).props(JobWorker.props()), "pool")

  def receive = {
    case request: DownloadRequest => {
      pool ! Job(UUID.randomUUID(), Download, request.url, request.path, self)
    }

    case JobSucceeded(id) => {
      log.info(s"Job $id finished successfully!")
    }

    case JobFailed(id, ex) => {
      log.info(s"Job $id failed, reason: $ex. Restarting!")
    }

    case _ => log.warning("Unrecognized command")  
  }
}
