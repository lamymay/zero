package com.arc.zero.controller.data.test.stress;

/**
 * @author yechao
 * @date 2020/12/19 3:48 下午
 */
public class ArcTask implements Runnable {

    private String message;
    public ArcTask(String message) {
        this.message =message;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        // mock select db
        //
        System.out.println(message);
    }
}
