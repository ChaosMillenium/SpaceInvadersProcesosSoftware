package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import procesos.grp7.spaceinvadersprocesossoftware.R;

public class MarcianoEspecial {
    private ImageView spriteMarciano;
    private int length;
    private int height;
    private float x;
    private float y;
    private boolean isVisible;
    private Context context;
    private String orientacion;
    private int screenX;
    private int screenY;
    private RelativeLayout layout;

    public MarcianoEspecial(Context context, int screenX, int screenY) {
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.length = screenX / 20;
        this.height = screenY / 20;
        this.isVisible = true;
        this.x = 0;
        this.y = 0;
        this.orientacion = "RIGHT";
    }

    public void addImageView(RelativeLayout layout, int id) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.spritemarciano2);
        imageView.setColorFilter(Color.RED);
        this.spriteMarciano = imageView;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.height = this.height; //40
        params.width = this.length; //80
        imageView.setId(id);
        layout.addView(imageView, params);
    }

    public boolean vivo() {
        return this.isVisible;
    }

    public void actualizaPosicion() {
        this.x += this.screenX/100;
    }

    public void dibuja() {
        this.spriteMarciano.setX(this.x);
        this.spriteMarciano.setY(this.y);
    }

    public ImageView getSpriteMarciano() {
        return spriteMarciano;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getLength() {
        return length;
    }
}
