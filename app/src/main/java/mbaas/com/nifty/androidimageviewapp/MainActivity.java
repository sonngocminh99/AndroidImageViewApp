package mbaas.com.nifty.androidimageviewapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FetchFileCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBFile;
import com.nifty.cloud.mb.core.NCMBObject;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_RESULT = 0;
    Button _btnShow;
    ImageView _iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //**************** APIキーの設定 **************
        NCMB.initialize(this.getApplicationContext(),"YOUR_NCMB_APPLICATION_KEY",
                "YOUR_NCMB_CLIENT_KEY");

        setContentView(R.layout.activity_main);

        _btnShow = (Button) findViewById(R.id.btnShow);
        _iv  = (ImageView) findViewById(R.id.imgShow);
        _btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 画像ダウンロードする
                NCMBFile file = new NCMBFile("mBaaS_image.png");
                file.fetchInBackground(new FetchFileCallback() {
                    @Override
                    public void done(byte[] dataFetch, NCMBException er) {
                        if (er != null) {
                            //失敗処理
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Notification from Nifty")
                                    .setMessage("Error:" + er.getMessage())
                                    .setPositiveButton("OK", null)
                                    .show();
                        } else {
                            //成功処理
                            Bitmap bMap = BitmapFactory.decodeByteArray(dataFetch, 0, dataFetch.length);
                            _iv.setImageBitmap(bMap);
                        }
                    }
                });
            }
        });

    }
}
