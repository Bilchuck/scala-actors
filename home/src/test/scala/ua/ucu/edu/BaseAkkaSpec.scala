package ua.ucu.edu

import akka.actor.ActorSystem
import akka.testkit.TestKitBase
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

abstract class BaseAkkaSpec(_system: ActorSystem) extends
  TestKitBase with WordSpecLike with Matchers with BeforeAndAfterAll {
  override implicit val system: ActorSystem = _system

  def this() = this(ActorSystem("test-system"))
}
