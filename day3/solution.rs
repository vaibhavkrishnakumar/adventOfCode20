use std::fs::File;
use std::io::{BufRead, BufReader, Lines, Result};
use std::path::Path;

const TREE : char = '#';

fn main()  -> Result<()> {
  let filename = "input.txt";
  let lines = file_as_lines(filename)?;

  let mut num_trees = 0;
  let mut index = 0;

  for line in lines {
    let chars : Vec<char> = to_vec_char(line);

    // XXX Width is calculated for each line although it's always the same
    // XXX Since lines is an iterator, we can't see just the first element
    let width = chars.len();

    if index_is_a_tree(chars, index) {
      num_trees += 1;
    }

    index = calculate_new_index(index, width);
  }

  println!("Answer to Part 1 is {}", num_trees);
  Ok(())
}

fn file_as_lines(filename : &str) -> Result<Lines<BufReader<File>>> {
  let path = Path::new(filename);
  let file = File::open(path)?;
  let reader = BufReader::new(file);
  Ok(reader.lines())
}

fn to_vec_char(line : Result<String>) -> Vec<char> {
  line.unwrap().chars().collect()
}

fn index_is_a_tree(chars: Vec<char>, index: usize) -> bool {
  chars[index] == TREE
}

fn calculate_new_index(index: usize, width: usize) -> usize {
  (index + 3) % width
}

