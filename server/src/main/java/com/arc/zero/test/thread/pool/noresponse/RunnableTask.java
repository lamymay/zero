package com.arc.zero.test.thread.pool.noresponse;

/**
 * @author yechao
 * @date 2020/10/12 4:34 下午
 */
public class RunnableTask implements Runnable {
    private int name;

    public RunnableTask(int name) {
        this.name = name;
    }


    @Override
    public void run() {
        System.out.println("### START TASK " + name + " ###");
        System.out.println(
                "name=" + Thread.currentThread().getName()
                        + "  id=" + Thread.currentThread().getId()
                        + " state=" + Thread.currentThread().getState());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
        }
        System.out.println("END TASK " + name + " !!!");
    }
}

