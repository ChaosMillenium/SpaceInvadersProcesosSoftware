package procesos.grp7.spaceinvadersprocesossoftware.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import procesos.grp7.spaceinvadersprocesossoftware.R;

public class AlertDialog extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_dialog);

        final EditText edad = findViewById(R.id.edadUsuario);
        Button aceptar = findViewById(R.id.AceptarBtn);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edad.getText().toString().isEmpty()) {
                    if (Integer.parseInt(edad.getText().toString()) >= 13) {
                        StartGameOlder(v);
                    } else {
                        StartGameYounger(v);
                    }
                }
            }
        });
    }

    public void StartGameOlder(View view) {
        /*Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();*/
        Intent intent = new Intent(this, GameActivity.class);
        String extra = "0";
        intent.putExtra("EXTRA_MESSAGE", extra);
        startActivityForResult(intent, 1);
        finish();
    }

    public void StartGameYounger(View view) {
        Intent intent = new Intent(this, GameUnder13Activity.class);
        startActivity(intent);
        finish();
    }
}
