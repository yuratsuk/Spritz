package controller;

import gui.View;
import model.WordGenerator;
import text.Word;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.NoSuchElementException;

/**
 * @author Yura
 * This class is used for communication between WordGenerator (model) and View
 */
public class Controller {

    private WordGenerator wordGenerator;
    private View view;
    private Timer timer;

    /**
     *
     * @param wordGenerator - it's a model (MCV)
     */
    public Controller(final WordGenerator wordGenerator)
    {
        this.wordGenerator = wordGenerator;
        //wordGenerator.setController(this);
        view = new View(this);
        timer = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Word word = null;
                try
                {
                   word = wordGenerator.nextWord();
                   timer.setDelay(new Double(word.getDelay() * 1000).intValue());
                   view.setWord(word);
                }
                catch (NoSuchElementException ex)
                {
                    view.setWord(new Word(0, "END", 1, 1000));
                    timer.stop();
                }

            }
        });

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

}
