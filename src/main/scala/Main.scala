import scala.collection.mutable.{Map, HashMap}
import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("src/main/deaths_state.csv")

    val data = source.getLines()
      .map(_.split(",").map(_.trim).toList)
      .toList
      .tail

    val dataMap = extractData[String, Int](data, (x: String) => x, (x: String) => x.toInt)

    source.close()

    val answer1 = dataMap
      .flatMap(_._2)
      .groupBy(_._1)
      .view.mapValues(_.map(_._2).sum)
      .maxBy(_._2)
      ._1

    val answer2 = dataMap
      .flatMap(_._2)
      .groupBy(_._1)
      .view.mapValues(_.map(_._2).sum)
      .values
      .sum
      .toDouble / dataMap.size

    val answer3 = dataMap
      .flatMap(_._2)
      .groupBy(_._1)
      .view.mapValues(_.map(_._2).sum)
      .toList
      .sortBy(_._2)
      .take(5)
      .map(_._1)
      .mkString(", ")

    println("Which states in Malaysia have the highest deaths due to Covid?")
    println("Answer: " + answer1)
    println("\nWhat is the average death per day for Malaysia in the provided data?")
    println("Answer: " + answer2)
    println("\nWhich of the five states have the lowest death in the provided data?")
    println("Answer: " + answer3)
  }

  def extractData[A, B](data: List[List[String]], convertState: String => A, convertCount: String => B): HashMap[String, Map[A, B]] = {
    val dataMap = new HashMap[String, Map[A, B]]()

    for (row <- data) {
      val date = row(0)
      val state = convertState(row(1))
      val deathCount = convertCount(row(5))

      val innerMap = dataMap.getOrElseUpdate(date, Map())

      innerMap.put(state, deathCount)
    }
    dataMap
  }
}
