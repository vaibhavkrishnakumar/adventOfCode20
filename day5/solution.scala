import scala.io.Source
import scala.collection.mutable.Set

object Solution {

  val filename = "input.txt";

  def main(args: Array[String]) : Unit = {
    val remainingSeatIds : Set[Int] = generateAllSeatIds().iterator.to(Set);
    Source.fromFile(filename).getLines()
      .map(toRowAndColumn)
      .map(Function.tupled(toNumbers _))
      .map(Function.tupled(toSeatId _))
      .foreach(remainingSeatIds.subtractOne);
    val mySeatId : Int = findMySeatId(remainingSeatIds);
    println(s"Answer to Part 2 is $mySeatId");
  }

  private def generateAllSeatIds() : Seq[Int] = {
    for (r <- 0 until 128; c <- 0 until 8) // Range is end exclusive
      yield(toSeatId(r, c))
  }

  private def toRowAndColumn(line: String) : (String, String) = {
    val isRowDescriptor = (char : Char) => 'F' == char || 'B' == char;
    line.span(isRowDescriptor);
  }

  private def toNumbers(rowDescriptor: String, columnDescriptor: String) : (Int, Int) = {
    val row = toNumber(rowDescriptor, 'F', 'B');
    val column = toNumber(columnDescriptor, 'L', 'R');
    (row, column);
  }

  private def toNumber(strDescriptor: String, zeroRep:Char, oneRep: Char) : Int = {
    val zeroOneStr = strDescriptor.replace(zeroRep, '0').replace(oneRep, '1');
    Integer.parseInt(zeroOneStr, 2);
  }

  private def toSeatId(row: Int, column: Int) : Int = {
    row*8 + column;
  }

  private def findMySeatId(seats: Set[Int]) : Int = {
    val mySeatId = seats.find(id => !seats.contains(id - 1) && !seats.contains(id + 1));
    mySeatId match {
      case Some(seatId) => seatId;
      case None => throw new IllegalArgumentException(s"Couldn't find my seat in this set: $seats");
    }
  }

}
