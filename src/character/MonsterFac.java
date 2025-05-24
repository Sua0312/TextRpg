package character;

import java.util.Random;

import util.Status;
import util.Util;

public class MonsterFac {
    private static final Random RANDOM = new Random();

    private static String getLabel(double ratio) {
        if (ratio < 0.8) return "EZ";
        if (ratio < 1.0) return "NORMAL";
        if (ratio < 1.4) return "HARD";
        if (ratio < 2.0) return "INSANE";
        return "OVERFLOWED";
    }

    public static Monster createMonster() {
        // 무작위 몬스터 타입 선택
        MonsterList[] types = MonsterList.values();
        MonsterList base = types[RANDOM.nextInt(types.length - 1)];

        // 난이도 배율 설정 (0.5 ~ 2.5)
        double ratio = RANDOM.nextDouble() * 2 + 0.5;

        // 배율 적용 후 Monster 인스턴스 생성
        return new Monster(getLabel(ratio) + " " + base.getName(), Util.multiStatus(new Status(base.getStatus()), ratio), false);
    }

    public static Monster createBoss(){
        MonsterList[] types = MonsterList.values();
        MonsterList base = types[types.length - 1];

        return new Monster("BOSS " + base.getName(), new Status(base.getStatus()), true);
    }
}
