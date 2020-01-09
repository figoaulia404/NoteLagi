package com.example.notelagi.Login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceManager {

    public static final String sp_tidaktahu = "tidak_tahu";
    public static final String sp_nik = "edit_nik";
    public static final String sp_pass = "edit_pass";

    public static final String sp_sudahlog = "spSudahLogin";

    SharedPreferences sharedPreferences;
    SharedPreferences Edit;
    SharedPreferences.Editor speditor;

     public SharedPrefrenceManager(Context context){
         sharedPreferences = context.getSharedPreferences(sp_tidaktahu,Context.MODE_PRIVATE);
         speditor = sharedPreferences.edit();
     }

     public void saveSPString(String Keysp, String vslue){
         speditor.putString(Keysp, vslue);
            speditor.commit();
     }

     public void saveSPInt(String Keysp, int value){
         speditor.putInt(Keysp, value);
         speditor.commit();
     }

     public void saveSpBoolean(String Keysp, boolean value){
         speditor.putBoolean(Keysp, value);
         speditor.commit();
     }

     public String getSp_nik(){
         return sharedPreferences.getString(sp_nik, "");
     }

     public String getsp_pass(){
         return sharedPreferences.getString(sp_pass, "");
     }

     public Boolean getsp_sudahlog(){
         return sharedPreferences.getBoolean(sp_sudahlog, false);
     }
}
