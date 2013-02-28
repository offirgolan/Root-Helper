package com.rootpower.roothelper;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;



public class RootHelper extends TabActivity {
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	Resources res = getResources(); // Resource object to get Drawables
	TabHost tabHost = getTabHost();  // The activity TabHost
	TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	Intent intent;  // Reusable Intent for each tab

	// Create an Intent to launch an Activity for the tab (to be reused)
	intent = new Intent().setClass(this, Home.class);

	// Initialize a TabSpec for each tab and add it to the TabHost
	spec = tabHost.newTabSpec("Home").setIndicator("Home",
	                  res.getDrawable(R.drawable.tab_home))
	              .setContent(intent);
	tabHost.addTab(spec);

	// Do the same for the other tabs
	intent = new Intent().setClass(this, FileBrowser.class);
	spec = tabHost.newTabSpec("FileManager").setIndicator("File Manager",
	                  res.getDrawable(R.drawable.tab_manager))
	              .setContent(intent);
	tabHost.addTab(spec);

	intent = new Intent().setClass(this, TermGroup.class);
	spec = tabHost.newTabSpec("Terminal").setIndicator("Terminal",
	                  res.getDrawable(R.drawable.tab_terminal))
	              .setContent(intent);
	tabHost.addTab(spec);
	
	/* intent = new Intent().setClass(this, ThirdTab.class);
	spec = tabHost.newTabSpec("Shortcuts").setIndicator("Shortcuts",
	                  res.getDrawable(R.drawable.tab_shortcuts))
	              .setContent(intent);
	tabHost.addTab(spec); */

	tabHost.setCurrentTab(0);
	//setTabColor(tabHost);
	}
	
	/*public static void setTabColor(TabHost tabhost) {
	    for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
	    {
	       //tabhost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#ffffffff")); //unselected
	       tabhost.getTabWidget().getChildAt(i).getLayoutParams().height = 80;
	    }
	    //tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundColor(Color.parseColor("#ffffffff")); // selected
	    tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).getLayoutParams().height = 80;
	    
	} */

}