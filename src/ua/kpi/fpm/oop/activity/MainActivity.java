package ua.kpi.fpm.oop.activity;

import java.io.FileNotFoundException;

import ua.kpi.fpm.oop.controller.Controller;
import ua.kpi.fpm.oop.loader.Loader;
import ua.kpi.fpm.oop.loader.UnsupportedFileFormatException;
import ua.kpi.fpm.oop.model.WordGenerator;
import ua.kpi.fpm.oop.spritz.R;
import ua.kpi.fpm.oop.text.Word;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spanned;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends Activity {

	TextView spritzView;
	Controller controller;
	private final int CENTRAL_POSITION = 5;
	SeekBar speed;
	MainActivity thisPtr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		thisPtr = this;
		setContentView(R.layout.main);
		
		spritzView = (TextView) findViewById(R.id.text);
		speed = (SeekBar) findViewById(R.id.speed);
		speed.setMax(900);
		
		OnSeekBarChangeListener speedChanged = new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				controller.setSpeed(speed.getProgress() + 100.0);
				
			}
		};
		
		speed.setOnSeekBarChangeListener(speedChanged);		
		
		
		
		//-----/
		
		
	        
	        
	                
	
	}
	
	public void OnOpenFileClick(View view) {
		OpenFileDialog fileDialog = new OpenFileDialog(this)
        .setFilter(".*\\.txt")
        .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
            @Override
            public void OnSelectedFile(String fileName) {
                Loader loader = null;
    	        try
    	        {
    	        	loader = Loader.getLoader(fileName);
    	        	WordGenerator generator = new WordGenerator(loader);
    		        generator.setCurrentSpeed(100);
    		        controller = new Controller(generator, thisPtr);
    	        }
    	        catch (FileNotFoundException e)
    	        {
    	        	//System.out.print(e);
    	        	
    	            return;
    	        }
    	        catch (UnsupportedFileFormatException e)
    	        {
    	            //System.out.print(e);
    	            return;
    	        }
            }
        });
		
fileDialog.show();

    }
	
	
	public void setWord(Word word)
	{
	    
		String text = word.getWord();
		int center = word.getCentralSymbol();
		
		if (text.length() == 0) return;
		
		String textBeforeCentralSymbol = text.substring(0, center);
		String centralSymbol = text.substring(center, center + 1);
		String textAfterCentralSymbol = text.substring(center + 1, text.length());
		
		String spaces = "<font color=#FFFFFF>";
		int numberOfSpaces = CENTRAL_POSITION - center;
        for (int i=0; i < numberOfSpaces; i++)
        {
        	spaces = spaces + "|";
        }
        spaces = spaces + "</font>";
		
        Spanned textSpan = android.text.Html.fromHtml(spaces+textBeforeCentralSymbol+"<font color=#FF0000>"+centralSymbol+ "</font>"+textAfterCentralSymbol);
		spritzView.setText(textSpan);
	}

	public void onClickStart(View v)
	{
		controller.start();
	}
	
	public void onClickStop(View v)
	{
		controller.stop();
	}
	
	public void backOnOneWord(View v)
	{
		controller.backOnOneWord();
	}
	
	public void backToBeginOfParagraph(View v)
	{
		controller.backToBeginOfParagraph();
	}
	
}
