package mcm.edu.ph.externalio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.os.Environment;
import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;

    private String filename = "Sarcia.txt";
    private String filepath = "Sarcia_externalio";
    File akongfile;
    String akonggitype = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtbox = findViewById(R.id.txtbox);
        final Button butclear = findViewById(R.id.butclear);
        final Button butread = findViewById(R.id.butread);
        final Button butwrite = findViewById(R.id.butwrite);

        butclear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        txtbox.setText("");
                    }
                });
        butwrite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileOutputStream fos = new FileOutputStream(akongfile);
                            fos.write(txtbox.getText().toString().getBytes());
                            fos.close();

                            //display file saved message
                            Toast.makeText(getBaseContext(), "File saved externally!",
                                    Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
        butread.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileInputStream fileIn = openFileInput("mytextfile.txt");
                            InputStreamReader InputRead = new InputStreamReader(fileIn);

                            char[] inputBuffer = new char[READ_BLOCK_SIZE];
                            String s = "";
                            int charRead;

                            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                                // char to string conversion
                                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                                s += readstring;
                            }
                            InputRead.close();
                            txtbox.setText(s);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            butwrite.setEnabled(false);
        }
    }
    private static boolean isExternalStorageReadOnly () {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable () {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
