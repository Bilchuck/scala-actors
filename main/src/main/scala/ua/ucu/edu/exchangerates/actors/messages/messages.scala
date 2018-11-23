package ua.ucu.edu.exchangerates.actors

import ua.ucu.edu.exchangerates.models.Currency

package object messages {

  case object BecomeYahooConverter
  case object BecomeNBUConverter

  case class GetExchangeRate(from: Currency, to: Currency)
  case class ExchangeRate(from: Currency, to: Currency, rate: Double)

}
