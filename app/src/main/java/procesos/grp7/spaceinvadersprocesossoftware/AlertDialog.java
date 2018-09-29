package procesos.grp7.spaceinvadersprocesossoftware;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AlertDialog extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.age_dialog);

        final EditText edad= findViewById(R.id.edadUsuario);
        Button aceptar=findViewById(R.id.AceptarBtn);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (Integer.parseInt( edad.getText().toString())>=13){
                   StartGame(v);
               }else{
                   System.out.println("No esta implementado");
               }
            }
        });
    }
    public void StartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
