package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Marciano {
    private /*Bitmap*/ImageView spriteMarciano;
    private int length ;
    private int height ;
    private float x;
    private float y;
    private boolean isVisible;
    private Context context;
    private String orientacion;
    private RelativeLayout layout;

    public Marciano (Context context, int screenX, int screenY, int row, int column){
        this.context = context ;
        this.length = screenX / 20 ;
        this.height = screenY / 20 ;
        this.isVisible = true ;
        //this.spriteMarciano = BitmapFactory.decodeResource(context.getResources(), R.drawable.spritemarciano) ;
        //this.spriteMarciano = Bitmap.createScaledBitmap(spriteMarciano, length, height, false) ;
        //this.spriteMarciano = new ImageView(this.context);
        this.x = column * (length + (screenX / 1000 )) ;
        this.y = row * (length + (screenX / 25 )/4) ;
        this.orientacion = "RIGHT";
    }

    public void actualizaPosicion(){
        if(this.orientacion.equals("RIGHT")) {
            this.x += 10;
        }
        else{
            this.x-=10;
        }
    }

    public void choquePared(){
        if(orientacion.equals("RIGHT")){
            this.orientacion = "LEFT";
        }
        else{
            this.orientacion = "RIGHT";
        }
        this.y += 20;
    }

    public void dibuja(){
        this.spriteMarciano.setX(this.x);
        this.spriteMarciano.setY(this.y);
    }

    public void addImageView(RelativeLayout layout){
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.spritemarciano);
        this.spriteMarciano = imageView;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.height = 40;
        params.width = 80;
        layout.addView(imageView, params);
    }

    public boolean vivo(){
        return this.isVisible;
    }

    public float getX(){
        return this.x;
    }

    public int getLength(){
        return this.length;
    }

    public ImageView getSpriteMarciano() {
        return spriteMarciano;
    }

    public float getY() {
        return y;
    }
}
