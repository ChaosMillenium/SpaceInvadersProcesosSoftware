package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Bullet {

    private Context context;
    private RelativeLayout gameLayout;

    public Bullet(Context context, RelativeLayout gameLayout) {
        this.context = context;
        this.gameLayout = gameLayout;
    }

    public void shoot(){
        generateView();
        //TODO Lógica
    }

    private void generateView(){
        ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, R.id.ship);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gameLayout.addView(bulletView, params);
        //TODO: Si se ejecuta lo comentado, la app se congela
        //Thread movementThread = new Thread(new BulletMovement(bulletView));
        //movementThread.run();
    }
}
