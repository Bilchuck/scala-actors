package ua.ucu.edu.basic

import akka.actor.Actor


class SimpleActor extends Actor {

  def receive = {
    case msg => println(s"${self.path} - $msg")
  }

}

object SimpleActorTest extends App {
  import akka.actor._

  /** Main **/

  val system = ActorSystem("intro-akka-demo")

  val simpleActor = system.actorOf(Props[SimpleActor], name = "simple")

  simpleActor ! "Yo! This is a message"
  // akka://intro-akka-demo/user/simple - Yo! This is a message

  simpleActor ! List(1,2,3)
  // akka://intro-akka-demo/user/simple - List(1, 2, 3)
}
