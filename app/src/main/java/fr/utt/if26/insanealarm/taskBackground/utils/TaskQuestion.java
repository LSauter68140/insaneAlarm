package fr.utt.if26.insanealarm.taskBackground.utils;

import android.content.res.Resources;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import fr.utt.if26.insanealarm.R;

public class TaskQuestion {

    public static final List<String> taskTypeNames = List.of(new String[]{"Maths", "Write"});

    public static String getMathsExample(int difficulty) {
        // random example ?
        switch (difficulty) {
            case 1:
                return "4+7";
            case 2:
                return "3+8*4";
            case 3:
                return "54*29+56";
            default:
                return "";
        }
    }

    public static String getWriteExample(int difficulty, Resources resources) {
        // random example ?
        switch (difficulty) {
            case 1:
                return "a";
            //return resources.getString(R.string.taskWriteEasy);
            case 2:
                return resources.getString(R.string.taskWriteMedium);
            case 3:
                return resources.getString(R.string.taskWriteHard);
            default:
                return "";
        }
    }

    public static HashMap<Float, String> getMathsTask(int difficulty) {
        int a, b, c;
        float result;
        String operation;
        HashMap<Float, String> mathTask = new HashMap<>();
        switch (difficulty) {
            case 1:
                a = (int) (Math.random() * 10 + 1);
                b = (int) (Math.random() * 10 + 1);
                return getMathsCalculation(a, b, 0);
            case 2:
                a = (int) (Math.random() * 10 + 1);
                b = (int) (Math.random() * 10 + 1);
                c = (int) (Math.random() * 10 + 1);
                mathTask = getMathsCalculation(b, c, 1);
                result = (float) mathTask.keySet().toArray()[0];
                operation = mathTask.get(result);
                mathTask = getMathsCalculation(a, result, 0);
                result = (float) mathTask.keySet().toArray()[0];
                operation = a + " " + (Objects.requireNonNull(mathTask.get(result)).contains("+") ? "+" : "-") + " " + operation;
                mathTask.put(result, operation);
                return mathTask;
            case 3:
                a = (int) (Math.random() * 100 + 10) % 100;
                b = (int) (Math.random() * 100 + 10) % 100;
                c = (int) (Math.random() * 100 + 10) % 100;
                mathTask = getMathsCalculation(a, b, 1);
                result = (float) mathTask.keySet().toArray()[0];
                operation = mathTask.get(result);
                mathTask = getMathsCalculation(c, result, 0);
                result = (float) mathTask.keySet().toArray()[0];
                operation = operation + " " + (Objects.requireNonNull(mathTask.get(result)).contains("+") ? "+" : "-") + " " + c;
                mathTask.put(result, operation);
                return mathTask;
            default:
                mathTask.put(0f, "0 + 0 ");
                return mathTask;
        }
    }

    public static String getWriteTask(int difficulty, Resources resources) {
        // if time add random or dictionary example
        switch (difficulty) {
            case 1:
                return "a";
            // return resources.getString(R.string.taskWriteEasy);
            case 2:
                return resources.getString(R.string.taskWriteMedium);
            case 3:
                return resources.getString(R.string.taskWriteHard);
            default:
                return "";
        }
    }

    private static HashMap<Float, String> getMathsCalculation(float a, float b, int operationType) {
        DecimalFormat df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        float result;
        String operation;
        HashMap<Float, String> mathTask = new HashMap<>();
        if (operationType == 0) {
            if (Math.random() > 0.5) {
                result = a + b;
                operation = "+";
            } else {
                result = a - b;
                operation = "-";
            }
        } else {
            if (Math.random() > 0.5) {
                result = a * b;
                operation = "*";
            } else {
                result = a / b;
                operation = "/";
            }
        }
        operation = df.format(a) + " " + operation + " " + df.format(b);
        mathTask.put(result, operation);
        return mathTask;
    }
}
