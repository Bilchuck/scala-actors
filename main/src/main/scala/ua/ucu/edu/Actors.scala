package ua.ucu.edu

import akka.pattern.ask
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Identify, Props}
import akka.util.Timeout

import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object Actors extends App {

  // protocol
  case class Hey(msg: String)
  case object WhatsMyName
  case class YourNameIs(name: String)
  case object ForgetYourName

  class NamesActor extends Actor {
    override def receive: Receive = {
      case WhatsMyName => sender ! YourNameIs("John")
    }
  }

  class HeyActor(
    num: Int
  ) extends Actor with ActorLogging {

    var name: String = _

    override def preStart(): Unit = {
      // <root>/user/hey/names
      val namesActor = context
        .actorOf(Props[NamesActor], "names")
      //
      namesActor ! WhatsMyName
    }

    override def receive: Receive = {
      case Hey("buddy") =>
        sender ! "here"
      case Hey(msg) =>
        log.info(s"received $msg. I'm number $num")
      case YourNameIs(name) =>
        context.become(iKnowMyName(name) orElse receive)
      case ForgetYourName =>
        log.info(s"forget everything")
        context.unbecome()
    }

    def iKnowMyName(name: String): Receive = {
      case Hey(msg: String) =>
        val response = s"received $msg. My name is $name"
        log.info(response)
        sender ! response

    }
  }

  val system = ActorSystem("test")

  //     <root>/user/hey
  val hey: ActorRef = system
    .actorOf(Props(classOf[HeyActor], 1), "hey")
  println(hey.path)


  hey ! Hey("bud")

  hey ! ForgetYourName

  hey ! Hey("bud")

  import scala.concurrent.duration._
  implicit val timeout = akka.util.Timeout(5 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  ask(system.actorSelection("/user/hey/names"), Identify).onComplete {result =>
    println("reference is:")
    println(result)
  }

//  implicit val timeout = akka.util.Timeout(5 seconds)

//  val fut: Future[Any] = akka.pattern.ask(hey, Hey("buddy"))
//  fut.onComplete { result =>
//    println(s"$result")
//  }
//
//  //Await.ready(fut, 10 seconds)
//
//
//  import akka.pattern.pipe
//
//  val completed: Future[Hey] = Future {
//    Thread.sleep(10000)
//    println("completed")
//    Hey("completed!")
//  }
//
//  val pipeReturns: Future[Hey] = pipe(completed).pipeTo(hey)


  //hey ! W


  //system.terminate()
}
