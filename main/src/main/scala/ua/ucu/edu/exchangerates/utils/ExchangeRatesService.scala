package ua.ucu.edu.exchangerates.utils

import ua.ucu.edu.exchangerates.models.Currency

import scala.concurrent.Future

trait ExchangeRatesService {

  def getRates(from: Currency, to: Currency): Future[String]

}
