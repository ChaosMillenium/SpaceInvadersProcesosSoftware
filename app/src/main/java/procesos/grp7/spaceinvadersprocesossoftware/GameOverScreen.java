package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GameOverScreen extends AppCompatActivity {
    TextView puntosFinales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_screen);
        puntosFinales = findViewById(R.id.puntuacionfinal);
        Intent intent = getIntent();
        String msg  = intent.getStringExtra("EXTRA_POINTS");
        puntosFinales.setText(msg);
    }
    public void GoTOMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
    public void RestartGame(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        String extra = "0";
        intent.putExtra("EXTRA_MESSAGE", extra);
        startActivityForResult(intent, 1);
        finish();
    }
}
