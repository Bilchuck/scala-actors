package ua.ucu.edu.exchangerates.models

import scala.util.Random

object Currency {
  private val currencies = UAH :: USD :: EUR :: Nil

  def apply(code: String) = currencies.find(_.code.equals(code)).getOrElse(throw new Exception(s"Unknown currency: $code"))

  def random = currencies(Random.nextInt(currencies.size))
}

abstract sealed class Currency(val code: String)

case object USD extends Currency("USD")
case object EUR extends Currency("EUR")
case object UAH extends Currency("UAH")
