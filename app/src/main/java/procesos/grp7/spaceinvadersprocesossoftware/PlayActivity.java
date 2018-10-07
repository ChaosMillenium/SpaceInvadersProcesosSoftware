package procesos.grp7.spaceinvadersprocesossoftware;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public abstract class PlayActivity extends AppCompatActivity {
    public boolean dead = false;

    public abstract void kill(final Object collider1, final ImageView collider2);
}
