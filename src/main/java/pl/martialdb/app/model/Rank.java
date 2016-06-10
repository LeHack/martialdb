package pl.martialdb.app.model;

public class Rank {
    public static enum RankType { KYU, DAN };
    public RankType type;
    public int level;

    public Rank(RankType type, int level) {
        this.type = type;
        this.level = level;
    }
    public Rank(String type, int level) {
        this(RankType.valueOf( type ), level);
    }
};
