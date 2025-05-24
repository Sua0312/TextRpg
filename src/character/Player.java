package character;

import inventory.Inventory;
import item.Equipment;
import util.ItemLogPrinter;
import util.Status;
import util.Util;

public class Player extends Character {
    private int level;       // 현재 레벨
    private int expMax;      // 다음 레벨까지 필요한 경험치
    private int expNow;      // 현재 경험치
    private int money;       // 보유 골드

    private Inventory inventory;    // 인벤토리
    private Equipment equipment;    // 현재 장착 중인 장비 (1개)


    public Player(String name, Status status) {
        super(name, status);

        this.level = 1;
        this.expMax = 10;
        this.expNow = 0;
        this.money = 0;

        inventory = new Inventory();
        equipment = null;
    }

    public int getLevel() {
        return level;
    }

    public int getExpMax() {
        return expMax;
    }

    public int getExpNow() {
        return expNow;
    }

    public boolean addExp(int value) {
        boolean up = false;
        expNow += value;

        while (expNow >= expMax) {
            levelUp();
            up = true;
        }
        return up;
    }

    public void levelUp() {
        level++;
        expNow = expNow - expMax;
        expMax += level * (level + 10);

        Status tempStats = getStatus();
        tempStats.hp += 10 + level * 3;
        tempStats.defense += 5 + level;
        tempStats.damageMax += 1 + level * 4;
        tempStats.damageMin += 1 + level * 2;
        setStatus(Util.calcCP(tempStats));
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int value) {
        money += value;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void equipItem(Equipment item) {
        if (equipment != null) {
            unequipItem(equipment);
        }
        equipment = item;
        setStatus(Util.addStatus(getStatus(), item.getStatus()));
        item.setEquipped(true);
        setStatus(Util.calcCP(getStatus()));
        ItemLogPrinter.printItemEquipped(item.getName());

    }
    public void unequipItem(Equipment item) {
        equipment = null;

        setStatus(Util.minusStatus(getStatus(), item.getStatus()));
        item.setEquipped(false);
        item.updateName();
        setStatus(Util.calcCP(getStatus()));
        ItemLogPrinter.printItemUnequipped(item.getName());
    }
}
