package ua.ucu.edu.handson

import akka.actor.ActorSelection
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

final case class Result(x: Int, s: String, d: Double)

case object Request

object AskTest extends App {

  implicit val timeout: Timeout = Timeout(5 seconds) // needed for `?` below

  val actorA, actorB, actorC, actorD : ActorSelection = ???

  import scala.concurrent.ExecutionContext.Implicits.global

  val f: Future[Result] =
    for {
      x ← ask(actorA, Request).mapTo[Int] // call pattern directly
      s ← (actorB ask Request).mapTo[String] // call by implicit conversion
      d ← (actorC ? Request).mapTo[Double] // call by symbolic name
    } yield Result(x, s, d)

  pipe(f).to(actorD)

  // pipe(f) to actorD

}
