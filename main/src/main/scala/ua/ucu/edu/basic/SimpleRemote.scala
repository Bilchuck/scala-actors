package ua.ucu.edu.basic

import akka.actor.{Actor, ActorSystem, Identify, Props}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext
import scala.language.postfixOps


class GreetingActor1 extends Actor {
  def receive: Receive = {
    case message : String => println("Hello " + message)
  }
}

object GreetingActor1 extends App {
  val mainConfig = ConfigFactory.load
  val system = ActorSystem("greeter1-system", mainConfig.getConfig("remote1"))

  val actorRef = system.actorOf(Props(new GreetingActor_1), "greeter1")
  actorRef ! "Hulk Hogan"
}

object SimpleRemote extends App {

  val system = ActorSystem("system2")

  val selection =
    system.actorSelection("akka.tcp://greeter1-system@127.0.0.1:2554/user/greeter1")

  import akka.pattern.ask
  import scala.concurrent.duration._
//  import

  implicit val timeout: Timeout = Timeout(5 seconds) // needed for `?` below
  import ExecutionContext.Implicits.global

  selection ! "Hey hulk"

  ask(selection, Identify).onComplete { result =>
    println("===")
    println(result)
  }
}
