use crate::merge::merge;
use std::thread;

pub fn sort(array: &[i32]) -> Vec<i32> {
    let len = array.len();
    if len <= 1 {
        array.to_vec()
    }
    else {
        let (first, second) = thread::scope(|s| {
                  let x = s.spawn(|| sort(&array[..len / 2]));
                  let y = sort(&array[..len / 2]);
                    (x.join().unwrap(), y)
            });
            merge(&first, &second)
        }

    }