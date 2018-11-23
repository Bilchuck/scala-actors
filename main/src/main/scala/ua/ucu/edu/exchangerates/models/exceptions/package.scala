package ua.ucu.edu.exchangerates.models

package object exceptions {

  case object ServiceUnavailableException extends Exception("Service Unavailable")

  case object UnauthorizedException extends Exception("Unauthorized")

  case object ForbiddenException extends Exception("Forbidden")

}
