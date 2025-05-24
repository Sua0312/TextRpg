package combat;

import character.Monster;
import character.MonsterFac;
import character.Player;
import game.GameUI;
import item.Equipment;
import util.BattleLogPrinter;

public class Battle {
    private final Player player;
    private Monster monster;

    private int turn;
    private boolean gameover;

    private double monsterHp;
    private double playerHp;

    public GameUI ui;

    public Battle(Player player, GameUI ui) {
        this.player = player;
        gameover = false;
        this.ui = ui;
    }

    public void init() {
        if (Math.random() < 0.05) {
            monster = MonsterFac.createBoss();
            monsterHp = monster.getStatus().hp;
            BattleLogPrinter.logBossEncounter();

        } else {
            monster = MonsterFac.createMonster();
            monsterHp = monster.getStatus().hp;
            BattleLogPrinter.logMonsterEncounter(monster.getName(), monster.getStatus().hp, (monster.getStatus().damageMin + monster.getStatus().damageMax) / 2.0);
        }
        playerHp = player.getStatus().hp;
        turn = 1;
    }

    public boolean run() {
        while (true) {
            if (turn > 0) {
                playerTurn();
            } else {
                monsterTurn();
            }

            ui.refreshAll(playerHp, monster, monsterHp);

            if (checkDead())
                break;

            turn *= -1;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return gameover;
    }

    public boolean checkDead() {
        if (playerHp <= 0) {
            BattleLogPrinter.logPlayerDeath();
            return true;
        }

        if (monsterHp <= 0) {
            if (monster.isBoss()) {
                BattleLogPrinter.logBossDefeated();
                gameover = true;
                return true;
            } else if (!monster.isBoss()) {
                BattleLogPrinter.logMonsterDefeated(monster.getName());

                int exp = monster.giveExp();
                BattleLogPrinter.logExp(exp);
                boolean lvUp = player.addExp(exp);
                if (lvUp) BattleLogPrinter.logLevelUp(player.getLevel());

                int money = monster.giveMoney();
                player.addMoney(money);
                BattleLogPrinter.logMoney(money);

                Equipment drop = monster.dropItem();
                if (drop != null) {
                    boolean added = player.getInventory().addItem(drop);
                    BattleLogPrinter.logDrop(drop.getName(), added);
                    if (!added) player.getInventory().sellItem(drop, player);
                }
            }
            return true;
        }
        return false;
    }

    public void playerTurn() {
        double damage = Math.max(1.0, player.getAttack() - monster.getDefense());
        monsterHp -= damage;
        BattleLogPrinter.logPlayerAttack(monster.getName(), damage, monsterHp);
    }

    public void monsterTurn() {
        double damage = Math.max(1.0, monster.getAttack() - player.getDefense());
        playerHp -= damage;
        BattleLogPrinter.logMonsterAttack(monster.getName(), damage, playerHp);
    }
}