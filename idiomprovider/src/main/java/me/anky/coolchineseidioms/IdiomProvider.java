package me.anky.coolchineseidioms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdiomProvider {
    public static String getIdiom() {

        // Collection of the idioms
        List<String> jokes = new ArrayList<>();
        jokes.add("һ������");
        jokes.add("���³�˫");
        jokes.add("һ��һ��");
        jokes.add("��������");

        // Randomly create an integer according to the size of the String array
        int rnd = new Random().nextInt(jokes.size());

        return jokes.get(rnd);
    }
}
