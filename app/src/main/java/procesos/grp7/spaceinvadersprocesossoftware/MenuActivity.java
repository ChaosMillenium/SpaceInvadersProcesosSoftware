package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void juegoMayor13(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
