package fr.utt.if26.insanealarm.ui.subPartAlarm.Sound;

import static android.content.Intent.ACTION_OPEN_DOCUMENT;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import fr.utt.if26.insanealarm.R;
import fr.utt.if26.insanealarm.databinding.FragmentSubPartSoundBinding;
import fr.utt.if26.insanealarm.ui.addAlarm.AddAlarmViewModel;
import fr.utt.if26.insanealarm.utils.FileManager;

public class SoundFragment extends Fragment {

    private FragmentSubPartSoundBinding binding;
    private AddAlarmViewModel alarmViewModel;
    // private TextView tvRingtoneName;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alarmViewModel = new ViewModelProvider(requireActivity()).get(AddAlarmViewModel.class);
        binding = FragmentSubPartSoundBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        /// disable click for switch
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchNeedVibration = root.findViewById(R.id.switchNeedVirbation);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchSameAsPhone = root.findViewById(R.id.switchSameAsPhone);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchIncreaseVolume = root.findViewById(R.id.switchIncreaseVomlume);

        switchNeedVibration.setClickable(false);
        switchSameAsPhone.setClickable(false);
        switchIncreaseVolume.setClickable(false);

        /// listener for volume Level

        ((SeekBar) root.findViewById(R.id.alarmSoundVolume)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    alarmViewModel.getAlarmVolume().setValue(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /// listener for each each layout
        binding.layoutSound.setOnClickListener(this::listenerLayoutSound);
        binding.layoutFlashLight.setOnClickListener(this::listenerLayoutFlashLight);
        binding.layoutNeedVibration.setOnClickListener(this::listenerLayoutVibration);
        binding.layoutIncreaseVolume.setOnClickListener(this::listenerLayoutIncreaseVolume);
        binding.layoutSameAsPhone.setOnClickListener(this::listenerLayoutSameAsPhone);

        /// link ui component to viewModel
        alarmViewModel.getFlashLightMode().observe(getViewLifecycleOwner(), flashlightMode ->
                ((TextView) root.findViewById(R.id.tvFlashLightStatus))
                        .setText(
                                alarmViewModel.ConvertFlashLightModeToStr(flashlightMode, getResources()))
        );
        alarmViewModel.getNeedVibration().observe(getViewLifecycleOwner(), switchNeedVibration::setChecked);
        alarmViewModel.getSameAsPhone().observe(getViewLifecycleOwner(), sameAsPhone -> {
            switchSameAsPhone.setChecked(sameAsPhone);
            if (sameAsPhone) {
                disableLL(binding.layoutAlarmVolume);
            } else {
                enableLL(binding.layoutAlarmVolume);
            }
        });
        alarmViewModel.getIncreaseVolume().observe(getViewLifecycleOwner(), switchIncreaseVolume::setChecked);

        alarmViewModel.getAlarmVolume().observe(getViewLifecycleOwner(), alarmVolume ->
                ((TextView) root.findViewById(R.id.alarmSoundVolumePreview)).setText(String.valueOf(alarmVolume))
        );
        alarmViewModel.getRingtoneName().observe(getViewLifecycleOwner(), ringtoneUri ->
                ((TextView) root.findViewById(R.id.tvRingtoneName)).setText(FileManager.getNameFromURI(requireContext(), Uri.parse(ringtoneUri)))
        );

        // listener for fragment sound

        requireActivity().getSupportFragmentManager()
                .setFragmentResultListener(
                        "ringtoneChosen", getViewLifecycleOwner(),
                        (requestKey, result) -> {
                            Log.i("soundFragment", result.getString("uriStr"));
                            Log.i("parsed", String.valueOf(Uri.parse(result.getString("uriStr"))));
                            alarmViewModel.getRingtoneName().setValue(result.getString("uriStr"));
                        });


        return root;
    }

    // listener function

    @SuppressLint("NonConstantResourceId")
    public void listenerLayoutFlashLight(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.alarm_sound_flash_mode);
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.alarmSoundFlashNone:
                    alarmViewModel.getFlashLightMode().setValue(0);
                    return true;
                case R.id.alarmSoundFlashStay:
                    alarmViewModel.getFlashLightMode().setValue(1);
                    return true;
                case R.id.alarmSoundFlashByFlash:
                    alarmViewModel.getFlashLightMode().setValue(2);
                    return true;
                default:
                    return false;
            }
        });
        //displaying the popup
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    public void listenerLayoutSound(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
        popupMenu.inflate(R.menu.alarm_sound_get_file);
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.alarmFromDevice:
                    triggerChooser(Intent.ACTION_GET_CONTENT);
                    return true;
                case R.id.alarmFromApp:
                    NavHostFragment.findNavController(this).navigate(R.id.nav_soundAlarmSoundLocalDevice);

                    return true;
                default:
                    return false;
            }
        });
        //displaying the popup
        popupMenu.show();

        // https://www.geeksforgeeks.org/how-to-add-audio-files-to-android-app-in-android-studio/ ffrom app
        // put list and lets go
    }

    public void listenerLayoutVibration(View v) {
        alarmViewModel.getNeedVibration().setValue(Boolean.FALSE.equals(alarmViewModel.getNeedVibration().getValue()));
    }

    public void listenerLayoutSameAsPhone(View v) {
        alarmViewModel.getSameAsPhone().setValue(Boolean.FALSE.equals(alarmViewModel.getSameAsPhone().getValue()));
    }

    public void listenerLayoutIncreaseVolume(View v) {
        alarmViewModel.getIncreaseVolume().setValue(Boolean.FALSE.equals(alarmViewModel.getIncreaseVolume().getValue()));
    }

    private void disableLL(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(false);
            if (child instanceof ViewGroup)
                disableLL((ViewGroup) child);
        }
    }

    private void enableLL(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setEnabled(true);
            if (child instanceof ViewGroup)
                enableLL((ViewGroup) child);
        }
    }

    // intent for the music chooser
    // Create this as a variable in your Fragment class
    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                // Do your code from onActivityResult

                Log.i("code", String.valueOf(result.getResultCode()));
                assert result.getData() != null;
                Log.i("res", result.getData().getData().toString());
                try {
                    Uri myUri = result.getData().getData();
                    // Ringtone r = RingtoneManager.getRingtone(getContext(), myUri);
                    //   r.play();

                    alarmViewModel.getRingtoneName().setValue(String.valueOf(myUri));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

    private void triggerChooser(String action) {
        Intent intent = new Intent(ACTION_OPEN_DOCUMENT, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI); // Uri
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Intent chooserIntent = Intent.createChooser(intent, "Open URL...");
        mLauncher.launch(chooserIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
