package game;

import character.Player;
import combat.Battle;
import util.BattleLogPrinter;
import util.ItemLogPrinter;
import util.RankingManager;
import util.Status;

import java.util.Scanner;

import javax.swing.JFrame;

/**
 * 게임 실행의 진입점 클래스
 * - 플레이어 이름 입력
 * - 보스 처치 시까지 전투 반복
 * - 클리어 시간 기록 후 랭킹 출력
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Coding Time Attack ==="); // 게임 시작 메시지 출력

        // ① 플레이어 이름 입력
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // ② 플레이어 캐릭터 생성 (기본 능력치 포함)
        Player player = new Player(name,  new Status(100, 10, 15, 10, 20, 5, 150));
        scanner.close();

        GameUI ui = new GameUI(player);

        // ③ 보스 캐릭터 생성 및 전투 시스템 초기화
        Battle battle = new Battle(player, ui);

        // ④ 게임 시작 시간 기록 (랭킹용)
        long startTime = System.currentTimeMillis();

        BattleLogPrinter.getUI(ui);
        ItemLogPrinter.getUI(ui);

        JFrame f = new JFrame("RPG");
        f.getContentPane().add(ui);
        f.setDefaultCloseOperation(3);
        f.pack();
        f.setResizable(false);

        f.setVisible(true);

        // ⑤ 보스 처치 시까지 전투 반복 루프
        while (true) {
            battle.init(); // 새로운 몬스터 또는 보스 등장 및 초기화

            if(battle.run()) { // 전투 실행 → 보스를 처치하면 true 반환
                long clearTime = System.currentTimeMillis() - startTime; // 클리어 시간 계산 (ms)

                // ⑥ 랭킹 저장 및 출력
                RankingManager ranking = new RankingManager();
                ranking.record(player.getName(), clearTime); // 이름과 시간 기록

                BattleLogPrinter.logRankings(ranking.getTopRankings()); // 상위 3명 출력

                break; // 게임 종료
            }

            // 다음 전투까지 대기 시간 (3초)
            ui.bLogPrinter(" ");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}