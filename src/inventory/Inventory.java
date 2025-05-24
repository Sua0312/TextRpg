package inventory;

import character.Player;
import item.Equipment;
import util.ItemLogPrinter;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Equipment> itemList;     // 장비 아이템 리스트
    private final int maxSize = 50;             // 최대 저장 수 제한
    public Inventory() {
        this.itemList = new ArrayList<>();
    }

    public boolean addItem(Equipment item) {
        if (itemList.size() >= maxSize)
            return false;
        itemList.add(item);
        // 전투력 기준 내림차순 정렬 (좋은 장비가 위로)
        //itemList.sort(Comparator.comparingDouble(equipment -> -equipment.cp));
        return true;
    }

    public void sellItem(Equipment item, Player player) {
        if (item.isEquipped()) {
            ItemLogPrinter.printItemSoldEquippedItem(item.getName());
        } else {
            int price = (int) (item.getCp()); // 전투력만큼 골드 획득
            itemList.remove(item);
            player.addMoney(price);
            ItemLogPrinter.printItemSold(item.getName(), price);
        }
    }

    public List<Equipment> getItemList() {
        return itemList;
    }

    public Equipment findDuplicate(Equipment target) {
        for (Equipment e : itemList) {
            if (e != target && e.getBaseName().equals(target.getBaseName()) && e.getQuality() == target.getQuality() && !e.isEquipped()) {
                return e; // 동일한 종류/품질의 다른 장비 반환
            }
        }
        return null;
    }
}
