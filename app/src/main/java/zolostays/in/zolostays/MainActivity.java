package zolostays.in.zolostays;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by gulshan on 15/07/17.
 */


public class MainActivity extends AppCompatActivity {


    private static String PATH = "";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<ListBean> itemLisTArray = new ArrayList<>();
    ListBean listBean;
    HashMap<String, Integer> tidPosition = new HashMap<String, Integer>();

    WebProgressprecentReceiver progressreceiver;
    IntentFilter progressfilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        progressfilter = new IntentFilter(WebProgressprecentReceiver.PROCESS_PROGRESS_RESPONSE);
        progressfilter.addCategory(Intent.CATEGORY_DEFAULT);
        progressreceiver = new WebProgressprecentReceiver();
        registerReceiver(progressreceiver, progressfilter);

        if (itemLisTArray.size() > 0) {

            int flag = 1;
            for (ListBean data : itemLisTArray) {
                tidPosition.put(data.getId(), flag - 1);

            }
        }

        mAdapter = new RecyclerViewAdapter(MainActivity.this, itemLisTArray);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareItemData();

    }

    private void prepareItemData() {

        listBean = new ListBean("100", "Naruto Shippuden", "http://wallpapercave.com/wp/wbnlpSz.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("101", "Goku", "http://wallpapercave.com/wp/n87jcGU.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("102", "Pikachu", "http://wallpapercave.com/wp/k1ExYwj.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("103", "Fullmetal Alchemist", "http://wallpapercave.com/wp/SOJBzJz.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("104", "Code Geass", "http://wallpapercave.com/wp/11QcXv7.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("105", "Death Note: Desu nôto", "http://wallpapercave.com/wp/gBekIXF.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("106", "Teen Wolf", "http://wallpapercave.com/wp/wp1810568.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("107", "Arrow", "http://wallpapercave.com/wp/IHDAMSM.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("108", "The flash", "http://wallpapercave.com/wp/wp1829245.jpg");
        itemLisTArray.add(listBean);


        listBean = new ListBean("109", "Spider Man", "http://wallpapercave.com/wp/wp2006935.jpg");
        itemLisTArray.add(listBean);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private List<ListBean> itemList;
        Context context;

        public ListBean getItem(int position) {

            return itemList.get(position);
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView Name;
            public ImageView image;
            public ImageView icon;
            // public ProgressBar progressBar;


            public MyViewHolder(View view) {
                super(view);
                Name = (TextView) view.findViewById(R.id.name);
                image = (ImageView) view.findViewById(R.id.title_img);
                icon = (ImageView) view.findViewById(R.id.icon);
                //progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            }
        }


        public RecyclerViewAdapter(Context ctx, List<ListBean> itemLists) {
            this.itemList = itemLists;
            this.context = ctx;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_items, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final ListBean itembean = itemList.get(position);
            Bitmap noimg = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_no_image);
            holder.Name.setText(itembean.getTitle());
            holder.image.setImageBitmap(noimg);
            File file = new File(getApplicationContext().getExternalFilesDir("Zolo"), "/image/");
            String filepath = file.getAbsolutePath() + "/" + itembean.getTitle() + itembean.getId() + position;
            // File file = new File(context.getExternalFilesDir("Zolo"), "/image/");
            Bitmap bitmap = BitmapFactory.decodeFile(filepath);

            if (bitmap != null) {
                holder.image.setImageBitmap(bitmap);
                holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete));
                holder.icon.setVisibility(View.GONE);
            } else {
                holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_download));
                holder.image.setImageBitmap(noimg);
                holder.icon.setVisibility(View.VISIBLE);

            }


            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            int w = holder.image.getWidth();
            int h = (int) (width / 1.77777778);
            // Log.e("hilength", "Height: " + h + " Width: " + w);
            holder.image.requestLayout();
            holder.image.getLayoutParams().height = h;

            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Constant.isOnline(context)) {
                        Intent i11 = new Intent(context, ImageViewDownloadService.class);
                        i11.putExtra("tid", itembean.getId());
                        i11.putExtra("url", itembean.getUrl());
                        i11.putExtra("name", String.valueOf(itembean.getTitle()));
                        i11.putExtra("pos", String.valueOf(position));
                        context.startService(i11);

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Check Internet connectivity.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


        @Override
        public int getItemCount() {
            return itemList.size();
        }

    }


    public class WebProgressprecentReceiver extends BroadcastReceiver {

        protected static final String PROCESS_PROGRESS_RESPONSE = "in.zolo.intent.action.DATA_PROCESS_IMAGE_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                if (!intent.getStringExtra("message").isEmpty())
                    Toast.makeText(getApplicationContext(), intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
                if (intent.getStringExtra("message").equals("Image downloaded."))
                    mAdapter.notifyDataSetChanged();


                if (recyclerView != null && Integer.parseInt(intent.getStringExtra("pos")) != -1
                        && tidPosition.get(intent.getStringExtra("position")) != null) {
                    View v = recyclerView.getChildAt(tidPosition.get(intent.getStringExtra("position")));
                    if (v != null) {
                        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
                        progressBar.setProgress(Integer.parseInt(intent.getStringExtra("fileProgress")));
                        
                    }

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (progressreceiver != null) {
            registerReceiver(progressreceiver, progressfilter);
        }
        mAdapter.notifyDataSetChanged();
        recyclerView.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressreceiver != null) {
            unregisterReceiver(progressreceiver);
        }
    }
}
