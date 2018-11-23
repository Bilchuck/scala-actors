package ua.ucu.edu.exchangerates

import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.pattern.ask
import ua.ucu.edu.exchangerates.actors.ExchangeRatesActor
import ua.ucu.edu.exchangerates.actors.messages.{BecomeNBUConverter, BecomeYahooConverter, GetExchangeRate}
import ua.ucu.edu.exchangerates.models.{EUR, UAH, USD}
import ua.ucu.edu.exchangerates.utils.{ConsoleHelper, HttpDependent, NBUExchangeRates, YahooExchangeRates}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn

object ModifyBehaviourApplication extends App with ConsoleHelper with HttpDependent {

  lazy val exchangeRatesSystem = ActorSystem("ExchangeRates")

  val exchangeActor = exchangeRatesSystem.actorOf(
    ExchangeRatesActor.props(YahooExchangeRates.getRates, NBUExchangeRates.getRates), "exchangeRates")

  appLoop()

  def appLoop(): Unit = {
    printScreen("Exchange Rates", {
      s"""
         |[1] Yahoo Exchange Rates
         |[2] NBU Exchange Rates
         |
         |[0] Exit""".stripMargin
    })
    StdIn.readLine() match {
      case "1" =>
        yahoo()  
      case "2" =>
        nbu()
      case "0" =>
        shutdown()
      case _ => appLoop()
    }
  }

  def yahoo() = {
    exchangeActor ! BecomeYahooConverter
    showRates("Yahoo Exchange Rates")
  }

  def nbu() = {
    exchangeActor ! BecomeNBUConverter
    showRates("NBU Exchange Rates")
  } 
  
  def showRates(title: String) = {
    for {
      uahUsd <- (exchangeActor ? GetExchangeRate(USD, UAH))(5 seconds).mapTo[String]
      uahEur <- (exchangeActor ? GetExchangeRate(EUR, UAH))(5 seconds).mapTo[String]
    } {
      printScreen(title, {
        s"""
           |$uahUsd
           |$uahEur
           |
           |Press any key...""".stripMargin
      })
    }
    StdIn.readLine()
    appLoop()
  }
  
  def shutdown() = {
    closeHttp()
    exchangeRatesSystem.terminate()
  }

}
