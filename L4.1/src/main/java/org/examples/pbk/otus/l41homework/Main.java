package org.examples.pbk.otus.l41homework;

//-Xms512m -Xmx512m
//-XX:+UseSerialGC
//-XX:+UseParallelGC
//-XX:+UseConcMarkSweepGC
//-XX:+UseG1GC

/**
 * Файлы логов по-умолчанию создаются в домашней директории пользователя.
 * Не забудьте почистить после тестовых запусков)))
 *
 * Выводы:
 * Copy + MarkSweepCompact: лучший вариант для малых объемов памяти по общему времени работы.
 * При этом по среднему времени работы на один такт CopyGC может немного уступать G1 Young Generation GC
 *
 * PS Scavenge + PS MarkSweep: отличается очень медленной работой, очень большими "Stop the World" паузами.
 * Возможны лучшие результаты при большем количестве физических, или логических процессоров.
 * При тестовых запусках плюсов не выявлено.
 *
 * ParNew + ConcurrentMarkSweep: ConcurrentMarkSweep - самый быстрый old generation GC.
 * Может быть эффективным в приложениях, требовательных к времени отклика.
 * В новых версиях JVM объявлен Deprecated. Возможно скоро будет удален.
 *
 * G1 Young Generation + G1 Old Generation - отличается стабильно высокой скоростью работы minGC.
 * Может быть эффективным в приложениях, требовательных к времени отклика.
 * Выглядит, как улучшенная версия и замена ParNew + ConcurrentMarkSweep в новых версиях JVM.
 */
public class Main {
    public static void main(String[] args) {
        GCInformation gcInfo = new GCInformation();
        String homeDirectory = System.getProperty("user.home");
        String logFileName = "[" + gcInfo.getMinGCName() + "]+[" + gcInfo.getMajGCName() + "].log";
        LogFile file = new LogFile(homeDirectory, logFileName);
        GCInfoCollector collector = new GCInfoCollector(gcInfo, file, 60*1000, 5*60*1000);
        MemoryLeaker memoryLeaker = new MemoryLeaker(5*60*1000);
        collector.collect();
        memoryLeaker.startCountdown();
    }
}
