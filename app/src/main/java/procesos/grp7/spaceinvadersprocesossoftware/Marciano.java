package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Marciano {
    private /*Bitmap*/ ImageView spriteMarciano;
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

    public Marciano(Context context, int screenX, int screenY, int row, int column) {
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.length = screenX / 20;
        this.height = screenY / 20;
        this.isVisible = true;
        this.x = column * (length + (screenX / 1000));
        this.y = row * (length + (screenX / 25) / 4);
        this.orientacion = "RIGHT";
    }

    public void actualizaPosicion() {
        if (this.orientacion.equals("RIGHT")) {
            this.x += screenX/100;
        } else {
            this.x -= screenX/100;
        }
    }

    public void choquePared() {
        if (orientacion.equals("RIGHT")) {
            this.orientacion = "LEFT";
        } else {
            this.orientacion = "RIGHT";
        }
        this.y += screenY/40;
    }

    public void dibuja() {
        this.spriteMarciano.setX(this.x);
        this.spriteMarciano.setY(this.y);
    }

    public void addImageView(RelativeLayout layout, int id) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.spritemarciano);
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

    public float getX() {
        return this.x;
    }

    public int getLength() {
        return this.length;
    }

    public ImageView getSpriteMarciano() {
        return spriteMarciano;
    }

    public float getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public RelativeLayout getLayout() {
        return layout;
    }
}
