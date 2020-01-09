package com.example.notelagi.callback;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.notelagi.R;

public abstract class MainActionCallback implements ActionMode.Callback {
    private ActionMode action;
    private MenuItem countItem, shareItem;

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.main_acation_mode,menu);
        this.action = mode;
        this.countItem = menu.findItem(R.id.action_checked_count);
        this.shareItem = menu.findItem(R.id.action_share_notes);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    public void setCount(String checkedCount) {
        if (countItem !=null)
            this.countItem.setTitle(checkedCount);
    }

    public void changeShareItemVisible(boolean b){
        shareItem.setVisible(b);
    }

    public ActionMode getAction(){
        return action;
    }

}
