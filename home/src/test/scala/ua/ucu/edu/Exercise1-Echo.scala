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

class EchoActor { //TODO: First, we need to make sure this extends Actor
  //TODO: Then we need to implement the receive-method
}

/**
  * The following test verifies that the functionality
  * of the code you've written works as expected,
  */

class EchoActorSpec extends BaseAkkaSpec(ActorSystem("test-system")) {
  "Exercise1" should {

    "teach you how to reply to the first message" in {
      //TODO: Create an ActorRef pointing to an instance of your EchoActor

      //TODO: Send the ActorRef a String message using ask pattern and make sure you get the expected response

      //TODO: Stop actor which is referenced by this ActorRef
    }

    "teach you how to respond with different kinds of responses" in {
      //TODO: Create an ActorRef pointing to an instance of your EchoActor

      //TODO: Send a lot of different kinds of messages (numbers, null, objects etc)
      //      to the ActorRef and make sure you consistently get the expected response

      //TODO: Stop the ActorRef
    }

    "teach you what happens when you send a message to a stopped actor" in {
      //TODO: Create an ActorRef pointing to an instance of your EchoActor

      //TODO: Send the ActorRef a String message using ? and make sure you get the expected response

      //TODO: Stop actor which is referenced by this ActorRef

      //TODO: Send a String message and test that no valid response received
    }

    "teach you how to watch on actor state" in {
      //TODO: Create an ActorRef pointing to an instance of your EchoActor

      //TODO: Create watcher actor to watch for actor termination (You can finish the snippet)

      /*case class IsTerminated(ref: ActorRef)
      val watcher = system.actorOf(Props(new Actor {
        context.watch(???)
        override def receive: Receive = {
          ???
        }
      }))
      Thread.sleep(2000)*/

      //TODO: Stop actor which is referenced by this ActorRef

      //TODO: Verify that actor is stopped
    }
  }
}