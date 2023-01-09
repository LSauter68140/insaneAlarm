package fr.utt.if26.insanealarm.ui.stopAlarm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.ActivityDoingTaskBinding;
import fr.utt.if26.insanealarm.model.Alarm;
import fr.utt.if26.insanealarm.model.Task;
import fr.utt.if26.insanealarm.utils.TaskQuestion;


public class DoingTaskActivity extends AppCompatActivity {

    Task task;
    Alarm alarm;
    Float resultTask;
    String questionTask;
    String taskType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDoingTaskBinding binding = ActivityDoingTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        task = null;
        // get data for doing task
        Intent getIntent = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            task = getIntent.getSerializableExtra("taskToDo", Task.class);
            alarm = getIntent.getSerializableExtra("alarm", Alarm.class);
        } else {
            task = (Task) getIntent.getSerializableExtra("taskToDo");
            alarm = (Alarm) getIntent.getSerializableExtra("alarm");
        }
        if (task == null)
            return;
        taskType = task.getType();
        binding.taskTodoTitle.append(
                taskType.equals("Maths") ?
                        getString(R.string.oneCalcul) :
                        getString(R.string.recopyText)
        );
        if (taskType.equals("Maths")) {
            HashMap<Float, String> mathsTask = TaskQuestion.getMathsTask(task.getDifficulty());
            resultTask = (float) mathsTask.keySet().toArray()[0];
            questionTask = mathsTask.get(resultTask);
            binding.answerTaskTodo.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            questionTask = TaskQuestion.getWriteTask(task.getDifficulty(), getResources());
        }
        binding.tvTaskTodoQu.setText(questionTask);

        // add timer

        new CountDownTimer(task.getTimer() * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                binding.tvTaskTodoTimer.setText(
                        getString(R.string.remingTime) + millisUntilFinished / 1000);
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                binding.tvTaskTodoTimer.setText("time out!");
                //new calculation
                recreate();
            }

        }.start();

        /// configure btn

        // skip button -  new calculation
        binding.btnTaskToDoSkip.setOnClickListener(v -> recreate());

        // check if the calculation is right !
        binding.btnTaskTodoOk.setOnClickListener(v -> {
            //check if it ok
            String resultUser = binding.answerTaskTodo.getText().toString();
            if ((taskType.equals("Maths") && Float.compare(Float.parseFloat(resultUser), resultTask) == 0) ||
                    taskType.equals("Write") && resultUser.trim().equals(questionTask.trim())
            ) {
                // ok
                Toast.makeText(getApplicationContext(), "ok babe", Toast.LENGTH_SHORT).show();
                Intent controlListenerIntent = new Intent(getApplicationContext(), ControlsListener.class);
                controlListenerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                controlListenerIntent.putExtra("alarm", alarm);
                //doingTaskIntent.putExtra("taskToDo", alarmToGoOff.getSnooze().getTask());
                getApplicationContext().startActivity(controlListenerIntent);
                onStop();
            } else {
                // oupsi
                Toast.makeText(getApplicationContext(), R.string.noValidateInfoTask, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

