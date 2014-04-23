package gui;

import controller.Controller;
import text.Word;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Yura
 * This is GUI.
 */
public class View extends JFrame{

    private JTextArea textArea;
    private JButton startButton;
    private JButton stopButton;
    private Controller controller;
    public final int MAX_WORD_LENGTH = 13;
    private final int CENTER_POSITION = 5;
    private final int MIN_SPEED = 50; //words per minute
    private final int MAX_SPEED = 1000;
    private final int DEFAULT_SPEED = 100;
    private JSlider speedSlider;


    /**
     *
     * @param controller - this object is used for communication between WordGenerator (model) and View
     */
    public View(final Controller controller)
    {


        super("Spritz"); //Заголовок окна
        //setBounds(100, 100, 200, 200);

        this.controller = controller;

        textArea = new JTextArea();
        add(textArea, BorderLayout.NORTH);
        textArea.setSelectedTextColor(Color.red);
        textArea.setSelectionColor(Color.white);
        textArea.setCaretColor(Color.white);
        textArea.setFont(Font.decode(Font.MONOSPACED));

        startButton = new JButton("Start");
        add(startButton, BorderLayout.WEST);

        stopButton = new JButton("Stop");
        add(stopButton, BorderLayout.EAST);

        speedSlider = new JSlider(MIN_SPEED ,MAX_SPEED, DEFAULT_SPEED);
        add(speedSlider, BorderLayout.SOUTH);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);


        startButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.start();
            }
        });

        stopButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                controller.stop();
            }
        });

        speedSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                controller.setSpeed(speedSlider.getValue());
            }
        });

    }

    /**
     * This method format and write word into textArea.
     * Word Central Symbol must be in CENTER_POSITION of text area
     * @param word - text and attribute (number of centralSymbol)
     */
    public void setWord(Word word)
    {
        textArea.requestFocus();
        int centralSymbol = word.getCentralSymbol();
        String text = word.getWord();
        int numberOfSpaces = CENTER_POSITION - centralSymbol;
        for (int i=0; i < numberOfSpaces; i++)
        {
            text = " " + text;
        }
        textArea.setText(text);
        textArea.select(CENTER_POSITION, CENTER_POSITION + 1);


    }
}
