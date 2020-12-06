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

def findThreeNumbersThatSumTo(target, input):
  for i in range(len(input)):
    element = input[i]
    modified_target = target - element
    modified_input = input[:i] + input[i+1:]
    answer = findTwoNumbersThatSumTo(modified_target, modified_input)
    if (answer is not None):
      return answer[0], answer[1], element

def calculateAnswer(*numbers):
  product = 1
  for number in numbers:
    product *= number
  return product

def part1(input, target):
  x, y = findTwoNumbersThatSumTo(target, input)
  print("The two numbers that sum to {} are {} and {}".format(target, x, y))
  return calculateAnswer(x, y)

def part2(input, target):
  x, y, z = findThreeNumbersThatSumTo(target, input)
  print("The three numbers that sum to {} are {}, {} and {}".format(target, x, y, z))
  return calculateAnswer(x, y, z)

def main(filename, target):
  input = readInputAsNumbers(filename)
  answer = part1(input, target)
  print("Answer to Part 1 is {}\n".format(answer))
  answer = part2(input, target)
  print("Answer to Part 2 is {}".format(answer))

filename = "input.txt"
target = 2020
main(filename, target)
