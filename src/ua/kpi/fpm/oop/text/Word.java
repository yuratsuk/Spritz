package ua.kpi.fpm.oop.text;

/**
 * @author Yura
 * This class contains word and atributes
 * id - number in paragraph
 * centralSymbol - symbol with that number will be central symbol
 * delay - delay time on the screen in millisecond
 */
public class Word {
    private String word;
    private int centralSymbol;
    private double delay;
    private int id;

    public Word(int id, String word, int centralSymbol, double delay)
    {
        this.id = id;
        this.word = word;
        this.centralSymbol = centralSymbol;
        this.delay = delay;
    }

    public int getId() {
        return id;
    }

    public int getCentralSymbol() {
        return centralSymbol;
    }

    public String getWord() {
        return word;
    }

    public double getDelay() {
        return delay;
    }
}
