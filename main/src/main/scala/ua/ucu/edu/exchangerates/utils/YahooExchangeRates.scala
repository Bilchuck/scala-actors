package ua.ucu.edu.exchangerates.utils

import com.typesafe.config.ConfigFactory
import dispatch._
import Defaults._
import ua.ucu.edu.exchangerates.models.Currency

trait YahooExchangeRates extends ExchangeRatesService {

  private val apiUrl = ConfigFactory.load().getString("exchangeRates.yahoo.url")

  def getRates(from: Currency, to: Currency) = {
    val req = url(apiUrl)
      .addQueryParameter("q", s"select * from yahoo.finance.xchange where pair = '$from$to'")
      .addQueryParameter("env", "store://datatables.org/alltableswithkeys")

    Http(req OK as.xml.Elem) map {rates =>
        val rate = ((rates \ "results").head \ "rate" \ "Rate").text.toDouble
        s"$from/$to => $rate"
    }
  }

}

object YahooExchangeRates extends YahooExchangeRates
