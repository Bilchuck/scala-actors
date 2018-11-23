package ua.ucu.edu.exchangerates

import akka.actor.{ActorSystem, Inbox, Props}
import akka.pattern.ask
import ua.ucu.edu.exchangerates.actors.ExchangeRatesSupervisor
import ua.ucu.edu.exchangerates.actors.messages.GetExchangeRate
import ua.ucu.edu.exchangerates.models.Currency
import ua.ucu.edu.exchangerates.utils.HttpDependent

import scala.concurrent.duration._
import scala.util.Try

object FaultHandlingApplication extends App with HttpDependent {

  val system = ActorSystem("FaultHandling")

  val exchangeRates = system.actorOf(Props[ExchangeRatesSupervisor], "exchangeRatesSupervisor")

  import system.dispatcher

  system.scheduler.schedule(0 seconds, 5 seconds) {
    (exchangeRates ? GetExchangeRate(Currency.random, Currency.random))(5 seconds) map {
      case resp: String => println(resp)
      case _ =>
    }
  }

}
