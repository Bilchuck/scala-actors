package ua.ucu.edu

import scala.collection.immutable
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

  val eggs = (1 to 5).map(_ => eggF)
  val seqEggs: Future[Seq[Egg]] = Future.sequence(eggs)
  val travCrackedEggs: Future[Seq[Cracked]] = Future.traverse(
    1 to 5 map (_ => chickenMagic())
  )(egg => Future(crack(egg)))

}
