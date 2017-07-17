package zolostays.in.zolostays;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static android.R.id.input;

/**
 * Created by gulshan on 15/07/17.
 */

public class ImageViewDownloadService  extends IntentService{


    private String clickedItemId="" ;
    private String url="" ;
    private String pos="" ;
    private String name="" ;

    public ImageViewDownloadService() {
        super(ImageViewDownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        clickedItemId = intent.getStringExtra("tid");
        url = intent.getStringExtra("url");
        pos = intent.getStringExtra("pos");
        name = intent.getStringExtra("name");

        try {
            if (Constant.isOnline(getApplicationContext())) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        startDownloadImage(url ,clickedItemId , pos);

                    }


                }).start();
            }

        }catch (Exception ex){

            Log.i("imageTry" , ex.getMessage());
        }

    }


    private void startDownloadImage(String urlpath ,String clickedItemId , String pos)  {

        try{


            int lenghtOfFile = 0;
            HttpGet httpPost = new HttpGet();
            URI website = new URI(urlpath.trim());
            httpPost.setURI(website);

            HttpClient client = new DefaultHttpClient();

            HttpResponse httpResponse;

            httpResponse = client.execute(httpPost);

            int responseCode = httpResponse.getStatusLine().getStatusCode();
            String message = httpResponse.getStatusLine().getReasonPhrase();
            HttpEntity entity = httpResponse.getEntity();
            String imgResponseCode = String.valueOf(responseCode);

            String app_name =  name + clickedItemId + pos ;
            if (entity != null) {

                lenghtOfFile = (int) entity.getContentLength();
                System.out.println("Lenght of file: " + lenghtOfFile);

                File file = new File(getExternalFilesDir("Zolo"), "/image/");


                if (!file.exists()) {
                    file.mkdirs();
                }

                File outputFile = new File(file, app_name);
                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = entity.getContent();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    total += 1024;
                    if (lenghtOfFile > 0){
                        //publishProgress((int) ((total * 100) / lenghtOfFile));
                        int progress = (int) ((total * 100) / lenghtOfFile);
                        imagedownlodRecevier(progress,false,"");
                    }else{
                        //publishProgress((int) ((total)));
                        int progress = (int) ((total));
                        imagedownlodRecevier(progress,false,"");
                    }
                    fos.write(buffer, 0, len1); // Write In
                    // FileOutputStream.
                }
                imagedownlodRecevier(100,false,"");

                fos.close();
                is.close();
            }

            if (imgResponseCode.equals(Status.SUCCESS)){
                imagedownlodRecevier(100,true,"Image downloaded.");
            }else {
                imagedownlodRecevier(0,false,"Network error. Image not downloaded.");
            }


        }catch(Exception e){
           // imagedownlodRecevier(-1,true, String.valueOf(e.getMessage()),false);
            imagedownlodRecevier(0,false,"Network error. Image not downloaded. ");
        }



    }

    private void imagedownlodRecevier(int progressCount,boolean downloaded,String message) {


        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra("fromService", true);
        broadcastIntent.putExtra("pos", pos);
        broadcastIntent.putExtra("position", clickedItemId);
        broadcastIntent.putExtra("fileDownloaded",downloaded);
        broadcastIntent.putExtra("fileProgress",String.valueOf(progressCount));
        broadcastIntent.putExtra("message", message);
        broadcastIntent.setAction("in.zolo.intent.action.DATA_PROCESS_IMAGE_RESPONSE");
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
    }



}
