package jswadia.msse.asu.edu.booksie;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {

    //public String usr;
    private EditText usernm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernm = (EditText)findViewById(R.id.uname);
        //usr = username.getText().toString();
        //usr = username.getText().toString();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loginClicked(View view)
    {
        Log.d("LoginActivity", "login clicked");
       String usr = usernm.getText().toString();
        Intent i = new Intent(LoginActivity.this, MoodActivity.class);
        i.putExtra("username", usr);
        startActivity(i);

        Toast t = Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT);
        t.show();
        //LoginActivity.this.finish();

    }

    public void signupClicked(View view)
    {
        Log.d("LoginActivity", "sign up clicked");
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
        LoginActivity.this.finish();

    }
}
