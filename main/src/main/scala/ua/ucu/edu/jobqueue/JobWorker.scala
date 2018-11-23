package ua.ucu.edu.jobqueue

import java.nio.file.{Files, Paths}
import java.util.UUID

import akka.actor.{Actor, ActorLogging, Props}

import scala.io.Source
import scala.util.Random

object JobWorker {
  sealed trait JobResult
  case class JobSucceeded(id: UUID)
  case class JobFailed(id: UUID, reason: Throwable)

  def props() = Props(classOf[JobWorker])
}

class JobWorker extends Actor with ActorLogging {
  import JobExecutor._
  import JobWorker._

  override def preRestart(cause: Throwable, msg: Option[Any]) = {
    msg foreach { self ! _ } // resend failed message to itself
  }

  def receive = {
    case Job(id, Download, url, path, requester) => {
      log.info(s"Executing $id job")

      try {
        val content = downloadContent(url)
        createFile(content, path)

        requester ! JobSucceeded(id)
      } catch {
        case ex: Exception => {
          requester ! JobFailed(id, ex)

          throw ex
        }
      }
    }

    case _ => log.warning("Unrecognized command")
  }

  def downloadContent(url: String) = {
    // synthetic error here, but it can be real!
    // comment the line to disable failures 
    if(shouldIFail) throw new Exception("Couldn't download url")

    Source.fromURL(url).mkString
  }

  def createFile(content: String, path: String) = {
    Files.write(Paths.get(path), content.getBytes)
  }

  def shouldIFail: Boolean = Random.nextBoolean()
}
