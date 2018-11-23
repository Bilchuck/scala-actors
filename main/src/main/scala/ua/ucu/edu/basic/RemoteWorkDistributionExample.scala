package ua.ucu.edu.basic

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory

import scala.io.StdIn

object RemoteWorkDistributionExample extends App {

  class Master(worker: ActorRef) extends Actor with ActorLogging {

    def receive: Receive = {
      case e =>
        log.info("Distributing work {}", e)
        worker ! e
    }
  }

  class Worker extends Actor with ActorLogging {

    def receive: Receive = {
      case x =>
        log.info("received {} at {}", x, self)
    }
  }

  val mainConfig = ConfigFactory.load

  val masterSystem = ActorSystem("master", mainConfig.getConfig("master"))

  val worker = masterSystem.actorOf(Props[Worker].withRouter(FromConfig), name = "remote-worker")

  //This will be created in remote node
  val remoteSystem1 = ActorSystem("remote1", mainConfig.getConfig("remote1"))
  val remoteSystem2 = ActorSystem("remote2", mainConfig.getConfig("remote2"))

  val master = masterSystem.actorOf(Props(new Master(worker)), name = "master")

  1 to 5 foreach (master ! _)

  StdIn.readLine("Finish?")

  remoteSystem1.terminate()
  remoteSystem2.terminate()
  masterSystem.terminate()

}
