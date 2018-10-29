package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import procesos.grp7.spaceinvadersprocesossoftware.R;

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
        Intent intent = new Intent(this, GameUnder13Activity.class);
        startActivity(intent);
        finish();
    }
}
