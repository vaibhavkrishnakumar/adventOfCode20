def readInputAsNumbers(filename):
  input_file = open(filename, "r")
  out = [int(line) for line in input_file.readlines()]
  input_file.close()
  return out

def findTwoNumbersThatSumTo(target, input):
  augends = set()
  for num in input:
    if (num in augends):
      return num, target - num
    augends.add(target - num)

def findThreeNumbersThatSumTo(target, input):
  for i in range(len(input)):
    modified_target = target - input[i]
    modified_input = listWithoutElementAtIndex(input, i)
    answer = findTwoNumbersThatSumTo(modified_target, modified_input)
    if (answer is not None):
      return answer[0], answer[1], input[i]

def listWithoutElementAtIndex(list, index):
  return list[:index] + list[index + 1:]

def calculateFinalAnswer(*numbers):
  product = 1
  for number in numbers:
    product *= number
  return product

def part1(input, target):
  twoNumbers = findTwoNumbersThatSumTo(target, input)
  if (twoNumbers is not None):
    x, y = twoNumbers
    print("The two numbers that sum to {} are {} and {}".format(target, x, y))
    return calculateFinalAnswer(x, y)
  else:
    print("No two numbers sum to {}".format(target))

def part2(input, target):
  threeNumbers = findThreeNumbersThatSumTo(target, input)
  if (threeNumbers is not None):
    x, y, z = threeNumbers
    print("The three numbers that sum to {} are {}, {} and {}".format(target, x, y, z))
    return calculateFinalAnswer(x, y, z)
  else:
    print("No three numbers sum to {}".format(target))

def main(filename, target):
  input = readInputAsNumbers(filename)

  answer = part1(input, target)
  if (answer is not None):
    print("Answer to Part 1 is {}\n".format(answer))

  answer = part2(input, target)
  if (answer is not None):
    print("Answer to Part 2 is {}\n".format(answer))

filename = "input.txt"
target = 2020
main(filename, target)
