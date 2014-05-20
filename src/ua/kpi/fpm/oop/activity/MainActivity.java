package ua.kpi.fpm.oop.activity;

import java.io.FileNotFoundException;

import ua.kpi.fpm.oop.controller.Controller;
import ua.kpi.fpm.oop.loader.Loader;
import ua.kpi.fpm.oop.loader.UnsupportedFileFormatException;
import ua.kpi.fpm.oop.model.WordGenerator;
import ua.kpi.fpm.oop.spritz.R;
import ua.kpi.fpm.oop.text.Word;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;




public class MainActivity extends Activity {

	final String LOG_TAG = "myLogs";
	
	private TextView spritzView;
	private Controller controller;
	private final int CENTRAL_POSITION = 5;
	private SeekBar speed;
	private MainActivity thisPtr;
	
	private String login;
	private String path;
	private int paragraph;
	private int word;
	
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
				if (controller != null)controller.setSpeed(speed.getProgress() + 100.0);
				
			}
		};
		
		speed.setOnSeekBarChangeListener(speedChanged);	
		
		login = getIntent().getStringExtra("login");
		path = getIntent().getStringExtra("path");
		paragraph = getIntent().getIntExtra("paragraph", 0);
		word = getIntent().getIntExtra("word", 0);
		
		
		
		Loader loader = null;
        try
        {
        	loader = Loader.getLoader(path);
        	WordGenerator generator = new WordGenerator(loader);
	        generator.setCurrentSpeed(100);
	        controller = new Controller(generator, thisPtr);
	        controller.setCurrentWord(word);
	        controller.setCurrentParagraph(paragraph);
	        
	        Log.d(LOG_TAG, path);
	        Log.d(LOG_TAG, String.valueOf(paragraph));
	        Log.d(LOG_TAG, String.valueOf(word));
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
	
	/*
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

    }*/
	
	public void OnSaveClick(View v)
	{
		DBHelper dbHelper = new DBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String[] args = new String[] { login };
		
		Cursor cursor = db.query("users", null, "user_name = ?", args,null,null,null);
		ContentValues content = new ContentValues();
		
		
		if (cursor.moveToFirst())
		{
			int idColIndex = cursor.getColumnIndex("user_id");
			int id = cursor.getInt(idColIndex);
			content.put("user_book", path);
			content.put("user_paragraph", controller.getCurrentParagraph());
			content.put("user_word", controller.getCurrentWord());
			db.update("users", content, "user_id = ?", new String[]{ String.valueOf(id) });
			db.close();
		}
		
		 
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
		//String spaces = "<font class = \"invisible_text\">";
		int numberOfSpaces = CENTRAL_POSITION - center;
        for (int i=0; i < numberOfSpaces; i++)
        {
        	spaces = spaces + "|";
        }
        //spaces = spaces + "</font>";
        spaces = spaces + "</font>";
        Spanned textSpan = android.text.Html.fromHtml(spaces+textBeforeCentralSymbol+"<font color=#FF0000>"+centralSymbol+ "</font>"+textAfterCentralSymbol);
		spritzView.setText(textSpan);
	}

	public void onClickStart(View v)
	{
		if (controller != null) controller.start();
	}
	
	public void onClickStop(View v)
	{
		if (controller != null) controller.stop();
	}
	
	public void backOnOneWord(View v)
	{
		if (controller != null) controller.backOnOneWord();
	}
	
	public void backToBeginOfParagraph(View v)
	{
		if (controller != null) controller.backToBeginOfParagraph();
	}
	
}
