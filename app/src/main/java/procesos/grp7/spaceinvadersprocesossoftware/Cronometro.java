package procesos.grp7.spaceinvadersprocesossoftware;

public class Cronometro extends Thread {
    private VistaMarcianoEspecial vistaMarcianoEspecial;
    private PlayActivity activity;

    public Cronometro(VistaMarcianoEspecial v, PlayActivity activity){
        this.vistaMarcianoEspecial = v;
        this.activity = activity;
    }

    public void run(){
        while (!activity.dead){
            try {
                Thread.sleep(10000);
                this.vistaMarcianoEspecial.setTemp(true);
            }catch (InterruptedException e){
                e.getCause();
            }
        }
    }
}
