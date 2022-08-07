package hamitmizrak.com.ecodationandroid1;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalStorageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_storage_view);
        //start codes
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("resim");
        String URL="android.resource://hamitmizrak.com/drawable/car";
        Uri uri=Uri.parse(URL);
        try {
            InputStream inputStream=getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        storageReference.putFile(uri);

        //end codes
    }
}