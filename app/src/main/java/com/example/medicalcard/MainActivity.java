package com.example.medicalcard;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MedicalCardDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MedicalCardDatabaseHelper(this);

        Button generateAndExportButton = findViewById(R.id.generateAndExportButton);
        Button SaveData = findViewById(R.id.SaveData);

       EditText EdName = findViewById(R.id.FullName);
       EditText  EdEmergencyContact =  findViewById(R.id.EmergencyContact);
       EditText EdAddress = findViewById(R.id.Adddress);
       EditText BloodGroup = findViewById(R.id.BloodGroup);
       EditText Gender = findViewById(R.id.Gender);

        SaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = EdName.getText().toString();
                String userEmergencyContact = EdEmergencyContact.getText().toString();
                String userAddress = EdAddress.getText().toString();
                String userGender = Gender.getText().toString();
                String userBloodGroup = BloodGroup.getText().toString();


                dbHelper.insertMedicalCard(userName, userEmergencyContact, userAddress, userGender, userBloodGroup);

            }
        });
        generateAndExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve medical information from the database
                MedicalCard medicalCard = dbHelper.retrieveMedicalCard();

                if (medicalCard != null) {
                    // Generate QR code
                    Bitmap qrCodeBitmap = generateQRCode(String.valueOf(medicalCard.getId()));

                    // Export medical card as PDF
                    exportAsPDF(medicalCard.getName(), medicalCard.getEmergencyContact(), medicalCard.getAddress(), medicalCard.getGender(),
                            medicalCard.getBloodGroup(), qrCodeBitmap);
                }
            }
        });
    }

    private Bitmap generateQRCode(String data) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.Black) : getResources().getColor(R.color.white));
                }
            }
            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void exportAsPDF(String name, String emergencyContact, String address, String gender, String bloodGroup, Bitmap qrCodeBitmap) {
        Document document = new Document();
        try {
            // Define the file path for the PDF
            File pdfFile = new File(getExternalFilesDir(null), "MedicalCard.pdf");
            FileOutputStream fos = new FileOutputStream(pdfFile);
            PdfWriter.getInstance(document, fos);

            document.open();

            // Convert Bitmap to iText Image
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            qrCodeBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleAbsolute(100f, 100f); // Adjust width and height as needed

            // Add medical information to the PDF
            document.add(image);
            document.add(new Paragraph("Name: " + name));
            document.add(new Paragraph("Emergency Contact: " + emergencyContact));
            document.add(new Paragraph("Address: " + address));
            document.add(new Paragraph("Gender: " + gender));
            document.add(new Paragraph("Blood Group: " + bloodGroup));

            document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
