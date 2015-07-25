package com.zworks.latest.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;



/**
 * Created by zhangqiang on 2015/7/25.
 */
public class ItemDeleteDialogFragment  extends DialogFragment {
    private String recordDate;
    private String recordId;
    private int position;
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public String getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
    public interface DeleteRecordItemDialogListener{
        public void onDeleteRecordItem(String recordId,int positon);
    };
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("删除"+recordDate+"?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteRecordItemDialogListener activity = (DeleteRecordItemDialogListener) getActivity();

                        activity.onDeleteRecordItem(recordId,position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
