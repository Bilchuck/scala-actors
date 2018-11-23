package ua.ucu.edu.basic

import akka.actor._
import akka.routing
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

object RouterExample extends App {

  class Master extends Actor {
    var router = {
      val routees = (1 to 10).map { i: Int =>
        val r = context.actorOf(Props(classOf[Worker], i))
        context watch r
        ActorRefRoutee(r)
      }
      Router(RoundRobinRoutingLogic(), routees)
    }

    def receive = {
      case Terminated(a) ⇒
        router = router.removeRoutee(a)
        val r = context.actorOf(Props[Worker])
        context watch r
        router = router.addRoutee(r)
      case x ⇒
        router.route(x, sender())
    }
  }

  class Worker(i: Int) extends Actor with ActorLogging {

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      log.info("Worker {} is about to get restarted", i)
    }

    override def postRestart(reason: Throwable): Unit = {
      log.info("Worker {} is restarted", i)
    }
    def receive: Receive = {
      case x =>
        if (i == 9) throw new RuntimeException("fglfjg")
        log.info("actor {} is processing {}", i, x)
        sender ! x
    }

  }

  val system = ActorSystem("akka-patterns")

  val ref = system.actorOf(Props[Master], "test-master")

  1 to 20 foreach (x => ref ! x)

  scala.io.StdIn.readLine("finish?")

  system.terminate()
}
