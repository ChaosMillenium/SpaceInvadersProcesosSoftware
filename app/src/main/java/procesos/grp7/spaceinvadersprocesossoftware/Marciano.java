package procesos.grp7.spaceinvadersprocesossoftware;

import android.util.Log;
import android.widget.ImageView;

public class Marciano extends Thread{
    private ImageView spriteMarciano;
    private int width ;
    private int height ;
    private String orientacion;
    private float x;
    private float y;

    public Marciano (ImageView spriteMarciano, int width, int height){
        this.spriteMarciano = spriteMarciano;
        this.spriteMarciano.setY(0);
        this.spriteMarciano.setX(0);
        this.x = this.spriteMarciano.getX();
        this.y = this.spriteMarciano.getY();
        this.width = width;
        this.height = height;
        this.orientacion = "DER";
    }

    private void actualizaPosicion(){
        if(this.orientacion.equals("DER")) {
            this.x+=10;
            this.spriteMarciano.setX(this.x);
            this.spriteMarciano.setY(this.y);
        }
        else{
            this.x-=10;
            this.spriteMarciano.setX(this.x);
           this.spriteMarciano.setY(this.y);
        }
    }

    public void run(){
        try {
            while (true) {
                if(this.orientacion.equals("DER")){
                    Log.d("width", "" + this.width);
                    if(this.x < this.width){
                        Thread.sleep(20);
                        actualizaPosicion();
                    }
                   else{
                        this.y += 50;
                        this.orientacion = "IZQ";
                    }
                }
                else{
                    if(this.x > 0){
                        Thread.sleep(20);
                        actualizaPosicion();
                    }
                    else{
                        this.y += 50;
                        this.orientacion = "DER";
                    }
                }
            }
        }catch (InterruptedException e){
            System.out.println(e.getCause());
        }
    }

}
