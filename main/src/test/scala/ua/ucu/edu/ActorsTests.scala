package ua.ucu.edu

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActors, TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class ActorsTests extends TestKit(ActorSystem("TestSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "An Echo actor" must {
    "send back messages unchanged" in {
      val echo = system.actorOf(TestActors.echoActorProps)
      echo ! "hello world"
      expectMsg("hello world")
    }
  }

  "test" must {
    "work" in {
      val probe = TestProbe()
      val echo = system.actorOf(TestActors.echoActorProps)
      echo.!("hey, dude")(probe.ref)
      probe.expectMsg("hey, dude")
    }
  }
}
