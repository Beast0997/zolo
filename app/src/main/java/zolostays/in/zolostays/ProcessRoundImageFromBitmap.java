package zolostays.in.zolostays;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

/**
 * Created by gulshan on 15/07/17.
 */

class ProcessRoundImageFromBitmap {

    static Context context;
    // private LruCache<String, Bitmap> memoryCache;
    ExecutorService executorService;
    MemoryCache memoryCache = new MemoryCache();

    private Map<ImageView, String> imageViews = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>());

    public ProcessRoundImageFromBitmap(Context context,String nid,String file,ImageView imagView) {
        this.context = context;
        //executorService = Executors.newFixedThreadPool(5);
        // Get memory class of this device, exceeding this amount will throw an
        // OutOfMemory exception.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;
        // System.out.println("cache>>"+cacheSize);

       /* memoryCache = new LruCache<String,Bitmap>(cacheSize) {

            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number
                // of items.
                return bitmap.getByteCount();
            }

        };*/
        if(cacheSize>100){
            loadBitmap(nid,file,imagView);
        }
    }
    public void loadBitmap(String nid,String file, ImageView imageView) {
//        if (cancelPotentialWork(resId, imageView)) {
//            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
//           // imageView.setBackgroundResource(R.drawable.empty_photo);
//            task.execute(resId);
//        }
        imageViews.put(imageView, nid);
        if (getBitmapFromMemCache(nid) == null) {
            // final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            // imageView.setBackgroundResource(R.drawable.empty_photo);
            // task.execute(file,nid);
            queuePhoto(nid,file, imageView);
            //System.out.println("queue>>>"+nid);
        }else{
            imageView.setImageBitmap(getBitmapFromMemCache(nid));
        }
    }

/*static class AsyncDrawable extends BitmapDrawable {
    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

    public AsyncDrawable(Resources res, Bitmap bitmap,
            BitmapWorkerTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
                bitmapWorkerTask);
    }

    public BitmapWorkerTask getBitmapWorkerTask() {
        return bitmapWorkerTaskReference.get();
    }
}

public static boolean cancelPotentialWork(String data, ImageView imageView) {
    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

    if (bitmapWorkerTask != null) {
        final int bitmapData = bitmapWorkerTask.data;
        if (bitmapData != data) {
            // Cancel previous task
            bitmapWorkerTask.cancel(true);
        } else {
            // The same work is already in progress
            return false;
        }
    }
    // No task associated with the ImageView, or an existing task was
    // cancelled
    return true;
}

private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
    if (imageView != null) {
        final Drawable drawable = imageView.getDrawable();
        if (drawable instanceof AsyncDrawable) {
            final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
            return asyncDrawable.getBitmapWorkerTask();
        }
    }
    return null;
}*/

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) memoryCache.get(key);
    }

    boolean imageViewReused(WeakReference<ImageView> imageViewReference) {
        String tag = imageViews.get(imageViewReference.get());
        if (tag == null || !tag.equals(imageViewReference.get()))
            return true;
        return false;
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        public String data = null;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage
            // collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {

            data = params[0];
            final Bitmap bitmap = decodeBitmapPath(
                    data, 200, 200);
            if(bitmap!=null)
                addBitmapToMemoryCache(params[1], bitmap);
            return bitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public static Bitmap decodeBitmapPath(String path, int width, int height) {
        Bitmap bmp = null;
        try {
            final BitmapFactory.Options ourOption = new BitmapFactory.Options();
            ourOption.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, ourOption);

            ourOption.inSampleSize = calculateInSampleSize(ourOption, width,
                    height);
            // Decode bitmap with inSampleSize set

            ourOption.inJustDecodeBounds = false;
            ourOption.inPreferredConfig = Bitmap.Config.RGB_565;
            ourOption.inDither = true;
            bmp = BitmapFactory.decodeFile(path, ourOption);
            bmp = setRoundcornerImage(bmp ,2,2);
		/*Matrix matrix = new Matrix();
		matrix.setRotate(rotationAngle, (float) bmp.getWidth() / 2,
				(float) bmp.getHeight() / 2);
		bmp = Bitmap.createBitmap(bmp, 0, 0, ourOption.outWidth,
				ourOption.outHeight, matrix, true);*/
            // Bitmap.crea
        } catch (Exception e) {
            Bitmap bitmap = null;

            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_no_image);
            bitmap = setRoundcornerImage(bitmap,2,2);
        }
        return bmp;
    }

    public static int calculateInSampleSize(BitmapFactory.Options ourOption,
                                            int imageWidth, int imageHeight) {
        final int height = ourOption.outHeight;
        final int width = ourOption.outWidth;
        int inSampleSize = 1;
        if (height > imageHeight || width > imageWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > imageHeight
                    && (halfWidth / inSampleSize) > imageWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    private void queuePhoto(String url, String file,ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url,file, imageView);

        //executorService.submit(new PhotosLoader(p));
        new PhotosLoader(p).run();

    }

    //Task for the queue
    private class PhotoToLoad {
        public String nid,file;
        public ImageView imageView;

        public PhotoToLoad(String u,String f, ImageView i) {
            nid = u;
            file = f;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {

            if (imageViewReused(photoToLoad))
                return;
            //Bitmap bmp = getBitmap(photoToLoad.url, photoToLoad.display_size);

            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = decodeBitmapPath(photoToLoad.file, 220, 220);
            if(bmp!=null){
                memoryCache.put(photoToLoad.nid, bmp);
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                Activity a = (Activity) photoToLoad.imageView.getContext();
                a.runOnUiThread(bd);

            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.nid))
            return true;
        return false;
    }

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);

        }
    }

    public void clearCache() {
        memoryCache.clear();
        //fileCache.clear();
    }

    private static Bitmap setRoundcornerImage(Bitmap bitmap, int cornerDips, int borderDips) {
        // TODO Auto-generated method stub
        Bitmap output = null;
        if (bitmap != null) {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int borderSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) borderDips,
                context.getResources().getDisplayMetrics());
        final int cornerSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (float) cornerDips,
                context.getResources().getDisplayMetrics());
        final Paint paint = new Paint();
        final Rect rect = new Rect(15, 15, bitmap.getWidth()-7, bitmap.getHeight()-6);
        //final Rect rect = new Rect(left, top, right, bottom)
        final RectF rectF = new RectF(rect);

        // prepare canvas for transfer
        paint.setAntiAlias(true);
        paint.setColor(0xFFFFFFFF);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

        // draw bitmap
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        // draw border
        int color = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) borderSizePx);
        canvas.drawRoundRect(rectF, cornerSizePx, cornerSizePx, paint);

        return output;

    }
}
