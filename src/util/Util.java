package util;

public class Util {
    /**
     * Coding Power(CP) 계산 함수
     * 공격 기대값 + 방어력 + 체력 기반
     * 밸런스와 크리티컬을 고려한 기대 공격력 사용
     */
    public static Status calcCP(Status status) {
        //평균 밸런스 비율
        double balRatio = 0.5 + status.balance / 200.0;

        //평균 공격력
        double avgAtk = status.damageMin + (status.damageMax - status.damageMin) * balRatio;

        //크리티컬 기대값
        double critChanceRatio = Math.min(1.0, status.critChance / 100.0);  // 최대 100%
        double critBonus = status.critMul / 100.0 - 1.0;                    // 크리 보너스 배율
        double expectedAtk = avgAtk * (1 + critChanceRatio * critBonus); // 기대 공격력

        //최종 CP
        status.cp = expectedAtk + status.defense + status.hp * 0.3;
        return status;
    }

    public static Status multiStatus(Status status, double value) {
        status.hp *= value;
        status.defense *= value;
        status.damageMin *= value;
        status.damageMax *= value;
        status.balance *= value;
        status.critChance *= value;
        status.critMul *= value;
        return status;
    }

    public static Status diviStatus(Status status, double value) {
        status.hp /= value;
        status.defense /= value;
        status.damageMin /= value;
        status.damageMax /= value;
        status.balance /= value;
        status.critChance /= value;
        status.critMul /= value;
        return status;
    }

    public static Status addStatus(Status player, Status equipment) {
        player.hp += equipment.hp;
        player.defense += equipment.defense;
        player.damageMin += equipment.damageMin;
        player.damageMax += equipment.damageMax;
        player.balance += equipment.balance;
        player.critChance += equipment.critChance;
        player.critMul += equipment.critMul;
        return player;
    }

    public static Status minusStatus(Status player, Status equipment) {
        player.hp -= equipment.hp;
        player.defense -= equipment.defense;
        player.damageMin -= equipment.damageMin;
        player.damageMax -= equipment.damageMax;
        player.balance -= equipment.balance;
        player.critChance -= equipment.critChance;
        player.critMul -= equipment.critMul;
        return player;
    }

    /**
     * 밸런스에 따른 명중 데미지 계수 계산
     * balance가 100에 가까울수록 최대 데미지 확률이 높아짐
     * 100 초과 시에는 데미지를 벗어날 확률도 생김
     */
    public static double calcBalance(double balance) {
        double rand = Math.random();
        if(balance <= 100){
            double weight = balance / 100.0;
            return rand * (1 - weight) + weight;
        }
        else{
            double overflow = (balance - 100.0) / 100.0;
            return 1.0 + rand * overflow;
        }
    }

    /**
     * 크리티컬 확률 및 배율 계산
     * 확률에 따라 크리티컬 배율 적용, 아니면 1.0 반환
     */
    public static double calcCritical(double critChance, double critMul) {
        if (Math.random() * 100 < critChance) {
            return critMul / 100.0;
        }
        return 1.0; // 일반 공격
    }

    /**
     * 실제 공격 시 데미지를 계산하여 반환
     * 밸런스: 평균치에 가까운 데미지를 낼 확률 결정
     * 크리티컬: 확률적으로 배율 증가 적용
     */
    public static double calcDamage(Status status) {
        double damage;
        double balRatio = Util.calcBalance(status.balance);     // 밸런스 비율 적용
        double critRatio = Util.calcCritical(status.critChance, status.critMul); // 크리티컬 배율 적용

        damage = status.damageMin + (status.damageMax - status.damageMin) * balRatio * critRatio; // 최종 데미지 계산
        return damage;
    }
}
