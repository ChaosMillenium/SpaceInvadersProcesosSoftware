package procesos.grp7.spaceinvadersprocesossoftware.Screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import procesos.grp7.spaceinvadersprocesossoftware.R;
import procesos.grp7.spaceinvadersprocesossoftware.Screens.AlertDialog;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public void StartAlertDialog (View view){
        Intent intent = new Intent(this, AlertDialog.class);
        startActivity(intent);
        finish();


    }

}
