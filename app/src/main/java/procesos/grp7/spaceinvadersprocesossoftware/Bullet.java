package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Bullet {

    private Context context;
    private RelativeLayout gameLayout;
    private ImageView bulletView;
    public static final int UP = -1;
    public static final int DOWN = 1;
    private int direction; //-1 hacia arriba, 1 hacia abajo

    public Bullet(Context context, RelativeLayout gameLayout, int direction) {
        this.context = context;
        this.gameLayout = gameLayout;
        this.direction = direction;
        this.generateView();
    }

    private void generateView() {
        ImageView bulletView = new ImageView(context);
        bulletView.setImageResource(R.drawable.bullet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ABOVE, R.id.ship);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gameLayout.addView(bulletView, params);
        this.bulletView = bulletView;

    }

    public void update() {
        bulletView.setY(bulletView.getY() + direction);
    }
}
