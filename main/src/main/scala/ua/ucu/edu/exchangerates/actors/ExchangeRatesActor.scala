package ua.ucu.edu.exchangerates.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.pipe
import ua.ucu.edu.exchangerates.actors.messages.{BecomeNBUConverter, BecomeYahooConverter, GetExchangeRate}
import ua.ucu.edu.exchangerates.models.Currency

import scala.concurrent.Future

object ExchangeRatesActor {

  def props(
       yahooService: (Currency, Currency) => Future[String],
       nbuService: (Currency, Currency) => Future[String]) = Props(classOf[ExchangeRatesActor], yahooService, nbuService)
}

class ExchangeRatesActor(
        yahooService: (Currency, Currency) => Future[String],
        nbuService: (Currency, Currency) => Future[String]) extends Actor with ActorLogging {

  private val exchange = """GetExchangeRate ([A-Z]{3})/([A-Z]{3})""".r

  def receive = yahooRates

  def yahooRates: Receive = rates(yahooService)

  def nbuRates: Receive = rates(nbuService)

  def rates(getRates: (Currency, Currency) => Future[String]): Receive = {
    case GetExchangeRate(from, to) =>
      pipe(getRates(from, to))(scala.concurrent.ExecutionContext.Implicits.global)
        .to(sender())

    case msg: String => msg match {
      case exchange(from, to) =>
        pipe(getRates(Currency(from), Currency(to)))(
          scala.concurrent.ExecutionContext.Implicits.global).to(sender())

      case msg => log.info(s"Received unknown message: $msg")
    }

    case BecomeYahooConverter => context.become(yahooRates)
    case BecomeNBUConverter   => context.become(nbuRates)

    case msg => log.info(s"Received unknown message: $msg")
  }

}
