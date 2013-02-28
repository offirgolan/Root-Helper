package com.rootpower.roothelper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipOutputStream;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends ListActivity
{
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle icicle)
   {
      super.onCreate(icicle);

      HomeListAdapter adapter = new HomeListAdapter(this);
      adapter.createList();

      getListView().setAdapter(adapter);

      getListView().setOnItemClickListener(new OnItemClickListener()
      {

         @Override
         public void onItemClick(AdapterView arg0, View arg1, int positon,
               long arg3)
         {
            switch (positon)
            {
            case 0:
               confirmationAlert("reboot", "Reboot");
               break;
            case 1:
               confirmationAlert("reboot recovery", "Reboot Recovery");
               break;
            case 2:
               confirmationAlert("reboot bootloader", "Reboot Bootloader");
               break;
            case 3:
               appBackup("Application Backup");
               break;
            case 4:
               dataBackup("Data Backup", "Backup Data?");
               break;
            case 5:
               appRestore();
               break;
            case 6:
               dataRestore("Data Restore", "Restore Data?");
               break;
            default:
               confirmationAlert("reboot", "Reboot");
            }

         }
      });
   }

   public void confirmationAlert(final String command, String title)
   {

      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(title);
      builder.setMessage("Are You Sure?");
      builder.setIcon(R.drawable.alert);
      builder.setCancelable(false);
      builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int id)
         {

            if (ShellInterface.isSuAvailable())
            {
               ShellInterface.runCommand(command);
            }

         }
      }).setNegativeButton("No", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int id)
         {
            dialog.cancel();

         }
      });
      builder.show();
   }

   private void getUerInputAlert(String title, String message)
   {

      final AlertDialog.Builder alert = new AlertDialog.Builder(this);
      alert.setTitle(title);
      alert.setMessage(message);

      final EditText input = new EditText(this);
      alert.setView(input);

      alert.setPositiveButton("Ok", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int whichButton)
         {
            String value = input.getText().toString().trim();
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT)
                  .show();
         }
      });

      alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int whichButton)
         {
            dialog.cancel();
         }
      });
      alert.show();

   }

   public void appBackup(String title)
   {

      final File locationApp = new File("/data/app/");
      final File locationAppPrivate = new File("/data/app-private/");
      File backupLocation = new File("/mnt/sdcard/RootHelper");

      int totalApps = locationApp.list().length
            + locationAppPrivate.list().length;

      final AlertDialog.Builder alert = new AlertDialog.Builder(this);
      alert.setTitle(title);
      alert.setMessage("Backup " + totalApps + " Apps?");
      alert.setIcon(R.drawable.alert);

      if (!backupLocation.exists())
      {
         backupLocation.mkdir();
      }
      backupLocation = new File("/mnt/sdcard/RootHelper/AppBackup");
      if (!backupLocation.exists())
      {
         backupLocation.mkdir();
      }

      alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int whichButton)
         {

            if (ShellInterface.isSuAvailable())
            {
               ShellInterface.runCommand("mount -o remount,rw /");
               ShellInterface.runCommand("chmod a+r /data");
               ShellInterface.runCommand("chmod a+r /data/app/");
               ShellInterface.runCommand("chmod a+r /data/app-private/");
               ShellInterface.runCommand("mount -o remount,ro /");
            }

            ZipFolder zipDir = new ZipFolder();
            try
            {
               // create a ZipOutputStream to zip the data to
               String date = new java.text.SimpleDateFormat(
                     "MM-dd-yyyy_hh-mm-ss").format(new java.util.Date());
               final String dateFinal = new String(date);

               File backupLocation = new File(
                     "/mnt/sdcard/RootHelper/AppBackup/" + dateFinal);
               if (!backupLocation.exists())
               {
                  backupLocation.mkdir();
               }

               File outFolder = new File("/mnt/sdcard/RootHelper/AppBackup/"
                     + dateFinal + "/" + dateFinal + "_APP" + ".zip");
               ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
                     outFolder));
               // call the zipDir method
               zipDir.zipDir("/data/app", zos);
               // close the stream
               zos.close();

               if (locationAppPrivate.list().length != 0)
               {
                  outFolder = new File("/mnt/sdcard/RootHelper/AppBackup/"
                        + dateFinal + "/" + dateFinal + "_APP_PRIVATE" + ".zip");
                  zos = new ZipOutputStream(new FileOutputStream(outFolder)); // assuming
                                                                              // that
                                                                              // there
                                                                              // is
                                                                              // a
                  // directory named inFolder (If there //isn't create
                  // one) in
                  // the same directory as the one the code //runs from,
                  // call the zipDir method
                  zipDir.zipDir("/data/app-private", zos); // close the
                                                           // stream
                  zos.close();
               }

            } catch (Exception e)
            {
               // handle exception
            }

         }

      });

      alert.setNegativeButton("No", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int whichButton)
         {
            dialog.cancel();
         }
      });
      alert.show();
   }

   public void dataBackup(String title, String message)
   {

      final AlertDialog.Builder alert = new AlertDialog.Builder(this);
      alert.setTitle(title);
      alert.setMessage(message);
      alert.setIcon(R.drawable.alert);

      File backupLocation = new File("/mnt/sdcard/RootHelper");
      if (!backupLocation.exists())
      {
         backupLocation.mkdir();
      }
      backupLocation = new File("/mnt/sdcard/RootHelper/DataBackup");
      if (!backupLocation.exists())
      {
         backupLocation.mkdir();
      }

      alert.setPositiveButton("Yes", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int whichButton)
         {

            if (ShellInterface.isSuAvailable())
            {
               ShellInterface.runCommand("mount -o remount,rw /");
               ShellInterface.runCommand("chmod a+r /data/");
               ShellInterface.runCommand("chmod a+r /data/data/");
               ShellInterface.runCommand("chmod a+r /data/data/*");
               ShellInterface.runCommand("mount -o remount,ro /");
            }
            ZipFolder zipDir = new ZipFolder();
            try
            {
               // create a ZipOutputStream to zip the data to
               String date = new java.text.SimpleDateFormat(
                     "MM-dd-yyyy_hh-mm-ss").format(new java.util.Date());
               final String dateFinal = new String(date);
               File backupLocation = new File(
                     "/mnt/sdcard/RootHelper/DataBackup/" + dateFinal);
               if (!backupLocation.exists())
               {
                  backupLocation.mkdir();
               }
               File outFolder = new File("/mnt/sdcard/RootHelper/DataBackup/"
                     + dateFinal + "/" + dateFinal + "_DATA" + ".zip");
               ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
                     outFolder));
               // assuming that there is a directory named inFolder (If
               // there
               // isn't create one) in the same directory as the one the
               // code
               // runs from,
               // call the zipDir method
               zipDir.zipDir("/data/data", zos);
               // close the stream
               zos.close();
            } catch (Exception e)
            {
               // handle exception
            }

         }

      });

      alert.setNegativeButton("No", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int whichButton)
         {
            dialog.cancel();
         }
      });
      alert.show();
   }

   public void appRestore()
   {

      final File unZipLocation = new File("/");

      final String zipPath = "/mnt/sdcard/RootHelper/AppBackup/";
      final File backupPath = new File(zipPath);
      final String unZipPath = unZipLocation.getPath();
      final String[] backup = backupPath.list();

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Choose a backup:");
      builder.setIcon(R.drawable.info);
      builder.setItems(backup, new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int item)
         {
            String zipLocation = new String(backupPath.getPath() + "/"
                  + backup[item] + "/" + backup[item] + "_APP" + ".zip");
            final Decompress decompress = new Decompress(zipLocation, unZipPath);
            String zipLocation2 = new String(backupPath.getPath() + "/"
                  + backup[item] + "/" + backup[item] + "_APP_PRIVATE" + ".zip");
            final Decompress decompress2 = new Decompress(zipLocation,
                  unZipPath);

            if (ShellInterface.isSuAvailable())
            {
               ShellInterface.runCommand("mount -o remount,rw /");
               ShellInterface.runCommand("mount -o remount,rw /data");
               ShellInterface.runCommand("chmod a+rw /data");
               ShellInterface.runCommand("chmod a+rw /data/app/");
               ShellInterface.runCommand("chmod a+rw /data/app-private/");
               ShellInterface.runCommand("mount -o remount,ro /");
            }
            decompress.unzip();

            File f = new File(zipLocation2);
            if (f.exists())
            {
               decompress2.unzip();
            }

         }
      });
      AlertDialog alert = builder.create();
      alert.show();
   }

   public void dataRestore(String title, String message)
   {

      final File unZipLocation = new File("/");

      final String zipPath = "/mnt/sdcard/RootHelper/DataBackup/";
      final File backupPath = new File(zipPath);
      final String unZipPath = unZipLocation.getPath();
      final String[] backup = backupPath.list();

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Choose a backup:");
      builder.setIcon(R.drawable.info);
      builder.setItems(backup, new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int item)
         {
            String zipLocation = new String(backupPath.getPath() + "/"
                  + backup[item] + "/" + backup[item] + "_DATA" + ".zip");
            final Decompress decompress = new Decompress(zipLocation, unZipPath);

            if (ShellInterface.isSuAvailable())
            {
               ShellInterface.runCommand("mount -o remount,rw /");
               ShellInterface.runCommand("mount -o remount,rw /data");
               ShellInterface.runCommand("chmod a+rw /data");
               ShellInterface.runCommand("chmod a+rw /data/data/");
               ShellInterface.runCommand("mount -o remount,ro /");
            }
            decompress.unzip();
         }
      });
      AlertDialog alert = builder.create();
      alert.show();
   }

   public void createNandroid()
   {

      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("Create a Nandroid Backup:");
      builder.setMessage("Are You Sure? \n\n(Reboot Required)");
      builder.setIcon(R.drawable.alert);
      builder.setCancelable(false);
      builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int id)
         {
            if (ShellInterface.isSuAvailable())
            {
               ShellInterface.runCommand("rm -r /cache/recovery/command");
               ShellInterface.runCommand("mkdir -p /cache/recovery/");
               ShellInterface
                     .runCommand("echo 'boot-recovery ' > /cache/recovery/command");
               // ShellInterface.runCommand("echo 'nandroid-mobile.sh -b --norecovery --nocache --nomisc --nosplash1 --nosplash2 --defaultinput 1>&2' >> /cache/recovery/command");
               // ShellInterface.runCommand("reboot recovery");
            }
         }
      }).setNegativeButton("No", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int id)
         {
            dialog.cancel();

         }
      });
      builder.show();
   }

}
