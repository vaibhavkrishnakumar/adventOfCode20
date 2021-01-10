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

def baseDir = '.'
def filename = 'input.txt'
def preambleLength = 25

def input = readFileAsNumbers(baseDir, filename)

def answer = findFirstNumberNotSummableInPreamble(input, preambleLength)
println "Answer to Part 1 is ${answer}"
