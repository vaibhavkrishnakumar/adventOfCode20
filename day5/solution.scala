import scala.io.Source

object Solution {

  val filename = "input.txt";

  def main(args: Array[String]) : Unit = {
    val maxSeatId : Int = Source.fromFile(filename).getLines()
      .map(toRowAndColumn)
      .map(toNumbers)
      .map(toSeatId)
      .max;
    println(s"Answer to Part 1 is $maxSeatId");
  }

  private def toRowAndColumn(line: String) : (String, String) = {
    val isRowDescriptor = (char : Char) => 'F' == char || 'B' == char;
    line.span(isRowDescriptor);
  }

  private def toNumbers(rowColumnDescriptors: (String, String)) : (Int, Int) = {
    val (rowDesc, columnDesc) = rowColumnDescriptors;
    val row = toNumber(rowDesc, 'F', 'B');
    val column = toNumber(columnDesc, 'L', 'R');
    (row, column);
  }

  private def toNumber(strDescriptor: String, zeroRep:Char, oneRep: Char) : Int = {
    val zeroOneStr = strDescriptor.replace(zeroRep, '0').replace(oneRep, '1');
    Integer.parseInt(zeroOneStr, 2);
  }

  private def toSeatId(rowAndColumn: (Int, Int)) : Int = {
    val (row, column) = rowAndColumn;
    row*8 + column;
  }
}

