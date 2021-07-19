package ar.com.unlam.enlazar.model.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrUtils {


    public static  Bitmap generateQr(String texto ){

        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode(texto, BarcodeFormat.QR_CODE,350,350);

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

            return bitmap;

        }catch (WriterException e){
            e.printStackTrace();
        }

        return null;
    }
}
