import scala.collection.mutable.{Map, HashMap}
import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val filePath = "death_state.csv"

    val source = Source.fromFile("src/main/deaths_state.csv")

    val data = source.getLines()
      .map(_.split(",").map(_.trim).toList)
      .toList

    val dataMap = new HashMap[String, Map[String, Int]]
    val dataRows = data.tail

    for (row <- dataRows) {
      val date = row(0)
      val state = row(1)
      val deathCount = row(2).toInt

      val innerMap = dataMap.getOrElseUpdate(date, Map())

      innerMap.put(state, deathCount)
    }

    source.close()

    println("Which states in Malaysia have the highest deaths due to Covid?")
    println(answer1)
    println("What is the average death per day for Malaysia in the provided data?")
    println(answer2)
    println("Which of the five states have the lowest death in the provided data?")
    println(answer3)
  }
}
