package org.gian.gian;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    ImageView imgvw;
    ProgressBar superprgbar;
    WebView wbv;
    private static int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_CODE);

        superprgbar = (ProgressBar) findViewById(R.id.Myprgbar);
        imgvw = (ImageView) findViewById(R.id.Myimgview);
        wbv = (WebView) findViewById(R.id.wbview);

        superprgbar.setMax(100);

        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }
        assert wbv != null;

        WebSettings webSettings = wbv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSettings.setMixedContentMode(0);
            wbv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 19) {
            wbv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < 19) {
            wbv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        wbv.setWebViewClient(new Callback());


        wbv.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfHloDFJwyqv2V7EdtmPdIuDrQxaSTGRsd6fxidFw2P6vC7_Q/viewform");
        wbv.setWebViewClient(new WebViewClient());
        wbv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                superprgbar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                imgvw.setImageBitmap(icon);
            }
        });

        wbv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                DownloadManager.Request myRequest = new DownloadManager.Request(Uri.parse(url));

                myRequest.allowScanningByMediaScanner();

                myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String fileName = url.substring(url.lastIndexOf('/') + 1);
                //URLUtil.guessFileName(url, null, null)
                myRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                DownloadManager myDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                myDownloadManager.enqueue(myRequest);
                Toast.makeText(HomeActivity.this, "Your file is Downloading...", Toast.LENGTH_SHORT).show();
            }
        });
            }
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.super_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }
    public class Callback extends WebViewClient {
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.menu_about:
                onAboutPressed();
                break;
            case R.id.menu_reload:
                wbv.reload();
                break;
            case R.id.menu_exit:
                onExitPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onAboutPressed()
    {
        Intent abt = new Intent(HomeActivity.this,AboutActivity.class);
        startActivity(abt);
    }

    public void onExitPressed()
    {
        final AlertDialog.Builder builder =new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Are you sure want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public void onBkPressed() {
        if(wbv.canGoBack())
        {
            wbv.goBack();
        }
        else
        {
            Toast.makeText(this, "Can't go Back!", Toast.LENGTH_SHORT).show();
            //super.onBackPressed();
        }
    }

}
