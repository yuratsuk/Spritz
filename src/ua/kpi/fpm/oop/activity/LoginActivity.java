package ua.kpi.fpm.oop.activity;


import ua.kpi.fpm.oop.spritz.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	private EditText login;
	private Button newBookBtn;
	private Button oldBookBtn;
	private Button loginBtn;
	
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private int paragraph;
	private int word;
	private String path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.login);
	    
	    login = (EditText) findViewById(R.id.login);
	    newBookBtn = (Button) findViewById(R.id.newBookBtn);
	    oldBookBtn = (Button) findViewById(R.id.oldBookBtn);
	    loginBtn = (Button) findViewById(R.id.loginBtn);
	    
	    dbHelper = new DBHelper(this);
	    db = dbHelper.getWritableDatabase();
	    
	    /*dbHelper.close();*/
	    
	   }
	
	public void newBookClick(View v)
	{
		//loginBtn.setVisibility(-1);  //-1 - invis,  0 - vis
		//String path = null;
		OpenFileDialog fileDialog = new OpenFileDialog(this)
        .setFilter(".*\\.txt")
        .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
            @Override
            public void OnSelectedFile(String fileName) {
               path = fileName;
               paragraph = 0;
       			word = 0;
       		
       		startActivity(createIntent());
            }
        });
		
		fileDialog.show();
		
		
	}
	
	public void oldBookClick(View v)
	{
		
		int oldBookColIndex = cursor.getColumnIndex("user_book");
		int parColIndex = cursor.getColumnIndex("user_paragraph");
		int wordColIndex = cursor.getColumnIndex("user_word");
		
		path = cursor.getString(oldBookColIndex); 
		paragraph = cursor.getInt(parColIndex);
		word = cursor.getInt(wordColIndex);
		
		db.close();				
		
		startActivity(createIntent());
	}
	
private Intent createIntent()
{
	Intent intent = new Intent(this, MainActivity.class);
	intent.putExtra("login",login.getText().toString());
	intent.putExtra("path",path);
	intent.putExtra("paragraph",paragraph);
	intent.putExtra("word",word);
	return intent;
}
	     
	public void loginClick(View v)
	{
		if ( login.getText().toString().equals("") ) return;

		db = dbHelper.getWritableDatabase();

		
		String[] args = new String[] { login.getText().toString() };
		cursor = db.query("users", null, "user_name = ?", args,null,null,null);
		
		int oldBookColIndex = cursor.getColumnIndex("user_book");
		
		if (cursor.moveToFirst())
		{
			if(cursor.getString(oldBookColIndex) != null) 
				{
				oldBookBtn.setVisibility(0);
				}
			
		} else
		{
			ContentValues content = new ContentValues();
			content.put("user_name", login.getText().toString());
			db.insert("users", null, content);
			db.close();
		}
		newBookBtn.setVisibility(0);
		loginBtn.setVisibility(-1);
		
		
	}
	
	

}
