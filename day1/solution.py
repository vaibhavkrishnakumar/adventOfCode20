def readInputAsNumbers(filename):
  input_file = open(filename, "r")
  out = [int(line) for line in input_file.readlines()]
  input_file.close()
  return out

def findTwoNumbersThatSumTo(target, input):
  secondNumber = set()
  for num in input:
    if (num in secondNumber):
      return num, target - num
    secondNumber.add(target - num)

def calculateAnswer(num1, num2):
  return num1*num2

def main(filename, target):
  input = readInputAsNumbers(filename)
  x, y = findTwoNumbersThatSumTo(target, input)
  print("Numbers that sum to {} are {} and {}".format(target, x, y))
  answer = calculateAnswer(x, y)
  print("Answer is {}".format(answer))

filename = "input.txt"
target = 2020
main(filename, target)
