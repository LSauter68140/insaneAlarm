package fr.utt.if26.insanealarm.ui.alarm;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentAlarmBinding;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.worker.AlarmGoOffWorker;


public class AlarmFragment extends Fragment {


    private AlarmViewModel alarmViewModel;

    // FAB menu
    FloatingActionButton fab, fab1, fab2, fab3;
    LinearLayout fabLayout1, fabLayout2, fabLayout3;
    View fabBGLayout;

    boolean isFABOpen = false;


    private FragmentAlarmBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.alarmRecyclerView);

        alarmViewModel =
                new ViewModelProvider(this).get(AlarmViewModel.class);
        final AlarmListAdapter adapter = new AlarmListAdapter(new AlarmListAdapter.AlarmDiff(), alarmViewModel, root);

        alarmViewModel.getAllAlarms().observe(getViewLifecycleOwner(), alarms -> {
            updateAlarmWorks(alarms);
            adapter.submitList(alarms);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // FAB menu

        // link fab
        fab = binding.fab;
        fab1 = binding.fab1;
        fab2 = binding.fab2;
        fab3 = binding.fab3;
        fabBGLayout = binding.fabBGLayout;
        fabLayout1 = binding.fabLayout1;
        fabLayout2 = binding.fabLayout2;
        fabLayout3 = binding.fabLayout3;

        fab.setOnClickListener(view -> {
            if (!isFABOpen) {
                showFABMenu();
            } else {
                closeFABMenu();
            }
        });
        fabBGLayout.setOnClickListener(v -> closeFABMenu());

        //event listener
        fab1.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_addEditAlarm);
            closeFABMenu();
        });
        fab2.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Alarm rapide", Toast.LENGTH_SHORT).show();
            closeFABMenu();
        });
        fab3.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Ajouter un rappel", Toast.LENGTH_SHORT).show();
            closeFABMenu();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    // FAB menu function

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabLayout3.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fabLayout3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout3.animate().translationY(0);
        fabLayout3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                    fabLayout3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
    }


    // work Manager function
    private void updateAlarmWorks(List<Alarm> alarms) {
        // cancel all the previous worker and create new one
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag("alarm");
        Duration timeBetween;
        for (Alarm alarm : alarms) {
            if (!alarm.getActivate())
                continue;
            timeBetween = Duration.between(LocalDateTime.now(), alarm.getAlarmFrequency().getNextRing());

            if (timeBetween.toMillis() < 0d) {
                // time passed
                // if frequency is non -> desactivate alarm
                // other wise recalculate new go off time !!!
                alarmViewModel.checkNextGoOff(alarm);
                continue;
            }

            OneTimeWorkRequest workAlarmRing =
                    new OneTimeWorkRequest.Builder(AlarmGoOffWorker.class)
                            .setInitialDelay(timeBetween.toMinutes(), TimeUnit.MINUTES)// Use this when you want to add initial delay or schedule initial work to `OneTimeWorkRequest` e.g. setInitialDelay(2, TimeUnit.HOURS)
                            .addTag("alarm")
                            .setInputData((new Data.Builder()).putInt("id", alarm.getId()).build())
                            .build();
            WorkManager.getInstance(requireContext()).enqueue(workAlarmRing);
            /// WorkManager.getInstance(requireContext()).pruneWork();
        }
    }
}