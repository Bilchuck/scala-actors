package ua.ucu.edu.basic

import akka.actor._

class GreetingActor_1 extends Actor {

  def receive: Receive = {
    case message : String => println("Hello " + message)
  }

}

object GreetingActor_1 extends App {
  val system = ActorSystem("MySystem")
  val actorRef = system.actorOf(Props(new GreetingActor_1), "greeter1")
  actorRef ! "Hulk Hogan"
}
