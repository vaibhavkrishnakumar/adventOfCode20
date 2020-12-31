import java.io.File

enum class Operation {
  NOP, ACC, JMP
}

fun main() {
  val filename: String = "input.txt"
  val bootCode: List<Pair<Operation, Int>> = File(filename).readLines().map { parse(it) }

  val (acc1, _) = runBootCode(bootCode)
  println("Answer to Part 1 is $acc1")

  val acc2 = runModifiedBootCode(bootCode)
  println("Answer to Part 2 is $acc2")
}

private fun parse(line: String): Pair<Operation, Int> {
  val opArg: List<String> = line.split(" ")
  val operation: String = opArg.get(0).toUpperCase()
  return Pair(Operation.valueOf(operation), Integer.parseInt(opArg.get(1)))
}

private fun runBootCode(bootCode: List<Pair<Operation, Int>>) : Pair<Int, Boolean> {
  var ipsSeen: MutableSet<Int> = mutableSetOf<Int>()
  var ip: Int = 0
  var acc: Int = 0
  var isLoop: Boolean = false

  while(true) {
    if (ipsSeen.contains(ip)) {
      isLoop = true
      break
    }
    if (ip >= bootCode.size) { // Terminating condition for Part 2
      break
    }
    ipsSeen.add(ip)
    val (op, arg) = bootCode.get(ip);
    // XXX Cannot use destructing declaration directly
    val (newIp, newAcc) = runOp(op, arg, ip, acc)
    ip = newIp
    acc = newAcc
  }
  return Pair(acc, isLoop)
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

private fun runModifiedBootCode(bootCode: List<Pair<Operation, Int>>) : Int {
  for (i in 0 until bootCode.size) {
    val modifiedBootCode: List<Pair<Operation, Int>> = modifyBootCode(i, bootCode)
    val (acc, isLoop) = runBootCode(modifiedBootCode)
    if (!isLoop) {
      return acc
    }
  }
  throw IllegalStateException()
}

private fun modifyBootCode(index: Int, bootCode: List<Pair<Operation, Int>>) : List<Pair<Operation, Int>> {
  return bootCode.mapIndexed { i, current -> if (i == index) swapJmpAndNop(current) else current }
}

private fun swapJmpAndNop(opArg: Pair<Operation, Int>) : Pair<Operation, Int> {
  val (op, arg) = opArg
  when(op) {
    Operation.NOP -> return Pair(Operation.JMP, arg)
    Operation.JMP -> return Pair(Operation.NOP, arg)
    Operation.ACC -> return Pair(op, arg)
  }
}
