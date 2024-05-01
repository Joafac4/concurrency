use std::time::Instant;
use matrix::Matrix;

mod matrix;

fn main() {
    let m1 = create_matrix(10, 1000000);

    test_sum(&m1);

    let m2 = m1.clone();
    test_add(&m1, &m2);
}

fn test_sum(result: &Matrix) {
    let t2 = Instant::now();
    let _ = result.sum_serial();
    println!("Serial 'sum': {:?}", t2.elapsed());

    let t3 = Instant::now();
    let _ = result.sum_parallel();
    println!("Parallel 'sum': {:?}", t3.elapsed());
}

fn test_add(m1: &Matrix, m2: &Matrix) {
    let t0 = Instant::now();
    let _ = m1.add_serial(&m2);
    println!("Serial 'add': {:?}", t0.elapsed());

    let t1 = Instant::now();
    let _ = m1.add_parallel(&m2);
    println!("Parallel 'add': {:?}", t1.elapsed());
}

fn create_matrix(rows: usize, cols: usize) -> Matrix {
    let mut result = Vec::new();
    let mut value = 0.0;
    for _ in 0..rows {
        let mut row = Vec::new();
        for _ in 0..cols {
            row.push(value);
            value += 1.0;
        }
        result.push(row);
    }
    Matrix(result)
}