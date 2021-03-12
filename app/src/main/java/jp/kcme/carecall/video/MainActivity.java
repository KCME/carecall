package jp.kcme.carecall.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CALL = 1;
    private static final int REQUEST_CODE_VIDEO_CALL = 2;

    private static final String[] PERMISSION_FOR_CALL = {Manifest.permission.CALL_PHONE};
    private static final String[] PERMISSION_FOR_VIDEO_CALL = {Manifest.permission.CALL_PHONE,};

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setClassName("jp.co.softfront.livytalk", "jp.co.softfront.livytalk.MainActivity");

        makeVideoCall();

        finish();
    }

    public void makeCall() {

        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:399005"));


        if (checkSelfPermission(PERMISSION_FOR_CALL)) {
            sendBroadcast(callIntent);
        } else {
            requestPermission(PERMISSION_FOR_CALL, REQUEST_CODE_CALL);
        }
    }
    public void makeVideoCall() {

        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:399005"));
        callIntent.setClassName("jp.kcme.shiraseai", "jp.kcme.shiraseai.VideoActivity");
        if (checkSelfPermission(PERMISSION_FOR_VIDEO_CALL)) {
            startActivity(callIntent);
        } else {
            requestPermission(PERMISSION_FOR_VIDEO_CALL, REQUEST_CODE_VIDEO_CALL);
        }
    }
    private boolean checkSelfPermission(String[] permissions) {
        for (String s : permissions) {
            if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }
    private void requestPermission(String[] permissions, int requestCode) {
        for (String s : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, s)) {
                ActivityCompat.requestPermissions(this, new String[]{s}, requestCode);
            } else
                ActivityCompat.requestPermissions(this, new String[]{s}, requestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults.length > 0) {
            for (int i : grantResults)
                if (i != PackageManager.PERMISSION_GRANTED)
                    return;
        } else {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_CALL:
                makeCall();
                break;
            case REQUEST_CODE_VIDEO_CALL:
                makeVideoCall();
                break;
        }
    }
}
