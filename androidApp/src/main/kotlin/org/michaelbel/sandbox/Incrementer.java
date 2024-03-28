package org.michaelbel.sandbox;

public class Incrementer {
    int count = 0;

    public /*synchronized*/ void inc() {
        if (count < 10) {
            count++;
            System.out.println("count = " + count);
            inc();
        }
    }
}