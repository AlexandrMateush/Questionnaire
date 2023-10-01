package com.example.a15151.entitys;

public class Result {

    private int level;
    private int lastScore;
    private int bestScore;

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLastScore(int lastScore) {
        this.lastScore = lastScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public Result(int level, int lastScore, int bestScore) {
        this.level = level;
        this.lastScore = lastScore;
        this.bestScore = bestScore;
    }

    public int getLevel() {
        return level;
    }

    public int getLastScore() {
        return lastScore;
    }

    public int getBestScore() {
        return bestScore;
    }
}

