package ua.ucu.edu.handson.basic_work.part1

import akka.actor.{Actor, ActorRef}

import scala.concurrent.duration._

case class Division(dividend: Int, divisor: Int)
object GetNumCompletedTasks
case class NumCompletedTasks(numCompleted: Int)


class ComputeActor(numCompletedTaskActor: ActorRef, logCompletedTasksInterval: FiniteDuration) extends Actor {

  def receive = {
    //TODO
    case _ => {}
  }
}
