package procesos.grp7.spaceinvadersprocesossoftware;

public class Cronometro extends Thread {
    private VistaMarcianoEspecial vistaMarcianoEspecial;

    public Cronometro(VistaMarcianoEspecial v){
        this.vistaMarcianoEspecial = v;
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(10000);
                this.vistaMarcianoEspecial.setTemp(true);
            }catch (InterruptedException e){
                e.getCause();
            }
        }
    }
}
