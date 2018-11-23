package ua.ucu.edu.basic_work.part2

import scala.concurrent.duration._
import akka.actor.{ActorContext, ActorRef, Props}
import ua.ucu.edu.basic_work.part1.ComputeActor


class ComputeActorFactory(numCompletedTaskActor: ActorRef, logCompletedTasksInterval: FiniteDuration = 1 second) {
  def create(context: ActorContext, actorName: String): ActorRef = {
    context.actorOf(Props(classOf[ComputeActor], numCompletedTaskActor, logCompletedTasksInterval), actorName)
  }
}
