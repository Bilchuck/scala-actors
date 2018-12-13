package ua.ucu.edu

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKitBase}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

abstract class BaseAkkaSpec(_system: ActorSystem) extends
  TestKitBase with WordSpecLike with Matchers with BeforeAndAfterAll with ImplicitSender {
  override implicit lazy val system: ActorSystem = _system

  def this() = this(ActorSystem("test-system"))
}
