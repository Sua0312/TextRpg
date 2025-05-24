package character;

import item.Drop;
import item.Equipment;
import util.Status;

public class Monster extends Character {
    private final boolean isBoss;
    public Monster(String name, Status status, boolean isBoss) {
        super(name, status);
        this.isBoss = isBoss;
    }

    public boolean isBoss() {
        return isBoss;
    }

    public int giveMoney() {
        return (int) Math.max(1, getStatus().cp / 2);
    }

    public int giveExp() {
        return (int) getStatus().cp;
    }

    public Equipment dropItem() {
        double dropRate = 30;
        if (Math.random() * 100 < dropRate) {
            return Drop.createEquipment(getStatus().cp); // CP 기반 장비 생성
        }
        return null; // 드랍 없음
    }
}
