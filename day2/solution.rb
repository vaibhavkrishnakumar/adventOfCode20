class InputRow

  @@range_separator = '-'

  def initialize(range, character, password)
    @@range = range
    @@character = character
    @@password = password
  end

  def is_valid_part_one
    min_occurrences, max_occurrences = split_range
    count = @@password.count(@@character)
    count >= min_occurrences and count <= max_occurrences
  end

  def is_valid_part_two
    index_one, index_two = split_range.map { |n| n - 1} # range is 1 indexed
    (@@password[index_one] == @@character) ^ (@@password[index_two] == @@character)
  end

  def split_range
    @@range.split(@@range_separator).map(&:to_i)
  end
end

# Line is of the form "range character: password"
def splitLine(line)
  arr = line.split
  character, _ = arr[1].split(':')
  InputRow.new(arr[0], character, arr[2])
end

filename = "input.txt"
number_of_valid_passwords_part_one = 0
number_of_valid_passwords_part_two = 0

File.foreach(filename) do |line|
  input_row = splitLine(line)

  if input_row.is_valid_part_one
    number_of_valid_passwords_part_one += 1
  end

  if input_row.is_valid_part_two
    number_of_valid_passwords_part_two += 1
  end
end

puts "Answer to Part 1 is #{number_of_valid_passwords_part_one}"
puts "Answer to Part 2 is #{number_of_valid_passwords_part_two}"

