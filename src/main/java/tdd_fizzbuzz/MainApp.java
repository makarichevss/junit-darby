package tdd_fizzbuzz;

import static tdd_fizzbuzz.FizzBuzz.compute;

public class MainApp {

    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            System.out.println(compute(i));
        }
    }
}
