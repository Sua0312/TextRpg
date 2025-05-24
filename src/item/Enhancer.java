package item;

import character.Player;
import inventory.Inventory;
import util.ItemLogPrinter;

public class Enhancer {
    public static void tryEnhance(Equipment item, Inventory inventory, Player player) {
        if(item.isEquipped()) {
            ItemLogPrinter.printEnhanceEquippedItem(item.getName());
            return;
        }
        int cost = calculateCost(item);
        if (player.getMoney() < cost) {
            ItemLogPrinter.printEnhanceNoMoney(item.getName());
            return;
        }

        player.addMoney(-cost);
        double successRate = calculateSuccessRate(item);

        if (Math.random() < successRate) {
            item.addEnhanceLevel();
            ItemLogPrinter.printEnhanceSuccess(item.getName(), item.getEnhanceLevel());
        } else {
            Equipment duplicate = inventory.findDuplicate(item);
            if (duplicate != null) {
                inventory.getItemList().remove(duplicate);
                ItemLogPrinter.printEnhancePreserved(item.getEnhanceLevel());
            } else {
                inventory.getItemList().remove(item);
                ItemLogPrinter.printEnhanceBroken(item.getName());
            }
        }
    }

    public static double calculateSuccessRate(Equipment item) {
        int level = item.getEnhanceLevel();
        double penalty = item.getQuality().getMultiplier() * level * level * 1.5;
        return Math.max(2.0, (100.0 - penalty)) / 100.0;
    }

    public static int calculateCost(Equipment item) {
        double base = item.getCp();

        double multiplier = 1.0 + item.getEnhanceLevel() * 0.6;
        return (int) (base * multiplier);
    }
}