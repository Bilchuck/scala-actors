package ua.ucu.edu.exchangerates.utils

import dispatch._
import ua.ucu.edu.exchangerates.models.Currency
import ua.ucu.edu.exchangerates.models.exceptions.{ForbiddenException, ServiceUnavailableException, UnauthorizedException}

import scala.util.Random

trait MixinProbabilisticException extends ExchangeRatesService {self: ExchangeRatesService =>

  abstract override def getRates(from: Currency, to: Currency): Future[String] = {
    failWithProbability(0.42, ServiceUnavailableException)
    failWithProbability(0.05, ForbiddenException)
    failWithProbability(0.3, UnauthorizedException)
    super.getRates(from, to)
  }

  private def failWithProbability(p: Double, e: Throwable): Unit = {
    if ((1-p) < Random.nextDouble()) throw e
  }

}
