package hamitmizrak.com.ecodationandroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

//telephone Validation
public class TelephoneValidation extends AppCompatActivity {

    //Login Global varaible
    EditText phoneNumberEditTextId;
    EditText phoneCodeEditTextId;
    Button confirmationButtonId;

    // call back data
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callBackData;

    // Firebase kimlik doğrulama
    FirebaseAuth firebaseAuth;

    //Database referans eğer kullanıcı sisteme giriş yaparsa database veri eklemek için kullanacağız
    //Eğer final yazarsan constructora eklemek zorunda sına nalamına gelir.
    DatabaseReference databaseReference;

    //Database kullanıcıs
    FirebaseUser firebaseUser;

    //Doğrulama işlemi için gerekli
    String recognationId;

    //verificationTelephone Method ==>   Eğer doğrulama yapılmamışsa bu metot çalışır
    public void verificationTelephone() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumberEditTextId.getText().toString(), 60, TimeUnit.SECONDS, this, callBackData);
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_validation);
        //start codes
        //id almak
        phoneNumberEditTextId = findViewById(R.id.phoneNumberEditTextId);
        phoneCodeEditTextId = findViewById(R.id.phoneCodeEditTextId);
        confirmationButtonId = findViewById(R.id.confirmationButtonId);
        phoneCodeEditTextId.setVisibility(View.INVISIBLE);

        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        //callback
        callBackData = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //kod gerek duymadığım durumlarda çalışsın
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //kimlikle sisteme giriş yapmak için
                //bu satırdakiler 150 aynısıdı
                firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //onComplete: işlem sonrası
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Giriş başarılı ise
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            //getUıd: benzersiz ID için kullanıyoruz.
                            // persons verisinin altına getUıd ekledim
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("persons").child(firebaseUser.getUid());

                            //Kişi eğer sisteme girmişse tekrar tekrar sisteme  giriş yapmaması için
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        //Kullanıcı sistemde olduğu için Admin sayfasına yönlendir
                                        Intent homePageActivityIndent = new Intent(getApplicationContext(), AdminActivity.class);
                                        startActivity(homePageActivityIndent);
                                    } else {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("Name", "Hamit");
                                        hashMap.put("Surname", "Mızrak ");
                                        hashMap.put("Photo", "");
                                        hashMap.put("Status", "Merhabalar");
                                        hashMap.put("Phone", firebaseUser.getPhoneNumber());
                                        databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //intent Ana activity geçiş
                                                    Intent homePageActivityIndent = new Intent(getApplicationContext(), AdminActivity.class);
                                                    startActivity(homePageActivityIndent);
                                                }
                                            }//onComplete end
                                        });// databaseReference.updateChildren
                                    }//end else
                                }//onDataChange end

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("OnCancelled", "Çıkıldı");
                                    Toast.makeText(TelephoneValidation.this, "Login Çıkış yapıldı", Toast.LENGTH_SHORT).show();
                                }//onCancelled
                            });
                        } //isSuccessful end
                    } //onCompleted end
                }); //signInWithCredential end;
            }//onVerificationCompleted end

            @Override// hata olursa
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("Doğrulama Hatası", e.getMessage());
            }//onVerificationFailed end

            //kod gönderdikten sonra
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                recognationId = s;
                confirmationButtonId.setText("Onaylama Yapabilirsiniz");
                //onaylama sonrasında editText çalışssın
                phoneNumberEditTextId.setVisibility(View.INVISIBLE);
                phoneCodeEditTextId.setVisibility(View.VISIBLE);
            }//onCodeSent end
        }; //callBackData end

        //butona tıklandığında gerçekleşecek olay
        confirmationButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recognationId != null) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(recognationId, phoneCodeEditTextId.getText().toString());
                    //kimlikle sisteme giriş yapmak için  //bu satırdakiler 79 aynısıdı
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Giriş başarılı ise
                            if (task.isSuccessful()) {
                                firebaseUser = firebaseAuth.getCurrentUser();
                                //getUıd: benzersiz ID için kullanıyoruz.
                                // persons verisinin altına getUıd ekledim
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("persons").child(firebaseUser.getUid());

                                //Kişi eğer sisteme girmişse tekrar tekrar sisteme  giriş yapmaması için
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            //Ana Activity Yönlendirmek
                                            Intent homePageActivityIndent = new Intent(getApplicationContext(), AdminActivity.class);
                                            startActivity(homePageActivityIndent);
                                        } else {
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("Name", "Hamit");
                                            hashMap.put("Surname", "Mızrak");
                                            hashMap.put("Photo", "");
                                            hashMap.put("Status", "Merhabalar");
                                            hashMap.put("Phone", firebaseUser.getPhoneNumber());
                                            databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        //intent Ana activity geçiş
                                                        Intent homePageActivityIndent = new Intent(getApplicationContext(), AdminActivity.class);
                                                        startActivity(homePageActivityIndent);
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("OnCancelled", "Çıkıldı");
                                    }//onCancelled
                                });
                            } //isSuccessful end
                        } //onCompleted end
                    }); //signInWithCredential end
                } else {
                    //Eğer doğrulama yapılmamışsa bu metot çalışsın
                    verificationTelephone();
                }
            } //onClick end
        });//setOnClickListener end
    }//onCreate ends
}