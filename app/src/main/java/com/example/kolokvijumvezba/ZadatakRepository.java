package com.example.kolokvijumvezba;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ZadatakRepository {
    private static final List<Zadatak> TASKS = new ArrayList<>();
    private static boolean isFabRed = false;

    private ZadatakRepository() {}

    public static List<Zadatak> getTasks() {
        return Collections.unmodifiableList(TASKS);
    }

    public static int addTask(Zadatak zadatak) {
        TASKS.add(zadatak);
        return TASKS.size();
    }

    public static int getCount() {
        return TASKS.size();
    }

    public static boolean isFabRed() {
        return isFabRed;
    }

    public static void setFabRed(boolean value) {
        isFabRed = value;
    }
}

