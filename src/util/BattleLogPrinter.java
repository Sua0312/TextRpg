package util;

import java.util.ArrayList;
import java.util.List;

import game.GameUI;

/**
 * 전투 중 발생하는 이벤트를 출력하는 유틸리티 클래스. 현재는 콘솔(System.out)로 출력되며, 이후 GUI와 연결되도록 수정 가능.
 */
public class BattleLogPrinter {
    private static final List<String> logBuffer = new ArrayList<>();
    private static GameUI ui;

    public static void getUI(GameUI gameUI) {
        ui = gameUI;
    }

    private static void printAndStore(String message) {
        // System.out.println(message);
        ui.bLogPrinter(message);
        logBuffer.add(message);
    }

    // 보스와 조우했을 때 호출
    public static void logBossEncounter() {
        printAndStore("!!! BOSS ENCOUNTER !!!");
    }

    // 일반 몬스터와 조우했을 때 호출
    public static void logMonsterEncounter(String name, double hp, double avgAtk) {
        printAndStore(String.format("You meet %s! / HP: %.0f | Avg ATK: %.1f", name, hp, avgAtk));
    }

    // 플레이어가 몬스터에게 공격했을 때 호출
    public static void logPlayerAttack(String target, double damage, double hpLeft) {
        printAndStore(String.format("You deal %.1f damage to %s", damage, target));
        printAndStore(String.format("%s HP left: %.1f", target, Math.max(hpLeft, 0)));
    }

    // 몬스터가 플레이어를 공격했을 때 호출
    public static void logMonsterAttack(String name, double damage, double hpLeft) {
        printAndStore(String.format("%s deals %.1f damage to you", name, damage));
        printAndStore(String.format("Player HP left: %.1f", Math.max(hpLeft, 0)));
    }

    // 플레이어가 사망했을 때 호출
    public static void logPlayerDeath() {
        printAndStore("You die!");
    }

    // 보스를 처치했을 때 호출 (게임 종료)
    public static void logBossDefeated() {
        printAndStore("\uD83C\uDF89 YOU DEFEATED THE FINAL BOSS! GAME OVER.");
    }

    // 일반 몬스터를 처치했을 때 호출
    public static void logMonsterDefeated(String name) {
        printAndStore(name + " is defeated!");
    }

    // 경험치를 획득했을 때 호출
    public static void logExp(int value) {
        printAndStore(String.format("You gained %d EXP!", value));
    }

    // 레벨업 시 호출
    public static void logLevelUp(int level) {
        printAndStore(String.format("\uD83C\uDF89 Level Up! You are now level %d!", level));
    }

    // 골드를 획득했을 때 호출
    public static void logMoney(int value) {
        printAndStore(String.format("You gained %d gold!", value));
    }

    // 아이템 드랍 결과 출력 (획득 또는 자동판매)
    public static void logDrop(String name, boolean added) {
        if (added) {
            printAndStore("You obtained: " + name);
        } else {
            printAndStore("Inventory is full. " + name + " was sold automatically.");
        }
    }

    // 랭킹 목록 출력 (상위 3위까지)
    public static void logRankings(List<RankRecord> rankings) {
        printAndStore("=== TOP 3 RANKINGS ===");
        for (RankRecord record : rankings) {
            printAndStore(record.toDisplayString());
        }
    }
}
