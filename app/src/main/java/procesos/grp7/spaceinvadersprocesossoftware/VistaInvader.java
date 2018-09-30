package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Context;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class VistaInvader extends Thread{
    private ArrayList<Marciano> marcianos;
    private int numMarcianos;
    private int screenX;
    private int screenY;
    private Context context;
    private boolean pared;
    private RelativeLayout layout;

    public VistaInvader(Context context, int screenX, int screenY, RelativeLayout layout){
        this.marcianos = new ArrayList<Marciano>();
        this.layout = layout;
        this.context=context;
        this.screenX = screenX;
        this.screenY = screenY;
        this.context = context;
        this.pared = false;
        rellenaMarcianos();
    }

    public void rellenaMarcianos(){
        this.numMarcianos = 0;
        for(int column = 0 ; column < 6; column++){
            for(int row = 0; row <5 ; row++){
                this.marcianos.add(numMarcianos, new Marciano(this.context, screenX, screenY, row, column));
                this.marcianos.get(numMarcianos).addImageView(this.layout);
                numMarcianos++;
            }
        }
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(150);
                movimiento();
                dibuja();
            }
        }catch (InterruptedException e){
            System.out.print(e.getCause());
        }
    }

    public void movimiento(){
        for(int i = 0; i<this.marcianos.size(); i++){
            if(marcianos.get(i).vivo()){
                marcianos.get(i).actualizaPosicion();
                if(marcianos.get(i).getX() > screenX - marcianos.get(i).getLength() || marcianos.get(i).getX() < 0){
                    pared = true;
                }
            }
        }
        if (pared){
            for(int i = 0; i<this.marcianos.size(); i++){
                marcianos.get(i).choquePared();
                pared = false;
            }
        }
    }

    public void dibuja(){
        for(int i = 0; i<this.marcianos.size(); i++){
            if(marcianos.get(i).vivo()){
                this.marcianos.get(i).dibuja();
            }
        }
    }
}
