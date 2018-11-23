package ua.ucu.edu.handson.workshop.part1

import akka.actor.{Actor, ActorRef}
import ua.ucu.edu.basic_work.work.RiskyWork

import scala.concurrent.duration._


case class Division(dividend: Int, divisor: Int)
object GetNumCompletedTasks
case class NumCompletedTasks(numCompleted: Int)
object SendNumCompletedTasks

class ComputeActor(numCompletedTaskActor: ActorRef, logCompletedTasksInterval: FiniteDuration) extends Actor {
  var numCompletedTasks: Int = 0

  override def preStart() = {
    scheduleSendingNumCompletedTasks()
  }

  def receive = {
    case s: String => {
      incrementNumCompletedTasks()
      sender ! s.length
    }
    case division: Division => {
      val result: Int = division.dividend / division.divisor
      incrementNumCompletedTasks()
      sender ! result
    }
    case work: RiskyWork => {
      val result = work.perform()
      incrementNumCompletedTasks()
      sender ! result
    }
    case GetNumCompletedTasks =>  {
      sender ! NumCompletedTasks(numCompletedTasks)
    }
    case SendNumCompletedTasks => {
      numCompletedTaskActor ! NumCompletedTasks(numCompletedTasks)
      scheduleSendingNumCompletedTasks()
    }
  }

  def incrementNumCompletedTasks() {
    numCompletedTasks += 1
  }

  def scheduleSendingNumCompletedTasks() {
    import context.dispatcher
    context.system.scheduler.scheduleOnce(logCompletedTasksInterval, self, SendNumCompletedTasks)
  }
}
