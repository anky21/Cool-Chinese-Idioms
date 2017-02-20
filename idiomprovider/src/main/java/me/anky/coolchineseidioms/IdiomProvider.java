package me.anky.coolchineseidioms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdiomProvider {
    public static String getIdiom() {

        // Collection of the idioms
        List<String> jokes = new ArrayList<>();
        jokes.add("Yi Ming Jing Ren");
        jokes.add("Hua Long Dian Jing");
        jokes.add("Yi Xin Yi Yi");
        jokes.add("Xi Qi Yang Yang");
        jokes.add("Hao Shi Cheng Shuang");
//        jokes.add("一");
//        jokes.add("二");
//        jokes.add("三");

        // Randomly create an integer according to the size of the String array
        int rnd = new Random().nextInt(jokes.size());

        return jokes.get(rnd);
    }
}
