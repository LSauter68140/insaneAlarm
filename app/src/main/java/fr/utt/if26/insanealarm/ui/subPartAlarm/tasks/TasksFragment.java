package fr.utt.if26.insanealarm.ui.subPartAlarm.tasks;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentSubPartTaskBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;
import fr.utt.if26.insanealarm.taskBackground.utils.TaskQuestion;

public class TasksFragment extends Fragment {
    private FragmentSubPartTaskBinding binding;
    private AddAlarmViewModel alarmViewModel;
    private TextView seekBarDesc;
    private TextView taskExample;

    private int difficulty;
    private int radioIndexSelected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NumberPicker npTaskNumber = binding.npTaskNumber;
        NumberPicker npTaskTimer = binding.npTaskTimer;

        npTaskNumber.setMinValue(0);
        npTaskNumber.setMaxValue(5);
        npTaskTimer.setMinValue(0);
        npTaskTimer.setMaxValue(120);

        SeekBar seekBar = binding.seekBar;
        seekBar.setMax(3);
        seekBar.setMin(1);

        seekBarDesc = binding.seekBarDesc;
        taskExample = binding.taskExample;

        if (getArguments() != null) {
            String parentFragment = getArguments().getString("parentFragment"); // Snooze or Dismiss
            binding.titleTask.setText(getArguments().getString("title"));
            // add observer and listener for dismiss and snooze toggle buttons
            Map<String, View> tasksFragmentWidget = Map.of(
                    "getType" + parentFragment + "Task", binding.radioGroup,
                    "getDifficulty" + parentFragment + "Task", binding.seekBar,
                    "getNumber" + parentFragment + "Task", binding.npTaskNumber,
                    "getTimer" + parentFragment + "Task", binding.npTaskTimer,
                    "getCanSkip" + parentFragment + "Task", binding.layoutCanSkip,
                    "getMuteSound" + parentFragment + "Task", binding.layoutMuteSound
            );

            for (Map.Entry<String, View> widget : tasksFragmentWidget.entrySet()) {
                Matcher m = Pattern.compile("(?<=get)(.*)(?=" + parentFragment + ")").matcher(widget.getKey());
                if (!m.find()) {
                    Log.i("[ERR]", "oupsi on a rien trouvé");
                    continue;
                }
                String typeFunction = m.group(0);
                if (typeFunction == null) {
                    continue;
                }
                switch (typeFunction) {
                    case "Type":
                        observerRadioGroupTask(widget.getKey(), (RadioGroup) widget.getValue(), getViewLifecycleOwner());
                        listenerRadioGroup(widget.getKey(), (RadioGroup) widget.getValue());
                        break;
                    case "Difficulty":
                        observerSeekBarTask(widget.getKey(), (SeekBar) widget.getValue(), getViewLifecycleOwner());
                        listenerSeekBar(widget.getKey(), (SeekBar) widget.getValue());
                        break;
                    case "Number":
                    case "Timer":
                        observerNumberPickerTask(widget.getKey(), (NumberPicker) widget.getValue(), getViewLifecycleOwner());
                        listenerNumberPickerTask(widget.getKey(), (NumberPicker) widget.getValue());
                        break;
                    case "CanSkip":
                    case "MuteSound":
                        observerLayoutTask(widget.getKey(), (LinearLayout) widget.getValue(), getViewLifecycleOwner());
                        listenerLayout(widget.getKey(), (LinearLayout) widget.getValue());
                        break;
                }
            }
        }
        return root;
    }

    public void observerRadioGroupTask(String method, RadioGroup radioGroup, androidx.lifecycle.LifecycleOwner g) {
        try {
            ((MutableLiveData<String>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .observe(g, v -> {
                        radioIndexSelected = alarmViewModel.getTypeToInt(v);
                        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(radioIndexSelected);
                        radioButton.setChecked(true);
                        if (radioIndexSelected == 0) {
                            binding.cstntLtSettingTask.setVisibility(View.GONE);
                        } else {
                            binding.cstntLtSettingTask.setVisibility(View.VISIBLE);
                        }
                        updateTextViewWithdiffType();
                    });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void observerSeekBarTask(String method, SeekBar seekBar, androidx.lifecycle.LifecycleOwner g) {
        try {
            ((MutableLiveData<Integer>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .observe(g, difficultyFromSeekBar -> {
                        seekBar.setProgress(difficultyFromSeekBar);
                        difficulty = difficultyFromSeekBar;
                        updateTextViewWithdiffType();
                    });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void observerNumberPickerTask(String method, NumberPicker numberPicker, androidx.lifecycle.LifecycleOwner g) {
        try {
            ((MutableLiveData<Integer>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .observe(g, numberPicker::setValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void observerLayoutTask(String method, LinearLayout linearLayout, androidx.lifecycle.LifecycleOwner g) {
        try {
            ((MutableLiveData<Boolean>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .observe(g, v -> ((Switch) linearLayout.getChildAt(1)).setChecked(v));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void observerTextView(String method, TextView textView, androidx.lifecycle.LifecycleOwner g) {
        try {
            ((MutableLiveData<String>)
                    alarmViewModel.getClass()
                            .getDeclaredMethod(method).invoke(alarmViewModel))
                    .observe(g, textView::setText);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    // listener part
    @SuppressLint("NonConstantResourceId")
    public void listenerRadioGroup(String method, RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            try {
                String taskTypeStatus;
                switch (checkedId) {
                    case R.id.rbTaskMath:
                        taskTypeStatus = "Maths";
                        break;
                    case R.id.rbTaskWrite:
                        taskTypeStatus = "Write";
                        break;
                    case R.id.rbTaskNone:
                    case -1:
                    default:
                        taskTypeStatus = "none";
                        break;
                }
                ((MutableLiveData<String>)
                        alarmViewModel.getClass()
                                .getDeclaredMethod(method).invoke(alarmViewModel))
                        .setValue(taskTypeStatus);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public void listenerSeekBar(String method, SeekBar seekBar) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    ((MutableLiveData<Integer>)
                            alarmViewModel.getClass()
                                    .getDeclaredMethod(method).invoke(alarmViewModel))
                            .setValue(progress);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void listenerNumberPickerTask(String method, NumberPicker numberPicker) {
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            try {
                ((MutableLiveData<Integer>)
                        alarmViewModel.getClass()
                                .getDeclaredMethod(method).invoke(alarmViewModel))
                        .setValue(newVal);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

    }

    public void listenerLayout(String method, LinearLayout linearLayout) {
        linearLayout.setOnClickListener(v -> {
            try {
                LinearLayout linearLayoutSelected = (LinearLayout) v;
                ((MutableLiveData<Boolean>)
                        alarmViewModel.getClass()
                                .getDeclaredMethod(method).invoke(alarmViewModel))
                        .setValue(!((MutableLiveData<Boolean>)
                                alarmViewModel.getClass()
                                        .getDeclaredMethod(method).invoke(alarmViewModel)).getValue());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

    }

    public void updateTextViewWithdiffType() {
        if (radioIndexSelected == 1) {
            // maths
            taskExample.setText(TaskQuestion.getMathsExample(difficulty));
        } else if (radioIndexSelected == 2) {
            // write
            taskExample.setText(TaskQuestion.getWriteExample(difficulty, getResources()));
        }
        // nothing
        seekBarDesc.setText(alarmViewModel.getDifficultyToString(difficulty, getResources()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
