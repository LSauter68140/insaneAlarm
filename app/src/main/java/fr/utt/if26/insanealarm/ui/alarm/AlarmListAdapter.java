package fr.utt.if26.insanealarm.ui.alarm;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import fr.utt.if26.insanealarm.model.Alarm;

public class AlarmListAdapter extends ListAdapter<Alarm, AlarmViewHolder> {

    protected AlarmListAdapter(@NonNull DiffUtil.ItemCallback<Alarm> diffCallback) {
        super(diffCallback);
    }

    protected AlarmListAdapter(@NonNull AsyncDifferConfig<Alarm> config) {
        super(config);
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AlarmViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm current = getItem(position);
        holder.bind(current);
    }

    static class AlarmDiff extends DiffUtil.ItemCallback<Alarm> {
        public boolean areItemsTheSame(@NonNull Alarm oldItem, @NonNull Alarm newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Alarm oldItem, @NonNull Alarm newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
