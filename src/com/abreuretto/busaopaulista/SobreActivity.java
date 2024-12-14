package com.abreuretto.busaopaulista;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import com.abreuretto.busaopaulista.R;

public class SobreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sobre, menu);
		return true;
	}
	
	
	  public void privaClick(View view) {  
	  	  //Implement image click function 
		  
	      Intent intent = new Intent(this, MostraWebActivity.class);
	      intent.putExtra("tipo", "1" );
	        startActivity(intent);
		  
	  }

	  
	  public void ajudaClick(View view) {  
	  	  //Implement image click function 
		  
	      Intent intent = new Intent(this, MostraWebActivity.class);
	      intent.putExtra("tipo", "2" );
	        startActivity(intent);
		  
	  }
	  
	  
	  public void urlClick(View view) {  
	  	  //Implement image click function 
		  
		  Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
          myWebLink.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.abreuretto.busaopaulista"));
              startActivity(myWebLink);
		  
	  }
	  
	  
	  
	  
}
