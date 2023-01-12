package fr.utt.if26.insanealarm.ui.alarm;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import fr.utt.if26.insanealarm.room.model.Alarm;

public class AlarmListAdapter extends ListAdapter<Alarm, AlarmViewHolder> {

    AlarmViewModel alarmViewModel;
    final View globalView;

    protected AlarmListAdapter(@NonNull DiffUtil.ItemCallback<Alarm> diffCallback, AlarmViewModel alarmViewHolder, View globalView) {
        super(diffCallback);
        this.alarmViewModel = alarmViewHolder;
        this.globalView = globalView;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AlarmViewHolder.create(parent, globalView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm current = getItem(position);
        holder.bind(current, alarmViewModel);
    }

    static class AlarmDiff extends DiffUtil.ItemCallback<Alarm> {
        public boolean areItemsTheSame(@NonNull Alarm oldItem, @NonNull Alarm newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Alarm oldItem, @NonNull Alarm newItem) {
            return oldItem.hashCode() == newItem.hashCode();
        }
    }
}
