package jp.co.altec.openingactionsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends Activity {
    UdpConnection udp;
    int count = 0;
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            if(count/4 > 0) {
                udp.mDeviceInfo.setPoint(new Point("0", "0", String.valueOf(++count)));
            }
            udp.sendBroadcast();
            mHandler.postDelayed(mRunnable,300);
            count++;
        }
    };

    Switch mObserverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btn = (Button)findViewById(R.id.button);
        mObserverBtn = (Switch)findViewById(R.id.switchObs);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                udp = new UdpConnection(getApplicationContext(), ((EditText)findViewById(R.id.editText)).getText().toString());
                udp.receiveBroadcast();

                Log.d("DEBUG", "/// DATA CONNECTION ///");
                mHandler.postDelayed(mRunnable, 300);

                if (mObserverBtn.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), ObserverActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
}
