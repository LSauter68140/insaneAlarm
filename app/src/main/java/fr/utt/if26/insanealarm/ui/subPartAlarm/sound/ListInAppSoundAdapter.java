package fr.utt.if26.insanealarm.ui.subPartAlarm.sound;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.List;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.utils.FileManager;

public class ListInAppSoundAdapter extends RecyclerView.Adapter<ListInAppSoundAdapter.ListInAppSoundAdapterViewHolder> {

    final List<Field> allFilesInPackage;
    final FragmentActivity activity;
    final Fragment fragment;

    public ListInAppSoundAdapter(List<Field> allFilesInPackage, FragmentActivity activity, Fragment fragment) {
        this.allFilesInPackage = allFilesInPackage;
        this.activity = activity;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ListInAppSoundAdapter.ListInAppSoundAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.fragement_adapter_list_item, parent, false);
        return new ListInAppSoundAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListInAppSoundAdapter.ListInAppSoundAdapterViewHolder holder, int position) {
        holder.display(allFilesInPackage.get(position), activity, fragment);
    }

    @Override
    public int getItemCount() {
        return allFilesInPackage.size();
    }

    static class ListInAppSoundAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSoundName;
        private final LinearLayout layoutSoundNameItem;

        public ListInAppSoundAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSoundName = itemView.findViewById(R.id.tvSoundName);
            layoutSoundNameItem = itemView.findViewById(R.id.layoutSoundNameItem);
        }

        void display(Field fileName, FragmentActivity activity, Fragment fragment) {
            tvSoundName.setText(fileName.getName());
            layoutSoundNameItem.setOnClickListener(v -> {
                try {
                    Uri ringtoneUri = FileManager.getUriToResource(itemView.getContext(), fileName.getInt(fileName));
                    Bundle result = new Bundle();
                    result.putString("uriStr", String.valueOf(ringtoneUri));

                    activity.getSupportFragmentManager().setFragmentResult("ringtoneChosen", result);
                    NavHostFragment.findNavController(fragment).navigateUp();

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
