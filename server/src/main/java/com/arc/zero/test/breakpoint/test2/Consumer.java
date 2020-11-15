package com.arc.zero.test.breakpoint.test2;

public class Consumer implements Runnable {
    private Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        while (true) {
            box.get();
        }
    }
}
