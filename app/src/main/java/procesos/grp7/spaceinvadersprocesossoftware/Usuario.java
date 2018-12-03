package procesos.grp7.spaceinvadersprocesossoftware;

public class Usuario<E> implements Comparable<E>{

    private String nombre;
    private String punts;
    private BitMapDataObject perfil;

    public Usuario (){
        nombre = null;
        punts = "0";
        perfil=null;
    }

    public Usuario (String n, String p,BitMapDataObject imagen){
        nombre = n;
        punts = p;
        perfil=imagen;
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

    public BitMapDataObject getPerfil() {
        return perfil;
    }

    public void setPerfil(BitMapDataObject perfil) {
        this.perfil = perfil;
    }
}
