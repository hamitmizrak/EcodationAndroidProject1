package hamitmizrak.com.ecodationandroid1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    //common variable
    Button loginButton;

    //Firebase Auth
    FirebaseAuth firebaseAuth;

    //user bilgisini almak
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //start codes

        //FirebaseAuth instance
        firebaseAuth=FirebaseAuth.getInstance();

        //sistemdeki kullanıcıyı almak
        user=firebaseAuth.getCurrentUser();

        //eğer kullanıcı sisteme giriş yapmışsa ana sayfaya yönlendirsin yoksa login sayfasına yönlendirsin
        loginButton=findViewById(R.id.isLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //eğer sistemde kullanıcı varsa secret yani sadece login olabilenlerin sayfasına yönlendir
                if(user!=null){
                    Intent adminPageActivityIndent=new Intent(getApplicationContext(),AdminActivity.class);
                    startActivity(adminPageActivityIndent);
                }else{ //eğer sistemde kullanıcı yoksa kayıt sayfasına yönlendir
                    Intent loginIntent=new Intent(getApplicationContext(),TelephoneValidation.class);
                    Toast.makeText(LoginActivity.this, "Login Sayfasına Yönlendiriliyorsunuz", Toast.LENGTH_SHORT).show();
                    startActivity(loginIntent);
                }
            }//end onClick
        }); //setOnClickListener ends code
    }// end onCreate
}//end LoginActivity