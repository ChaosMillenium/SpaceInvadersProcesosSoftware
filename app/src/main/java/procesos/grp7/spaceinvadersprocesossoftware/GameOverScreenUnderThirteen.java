package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GameOverScreenUnderThirteen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_screen_under_thirteen);
    }
    public void GoTOMenu(View view) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void RestartGame(View view) {
        //descomentar cuando este hecho el GameActivityUnderthirtenn y cambiarlo en el intent
        //Intent intent = new Intent(this, GameActivity.class);
        //startActivity(intent);
        //finish();
    }
}
