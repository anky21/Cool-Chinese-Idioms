package me.anky.coolchineseidioms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdiomProvider {
    public static String getIdiom() {

        // Collection of the idioms
        List<String> jokes = new ArrayList<>();
        jokes.add("一鸣惊人");
        jokes.add("好事成双");
        jokes.add("一心一意");
        jokes.add("坐井观天");

        // Randomly create an integer according to the size of the String array
        int rnd = new Random().nextInt(jokes.size());

        return jokes.get(rnd);
    }
}
