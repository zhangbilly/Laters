package com.zworks.latest.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zworks.latest.R;
import com.zworks.latest.Utils.DaysUtil;

/**
 * Created by zhangqiang on 2015/7/25.
 */
public class DateAndEpisodeDialogFragment  extends DialogFragment {
    private long firstDate;
    private View view;

    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }
    public long getFirstDate() {
        return firstDate;
    }
    public void setFirstDate(long firstDate) {
        this.firstDate = firstDate;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        TextView firstDateView = (TextView)getActivity().findViewById(R.id.firstairdate);
        String firstDateString = DaysUtil.longToString(firstDate);
        firstDateView.setText(firstDateString);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.recordtype_date,null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText episodeView = (EditText)getActivity().findViewById(R.id.episodenumber);
                        String episodeString = episodeView.getText().toString();
                        int episodeNumber = Integer.valueOf(episodeString);
                        OnfinishDateAndEpisode activity = (OnfinishDateAndEpisode)getActivity();
                        activity.onFinishSelectDateAndEpisode(firstDate, episodeNumber);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    public interface OnfinishDateAndEpisode {
        void onFinishSelectDateAndEpisode(long firstdate,int episode);
    }
}
