package procesos.grp7.spaceinvadersprocesossoftware;


public class Usuario<E> implements Comparable<E>{

    private String nombre;
    private String punts;

    public Usuario (){
        nombre = null;
        punts = "0";
    }

    public Usuario (String n, String p){
        nombre = n;
        punts = p;
    }

    public int compareTo (E object){
        Usuario user = (Usuario) object;
        if (Integer.parseInt(this.punts) == Integer.parseInt(user.punts)){
            return 0;
        } else if (Integer.parseInt(this.punts) > Integer.parseInt(user.punts)){
            return 1;
        } else return -1;
    }

    public String getMessage(){
        return nombre + ": " + punts;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPunts() {
        return punts;
    }
}
