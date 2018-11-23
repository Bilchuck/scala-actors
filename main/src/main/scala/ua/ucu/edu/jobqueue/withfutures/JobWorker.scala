package ua.ucu.edu.jobqueue.withfutures

import java.nio.file.{Files, Paths}
import java.util.UUID

import akka.actor.{Actor, ActorLogging, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Random, Success}

object JobWorker {
  import JobExecutor._

  sealed trait JobResult
  case class JobSucceeded(id: UUID)
  case class JobFailed(id: UUID, reason: Throwable)

  case class FailWith(ex: Throwable, job: Job)
  
  def props() = Props(classOf[JobWorker])
}

class JobWorker extends Actor with ActorLogging {
  import JobWorker._
  import JobExecutor._

  override def preRestart(cause: Throwable, msg: Option[Any]) = {
    msg foreach {
      case FailWith(_, job) => self ! job // resend failed message to itself
      case _ =>  
    } 
  }

  def receive = {
    case job @ Job(id, Download, url, path, requester) => {
      log.info(s"Executing $id job")

      val result = for {
        content <- downloadContent(id, url)
        _ <- createFile(content, path)
      } yield id

      result onComplete {
        case Success(_) => {
          requester ! JobSucceeded(id)
        }
        case Failure(ex) => {
          requester ! JobFailed(id, ex)

          self ! FailWith(ex, job)
        }
      }
    }

    case fw: FailWith => throw fw.ex  

    case _ => log.warning("Unrecognized command")
  }

  def downloadContent(id: UUID, url: String): Future[String] = Future {
    // synthetic error here, but it can be real!
    // comment the line to disable failures 
    if(shouldIFail) throw new Exception("Couldn't download url")

    Source.fromURL(url).mkString
  }

  def createFile(content: String, path: String) = Future {
    Files.write(Paths.get(path), content.getBytes)
  }

  def shouldIFail: Boolean = Random.nextBoolean()
}
