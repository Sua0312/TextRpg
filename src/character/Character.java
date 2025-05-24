package character;

import util.Status;
import util.Util;


public class Character {
    private final String name;
    private Status status;

    public Character(String name, Status status) {
        this.name = name;
        setStatus(Util.calcCP(status));
    }

    public String getName() {
        return name;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getAttack() {
        return Util.calcDamage(status);
    }

    public double getDefense() {
        return status.defense;
    }
}
