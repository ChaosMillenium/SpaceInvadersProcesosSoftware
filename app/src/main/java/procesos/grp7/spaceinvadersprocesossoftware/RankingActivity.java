package procesos.grp7.spaceinvadersprocesossoftware;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RankingActivity extends AppCompatActivity {

    private String puntos;
    private int points;
    private String name;
    private BitMapDataObject perfil;
    private String rebote;
    private Usuario[] puntuaciones;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Intent intent = getIntent();
        name = intent.getStringExtra("EXTRA_MESSAGE");
        puntos = intent.getStringExtra("EXTRA_MESSAGE2");
        rebote = intent.getStringExtra("REBOTE");
        points = Integer.parseInt(puntos);
        opcionesPorPuntos();
        perfil = new BitMapDataObject((Bitmap) intent.getExtras().get("PHOTO"));
        puntuaciones = new Usuario[10];
        Usuario user = new Usuario(name, puntos, perfil);
        sacarPuntuaciones();
        leerFile();
        colocarUsuario(user);
        writeFile();
        puntuar();



    }

    public void sacarPuntuaciones() {
        ImageView perfil9 = findViewById(R.id.imageView9);
        perfil9.buildDrawingCache();
        Bitmap bmap = perfil9.getDrawingCache();
        BitMapDataObject b = new BitMapDataObject(bmap);

        for (int i = 0; i < 10; i++) {

            puntuaciones[i] = new Usuario("Vacío", 0 + "", b);

        }
    }

    public void colocarUsuario(Usuario user) {
        int i = 9;
        while ((i >= 0) && user.compareTo(puntuaciones[i]) >= 0) {
            i--;
        }
        i++;
        if (i<10) {
            if (i >= 0) {

                if (i != 9)
                    for (int j = 8; j >= i; j--) {
                        puntuaciones[j + 1] = puntuaciones[j];
                    }
                puntuaciones[i] = user;
            }
        }
    }

    public void puntuar() {
        TextView puntos = findViewById(R.id.punt);
        puntos.setText(("1- " + puntuaciones[0].getMessage()));
        ImageView perfil1 = findViewById(R.id.imageView);
        perfil1.setImageBitmap(puntuaciones[0].getPerfil().getCurrentImage());

        TextView puntos0 = findViewById(R.id.punt2);
        puntos0.setText(("2- " + puntuaciones[1].getMessage()));
        ImageView perfil2 = findViewById(R.id.imageView2);
        perfil2.setImageBitmap(puntuaciones[1].getPerfil().getCurrentImage());

        TextView puntos1 = findViewById(R.id.punt3);
        puntos1.setText(("3- " + puntuaciones[2].getMessage()));
        ImageView perfil3 = findViewById(R.id.imageView3);
        perfil3.setImageBitmap(puntuaciones[2].getPerfil().getCurrentImage());

        TextView puntos2 = findViewById(R.id.punt4);
        puntos2.setText(("4- " + puntuaciones[3].getMessage()));
        ImageView perfil4 = findViewById(R.id.imageView4);

        perfil4.setImageBitmap(puntuaciones[3].getPerfil().getCurrentImage());

        TextView puntos3 = findViewById(R.id.punt5);
        puntos3.setText(("5- " + puntuaciones[4].getMessage()));
        ImageView perfil5 = findViewById(R.id.imageView5);

        perfil5.setImageBitmap(puntuaciones[4].getPerfil().getCurrentImage());

        TextView puntos4 = findViewById(R.id.punt6);
        puntos4.setText(("6- " + puntuaciones[5].getMessage()));
        ImageView perfil6 = findViewById(R.id.imageView6);

        perfil6.setImageBitmap(puntuaciones[5].getPerfil().getCurrentImage());

        TextView puntos5 = findViewById(R.id.punt7);
        puntos5.setText(("7- " + puntuaciones[6].getMessage()));
        ImageView perfil7 = findViewById(R.id.imageView7);

        perfil7.setImageBitmap(puntuaciones[6].getPerfil().getCurrentImage());

        TextView puntos6 = findViewById(R.id.punt8);
        puntos6.setText(("8- " + puntuaciones[7].getMessage()));
        ImageView perfil8 = findViewById(R.id.imageView8);

        perfil8.setImageBitmap(puntuaciones[7].getPerfil().getCurrentImage());

        TextView puntos7 = findViewById(R.id.punt9);
        puntos7.setText(("9- " + puntuaciones[8].getMessage()));
        ImageView perfil9 = findViewById(R.id.imageView9);

        perfil9.setImageBitmap(puntuaciones[8].getPerfil().getCurrentImage());

        TextView puntos8 = findViewById(R.id.punt10);
        puntos8.setText(("10- " + puntuaciones[9].getMessage()));
        ImageView perfil10 = findViewById(R.id.imageView10);

        perfil10.setImageBitmap(puntuaciones[9].getPerfil().getCurrentImage());

    }


    public void leerFile() {
        try {
            File directory = this.getFilesDir();
            File file = new File(directory, "Ranking.bin");
            ObjectInputStream filein = new ObjectInputStream(new FileInputStream(file));
            for (int i = 0; i < 10; i++) {
                ImageView perfil9 = findViewById(R.id.imageView9);
                perfil9.buildDrawingCache();
                Bitmap bmap = perfil9.getDrawingCache();
                BitMapDataObject p = new BitMapDataObject(bmap);
                Object nombre = filein.readObject();
                Object puntuacion = filein.readObject();
                p.readObject(filein);
                puntuaciones[i] = new Usuario((String) nombre, (String) puntuacion, p);

            }
            filein.close();


        } catch (Exception e) {
        }
    }

    public void writeFile() {
        try {
            File directory = this.getFilesDir();
            File file = new File(directory, "Ranking.bin");
            FileOutputStream fileoutputstream = new FileOutputStream(file);
            ObjectOutputStream fileout = new ObjectOutputStream(fileoutputstream);
            for (int i = 0; i < 10; i++) {
                fileout.writeObject(puntuaciones[i].getNombre());
                fileout.writeObject(puntuaciones[i].getPunts());
                puntuaciones[i].getPerfil().writeObject(fileout);
            }
            fileout.close();
            fileout.close();
            fileoutputstream.close();
        } catch (Exception ex) {
            System.out.println("Error serializando");
            System.out.println(ex.getMessage());
            System.out.println(ex.toString());
        }
    }

    public void opcionesPorPuntos (){
        if (this.points>=500){

            Button botMenu = findViewById(R.id.points2);
            botMenu.setVisibility(View.VISIBLE);
            //mostrar dos botones
        }
    }

    public void GoTOMenu(View view) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);

        finish();
    }

    public void RestartGame(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        String extra = "0";
        intent.putExtra("EXTRA_MESSAGE", extra);
        intent.putExtra("REBOTE", rebote);
        startActivityForResult(intent, 1);
        finish();
    }
}
