package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MediaPlayer music = MediaPlayer.create(this, R.raw.shovel);
        CronoMusic crono = new CronoMusic(music, this);
        crono.start();
    }
    public void StartAlertDialog (View view){
        Intent intent = new Intent(this, AlertDialog.class);
        startActivity(intent);
        finish();
    }

}
