package com.rootpower.roothelper;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TermGroup extends ActivityGroup {

        // Keep this in a static variable to make it accessible for all the nested activities, lets them manipulate the view
	public static TermGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      group = this;

              // Start the root activity withing the group and get its view
	      View view = getLocalActivityManager().startActivity("TermActivity", new
	    	      							Intent(this, Term.class)
	    	      							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	    	                                .getDecorView();

              // Replace the view of this ActivityGroup
	      setContentView(view);

	   }
}
