package org.examples.pbk.otus.l41homework;

import java.text.DateFormat;
import java.util.*;

public class GCInfoCollector {
    private GCInformation gcInfo;
    private LogFile file;
    private long collectionPeriod;
    private long totalTime;

    private Timer timer;
    private TimerTask collectInfoTask;
    private TimerTask cancellationTask;

    private GCInformation.GCStats first;
    private GCInformation.GCStats previous;
    private GCInformation.GCStats current;

    public GCInfoCollector(GCInformation gcInfo, LogFile file, long collectionPeriod, long totalTime) {
        this.gcInfo = gcInfo;
        this.file = file;
        this.collectionPeriod = collectionPeriod;
        this.totalTime = totalTime;
        initTask();
    }

    public void collect() {
        timer.scheduleAtFixedRate(collectInfoTask, 0, collectionPeriod);
        timer.schedule(cancellationTask, totalTime);
    }

    private void initTask() {
        this.timer = new Timer("GCinfoCollector");
        this.collectInfoTask = new TimerTask() {
            @Override
            public void run() {
                GCInformation.GCStats gcStats = gcInfo.getGCStats();
                current = gcStats;
                if (previous != null) {
                    String difference = getDifferenceString(previous, current);
                    System.out.println(difference);
                    file.writeLogEntry(difference);
                } else {
                    first = gcStats;
                }
                previous = gcStats;
            }
        };
        this.cancellationTask = new TimerTask() {
            @Override
            public void run() {
                GCInformation.GCStats total = gcInfo.getGCStats();
                String totalDifference = getDifferenceString(first, total);
                System.out.println("Total: " + totalDifference);
                file.writeLogEntry("Total: " + totalDifference);
                file.writeLogEntry("");
                timer.cancel();
            }
        };
    }

    private String getDifferenceString(GCInformation.GCStats previousStats, GCInformation.GCStats currentStats) {
        long minGCCount = currentStats.minGCCount - previousStats.minGCCount;
        long minGCTime = currentStats.minGCTime - previousStats.minGCTime;
        long majGCCount = currentStats.majGCCount - previousStats.majGCCount;
        long majGCTime = currentStats.majGCTime - previousStats.majGCTime;
        StringBuilder sb = new StringBuilder(String.format("[%s]-[%s]: ", millisToDate(previousStats.timestamp), millisToDate(currentStats.timestamp)))
                .append(String.format("MinGC[%s] -> count:%d, time(ms):%d; ", previousStats.minGCName, minGCCount, minGCTime))
                .append(String.format("MajGC[%s] -> count:%d, time(ms):%d", previousStats.majGCName, majGCCount, majGCTime));
        return sb.toString();
    }

    private String millisToDate(long millis){
        return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US).format(millis);
    }
}
