class Passport

  def initialize(fields)
    @@fields = fields
  end

  def is_valid_part_one
    required_fields = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
    required_fields.map { |f| @@fields.has_key?(f) } .all?
  end

  def is_valid_part_two
    is_valid_part_one and @@fields.map { |k,v| is_valid(k, v) } .all?
  end

  private

  def is_valid(key, value)
    case key
    when "byr"
      is_value_in_range(value, 1920, 2002)
    when "iyr"
      is_value_in_range(value, 2010, 2020)
    when "eyr"
      is_value_in_range(value, 2020, 2030)
    when "hgt"
      is_height_valid(value)
    when "hcl"
      is_hair_colour_valid(value)
    when "ecl"
      is_eye_colour_valid(value)
    when "pid"
      is_pid_valid(value)
    when "cid"
      true
    else
      false
    end
  end

  def is_value_in_range(val, min, max)
    value = val.to_i
    value >= min and value <= max
  end

  def is_height_valid(height)
    units = height[-2..-1]
    magnitude = height.delete(units)
    case units
    when "cm"
      is_value_in_range(magnitude, 150, 193)
    when "in"
      is_value_in_range(magnitude, 59, 76)
    else
      false
    end
  end

  def is_hair_colour_valid(colour)
    !!colour[/^#\h{6}$/] # matches '#' followed by 6 hex chars
  end

  def is_eye_colour_valid(colour)
    ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"].include? colour
  end

  def is_pid_valid(pid)
    !!pid[/^[0-9]{9}$/] # matches any 9 digit string
  end

end

def to_passport(line)
  entries = line.strip.split(" ")
  fields = Hash[entries.map { |x| x.split(":") }]
  Passport.new(fields)
end

filename = "input.txt"
number_of_valid_passports_part_one = 0
number_of_valid_passports_part_two = 0

# Split on empty line
File.foreach(filename, "\n\n") do |line|

  passport = to_passport(line)

  if passport.is_valid_part_one
    number_of_valid_passports_part_one += 1
  end

  if passport.is_valid_part_two
    number_of_valid_passports_part_two += 1
  end
end

puts "Answer to Part 1 is #{number_of_valid_passports_part_one}"
puts "Answer to Part 2 is #{number_of_valid_passports_part_two}"

