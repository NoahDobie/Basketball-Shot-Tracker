package com.example.basketballshottracker.ui.history;

public class previousSession {

    private String date;
    private String makes;
    private String misses;
    private String total;
    private boolean expanded;
    private boolean highScore = false;


    public previousSession(String date, String makes, String misses, String total) {
        this.date = date;
        this.makes = makes;
        this.misses = misses;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = date;
    }

    public String getMakes() {
        return makes;
    }

    public void setMakes(String makes) {
        this.makes = makes;
    }

    public String getMisses() {
        return misses;
    }

    public void setMisses(String misses) {
        this.misses = misses;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isHighScore() {
        return highScore;
    }

    public void setHighScore(boolean highScore) {
        this.highScore = highScore;
    }

    @Override
    public String toString() {
        return "Date{" +
                "makes='" + makes + '\'' +
                ", misses='" + misses + '\'' +
                ", total='" + total + '\'' +
                ", expanded=" + expanded + '\'' +
                ", highScore=" + highScore +
                '}';
    }
}
