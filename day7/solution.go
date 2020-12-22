package main

import (
    "bufio"
    "fmt"
    "os"
    "strings"
    "regexp"
    )

func main() {
filename := "test.txt"
            file, _ := os.Open(filename)

            defer file.Close()

            scanner := bufio.NewScanner(file)
            for scanner.Scan() {
bag := LineToBag(scanner.Text())
       fmt.Println(bag)
       fmt.Println("-")
            }

}

func LineToBag(line string) bag {
out := strings.SplitN(line, "contain", 2)
       colour := strings.Split(out[0], "bags")[0]
       contents := LineToContents(out[1])
       return bag{colour, contents}
}

func LineToContents(line string) []bag {
out := strings.Split(line, ",")
       if out[0] == line {
         return EmptyOrOne(out[0])
       } else {
         return Many(out)
       }
}

func EmptyOrOne(str string) []bag {
  isEmpty, _ := regexp.MatchString("no", str)
    if isEmpty {
      return []bag{}
    } else {
      return []bag{CreateBag(str)}
    }
}

func Many(strs []string) []bag {
contents := make([]bag, 0)
            for _, s := range strs {
              contents = append(contents, CreateBag(s))
            }
          return contents
}

func CreateBag(str string) bag {
col := strings.SplitN(str, "bag", 2)
       return bag{col[0], []bag{}}
}

type bag struct {
  colour string
    contents []bag
}

