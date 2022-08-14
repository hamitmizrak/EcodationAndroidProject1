package hamitmizrak.com.ecodationandroid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class TelephoneValidation2 extends AppCompatActivity {
    //global Variable
    EditText phoneNumberEditTextId2;
    EditText phoneCodeEditTextId2;
    Button confirmationButtonId2;

    //Firebase Auth
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    //callback data
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callBackData;

    //Doğrulama işlemi
    String recognationId;

    //VerificationTelephoneMethod
    public void verifactionTelephone(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumberEditTextId2.getText().toString(),60, TimeUnit.SECONDS,this,callBackData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_validation2);
        //start codes

        //id
        phoneNumberEditTextId2=findViewById(R.id.phoneNumberEditTextId2);
        phoneCodeEditTextId2=findViewById(R.id.phoneCodeEditTextId2);
        confirmationButtonId2=findViewById(R.id.confirmationButtonId2);

        //Gorunmez: invisibility ==> phoneCodeEditTextId2
        phoneCodeEditTextId2.setVisibility(View.INVISIBLE);

        //callBackData start
        callBackData=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            //ben ekledim
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
            }
        };

        //callBackData end

        //end codes
    }// end onCreate
}//end TelephoneValidation2