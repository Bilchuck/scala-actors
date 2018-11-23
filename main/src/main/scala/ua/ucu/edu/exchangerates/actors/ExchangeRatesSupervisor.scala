package ua.ucu.edu.exchangerates.actors

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor._
import ua.ucu.edu.exchangerates.models.exceptions.{ForbiddenException, ServiceUnavailableException, UnauthorizedException}

import scala.concurrent.duration._
import ua.ucu.edu.exchangerates.utils._

class ExchangeRatesSupervisor extends Actor with ActorLogging {

  object YahooServiceWithFailures extends YahooExchangeRates with MixinProbabilisticException

  override def supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 1){

      case ServiceUnavailableException => Resume
      case UnauthorizedException => Restart
      case ForbiddenException => Stop
      case e: Exception => Escalate

    }

  def initializeWorker = {
    val exchangeRates = context.actorOf(
      ExchangeRatesActor.props(YahooServiceWithFailures.getRates, NBUExchangeRates.getRates), "exchangeRates"
    )
    context.watch(exchangeRates)
    exchangeRates
  }

  def receive = active(initializeWorker)

  def recovering: Receive = {
    case _ =>
      log.info(
        s"""
           |*********************************************************
           |  S Y S T E M   R E C O V E R Y   I N   P R O G R E S S
           |*********************************************************
           |
           |– It takes some time...
         """.stripMargin)
  }

  def active(exchangeRates: ActorRef): Receive = {
    case Terminated(`exchangeRates`) =>
      log.info(
        s"""
           |*********************************************************
           |    E X C H A N G E   R A T E S   T E R M I N A T E D
           |*********************************************************
           |
           |– System should be recovered from this state
         """.stripMargin)
      context.become(recovering)
      import context.dispatcher
      context.system.scheduler.scheduleOnce(15 seconds) {
        context.become(active(initializeWorker))
      }
    case msg =>
      log.info(s"Received msg: $msg")
      exchangeRates forward msg
  }

}
