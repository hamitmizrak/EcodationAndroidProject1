package hamitmizrak.com.ecodationandroid1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hamitmizrak.com.ecodationandroid1.ui.main.SectionsPagerAdapter;
import hamitmizrak.com.ecodationandroid1.databinding.ActivityLogin2Binding;

public class LoginActivity2 extends AppCompatActivity {
    //global variable
    Button isLogin2;

    //Firebase Auth
    FirebaseAuth firebaseAuth;

    //User Bilgisini almak
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //start codes

        //FirebaseAuth instance
        firebaseAuth=FirebaseAuth.getInstance();

        //sistemdeki user almak
        user=firebaseAuth.getCurrentUser();

        isLogin2=findViewById(R.id.isLogin2);
        isLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!=null){
                    Intent adminPageActivity2=new Intent(getApplicationContext(),AdminActivity2.class);
                    Toast.makeText(LoginActivity2.this, "Admin Activity 2 yönlendirildi", Toast.LENGTH_SHORT).show();
                    startActivity(adminPageActivity2);
                }else{
                   //Validation Telephone Activity
                    Intent telefonValidationPageActivity2=new Intent(getApplicationContext(),TelephoneValidation2.class);
                    Toast.makeText(LoginActivity2.this, "Telephone Validation2 2 yönlendirildi", Toast.LENGTH_SHORT).show();
                    startActivity(telefonValidationPageActivity2);
                }
            }
        });

        //end codes
    }
}