import scala.io.Source

object Solution {

  val filename = "input.txt";

  def main(args: Array[String]) : Unit = {
    Source.fromFile(filename).getLines().foreach { x => println(x) };
  }
}

class Passport(
    var byr: Option[String],
    var iyr: Option[String],
    var eyr: Option[String],
    var hgt: Option[String],
    var hcl: Option[String],
    var ecl: Option[String],
    var pid: Option[String],
    var cid: Option[String]
    ) {

  def isValid() : bool = {
    // cid doesn't have to be present for validity
    allAreDefined(byr, iyr, eyr, hgt, hcl, ecl, pid)
  }

  // XXX Should be a util method
  private def allAreDefined(options: Option*) {
    return options.stream().allMatch(Option::isDefined)
  }

}

object Passport {
  def create(passport: String) : Passport = {
    var fields = passport.split("\r\n ").map(str -> {
        keyValue = str.split(":");
        (keyValue[0], keyValue[1]);
        })

    return new Passport(map.get("byr"));
  }
}

