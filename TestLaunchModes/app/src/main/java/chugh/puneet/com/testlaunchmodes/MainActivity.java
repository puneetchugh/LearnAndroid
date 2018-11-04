package chugh.puneet.com.testlaunchmodes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.main_button_single_instance);
        Button button2 = (Button) findViewById(R.id.main_button_single_task);
        Button button3 = (Button) findViewById(R.id.main_single_top);
        Button button4 = (Button) findViewById(R.id.main_standard);

        button1.setOnClickListener( (View view) -> {
                    startActivity(new Intent(getBaseContext(), SingleInstance.class));
                }
        );

        button2.setOnClickListener( (View view) -> {
                    startActivity(new Intent(getBaseContext(), SingleTask.class));
                }
        );

        button3.setOnClickListener( (View view) -> {
                    startActivity(new Intent(getBaseContext(), SingleTop.class));
                }
        );
        button4.setOnClickListener( (View view) -> {
                    startActivity(new Intent(getBaseContext(), Standard.class));
                }
        );
    }
}
