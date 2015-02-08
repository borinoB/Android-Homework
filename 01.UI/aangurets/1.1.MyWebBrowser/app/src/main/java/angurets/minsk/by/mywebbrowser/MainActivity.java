package angurets.minsk.by.mywebbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by aangurets on 07.02.2015.
 */

public class MainActivity extends Activity {

    private EditText mAdress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mAdress = (EditText) findViewById(R.id.adress);
    }

    public void goTo(View view) {
        Intent intent = new Intent(getApplicationContext(), BrowserActivity.class);
        intent.putExtra("adress", mAdress.getText().toString());
        startActivity(intent);
        onShow(view);
    }

    public void onShow(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.show, Toast.LENGTH_SHORT);
        toast.show();
    }
}
