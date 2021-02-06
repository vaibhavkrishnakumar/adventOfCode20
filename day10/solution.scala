import scala.io.Source

object Solution {

  val filename = "input.txt";

  def main(args: Array[String]) : Unit = {
    val adapters = Source.fromFile(filename).getLines().map(_.toInt).toSeq.sorted;
    val adaptersWithSourceAndTarget = addSourceAndTarget(adapters)

    val (singleJumps, tripleJumps) = adapterJumps(adaptersWithSourceAndTarget);
    val answerPart1 = singleJumps*tripleJumps;
    println(s"Answer to Part 1 is $answerPart1");

    val answerPart2 = numberOfCombinations(adaptersWithSourceAndTarget);
    println(s"Answer to Part 2 is $answerPart2");
  }

  private def addSourceAndTarget(adapters: Seq[Int]) : Seq[Int] = {
    val source: Int = 0
    val target: Int = adapters.max + 3
    (source +: adapters) :+ target
  }

  private def adapterJumps(adapters: Seq[Int]) : (Int, Int) = {
    var singleJumps : Int = 0;
    var tripleJumps : Int = 0;

    var current : Int = 0;
    adapters.foreach(jolt => {
      val jump = jolt - current;
      jump match {
        case 1 => singleJumps = singleJumps + 1
        case 3 => tripleJumps = tripleJumps + 1
        case _ =>
      }
      current = jolt
    });

    (singleJumps, tripleJumps)
  }

  private def numberOfCombinations(adapters: Seq[Int]) : BigInt = {
    val numWays = calculateNumberOfWaysForEachJolt(adapters)
    return valueOfMaxKey(numWays)
  }

  private def calculateNumberOfWaysForEachJolt(adapters: Seq[Int]) : Map[Int, BigInt] = {
    // Only one way of "reaching" 0
    var numWays : Map[Int, BigInt] = Map(0 -> 1)

    for (i <- 1 until adapters.length) {
      val jolt : Int = adapters(i)
      var numWaysForJolt : BigInt = getOrDefaultToZero(numWays, jolt - 1)
      if (jolt > 1) {
        numWaysForJolt += getOrDefaultToZero(numWays, jolt - 2)
      }
      if (jolt > 2) {
        numWaysForJolt += getOrDefaultToZero(numWays, jolt - 3)
      }
      numWays = numWays + (jolt -> numWaysForJolt)
    }
    return numWays
  }

  private def getOrDefaultToZero(map: Map[Int, BigInt], key : Int) : BigInt = {
    map.get(key).getOrElse(BigInt(0))
  }

  private def valueOfMaxKey(map: Map[Int, BigInt]) : BigInt = {
    map.maxBy(_._1)._2
  }
}
