/**
 * Exercise 1
 *
 * In this exercise we will learn how to create Akka Actor,
 * the "EchoActor", the purpose of this Actor is to echo back incoming messages
 * to whoever sent the message. If there is way to reply, it should do nothing.
 *
 * Objective is to learn how to create Actors, receive messages and reply with a response.
 * On top of that we will write some tests using ScalaTest and Akka TestKit
 */

package ua.ucu.edu

import akka.actor._
import akka.actor.Actor._
import akka.util.Timeout
import scala.concurrent.duration._

/**
 * Below is the blueprint of our soon to be EchoActor,
 * this is where you will implement the behavior of the actor.
 *
 * Underneath the EchoActor-class is the unit-test you will create
 * to verify your implementation.
 *
 * You can run this code using sbt:
 * sbt > project home
 * sbt > test-only ua.ucu.edu.EchoActorSpec
 */

class EchoActor extends Actor {
  override def receive: Receive = {
    case msg: String => sender ! s"echo String($msg)"
    case num: Number => sender ! s"echo num($num)"
    case Nil => sender ! s"echo null"
    case obj: Object => sender ! s"echo some object"
    case _ => throw new Error("Unexpected type")
  }
}

/**
  * The following test verifies that the functionality
  * of the code you've written works as expected,
  */

class EchoActorSpec extends BaseAkkaSpec(ActorSystem("test-system")) {
  override protected def afterAll(): Unit = {
    super.afterAll()
    // stop actorRef here
    shutdown(system)
  }

  "Exercise1" should {

    "teach you how to reply to the first message" in {
      val ref = system.actorOf(Props[EchoActor])
      ref ! "Boo"
      expectMsg("echo String(Boo)")
    }

    "teach you how to respond with different kinds of responses" in {
      val ref = system.actorOf(Props[EchoActor])

      object obj {
        val name: String = "Anton"
      }
      ref ! "text"
      ref ! 123
      ref ! Nil
      ref ! obj

      expectMsg("echo String(text)")
      expectMsg("echo num(123)")
      expectMsg("echo null")
      expectMsg("echo some object")
    }

    "teach you what happens when you send a message to a stopped actor" in {
      val ref = system.actorOf(Props[EchoActor])

      ref ! "Before"

      system.stop(ref)

      ref ! "After"

      expectMsg("echo String(Before)")
      receiveN(1)
    }

    "teach you how to watch on actor state" in {
      val ref = system.actorOf(Props[EchoActor])

      case class IsTerminated(ref: ActorRef)
      val watcher = system.actorOf(Props(new Actor {
        override def preStart = {
          context.watch(ref)
        }

        override def receive: Receive = {
          case Terminated(_) => {
            sender ! IsTerminated(ref)
          }
          case _ => println("something else")
        }
      }))


      system.stop(ref)
      Thread.sleep(2000)

      expectMsg(IsTerminated(ref))
    }
  }
}