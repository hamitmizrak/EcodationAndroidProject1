package hamitmizrak.com.ecodationandroid1;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseSelectActivity extends AppCompatActivity {

    //Global variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_select);
        //start codes

        //SELECT
        //Firebaseden Javaya veri almak => KEY'e erişmek
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("secret").child("username");
        Log.e("KEY",databaseReference.getKey());

        //Firebase Key ve Values almak
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("KEY",snapshot.getKey());
                Log.e("VALUE",snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //WRITER
        //Javadan Firebaseye veri göndermek
        DatabaseReference databaseReference2=FirebaseDatabase.getInstance().getReference().child("secret").child("username").push();
        databaseReference2.setValue("önemli veriler56");

        //end codes
    }
}