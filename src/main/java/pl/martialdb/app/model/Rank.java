package pl.martialdb.app.model;

public class Rank {
    public static enum RankType { KYU, DAN };
    public RankType type;
    public int level;

    public Rank(String type, int level) {
        this.type = RankType.valueOf( type );
        this.level = level;
    }
};
