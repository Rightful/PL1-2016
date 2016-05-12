package io.github.programminglife2016.pl1_2016.parser;

/**
 * Created by Kamran Tadzjibov on 11.05.2016.
 */
public class Tuple implements ITuple {
    private int Item1;
    private int Item2;

    public Tuple (int Item1, int Item2) {
        this.Item1 = Item1;
        this.Item2 = Item2;
    }

    public int getItem1() {
        return Item1;
    }

    public int getItem2() {
        return Item2;
    }
}
