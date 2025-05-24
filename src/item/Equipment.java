package item;

import util.Status;
import util.Util;

public class Equipment {
    private final String baseName;      // 장비 기본 이름 (ex. "Basic Sword")
    private String name;
    private final Quality quality;      // 품질 등급 (COMMON ~ LEGENDARY)
    private boolean isEquipped;   // 장착 여부
    private double statVariance;  // 스탯 배율
    private int enhanceLevel;

    private Status status;

    public Equipment(String name, Status status, Quality quality) {
        this.baseName = name;
        this.name = quality + " " + name;

        this.status =  Util.calcCP(status);

        this.quality = quality;
        this.statVariance = 1.0;
        this.enhanceLevel = 0;
        this.isEquipped = false;
    }

    public void updateName() {
        String enhanced = (enhanceLevel > 0) ? ("+" + enhanceLevel + " ") : "";
        this.name = enhanced + quality.toString() + " " + baseName;
    }

    public void resetValue(){
        status = Util.diviStatus(status, statVariance);
    }

    public void updateValue(){
        status = Util.multiStatus(status, statVariance);
        this.status =  Util.calcCP(status);
    }

    public String getName() {
        return name;
    }

    public String getBaseName() {
        return baseName;
    }

    public Quality getQuality() {
        return quality;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }

    public int getEnhanceLevel() {
        return enhanceLevel;
    }

    public void addEnhanceLevel() {
        resetValue();
        this.enhanceLevel++;
        this.statVariance *= 1.0 + 0.15 * quality.getMultiplier() * enhanceLevel;
        updateValue();
        updateName();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getCp(){
        return status.cp;
    }
}
