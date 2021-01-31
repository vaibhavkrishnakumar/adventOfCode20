import scala.io.Source

object Solution {

  val filename = "input.txt";

  def main(args: Array[String]) : Unit = {
    val adapters = Source.fromFile(filename).getLines().map(_.toInt).toSeq.sorted;
    val (oneJumps, threeJumps) = adapterJumps(adapters);
    val answerPart1 = oneJumps*threeJumps;
    println(s"Answer to Part 1 is $answerPart1");
  }

  def adapterJumps(adapters: Seq[Int]) : (Int, Int) = {
    var oneJumps : Int = 0;
    var threeJumps : Int = 1; // Final jump to device's built-in adapter

    var current : Int = 0;
    adapters.foreach(jolt => {
      val jump = jolt - current;
      jump match {
        case 1 => oneJumps = oneJumps + 1
        case 3 => threeJumps = threeJumps + 1
      }
      current = jolt
    });

    (oneJumps, threeJumps)
  }

}
