package com.example.abcde;




import javax.xml.validation.Validator;
import android.util.Log;
import android.text.Annotation;
import android.text.InputFilter.LengthFilter;

import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.ImageView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.view.MenuItem.OnMenuItemClickListener;

public class MainActivity extends Activity implements OnClickListener {

	// Declare Variables
	ImageView Img;
	//AdapterImages imageAdapter;
	private static final int CAMERA_REQUEST = 1;
	private static final int PICK_FROM_GALLERY = 2;
	
	
	private ImageButton ib;
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	
	private long rowID;
	private EditText edit_fname;
	private EditText edit_lname;
	private EditText edit_dob;
	private EditText edit_mobile;
	private EditText edit_email;
	private EditText edit_address;
	private ImageView img;
	
	String edit_fnamev,edit_lnamev,edit_dobv,edit_mobilev,edit_emailv,edit_addressv;
	private static final String KEY_IMAGE = "image";

	private static final String FIRSTNAME = "fName";
	private static final String LASTNAME = "lname";
	private static final String DOB = "dob";
	private static final String MOBILE = "mobile";
	private static final String EMAIL = "email";
	private static final String ADDRESS = "address";
	private static final String TAG = null;
	String namepattern="[A-Za-z]{30}";
	String emailpattern="[a-zA-z0-9_]+@+[a-z]+.+[a-z]+";
	String phnum="[0-9]{10}";
	
	//public Button Save; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contacts);
		
		//ib = (ImageButton) findViewById(R.id.imageButton1);
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
	//	edit_dob = (EditText) findViewById(R.id.editDob);
		ib.setOnClickListener(this);
		
		Img=(ImageView)findViewById(R.id.image);
		Img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectImage();
			}
		});
		
		
		
		
		edit_fname = (EditText) findViewById(R.id.editfName);
		edit_lname = (EditText) findViewById(R.id.editlName);
		edit_dob = (EditText) findViewById(R.id.editDob );
		edit_mobile = (EditText) findViewById(R.id.editMobile);
		edit_email = (EditText) findViewById(R.id.editEmail);
		edit_address = (EditText) findViewById(R.id.editAddress);
		
		
		//validation
		
		

		

		// Retrieve the Row ID from ViewNote.java
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			
			rowID = extras.getLong("row_id");
			
			edit_fname.setText(extras.getString(FIRSTNAME));
			edit_lname.setText(extras.getString(LASTNAME));
			edit_dob.setText(extras.getString(DOB));
			edit_mobile.setText(extras.getString(MOBILE));
			edit_email.setText(extras.getString(EMAIL));
			edit_address.setText(extras.getString(ADDRESS));
		}
		}
	
	String Email="[a-z0-9]@"+"[a-z]."+"[a-z]";
	String name="([A-Za-z]|[.]";
	String mobile="[0-9]{10}";
	
	

	
	
	//DOB
	
	@Override
	public void onClick(View v) {
		showDialog(0);
	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			edit_dob.setText(selectedDay + " / " + (selectedMonth + 1) + " / "
					+ selectedYear);
		}
	};
	
	// Create an ActionBar menu
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Save")
				.setOnMenuItemClickListener(this.SaveButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
				
			

		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	

	private void selectImage() {
		// TODO Auto-generated method stub
		final CharSequence[] options={"Take Photo","Choose from Gallery","Cancel"};
		AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Add Photo");
		builder.setItems(options, new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int item) {
				// TODO Auto-generated method stub

				if(options[item].equals("Take Photo")){
					Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File f=new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
					intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
					startActivityForResult(intent, 1);
				}
				else if(options[item].equals("Choose from Gallery")){
					Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, 2);
				}
				else if(options[item].equals("Cancel")){
					dialog.dismiss();
				}
				
			}
			
		
	});
		builder.show();
	}
	
	
	
	protected void onActivityResult(int requestCode,int resultCode,Intent data) {
		super.onActivityResult(requestCode,resultCode,data);
		if(resultCode==RESULT_OK){
			if(requestCode==1){
				File f=new File(Environment.getExternalStorageDirectory().toString());
				for(File temp:f.listFiles()){
					if(temp.getName().equals("temp.jpg")){
						f=temp;
						break;
					}
				}
				try{
					Bitmap bitmap;
					BitmapFactory.Options bitmapOptions=new BitmapFactory.Options();
					bitmapOptions.inSampleSize=2;
					bitmap=BitmapFactory.decodeFile(f.getAbsolutePath(),bitmapOptions);
					Img.setImageBitmap(bitmap);
					String path=android.os.Environment.getExternalStorageDirectory()+File.separator+"Phonix"+File.separator+"default";
					f.delete();
					OutputStream outFile=null;
					File file=new File(path,String.valueOf(System.currentTimeMillis())+".jpeg");
					try{
						outFile=new FileOutputStream(file);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
						outFile.flush();
						outFile.close();
					}catch(FileNotFoundException e){
						e.printStackTrace();
					}catch (IOException e) {
						e.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}else if(requestCode==2){
				Uri selectedImage=data.getData();
				String[] filePath={MediaStore.Images.Media.DATA};
				Cursor c=getContentResolver().query(selectedImage, filePath, null, null, null);
				c.moveToFirst();
				int columnIndex=c.getColumnIndex(filePath[0]);
				String picturePath=c.getString(columnIndex);
				c.close();
				Bitmap thumbnail=(BitmapFactory.decodeFile(picturePath));
				//img.setImageURI(selectedImage);
				
				Log.d(TAG,"FILE Uri:"+selectedImage.toString());
				//Log.w("path of image from gallery......*********.......",picturePath+"");
				Img.setImageBitmap(thumbnail);
			}
		}
	}

	
	

	// Capture save menu item click
	OnMenuItemClickListener SaveButtonClickListener = new OnMenuItemClickListener() {
		public boolean onMenuItemClick(MenuItem item) {

			// Passes the data into saveNote() function
			String a="[a-z0-9._-]+@[a-z]+\\.+[a-z]+";
			String b="[a-z0-9._-]";
			//String email1=edit_email.getText().toString().trim();
			String email=edit_email.getText().toString().trim();
		
		if(edit_fname.getText().length()!= 0 && edit_lname.getText().length()!=0 && edit_dob.getText().length()!=0&&
					edit_mobile.getText().length()==10 && email.matches(a) && edit_address.getText().length()!=0) {
				AsyncTask<Object, Object, Object> saveNoteAsyncTask = new AsyncTask<Object, Object, Object>() {
					@Override
					protected Object doInBackground(Object... params) {
						saveNote();
						return null;
					}

					@Override
					protected void onPostExecute(Object result) {
						// Close this activity
						finish();
					}
				};
				// Execute the saveNoteAsyncTask AsyncTask above
				saveNoteAsyncTask.execute((Object[]) null);
				Toast.makeText(getApplicationContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();

			}

			else {
				if(edit_mobile.getText().length()<10||email.matches(b)){
					Toast.makeText(getApplicationContext(), "Invalid value(s)!", Toast.LENGTH_SHORT).show();
				}
				

				
				else{
				// Display a simple alert dialog that forces user to put in a title
				AlertDialog.Builder alert = new AlertDialog.Builder(
						MainActivity.this);
				//alert.setTitle("Title is required");
				alert.setMessage("Please enter Empty Field(s)!");
				alert.setPositiveButton("Ok", null);
				alert.show();
			}
			}


			return false;
			
		}
	};
	
	



	// saveNote() function
	private void saveNote() {
		DatabaseConnector dbConnector = new DatabaseConnector(this);

		if (getIntent().getExtras() == null) {
			// Passes the data to InsertNote in DatabaseConnector.java
			dbConnector.InsertNote(edit_fname.getText().toString(), edit_lname.getText().toString(),
					edit_dob.getText().toString(),edit_mobile.getText().toString(),
					edit_email.getText().toString(),edit_address.getText().toString());
		} else {
			// Passes the Row ID and data to UpdateNote in DatabaseConnector.java
			dbConnector.UpdateNote(rowID,edit_fname.getText().toString(), edit_lname.getText().toString(),
					edit_dob.getText().toString(),edit_mobile.getText().toString(),
					edit_email.getText().toString(),edit_address.getText().toString());
		}
	}
}
