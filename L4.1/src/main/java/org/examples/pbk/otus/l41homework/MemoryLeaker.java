package org.examples.pbk.otus.l41homework;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MemoryLeaker {
    private static final long TASK_PERIOD = 1000;

    private long time;
    private Queue queue;
    private Timer timer;
    private TimerTask task;

    public MemoryLeaker(int timeToOOMError) {
        this.time = timeToOOMError;
        this.queue = new ConcurrentLinkedQueue();
        init();
    }

    private void init() {
        Timer timer = new Timer("MemoryLeaker Timer");

        TimerTask task = new TimerTask() {
            Runtime runtime = Runtime.getRuntime();
            long previousFreeMemory;
            long totalTasks = Math.round(time / TASK_PERIOD);
            Integer obj = 0;
            int objSize = 16;
            @Override
            public void run() {
                if (totalTasks > 0) {
                    long currentFreeMemory = runtime.freeMemory();
                    int objToAdd = Math.round((currentFreeMemory / objSize / totalTasks)) + 1;
                    int objToRemove = Math.round(objToAdd / 2) - 1;
                    if (totalTasks == 1 && objToAdd < 10_000) {
                        objToAdd = 10_000;
                    }
                    System.out.println("Task #" + totalTasks +
                            " -> free memory:[" + currentFreeMemory/(1 << 20) + "Mb " +
                            (currentFreeMemory > previousFreeMemory? "\u001B[32m\u2191\u001B[0m": "\u001B[31m\u2193\u001B[0m") +
                            " ] -> creating " + objToAdd + " objects");
                    previousFreeMemory = currentFreeMemory;
                    while (objToAdd > 0) {
                        queue.add(obj++);
                        objToAdd--;
                    }
                    while (objToRemove > 0) {
                        queue.poll();
                        objToRemove--;
                    }
                    totalTasks--;
                } else {
                    timer.cancel();
                }
            }
        };
        this.timer = timer;
        this.task = task;
    }

    public void startCountdown() {
        timer.scheduleAtFixedRate(task, 1100, TASK_PERIOD);
    }
}
