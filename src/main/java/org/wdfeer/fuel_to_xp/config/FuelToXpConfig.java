package org.wdfeer.fuel_to_xp.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class FuelToXpConfig extends MidnightConfig {
    @Entry(min = 10, max = 1E4)
    public static int fuelPerXp = 200;
    @Entry(min = 0, max = 1E4)
    public static int delayTicks = 20;
}
