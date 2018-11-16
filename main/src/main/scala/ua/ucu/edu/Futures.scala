package ua.ucu.edu

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object Futures {

  trait Egg
  trait Cracked extends Egg

  def chickenMagic(): Egg = ???
  def crack(e: Egg): Cracked = ???

  // ExecutionContext decides how to schedule and on what thread
  val executionContext: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global

  def eggF: Future[Egg] =
    Future {
      chickenMagic()
    }(executionContext)

  val crackedEggF: Future[Cracked] = eggF.map(crack)(executionContext)

  crackedEggF.onComplete{
    case Success(crackedEgg) => ???
    case Failure(exception)  => ???
  }(executionContext)


}
