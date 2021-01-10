def readFileAsNumbers(baseDir, filename) {
  return new File(baseDir, filename).readLines().collect { new BigInteger(it) }
}

def findFirstNumberNotSummableInPreamble(input, preambleLength) {
  for (int i = preambleLength; i < input.size(); i++) {
    def preamble = input[i-preambleLength..i-1]
    def target = input[i]
    if (targetCannotBeFormedFromPreamble(preamble, target)) {
      return target
    }
  }
  throw new IllegalStateException("All numbers can be formed from the preamble")
}

def targetCannotBeFormedFromPreamble(preamble, target) {
  for (int i = 0; i < preamble.size(); i++) {
    for (int j = i+1; j < preamble.size(); j++) {
      if (preamble[i] + preamble[j] == target) {
        // target CAN be formed with preamble
        return false
      }
    }
  }
  return true
}

def findContiguousNumbersThatSumTo(target, input) {
  def targets = [target]
  for (int i = 1; i < input.size(); i++) {
    def range = input[0..i]
    def sumToI = range.sum()
    if (targets.contains(sumToI)) {
      return findSubRangeToEndThatSumsToTarget(range, target)
    } else {
      targets.add(targets[i-1] + input[i-1])
    }
  }
  throw new IllegalStateException("No contiguous range sums to ${target}")
}

// [0, 1, 2, 3...n] --> find a sub range that ends with n and sums to target
def findSubRangeToEndThatSumsToTarget(range, target) {
  for (int i = 0; i < range.size(); i++) {
    def subRangeToEnd = range[i..range.size()-1]
    if (subRangeToEnd.sum() == target) {
      return subRangeToEnd
    }
  }
  throw new IllegalArgumentException("Invalid range, no sub-range sums to ${target}")
}

def calculateMinAddMax(range) {
  return range.min() + range.max()
}

// ---

baseDir = '.'
filename = 'input.txt'
preambleLength = 25

input = readFileAsNumbers(baseDir, filename)

answer = findFirstNumberNotSummableInPreamble(input, preambleLength)
println "Answer to Part 1 is ${answer}"

range = findContiguousNumbersThatSumTo(answer, input)
answer = calculateMinAddMax(range)
println "Answer to Part 2 is ${answer}"
