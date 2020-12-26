filename = "input.txt"

def anyone_answered_yes(each_persons_answers):
  return len(reduce(set.union, each_persons_answers))

def everyone_answered_yes(each_persons_answers):
  return len(reduce(set.intersection, each_persons_answers))

with open(filename, "r") as file:
  count_part_1 = 0
  count_part_2 = 0
  groups = file.read()
  for group in groups.split("\n\n"):
    each_persons_answers = [set(person) for person in group.split()]
    count_part_1 += anyone_answered_yes(each_persons_answers)
    count_part_2 += everyone_answered_yes(each_persons_answers)
  print("Answer to Part 1 is {}".format(count_part_1))
  print("Answer to Part 2 is {}".format(count_part_2))

