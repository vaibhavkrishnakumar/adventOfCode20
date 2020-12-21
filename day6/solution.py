filename = "input.txt"

def remove_all_whitespace(string):
  return "".join(string.split())

with open(filename, "r") as file:
  count = 0
  lines = file.read()
  for line in lines.split("\n\n"):
    count += len(set(remove_all_whitespace(line)))
  print("Answer to Part 1 is {}".format(count))

