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


       /* try {
            BufferedInputStream inputfile;
            Long downloadedSize = null;
            long fileLength;
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            File file = new File(getExternalFilesDir("Zolo"), "/Image");
            RandomAccessFile output;

            if (file.exists()){
                System.out.print("progressCount file" + String.valueOf(file.exists()));
                connection.setAllowUserInteraction(true);
                connection.setRequestProperty("Range", "bytes=" + file.length() + "-");
            }else{
                System.out.print("progressCount file" + "No");
            }

            connection.setConnectTimeout(14000);
            connection.setReadTimeout(20000);
            connection.connect();
            System.out.print("progressCount downloadedSize" + connection.getResponseCode());
            System.out.print("progressCount downloadedSize" + connection.getResponseCode());
            if (connection.getResponseCode() / 100 != 2)
                throw new Exception("Invalid response code!");

            else {
                String connectionField = connection.getHeaderField("content-range");


                if (connectionField != null){
                    String[] connectionRanges = connectionField.substring("bytes=".length()).split("-");
                    downloadedSize = Long.valueOf(connectionRanges[0]);
                    System.out.print("progressCount downloadedSize" + String.valueOf(downloadedSize));
                }

                if (connectionField == null && file.exists())
                    file.delete();

                fileLength = connection.getContentLength();

                imagedownlodRecevier(fileLength,true , 0);
                inputfile = new BufferedInputStream(connection.getInputStream());
                output = new RandomAccessFile(file, "rw");
                output.seek(fileLength);
                System.out.print("progressCount downloadedSize" + downloadedSize);
                if (!file.exists()) {
                    file.mkdirs();
                }



                byte data[] = new byte[1024];
                int count = 0;
                int __progress = 0;

                while ((count = inputfile.read(data, 0, 1024)) != -1
                        && __progress != 100) {
                    downloadedSize += count;
                    output.write(data, 0, count);
                    __progress = (int) ((downloadedSize * 100) / fileLength);
                    System.out.print("progressCount " + __progress);
                    imagedownlodRecevier(fileLength,false, __progress);
                }

             *//*   byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    total += 1024;
                    if (lenghtOfFile > 0){
                        //publishProgress((int) ((total * 100) / lenghtOfFile));
                        int progress = (int) ((total * 100) / lenghtOfFile);
                        imagedownlodRecevier(progress,false,"",false);
                    }else{
                        //publishProgress((int) ((total)));
                        int progress = (int) ((total));
                        imagedownlodRecevier(progress,false,"",false);
                    }
                    fos.write(buffer, 0, len1); // Write In
                    // FileOutputStream.
                }*//*

                output.close();
                inputfile.close();
            }


        }catch (Exception e) {
            e.printStackTrace();
            System.out.printf("Exception", e.getMessage());

        }*/


        try{
            System.out.println("doin background" + urlpath);

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

            System.out.println("Lenght of file CODE: " + responseCode);
            System.out.println("Lenght of file Res val: " + message);
            String app_name =  name + clickedItemId + pos ;
            if (entity != null) {

                lenghtOfFile = (int) entity.getContentLength();
                System.out.println("Lenght of file: " + lenghtOfFile);

                File file = new File(getExternalFilesDir("Zolo"), "/image");


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
                        imagedownlodRecevier(progress,false,"",false);
                    }else{
                        //publishProgress((int) ((total)));
                        int progress = (int) ((total));
                        imagedownlodRecevier(progress,false,"",false);
                    }
                    fos.write(buffer, 0, len1); // Write In
                    // FileOutputStream.
                }
                imagedownlodRecevier(1500000,false,"",false);
                fos.flush();
                fos.close();
                is.close();
            }

            System.out.println("previous onpost");
            if (imgResponseCode.equals(Status.SUCCESS)) {
                imagedownlodRecevier(-1,true,"Image downloaded.",false);
            } else {
                imagedownlodRecevier(-1,true,"Network error. Image not downloaded. 111",false);

                //ToastUserMessage.message(this, "Network error. Training not downloaded.");

            }
            System.out.println("onpost");

        }catch(Exception e){
           // imagedownlodRecevier(-1,true, String.valueOf(e.getMessage()),false);
            imagedownlodRecevier(-1,true,"Network error. Image not downloaded.  2222",false);
        }



    }

    private void imagedownlodRecevier(int progressCount,boolean downloaded,String message,boolean fullyDownloade) {
        //System.out.println("train zip reciver invoke>>>");


        Intent broadcastIntent = new Intent();
        broadcastIntent.putExtra("fromService", true);
        broadcastIntent.putExtra("pos", pos);
        broadcastIntent.putExtra("position", clickedItemId);
        broadcastIntent.putExtra("fileDownloaded",downloaded);
        broadcastIntent.putExtra("fileProgress",String.valueOf(progressCount));
        broadcastIntent.putExtra("message", message);
        broadcastIntent.putExtra("fileUnZiped",fullyDownloade);
        broadcastIntent.setAction("in.zolo.intent.action.DATA_PROCESS_IMAGE_RESPONSE");
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);
    }



}
