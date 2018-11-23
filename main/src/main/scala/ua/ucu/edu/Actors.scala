package ua.ucu.edu

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

object Actors extends App {

  /* protocol */
  case class Hey(msg: String)
//  case object WhatsMyName
//  case class Name(name: String)
  case class MakeChild(name: String)

  class HeyActor extends Actor with ActorLogging {

    override def receive: Receive = {
      case Hey(msg) => log.info(s"==\n==\n==Hey, $msg")
    }
  }

  implicit val system: ActorSystem = ActorSystem("test-system")

  val actor = system.actorOf(Props[HeyActor])

  actor ! Hey("dude")

  system.actorSelection("/user/dude")

  system.terminate()
}
