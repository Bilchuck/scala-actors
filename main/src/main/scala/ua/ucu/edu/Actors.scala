package ua.ucu.edu

import akka.actor.{Actor, ActorRef}
import akka.routing.{DefaultResizer, RoundRobinPool, RoundRobinRoutingLogic, Router}

object Actors {

  // Message Protocol
  case class Greeting(who: String)
  case class Respond(gr: Greeting)

  class GreetingActor extends Actor {
    // Put mutable state in here..

    def receive: Receive = {
      // Define the behavior here..
      case Greeting(who) => println(s"Hello $who")
      case Respond(_) => sender ! "hey"
    }
  }

  import akka.actor.ActorSystem
  import akka.actor.Props

  val system: ActorSystem = ActorSystem.create("MySystem")
  val greeter: ActorRef = system.actorOf(Props[Greeting], "greeter")

  import akka.actor.Props

  val nrOfInstances = 5
  val router: ActorRef = system.actorOf(
    RoundRobinPool(nrOfInstances)
      .withResizer(DefaultResizer(2, 15))
      .props(Props[GreetingActor]))

  object A {
    object ThreadsUnprotectedUid extends App {
      var uidCount = 0L
      def getUniqueId() = this.synchronized {
        val freshUid = uidCount + 1
        uidCount = freshUid
        freshUid
      }
      def printUniqueIds(n: Int): Unit = {
        val uids = for (i<- 0 until n) yield getUniqueId()
        log(s"Generated uids: $uids")
      }
      val t = thread { printUniqueIds(5) }
      printUniqueIds(5)
      t.join()
    }




  }
}
