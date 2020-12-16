use std::fs::File;
use std::io::{BufRead, BufReader, Lines, Result};
use std::path::Path;
use std::vec::Vec;

const TREE : char = '#';

struct Route {
right_step : usize,
           down_step: usize,
           num_trees: u128,
           index: usize,
}

fn main()  -> Result<()> {
  let filename = "input.txt";

  let lines = file_as_lines(&filename)?;
  let mut routes : Vec<Route> = define_routes();

  for (line_num, line) in lines.enumerate() {
    let chars : Vec<char> = to_vec_char(line);

    // XXX Width is calculated for each line although it's always the same
    // XXX Since lines is an iterator, we can't see just the first element
    let width = chars.len();

    for route in &mut routes {
      let is_line_eligible = is_line_eligible_for_route(line_num, route.down_step);
      if !is_line_eligible {
        continue;
      }

      let index = &mut route.index;
      let num_trees = &mut route.num_trees;
      let index_increment = route.right_step;

      if index_has_a_tree(&chars, index) {
        *num_trees += 1;
      }
      *index = calculate_new_index(*index, index_increment, width);
    }
  }

  print_answers(routes);

  Ok(())
}

fn file_as_lines(filename : &str) -> Result<Lines<BufReader<File>>> {
  let path = Path::new(filename);
  let file = File::open(path)?;
  let reader = BufReader::new(file);
  Ok(reader.lines())
}

// All routes start at index 0, without any trees
fn define_routes() -> Vec<Route> {
  let mut routes = Vec::new();
  routes.push(Route {right_step: 1, down_step: 1, index: 0, num_trees: 0});
  routes.push(Route {right_step: 3, down_step: 1, index: 0, num_trees: 0});
  routes.push(Route {right_step: 5, down_step: 1, index: 0, num_trees: 0});
  routes.push(Route {right_step: 7, down_step: 1, index: 0, num_trees: 0});
  routes.push(Route {right_step: 1, down_step: 2, index: 0, num_trees: 0});
  routes
}

fn to_vec_char(line : Result<String>) -> Vec<char> {
  line.unwrap().chars().collect()
}

fn is_line_eligible_for_route(line_num: usize, down_step: usize)  -> bool {
  line_num % down_step == 0
}

fn index_has_a_tree(chars: &Vec<char>, index: &usize) -> bool {
  chars[*index] == TREE
}

fn calculate_new_index(index: usize, index_increment : usize, width: usize) -> usize {
  (index + index_increment) % width
}

fn print_answers(routes: Vec<Route>) ->  () {
  println!("Answers to Part 2 are: ");
  let final_answer : u128 = routes.iter()
    .map(|x| x.num_trees as u128)
    .map(|x| {println!("{}", x); x})
    .product();
  println!("Final Answer to Part 2 is {}", final_answer);
}
