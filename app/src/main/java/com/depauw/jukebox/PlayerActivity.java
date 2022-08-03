package com.depauw.jukebox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.depauw.jukebox.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;

    private float Avg1 = 0;
    private float Avg2 = 0;
    private float Avg3 = 0;

    private MediaPlayer mediaPlayer;

    public String getTextViewText(TextView view){
        return view.getText().toString();
    }

    public int getTextViewInt(TextView view){
        return Integer.valueOf(getTextViewText(view));
    }

    public int returnColor(){
        return Color.rgb(getTextViewInt(binding.textviewRed), getTextViewInt(binding.textviewGreen), getTextViewInt(binding.textviewBlue));
    }



    private SeekBar.OnSeekBarChangeListener seekbar_OnChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            switch (seekBar.getId())
            {
                case R.id.seekbar_red:
                    binding.textviewRed.setText(String.valueOf(i));
                    binding.constraintlayout.setBackgroundColor(returnColor());
                    break;
                case R.id.seekbar_green:
                    binding.textviewGreen.setText(String.valueOf(i));
                    binding.constraintlayout.setBackgroundColor(returnColor());
                    break;
                case R.id.seekbar_blue:
                    binding.textviewBlue.setText(String.valueOf(i));
                    binding.constraintlayout.setBackgroundColor(returnColor());
                    break;
                case R.id.seekbar_song_position:
                    Log.d("Omer", String.valueOf(mediaPlayer.getDuration() * binding.seekbarSongPosition.getProgress()));
                    Log.d("Omer", String.valueOf(mediaPlayer.getDuration()));
                    mediaPlayer.seekTo(mediaPlayer.getDuration() * binding.seekbarSongPosition.getProgress()/100);
                    break;
            }


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private RadioGroup.OnCheckedChangeListener radiogroup_songs_OnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            mediaPlayer.stop();
            createMediaPlayer(checkedId);

        }
    };

    private View.OnClickListener button_cast_vote_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            updateRating(binding.radiogroupSongs.getCheckedRadioButtonId());

        }

    };

    private void updateRating(int checkedId){
        switch(checkedId){
            case R.id.radio_song1:
                Avg1 *= Integer.valueOf(binding.textviewNumVotes1.getText().toString());
                Avg1 += binding.ratingbarVoterRating.getRating();
                binding.textviewNumVotes1.setText(String.valueOf(Integer.valueOf(binding.textviewNumVotes1.getText().toString())+1));
                Avg1 /= Integer.valueOf(binding.textviewNumVotes1.getText().toString());
                binding.progressbarAverageRating1.setProgress(Math.round(Avg1));
                break;
            case R.id.radio_song2:
                Avg2 *= Integer.valueOf(binding.textviewNumVotes2.getText().toString());
                Avg2 += binding.ratingbarVoterRating.getRating();
                binding.textviewNumVotes2.setText(String.valueOf(Integer.valueOf(binding.textviewNumVotes2.getText().toString())+1));
                Avg2 /= Integer.valueOf(binding.textviewNumVotes2.getText().toString());
                binding.progressbarAverageRating2.setProgress(Math.round(Avg2));
                break;
            case R.id.radio_song3:
                Avg3 *= Integer.valueOf(binding.textviewNumVotes3.getText().toString());
                Avg3 += binding.ratingbarVoterRating.getRating();
                binding.textviewNumVotes3.setText(String.valueOf(Integer.valueOf(binding.textviewNumVotes3.getText().toString())+1));
                Avg3 /= Integer.valueOf(binding.textviewNumVotes3.getText().toString());
                binding.progressbarAverageRating3.setProgress(Math.round(Avg3));
                break;
        }
        binding.ratingbarVoterRating.setRating(0);
        Log.d("Omer", String.valueOf(Avg1));
        Log.d("Omer", String.valueOf(Avg2));
        Log.d("Omer", String.valueOf(Avg3));

    }




    private void createMediaPlayer(int checkedId){
        switch(checkedId){
            case R.id.radio_song1:
                binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track1, getTheme()));
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, R.raw.track1);
                mediaPlayer.start();
                break;
            case R.id.radio_song2:
                binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track2, getTheme()));
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, R.raw.track2);
                mediaPlayer.start();
                break;
            case R.id.radio_song3:
                binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track3, getTheme()));
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, R.raw.track3);
                mediaPlayer.start();
                break;
        }
        binding.seekbarSongPosition.setProgress(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.seekbarRed.setOnSeekBarChangeListener(seekbar_OnChangeListener);
        binding.seekbarGreen.setOnSeekBarChangeListener(seekbar_OnChangeListener);
        binding.seekbarBlue.setOnSeekBarChangeListener(seekbar_OnChangeListener);
        binding.radiogroupSongs.setOnCheckedChangeListener(radiogroup_songs_OnCheckedChangeListener);
        binding.seekbarSongPosition.setOnSeekBarChangeListener(seekbar_OnChangeListener);
        binding.buttonCastVote.setOnClickListener(button_cast_vote_OnClickListener);


        createMediaPlayer(binding.radiogroupSongs.getCheckedRadioButtonId());

    }
}