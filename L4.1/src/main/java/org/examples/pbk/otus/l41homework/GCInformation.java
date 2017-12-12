package org.examples.pbk.otus.l41homework;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.util.*;

public class GCInformation {

    static final Set<String> MIN_GC_NAMES = new HashSet<>(8);
    static final Set<String> MAJ_GC_NAMES = new HashSet<>(8);

    static {
        MIN_GC_NAMES.add("Copy");
        MIN_GC_NAMES.add("PS Scavenge");
        MIN_GC_NAMES.add("ParNew");
        MIN_GC_NAMES.add("G1 Young Generation");

        MAJ_GC_NAMES.add("MarkSweepCompact");
        MAJ_GC_NAMES.add("PS MarkSweep");
        MAJ_GC_NAMES.add("ConcurrentMarkSweep");
        MAJ_GC_NAMES.add("G1 Old Generation");
    }

    private GarbageCollectorMXBean minGC;
    private GarbageCollectorMXBean majGC;

    public class GCStats {
        long timestamp;
        String minGCName;
        String majGCName;
        long minGCCount;
        long majGCCount;
        long minGCTime;
        long majGCTime;

        private GCStats() {}

        public String toString() {
            StringBuilder sb = new StringBuilder(String.format("[%s]: ", millisToDate(timestamp)))
                    .append(String.format("MinGC[%s] -> count:%d, time(ms):%d; ", minGCName, minGCCount, minGCTime))
                    .append(String.format("MajGC[%s] -> count:%d, time(ms):%d", majGCName, majGCCount, majGCTime));
            return sb.toString();
        }

        private String millisToDate(long millis){
            return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.US).format(millis);
        }
    }

    public GCInformation() {
        init();
    }

    private void init() {
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            String gcName = gc.getName();
            if (MIN_GC_NAMES.contains(gcName)) {
                minGC = gc;
            } else if (MAJ_GC_NAMES.contains(gcName)) {
                majGC = gc;
            } else {
                throw new RuntimeException("Unknown GC");
            }
        }
    }

    public GCStats getGCStats() {
        GCStats stats = new GCStats();
        stats.timestamp = System.currentTimeMillis();
        stats.minGCName = minGC.getName();
        stats.minGCCount = minGC.getCollectionCount();
        stats.minGCTime = minGC.getCollectionTime();
        stats.majGCName = majGC.getName();
        stats.majGCCount = majGC.getCollectionCount();
        stats.majGCTime = majGC.getCollectionTime();
        return stats;
    }

    public String getMinGCName() {
        return minGC.getName();
    }

    public String getMajGCName() {
        return majGC.getName();
    }

    public void printGCStats(GCStats gcStats) {
        System.out.print(gcStats.toString());
    }
}
