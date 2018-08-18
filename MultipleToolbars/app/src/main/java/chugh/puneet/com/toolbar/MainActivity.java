package chugh.puneet.com.toolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mytoolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);

        Toolbar myAnotherToolbar = findViewById(R.id.my_another_toolbar);
        myAnotherToolbar.setTitle("Puneet The Chugh");
        setSupportActionBar(myAnotherToolbar);

    }
}
