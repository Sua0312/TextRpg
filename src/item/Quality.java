package item;

public enum Quality {
    COMMON("Common", 1.0),        // 가장 낮은 등급
    MAGIC("Magic", 1.2),
    RARE("Rare", 1.5),
    EPIC("Epic", 2.0),
    LEGENDARY("Legendary", 3.0); // 가장 높은 등급

    private final String label;        // 품질 이름 (표시용)
    private final double multiplier;   // 스탯 배율 (높을수록 성능 우수)

    Quality(String label, double multiplier) {
        this.label = label;
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        return label;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public static Quality randomQuality() {
        double r = Math.random();
        if (r < 0.40) return COMMON;
        if (r < 0.70) return MAGIC;
        if (r < 0.85) return RARE;
        if (r < 0.95) return EPIC;
        return LEGENDARY;
    }
}
