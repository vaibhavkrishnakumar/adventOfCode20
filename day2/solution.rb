class InputRow

  def initialize(min_occurrences, max_occurrences, character, password)
    @@min_occurrences = min_occurrences
    @@max_occurrences = max_occurrences
    @@character = character
    @@password = password
  end

  def is_valid
    count = @@password.count(@@character)
    count >= @@min_occurrences and count <= @@max_occurrences
  end

end

# Line is of the form "min_occurrences-max_occurrences character: password"
def splitLine(line)
  arr = line.split
  min_occurrences, max_occurrences = arr[0].split('-')
  character, _ = arr[1].split(':')
  InputRow.new(min_occurrences.to_i, max_occurrences.to_i, character, arr[2])
end

filename = "input.txt"
number_of_valid_passwords = 0

File.foreach(filename) do |line|
  input_row = splitLine(line)
  if input_row.is_valid
    number_of_valid_passwords += 1
  end
end

puts "Answer to Part 1 is #{number_of_valid_passwords}"

