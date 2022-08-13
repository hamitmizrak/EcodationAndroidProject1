package hamitmizrak.com.ecodationandroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

//Firebase Telephone validation
public class TelephoneValidation extends AppCompatActivity {
    //global variable

    //telephone verification
    EditText editTextFirebasetelephoneValidationId;
    Button buttonFirebasetelephoneValidationId;
    EditText editTextFirebaseVerificationCodeId;

    //firebase login telephone
    //firebase bağlanmak
    FirebaseAuth firebaseAuth;

    //verification firebase sonucu
    PhoneAuthProvider.OnVerificationStateChangedCallbacks myCallBack;

    //verification id
    String verificationID;

    //doğrulamanın başlaması için gereken method
    // En fazla 60 saniye bağlanma ve geri dönüş
    public void startedVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                editTextFirebasetelephoneValidationId.getText().toString(), 60, TimeUnit.SECONDS, this, myCallBack);
    }

    //onCreate Method: Projemizi açıtğımızda ilk çalışacak kdo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_validation);
        //start codes

        //id
        editTextFirebasetelephoneValidationId = findViewById(R.id.editTextFirebasetelephoneValidationId);
        buttonFirebasetelephoneValidationId = findViewById(R.id.buttonFirebasetelephoneValidationId);
        editTextFirebaseVerificationCodeId = findViewById(R.id.editTextFirebaseVerificationCodeId);

        //Firebase bağlanmak için
        firebaseAuth = FirebaseAuth.getInstance();

        buttonFirebasetelephoneValidationId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificationID dolu ise sisteme giriş yapabilirim
                //Eğer verficationId null ise tekrardan startedVerfication Metodu çalışsın
                if(verificationID!=null){
                    //verification code almak ve sisteme giriş yapmak
                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(
                            verificationID,editTextFirebaseVerificationCodeId.getText().toString());
                    firebaseAuth.signInWithCredential(phoneAuthCredential);
                }else{
                    Toast.makeText(TelephoneValidation.this, "Verification code tekrarı", Toast.LENGTH_SHORT).show();

                    //doğrulamanın başlaması için gereken method
                    startedVerification();
                }
            }
        });


        //myCallBack Function abstract: verification callBack
        myCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            //verification koduna gerek olmadığı durumlarda bu metot kullanılır
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                firebaseAuth.signInWithCredential(phoneAuthCredential);
                Toast.makeText(TelephoneValidation.this, "Verification olmadan giriş yapıldı", Toast.LENGTH_SHORT).show();
            }

            // Verificationdan bize hata fırlatılırsa bu metot devreye girer
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(TelephoneValidation.this, "Telefonla Sisteme Giriş yapılırken hata meydana geldi", Toast.LENGTH_SHORT).show();
                Log.e("onVerificationFailed","Sisteme Giriş yapılırken hata meydana geldi");
            }

            //verification kod gönderdikten sonra çalışması sağlayan method
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //start
                verificationID=s;
                buttonFirebasetelephoneValidationId.setText("Validation Verify Code Request");
                Toast.makeText(TelephoneValidation.this, "Verification kod gönderildi", Toast.LENGTH_SHORT).show();
            }
        };//end myCallBack
    }//end onCreate
}//end class