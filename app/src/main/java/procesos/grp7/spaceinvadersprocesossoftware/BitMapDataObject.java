package procesos.grp7.spaceinvadersprocesossoftware;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class BitMapDataObject implements Serializable {
    public Bitmap getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Bitmap currentImage) {
        this.currentImage = currentImage;
    }

    private Bitmap currentImage;

    public BitMapDataObject(Bitmap bitmap) {
        currentImage = bitmap;
    }

    public void writeObject(java.io.ObjectOutputStream out) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        currentImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        out.writeInt(byteArray.length);
        out.write(byteArray);

    }

    public void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {


        int bufferLength = in.readInt();

        byte[] byteArray = new byte[bufferLength];

        int pos = 0;
        do {
            int read = in.read(byteArray, pos, bufferLength - pos);

            if (read != -1) {
                pos += read;
            } else {
                break;
            }

        } while (pos < bufferLength);

        currentImage = BitmapFactory.decodeByteArray(byteArray, 0, bufferLength);

    }
}


