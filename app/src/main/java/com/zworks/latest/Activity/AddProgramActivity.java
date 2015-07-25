package com.zworks.latest.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zworks.latest.Common.Constants;
import com.zworks.latest.Dialog.DateAndEpisodeDialogFragment;
import com.zworks.latest.Dialog.DateDialogFragment;
import com.zworks.latest.Dialog.WeekDialogFragment;
import com.zworks.latest.R;
import com.zworks.latest.Utils.DatabaseHelper;
import com.zworks.latest.Utils.DaysUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhangqiang on 2015/7/25.
 */
public class AddProgramActivity extends Activity implements WeekDialogFragment.SelectWeekDialogListener,DateDialogFragment.SelectDateDialogListener,DateAndEpisodeDialogFragment.OnfinishDateAndEpisode {
    private String programName;
    private long begindate=0;
    private String updateTime;
    private List<Integer> selecteddays;
    private List<Long> days;
    RadioGroup recordType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);

    }

    public void showWeekDialog(View view){
        WeekDialogFragment weekDialogFragment = new WeekDialogFragment();
        if(selecteddays!=null){
            weekDialogFragment.setmSelectedItems(selecteddays);
        }else{
            weekDialogFragment.setmSelectedItems(new ArrayList<Integer>());
        }

        weekDialogFragment.show(getFragmentManager(), "weekdialog");

    }
    public String getProgramName(){
        return ((TextView) findViewById(R.id.programName)).getText().toString();
    }


    public void onFinishEditDialog(List<Integer> selecteddays,String[] days) {
        // TODO Auto-generated method stub
        this.selecteddays = selecteddays;
        TextView textView = (TextView) findViewById(R.id.selectedweek);
        String dayName = DaysUtil.getDayName(selecteddays, days);
        textView.setText(dayName);
        this.updateTime = dayName;
    }
    public void showBeginDateDialog(View view){
        DateDialogFragment dateDialogFragment = new DateDialogFragment();
        Calendar selectedDate = Calendar.getInstance(Locale.getDefault());
        if(begindate!=0)
            selectedDate.setTimeInMillis(begindate);
        dateDialogFragment.setSelectedDate(selectedDate);
        dateDialogFragment.show(getFragmentManager(), "datedialog");
    }

    public void onFinishSelectDateDialog(long date) {
        // TODO Auto-generated method stub
        this.begindate = date;
        Calendar c = Calendar.getInstance(Locale.getDefault());
        c.setTimeInMillis(begindate);
        //System.out.println(c.get(Calendar.DATE));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        String sb = format.format(c.getTime());
        System.out.println(sb);
        TextView textView = (TextView) findViewById(R.id.selectedbegindate);
        textView.setText(sb);
    }
    public void addProgram(View view){
        if(getProgramName().matches("")){
            Toast.makeText(this, "You did not enter a programname", Toast.LENGTH_SHORT).show();
            return;
        }
        if(begindate==0){
            Toast.makeText(this, "You did not select a begindate", Toast.LENGTH_SHORT).show();
            return;
        }
        if(selecteddays==null){
            Toast.makeText(this, "You did not select a play time", Toast.LENGTH_SHORT).show();
            return;
        }
        recordType = (RadioGroup)findViewById(R.id.radioRecordType);
        int checkId = recordType.getCheckedRadioButtonId();
        if(checkId==-1){
            Toast.makeText(this, "You did not select a record type", Toast.LENGTH_SHORT).show();
            return;
        }
        if(checkId==R.id.radioEpisode||checkId==R.id.radioBoth){
            showDateAndEpisodeDialog();
        }
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.Program.PROGRAM_NAME, getProgramName());
        values.put(Constants.Program.AIR_DAYS, this.updateTime);
        values.put(Constants.Program.BEGIN_DATE, this.begindate);
        long programId = db.insert(Constants.Program.TABLE_NAME,null,values);
        days = DaysUtil.initRecordDate(selecteddays, begindate);
        System.out.println("需添加的数据："+days);
        for(int i=0;i<days.size();i++){
            ContentValues record = new ContentValues();
            record.put(Constants.Record.PROGRAM_ID, programId);
            record.put(Constants.Record.RECORD_TIME, days.get(i));
            record.put(Constants.Record.STATUS,1);
            db.insert(Constants.Record.TABLE_NAME, null, record);
        }
        db.close();
        Intent intent = new Intent();
        // 把返回数据存入Intent
        intent.putExtra(Constants.Program.PROGRAM_NAME, getProgramName());
        intent.putExtra(Constants.Program._ID, String.valueOf(programId));
        intent.putExtra(Constants.Program.AIR_DAYS, this.updateTime);
        intent.putExtra("COUNT", days.size());
        // 设置返回数据
        this.setResult(RESULT_OK, intent);
        this.finish();

    }
    public void showDateAndEpisodeDialog(){
        DateAndEpisodeDialogFragment dateAndEpisodeDialogFragment = new DateAndEpisodeDialogFragment();
        days = DaysUtil.initRecordDate(selecteddays, begindate);
        dateAndEpisodeDialogFragment.setFirstDate(days.get(0));


        dateAndEpisodeDialogFragment.show(getFragmentManager(), "dateandepisode");
    }

    @Override
    public void onFinishSelectDateAndEpisode(long firstdate, int episode) {
        // TODO Auto-generated method stub

    }
}
