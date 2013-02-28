package com.rootpower.roothelper;

public class HomeListItem
{
   private String title;
   private String subtitle;
   
   HomeListItem(String in_title, String in_sub)
   {
      setTitle(in_title);
      setSubtitle(in_sub);
   }

   public void setSubtitle(String subtitle)
   {
      this.subtitle = subtitle;
   }

   public String getSubtitle()
   {
      return subtitle;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public String getTitle()
   {
      return title;
   }

}
