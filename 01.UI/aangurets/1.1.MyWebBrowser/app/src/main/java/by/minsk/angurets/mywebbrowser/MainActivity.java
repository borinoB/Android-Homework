package by.minsk.angurets.mywebbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by aangurets on 07.02.2015.
 */

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText mAdress;
    public static final String ADRESS = "adress";
    private Button mButtonGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mAdress = (EditText) findViewById(R.id.entered_adress);
        mButtonGo = (Button) findViewById(R.id.go_button);

        mButtonGo.setOnClickListener(this);
    }

    public void onShow(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.show, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra(ADRESS, mAdress.getText().toString());
        startActivity(intent);
        onShow(view);
    }
}
