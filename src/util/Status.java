package util;

public class Status {
    public double hp;           // 체력
    public double defense;      // 방어력
    public double damageMax;    // 최대 공격력
    public double damageMin;    // 최소 공격력
    public double balance;      // 명중 밸런스 (높을수록 평균치에 가까운 데미지)
    public double critChance;   // 크리티컬 확률 (%)
    public double critMul;      // 크리티컬 배율 (%)
    public double cp;           // 전투력 (Coding Power)

    public Status(double hp, double defense,
                  double damageMax, double damageMin,
                  double balance, double critChance, double critMul) {
        this.hp = hp;
        this.defense = defense;
        this.damageMax = damageMax;
        this.damageMin = damageMin;
        this.balance = balance;
        this.critChance = critChance;
        this.critMul = critMul;
    }

    public Status(Status other) {
        this.hp = other.hp;
        this.defense = other.defense;
        this.damageMin = other.damageMin;
        this.damageMax = other.damageMax;
        this.balance = other.balance;
        this.critChance = other.critChance;
        this.critMul = other.critMul;
        this.cp = other.cp;
    }

}