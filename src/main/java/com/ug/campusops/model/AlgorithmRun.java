package com.ug.campusops.model;

/**
 * Records the result of a single algorithm performance experiment.
 * Used for the empirical efficiency lab (Section 9 of the project brief).
 *
 * Maps to the 'algorithm_runs' database table.
 */
public class AlgorithmRun {

    private int runId;
    private String algorithmName;  // e.g. "Dijkstra", "QuickSort", "BFS", "BinarySearch"
    private int inputSize;         // number of elements/nodes used in this run
    private long timeNs;           // execution time in nanoseconds
    private long memoryKb;         // memory used in kilobytes
    private String dateRun;        // ISO datetime string, e.g. "2025-10-01T14:30:00"

    /** Default constructor */
    public AlgorithmRun() {}

    /** Full constructor */
    public AlgorithmRun(int runId, String algorithmName, int inputSize,
                        long timeNs, long memoryKb, String dateRun) {
        this.runId = runId;
        this.algorithmName = algorithmName;
        this.inputSize = inputSize;
        this.timeNs = timeNs;
        this.memoryKb = memoryKb;
        this.dateRun = dateRun;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public int getRunId() { return runId; }
    public String getAlgorithmName() { return algorithmName; }
    public int getInputSize() { return inputSize; }
    public long getTimeNs() { return timeNs; }
    public long getMemoryKb() { return memoryKb; }
    public String getDateRun() { return dateRun; }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setRunId(int runId) { this.runId = runId; }
    public void setAlgorithmName(String algorithmName) { this.algorithmName = algorithmName; }
    public void setInputSize(int inputSize) { this.inputSize = inputSize; }
    public void setTimeNs(long timeNs) { this.timeNs = timeNs; }
    public void setMemoryKb(long memoryKb) { this.memoryKb = memoryKb; }
    public void setDateRun(String dateRun) { this.dateRun = dateRun; }

    @Override
    public String toString() {
        return String.format("AlgorithmRun[id=%d, algo='%s', n=%d, time=%dns, mem=%dKB]",
                runId, algorithmName, inputSize, timeNs, memoryKb);
    }
}
