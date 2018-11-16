package ua.ucu.edu

import scala.collection.parallel.immutable

object ParCol {

  class ParString(val str: String)
    extends immutable.ParSeq[Char] {
    def apply(i: Int): Char = ???

    def length: Int = str.length

    def seq = new collection.immutable.WrappedString(str)

    def splitter = ???

    override def newCombiner = ???

  }


  ???


  object CustomCharCount extends App {
    val txt = "A custom text " * 250000
    val partxt = new ParString(txt)

    val seqtime = warmedTimed(50) {
      txt.foldLeft(0)((n, c) => if (Character.isUpperCase(c)) n + 1 else n)
    }

    println(s"Sequential time - $seqtime ms")

    val partime = warmedTimed(50) {
      partxt.aggregate(0)((n, c) => if (Character.isUpperCase(c)) n + 1 else n, _ + _)
    }

    println(s"Parallel time   - $partime ms")

  }

}
