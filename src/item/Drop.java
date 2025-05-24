package item;

import util.Status;
import util.Util;

import java.util.Random;

public class Drop {
    private static final Random RANDOM = new Random();

    public static Equipment createEquipment(double cp) {
        EquipmentList[] list = EquipmentList.values();
        EquipmentList base = list[RANDOM.nextInt(list.length)];
        Quality quality = Quality.randomQuality();
        double multiplier = quality.getMultiplier() * cp / 25;
        return new Equipment(base.getName(), Util.multiStatus(new Status(base.getStatus()), multiplier), quality);
    }
}
