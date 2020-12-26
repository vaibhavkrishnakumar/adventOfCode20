package main

import (
  "bufio"
  "fmt"
  "os"
  "strings"
  "regexp"
  "strconv"
)

func main() {
  filename := "input.txt"
  file, _ := os.Open(filename)

  defer file.Close()

  scanner := bufio.NewScanner(file)
  bags := make([]bag, 0)
  for scanner.Scan() {
    bags = append(bags, LineToBag(scanner.Text()))
  }

  shinyGoldCarriers := NumCarriers(bags, "shiny gold")
  fmt.Println("Answer to Part 1 is " + strconv.Itoa(shinyGoldCarriers))

  shinyGoldContents := NumContents(bags, "shiny gold")
  fmt.Println("Answer to Part 2 is " + strconv.Itoa(shinyGoldContents))
}

func LineToBag(line string) bag {
  colourAndContents := SplitLine(line)
  colour := ExtractColour(colourAndContents[0])
  contents := ExtractContentAsBags(colourAndContents[1])
  return bag{colour, contents}
}

func SplitLine(line string) []string {
  return strings.SplitN(line, "contain", 2)
}

func ExtractColour(colourStr string) string {
  return strings.Split(colourStr, "bags")[0]
}

func ExtractContentAsBags(contentsStr string) []bag {
  contents := strings.Split(contentsStr, ",")
  if contents[0] == contentsStr {
    return EmptyOrOne(contents[0])
  } else {
    return Many(contents)
  }
}

func EmptyOrOne(content string) []bag {
  isEmpty, _ := regexp.MatchString("no", content)
  if isEmpty {
    return []bag{}
  } else {
    return []bag{CreateBag(content)}
  }
}

func Many(contents []string) []bag {
  contentAsBags := make([]bag, 0)
  for _, content := range contents {
    contentAsBags = append(contentAsBags, CreateBag(content))
  }
  return contentAsBags
}

func CreateBag(content string) bag {
  colour := strings.SplitN(content, "bag", 2)[0]
  return bag{colour, []bag{}}
}

func NumCarriers(bags []bag, bagToCheck string) int {
  return len(Carriers(bags, bagToCheck))
}

func Carriers(bags []bag, bagToCheck string) map[string]struct{} {
  bagsWhichEventuallyContainBagToCheck := make(map[string]struct{})

  for _, bag := range bags {
    if ContentsIncludeBagToCheck(bag.contents, bagToCheck) {
      bagsWhichEventuallyContainBagToCheck[bag.colour] = struct{}{}
      bagsToAdd := Carriers(bags, bag.colour)
      AddAllToSet(bagsWhichEventuallyContainBagToCheck, ExtractKeys(bagsToAdd))
    }
  }

  return bagsWhichEventuallyContainBagToCheck
}

func ContentsIncludeBagToCheck(bags []bag, bagToCheck string) bool {
  for _, bag := range bags {
    containsBagToCheck, _ := regexp.MatchString(bagToCheck, bag.colour)
    if containsBagToCheck {
      return true
    }
  }
  return false
}

func ExtractKeys(inputMap map[string]struct{}) []string {
  keys := make([]string, 0, len(inputMap))
  for key := range inputMap {
    keys = append(keys, key)
  }
  return keys
}

func AddAllToSet(set map[string]struct{}, entries []string) {
  for _, entry := range entries {
    set[entry] = struct{}{}
  }
}

func NumContents(bags []bag, bagToStart string) int {
  totalCount := 0

  for _, bag := range bags {
    if strings.TrimSpace(bag.colour) == bagToStart {
      for bag, count := range ExtractContentsAndCount(bag) {
        totalCount = totalCount + count + count*NumContents(bags, bag)
      }
    }
  }

  return totalCount
}

func ExtractContentsAndCount(input bag) map[string]int {
  contents := make(map[string]int)
  for _, bag := range input.contents {
    countAndColour := SplitColour(bag.colour)
    colour := countAndColour[1]
    count, _ := strconv.Atoi(countAndColour[0])
    contents[colour] = count
  }
  return contents
}

func SplitColour(colour string) []string {
  return strings.SplitN(strings.TrimSpace(colour), " ", 2)
}

type bag struct {
  colour string
  contents []bag
}

