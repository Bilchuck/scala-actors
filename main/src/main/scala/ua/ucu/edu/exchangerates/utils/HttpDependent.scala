package ua.ucu.edu.exchangerates.utils

import dispatch.Http

trait HttpDependent {

  def closeHttp() = Http.default.client.close()

}
