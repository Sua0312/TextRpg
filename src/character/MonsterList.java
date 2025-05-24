package character;

import util.Status;

/**
 * 몬스터의 기본 능력치를 정의한 enum 목록
 * 각 항목은 이름, HP, 방어력, 공격력, 크리티컬 정보 등을 포함함
 */
public enum MonsterList {
    BRONZE("Bronze", new Status(10, 1, 3, 1, 10, 5, 120)),
    SILVER("Silver",  new Status(50, 5, 10, 5, 20,10, 130)),
    GOLD("Gold",  new Status(200, 10, 40, 20, 30,15, 150)),
    PLATINUM("Platinum", new Status(500, 20, 200, 100, 40,20, 160)),
    DIAMOND("Diamond", new Status(2000, 50, 650, 450, 50, 30, 200)),
    Ruby("ruby", new Status(25000, 1000, 2800, 2100, 300, 75, 500));

    private final String name;
    private final Status status;

    MonsterList(String name, Status status) {
        this.name = name;
        this.status = status;
    }
    public String getName() {
        return name;
    }
    public Status getStatus() {
        return status;
    }
}
