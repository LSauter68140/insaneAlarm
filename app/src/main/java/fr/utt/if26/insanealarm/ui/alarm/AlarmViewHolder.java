package fr.utt.if26.insanealarm.ui.alarm;

import static fr.utt.if26.insanealarm.R.id;
import static fr.utt.if26.insanealarm.R.layout;
import static fr.utt.if26.insanealarm.R.menu;
import static fr.utt.if26.insanealarm.R.string;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.utils.DayTimeTranslator;
import fr.utt.if26.insanealarm.worker.AlarmGoOffWorker;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class AlarmViewHolder extends RecyclerView.ViewHolder {

    private final TextView nextRingTime;
    private final TextView frequency;
    private final ImageButton settingBtn;
    private final Switch activateAlarm;
    private final View viewGlobal;

    @SuppressLint("NonConstantResourceId")
    public AlarmViewHolder(@NonNull View itemView, View viewGlobal) {
        super(itemView);
        nextRingTime = itemView.findViewById(id.listItemNextRingTime);
        frequency = itemView.findViewById(id.listItemFrequency);
        settingBtn = itemView.findViewById(id.listItemSettingBtn);
        activateAlarm = itemView.findViewById(id.listItemActivateAlarm);
        this.viewGlobal = viewGlobal;
    }


    public void bind(Alarm alarm, AlarmViewModel alarmViewModel) {
        nextRingTime.setText(formatNextTimeToGoOff(alarm.getTimeToGoOff()));
        frequency.setText(formatFrequency(alarm, itemView.getResources()));
        activateAlarm.setChecked(alarm.getActivate());
        activateAlarm.setOnClickListener(v -> onClickAlarmSwitch(alarmViewModel, alarm));
        settingBtn.setOnClickListener(v -> onClickSettingBtn(v, alarmViewModel, alarm));
    }

    static AlarmViewHolder create(ViewGroup parent, View viewGlobal) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout.alarm_list_item, parent, false);
        return new AlarmViewHolder(view, viewGlobal);
    }

    private String formatNextTimeToGoOff(LocalTime localTime) {
        return DayTimeTranslator.readableTime((long) localTime.getHour(), (long) localTime.getMinute());
    }

    private String formatFrequency(Alarm alarm, Resources resources) {
        ArrayList<String> validDays = alarm.getFrequency().getWeekSchedule();
        if (validDays.size() == 0) {
            return resources.getString(string.unique);
        }

        Boolean isShort = validDays.size() > 2;

        StringBuilder weekSummary = new StringBuilder();
        for (String validDay : validDays
        ) {
            weekSummary.append(DayTimeTranslator.getDay(validDay, isShort, resources)).append(",");
        }
        return weekSummary.substring(0, weekSummary.length() - 1);
    }

    // add listener function for each component

    public void onClickAlarmSwitch(AlarmViewModel alarmViewModel, Alarm alarm) {
        alarm.setActivate(activateAlarm.isChecked());
        alarmViewModel.updateAlarm(alarm);
    }

    @SuppressLint("NonConstantResourceId")
    public void onClickSettingBtn(View view, AlarmViewModel alarmViewModel, Alarm alarm) {
        PopupMenu popupMenu = new PopupMenu(settingBtn.getContext(), settingBtn, R.style.alarmSettingPopupMenu);
        popupMenu.inflate(menu.alarm_setting_context);
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case id.alarmSettingEdit:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("alarmToEdit", alarm);
                    Navigation.findNavController(viewGlobal).navigate(id.nav_addEditAlarm, bundle);
                    return true;
                case id.alarmSettingPreview:
                    OneTimeWorkRequest workAlarmRing =
                            new OneTimeWorkRequest.Builder(AlarmGoOffWorker.class)
                                    .setInitialDelay(0, TimeUnit.MINUTES)// now for preview
                                    .addTag("alarm")
                                    .setInputData((new Data.Builder()).putInt("id", alarm.getId()).build())
                                    .build();
                    WorkManager.getInstance(alarmViewModel.getApplication().getApplicationContext()).enqueue(workAlarmRing);
                    return true;
                case id.alarmSettingDelete:
                    alarmViewModel.deleteAlarmById(alarm.getId());
                    return true;
                default:
                    return false;
            }
        });
        //displaying the popup
        popupMenu.show();
        // better style use windows popup
        // https://stackoverflow.com/questions/49706495/how-to-pass-a-custom-layout-to-a-popupmenu
        // https://stackoverflow.com/questions/34449251/implement-pop-up-menu-with-margin
        // https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android

        alarmViewModel.updateAlarm(alarm);
    }

}
