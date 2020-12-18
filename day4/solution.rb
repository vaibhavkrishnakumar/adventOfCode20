class Passport

  def initialize(fields)
    @@fields = fields
  end

  def is_valid_part_one
    required_fields = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
    required_fields.map { |f| @@fields.has_key?(f) } .all?
  end

end

def to_passport(line)
  entries = line.strip.split(" ")
  fields = Hash[entries.map { |x| x.split(":") }]
  Passport.new(fields)
end

filename = "input.txt"
number_of_valid_passports_part_one = 0

# Split on empty line
File.foreach(filename, "\n\n") do |line|

  passport = to_passport(line)

  if passport.is_valid_part_one
    number_of_valid_passports_part_one += 1
  end

end

puts "Answer to Part 1 is #{number_of_valid_passports_part_one}"

