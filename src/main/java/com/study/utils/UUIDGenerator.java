package com.study.utils;

import java.util.UUID;

public class UUIDGenerator {
    public static final UUIDGenerator INSTANCE = new UUIDGenerator();

    private static short counter = (short) 0;

    public static String uuidUpperCase36() {
        return INSTANCE.upperCase();
    }

    public static String uuidLowerCase36() {
        return INSTANCE.lowerCase();
    }

    public static String uuidUpperCase32() {
        return INSTANCE.upperCase().replace("-", "");
    }

    public static String uuidLowerCase32() {
        return INSTANCE.lowerCase().replace("-", "");
    }

    public String upperCase() {
        return generateUUID().toString().toUpperCase();
    }

    public String lowerCase() {
        return generateUUID().toString().toLowerCase();
    }

    private UUID generateUUID() {
        long mostSignificantBits = (System.currentTimeMillis() << 20) | (getCount());
        long leastSignificantBits = UUID.randomUUID().getLeastSignificantBits();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    private short getCount() {
        synchronized (UUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }

}
