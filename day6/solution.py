filename = "input.txt"

def remove_all_whitespace(string):
  return "".join(string.split())

def anyone_answered_yes(group):
  return set(remove_all_whitespace(group))

def everyone_answered_yes(group):
  each_persons_answers = [set(person) for person in group.split()]
  return reduce(set.intersection, each_persons_answers)

with open(filename, "r") as file:
  count_part_1 = 0
  count_part_2 = 0
  groups = file.read()
  for group in groups.split("\n\n"):
    count_part_1 += len(anyone_answered_yes(group))
    count_part_2 += len(everyone_answered_yes(group))
  print("Answer to Part 1 is {}".format(count_part_1))
  print("Answer to Part 2 is {}".format(count_part_2))

