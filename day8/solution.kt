import java.io.File

enum class Operation {
  NOP, ACC, JMP
}

fun main() {
  val filename: String = "input.txt"
  val bootCode: List<Pair<Operation, Int>> = File(filename).readLines().map { parse(it) }

  val acc: Int = runBootCode(bootCode)
  println("Answer to Part 1 is $acc")
}

private fun parse(line: String): Pair<Operation, Int> {
  val opArg: List<String> = line.split(" ")
  val operation: String = opArg.get(0).toUpperCase()
  return Pair(Operation.valueOf(operation), Integer.parseInt(opArg.get(1)))
}

private fun runBootCode(bootCode: List<Pair<Operation, Int>>) : Int {
  var ipsSeen: MutableSet<Int> = mutableSetOf<Int>()
  var ip: Int = 0
  var acc: Int = 0

  while(true) {
    if (ipsSeen.contains(ip)) {
      break
    }
    ipsSeen.add(ip)
    val (op, arg) = bootCode.get(ip);
    // XXX Cannot use destructing declaration directly
    val (newIp, newAcc) = runOp(op, arg, ip, acc)
    ip = newIp
    acc = newAcc
  }
  return acc
}

private fun runOp(op: Operation, arg: Int, ip: Int, acc: Int) : Pair<Int, Int> {
  val newIp: Int = incrementInstructionPointer(ip)
  when(op) {
    Operation.NOP -> return Pair(newIp, acc)
    Operation.ACC -> return Pair(newIp, acc(arg, acc))
    Operation.JMP -> return Pair(jmp(arg, ip), acc)
  }
}

private fun incrementInstructionPointer(ip: Int): Int {
  return ip + 1
}

private fun acc(arg: Int, acc: Int): Int {
  return acc + arg
}

private fun jmp(arg: Int, ip: Int): Int {
  return ip + arg
}

