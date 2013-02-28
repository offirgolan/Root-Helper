package com.rootpower.roothelper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeListAdapter extends BaseAdapter    {
   
   private List<HomeListItem> tableValues = new ArrayList<HomeListItem>();
   private Context context;
   
   public HomeListAdapter(Context context)
   {
      this.context = context;
   }
      
   public void createList()
   {
      tableValues.add(new HomeListItem("Reboot", "Reboot phone normally"));
      tableValues.add(new HomeListItem("Reboot Recovery", "Reboot phone into recovery"));
      tableValues.add(new HomeListItem("Reboot Bootloader", "Reboot phone into bootloader"));
      tableValues.add(new HomeListItem("Backup Apps", "Backup all your apps to the SD-Card"));
      tableValues.add(new HomeListItem("Backup Data", "Backup all phone data to the SD-Card"));
      tableValues.add(new HomeListItem("Restore Apps", "Restore your apps to your phone from a previous backup"));
      tableValues.add(new HomeListItem("Restore Data", "Restore your data to your phone from a previous backup"));
   }
   
      
   @Override
   public View getView(int position, View convertView, ViewGroup parent)   {
   
      LinearLayout itemLayout;
      itemLayout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
      
       TextView title = (TextView) itemLayout.findViewById(R.id.threadTitle);
       TextView sub = (TextView) itemLayout.findViewById(R.id.threadSubTitle);
       
       title.setText(tableValues.get(position).getTitle());
       sub.setText(tableValues.get(position).getSubtitle());
       
       return itemLayout;

   }


   @Override
   public int getCount()
   {
      // TODO Auto-generated method stub
      return tableValues.size();
   }

   @Override
   public Object getItem(int position)
   {
      // TODO Auto-generated method stub
      return tableValues.get(position);
   }

   @Override
   public long getItemId(int position)
   {
      // TODO Auto-generated method stub
      return 0;
   }
   
   public List<HomeListItem> getList()
   {
      return tableValues;
   }

}