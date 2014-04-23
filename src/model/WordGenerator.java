package model;


import controller.Controller;
import loader.Loader;
import text.Paragraph;
import text.Word;

import java.util.*;

/**
 * @author Lana
 * This class divides paragraph into words and set attributes (id, number of central symbol and delay)
 */
public class WordGenerator {

    private int currentWord;
    private Loader loader;
    private List<String> words;
    private double currentSpeed;    //words per minute

    //private Controller controller;

    /**
     * @param loader - typed to file loader
     */
    public WordGenerator(Loader loader)
    {
        currentWord = 0;
        this.loader = loader;
        words = new ArrayList<String>();

    }

    /**
     * This method requests the loader for new paragraph.
     * Then it divide paragraph into words
     * @throws NoSuchElementException
     */
    private void loadNextParagraph() throws NoSuchElementException
    {
        Paragraph paragraph = loader.nextParagraph();
        String text = paragraph.getParagraph() + " ";
        for (; !(text.isEmpty()) ;)
        {
            int firstSpace = text.indexOf(" ");
            words.add(text.substring(0,firstSpace));
            text = text.substring(firstSpace + 1);
        }
    }

    /**
     * @return text of next word in container
     * @throws NoSuchElementException
     */
    public Word nextWord() throws NoSuchElementException
    {
        checkTheEndOfParagraph();

        //if (words.get(currentWord).length() > 13)

        int centralSymbol = (words.get(currentWord).length() + 6) / 4 - 1; // spritz formula
        double delay = 60.0 / currentSpeed; // 60 seconds in minute

        return new Word(currentWord, words.get(currentWord++), centralSymbol, delay);
    }

    /**
     * This method calls method loadNextParagraph, if current paragraph is ended
     * @throws NoSuchElementException
     */
    private void checkTheEndOfParagraph() throws NoSuchElementException
    {
        if (currentWord == words.size())
        {
            this.loadNextParagraph();
        }
    }

    private void setCurrentWord(int currentWord)
    {
        this.currentWord = currentWord;
    }

    public int getCurrentWord(){
        return currentWord;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    /*public void setController(Controller controller) {
        this.controller = controller;
    }*/
}
