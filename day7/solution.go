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
filename := "test.txt"
            file, _ := os.Open(filename)

            defer file.Close()

            scanner := bufio.NewScanner(file)
            bags := make([]bag, 0)
            for scanner.Scan() {
              bags = append(bags, LineToBag(scanner.Text()))
            }

shinyGoldCarriers := NumCarriers(bags, "shiny gold")
                     fmt.Println("Answer to Part 1 is " + strconv.Itoa(shinyGoldCarriers))
}

func LineToBag(line string) bag {
colourAndContents := SplitLine(line)
                     colour := ExtractColour(colourAndContents[0])
                     contents := ExtractContents(colourAndContents[1])
                     return bag{colour, contents}
}

func SplitLine(line string) []string {
  return strings.SplitN(line, "contain", 2)
}

func ExtractColour(colourStr string) string {
  return strings.Split(colourStr, "bags")[0]
}

func ExtractContents(contentsStr string) []bag {
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
contentsAsBags := make([]bag, 0)
                  for _, content := range contents {
                    contentsAsBags = append(contentsAsBags, CreateBag(content))
                  }
                return contentsAsBags
}

func CreateBag(content string) bag {
colour := strings.SplitN(content, "bag", 2)[0]
          return bag{colour, []bag{}}
}

func NumCarriers(bags []bag, bagToCheck string) int {
  return len(Carriers(bags, bagToCheck))
}

func Carriers(bags []bag, bagToCheck string) map[string]struct{} {
setBagsWhichEventuallyContainBagToCheck := make(map[string]struct{})
                                             setBagsWhichDirectlyContainBagToCheck := make(map[string]struct{})
                                             for _, bag := range bags {
                                               if ContentsIncludesBagToCheck(bag.contents, bagToCheck) {
                                                 AddToSets(bag.colour, setBagsWhichEventuallyContainBagToCheck, setBagsWhichDirectlyContainBagToCheck)
                                               }
                                             }
                                           for bag := range setBagsWhichDirectlyContainBagToCheck {
bagsToAdd := Carriers(bags, bag)
keys := ExtractKeys(bagsToAdd)
AddAllToSet(setBagsWhichEventuallyContainBagToCheck, keys)
                                           }
                                           return setBagsWhichEventuallyContainBagToCheck
}

func ContentsIncludesBagToCheck(bags []bag, bagToCheck string) bool {
  for _, bag := range bags {
    containsBagToCheck, _ := regexp.MatchString(bagToCheck, bag.colour)
      if containsBagToCheck {
        return true
      }
  }
  return false
}

func AddToSets(entry string, sets ...map[string]struct{}) {
  for _, set := range sets {
    set[entry] = struct{}{}
  }
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

type bag struct {
  colour string
    contents []bag
}

