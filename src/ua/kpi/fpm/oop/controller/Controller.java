package ua.kpi.fpm.oop.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.xmlpull.v1.XmlPullParserException;

import ua.kpi.fpm.oop.activity.MainActivity;
import ua.kpi.fpm.oop.model.WordGenerator;
import ua.kpi.fpm.oop.text.Word;
import android.os.Handler;
//import java.util.Timer;

/**
 * @author Yura
 * This class is used for communication between WordGenerator (model) and View
 */
public class Controller {

    private WordGenerator wordGenerator;
    private MainActivity view;
    private UITimer timer;
    public Handler uiHandler = new Handler();
    private Word currentWord;

    private Runnable runMethod = new Runnable() 
      {
          public void run()
          {
                // do something
        	  //Word word = null;
        	  try
              {
        		 view.setWord(currentWord);
        		 currentWord = wordGenerator.nextWord();
                 timer.setDelay(Double.valueOf(currentWord.getDelay() * 1000).intValue());
                 
              }
              catch (NoSuchElementException ex) 
              {
            	  view.setWord(new Word(0, "END", 1, 1000));
                  
                  timer.stop();
              }
        	  catch (NullPointerException e)
        	  {
        		  currentWord = wordGenerator.nextWord();
                  timer.setDelay(Double.valueOf(currentWord.getDelay() * 1000).intValue());
        	  }

          }
      };

    /**
     *
     * @param wordGenerator - it's a model (MCV)
     */
    public Controller(final WordGenerator wordGenerator, MainActivity view)
    {
        this.wordGenerator = wordGenerator;
        this.view = view;
        //wordGenerator.setController(this);
        //view = new View(this);
        
        timer = new UITimer(uiHandler, runMethod, 5);        
        

    }

    public void setSpeed(double speed)
    {
        wordGenerator.setCurrentSpeed(speed);
    }

    /**
     * This method starts timer.
     * Timer sends words to View.
     */
    public void start()
    {
        timer.start();
    }

    /**
     * This method stops timer.
     */
    public void stop()
    {
        timer.stop();
    }

    public void backOnOneWord()
    {
    	int currentWord = wordGenerator.getCurrentWord();
    	if (currentWord > 1)
    		{
    		wordGenerator.setCurrentWord(currentWord - 2);
    		view.setWord(wordGenerator.nextWord());
    		}
    }
    public void backToBeginOfParagraph()
    {
    	wordGenerator.setCurrentWord(0);
    	view.setWord(wordGenerator.nextWord());
    }
}
