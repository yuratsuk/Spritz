package ua.kpi.fpm.oop.model;


import ua.kpi.fpm.oop.controller.Controller;
import ua.kpi.fpm.oop.loader.Loader;
import ua.kpi.fpm.oop.text.Paragraph;
import ua.kpi.fpm.oop.text.Word;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Yura
 * This class divides paragraph into words and set attributes (id, number of central symbol and delay)
 */
public class WordGenerator {

    private int currentWord;
    private Loader loader;
    private List<String> words;
    private double currentSpeed;    //words per minute
    private final double COMMA_DELAY_FACTOR = 1.5;
    private final double POINT_DELAY_FACTOR = 2; 

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
            String word = text.substring(0,firstSpace);
            for (; word.length() > 13 ;)
            {
            	words.add(word.substring(0, word.length() / 2) + "-");
            	word = word.substring(word.length() /2);
            }
            words.add(word);
            if (word.endsWith(".")) words.add(" ");
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

        String word = words.get(currentWord);
        int centralSymbol = (word.length() + 6) / 4 - 1; // spritz formula
        double delay = 60.0 / currentSpeed; // 60 seconds in minute
        
        if (word.endsWith(",")) delay *= COMMA_DELAY_FACTOR;
        if (word.endsWith(".")) delay *= POINT_DELAY_FACTOR;
        if (word.endsWith("!")) delay *= POINT_DELAY_FACTOR;
        if (word.endsWith("?")) delay *= POINT_DELAY_FACTOR;
        if (word.endsWith(";")) delay *= POINT_DELAY_FACTOR;
       
        Word newWord = new Word(currentWord, words.get(currentWord), centralSymbol, delay);
        currentWord++;
        return newWord;
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

    public void setCurrentWord(int currentWord)
    {
        this.currentWord = currentWord;
    }

    public int getCurrentWord()
    {
        return currentWord;
    }

    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    public void setCurrentParagraph(int paragraph) throws FileNotFoundException
    {
    	loader.setCurrentparagraph(paragraph);
    	loadNextParagraph();
    }

    public int getCurrentParagraph()
    {
    	return loader.getCurrentParagraph();
    }
    /*public void setController(Controller controller) {
        this.controller = controller;
    }*/
}
