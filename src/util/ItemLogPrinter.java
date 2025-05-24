package util;

import java.util.ArrayList;
import java.util.List;

import game.GameUI;

public class ItemLogPrinter {
    private static final List<String> logBuffer = new ArrayList<>();
    private static GameUI ui;

    public static void getUI(GameUI gameUI) {
        ui = gameUI;
    }

    private static void printAndStore(String message) {
        ui.eLogPrinter(message);
        logBuffer.add(message);
    }

    public static void printItemEquipped(String itemName) {
        printAndStore("[아이템 장착] '" + itemName + "'을(를) 장착했습니다.");
    }

    public static void printItemUnequipped(String itemName) {
        printAndStore("[아이템 해제] '" + itemName + "'을(를) 장착 해제했습니다.");
    }

    public static void printEnhanceSuccess(String itemName, int newLevel) {
        printAndStore("[강화 성공] '" + itemName + "'이(가) +" + newLevel + "로 강화되었습니다.");
    }

    public static void printEnhanceBroken(String itemName) {
        printAndStore("[장비 파괴] '" + itemName + "'이(가) 파괴되었습니다!");
    }

    public static void printEnhanceNoMoney(String itemName) {
        printAndStore("[강화 불가] '" + itemName + "'을(를) 강화하기에 골드가 부족합니다.");
    }

    public static void printEnhanceEquippedItem(String itemName) {
        printAndStore("[강화 제한] '" + itemName + "'은(는) 장착 중이므로 강화할 수 없습니다.");
    }

    public static void printEnhancePreserved(int level) {
        printAndStore("[강화 유지] 같은 이름과 레어도의 장비가 대신 파괴되었습니다 +" + level + "로 유지됩니다.");
    }

    public static void printItemSoldEquippedItem(String itemName) {
        printAndStore("[판매 제한] '" + itemName + "'은(는) 장착 중이므로 판매할 수 없습니다.");
    }

    public static void printItemSold(String itemName, int gold) {
        printAndStore("[아이템 판매] '" + itemName + "'을(를) " + gold + "골드에 판매했습니다.");
    }
}
