package ua.ucu.edu

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

class DemoAkkaSpec extends BaseAkkaSpec(ActorSystem("test-system")) {

  override protected def afterAll(): Unit = {
    super.afterAll()
    shutdown(system)
  }

  "dummy assertion" should {
    "work" in {
      "non-null" should not be null
    }
  }

  "test actor" should {
    "work as expected" in {
      val testActor = system.actorOf(Props[TestActor])
      testActor ! "test"
      expectMsg("ok, test")
    }
  }

}

class TestActor extends Actor with ActorLogging { _self =>
  override def receive: Receive = {
    case msg: String => _self.log.info(msg); sender ! s"ok, $msg"
  }
}