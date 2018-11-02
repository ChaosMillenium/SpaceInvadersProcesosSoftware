package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverScreen extends AppCompatActivity {
    TextView puntosFinales;

    String nombre;
    int puntuacion;
    Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_screen);
        puntosFinales = findViewById(R.id.puntuacionfinal);
        Intent intent = getIntent();
        String msg  = intent.getStringExtra("EXTRA_POINTS");
        puntosFinales.setText(msg);
        puntuacion = Integer.parseInt(msg);




    }
    public void GoTOPoints(View view) {
        final EditText nombreU = findViewById(R.id.Nombre);
        nombre = nombreU.getText().toString();
        if (!nombre.equals("")) {
            Intent intent = new Intent(this, RankingActivity.class);
            intent.putExtra("EXTRA_MESSAGE", nombre);
            intent.putExtra("EXTRA_MESSAGE2", puntuacion + "");
            startActivity(intent);
            finish();
        }
    }

}
