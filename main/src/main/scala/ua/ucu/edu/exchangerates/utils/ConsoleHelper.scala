package ua.ucu.edu.exchangerates.utils

trait ConsoleHelper {

  def clearScreen() = print("\033[2J")

  def printScreen(title: String, content: => String) = {
    clearScreen()

    val titleDecorated =  title.map(_.toUpper).mkString(" ")

    println(
      s"""
         |************************************************************
         |${(0 to ((60 - titleDecorated.length) / 2)).map(_ => " ").mkString}$titleDecorated
         |************************************************************
         |$content""".stripMargin)

  }

}
