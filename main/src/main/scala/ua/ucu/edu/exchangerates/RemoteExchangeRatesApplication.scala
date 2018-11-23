package ua.ucu.edu.exchangerates

import akka.actor.{ActorSystem, Props}
import akka.routing.ScatterGatherFirstCompletedPool
import ua.ucu.edu.exchangerates.actors.ExchangeRatesActor
import ua.ucu.edu.exchangerates.utils.{NBUExchangeRates, YahooExchangeRates}

import scala.concurrent.duration._

object RemoteExchangeRatesApplication extends App {

  lazy val exchangeRatesSystem = ActorSystem("ExchangeRates")

  exchangeRatesSystem.actorOf(
    ExchangeRatesActor.props(YahooExchangeRates.getRates, NBUExchangeRates.getRates)
      .withRouter(ScatterGatherFirstCompletedPool(10, within = 10 seconds)),
    "exchangeRates")

}
