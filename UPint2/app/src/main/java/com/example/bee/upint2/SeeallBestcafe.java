package com.example.bee.upint2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.bee.upint2.adapter.MyRecyclerViewAdapter;
import com.example.bee.upint2.adapter.RecyclerViewClickListener;

import java.util.ArrayList;

public class SeeallBestcafe extends AppCompatActivity implements RecyclerViewClickListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ActionMenuView amvMenu;
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeall_bestcafe);


        ArrayList<String> CafeList = new ArrayList<>();
        CafeList.add("Sweet Stories");
        CafeList.add("Damnoen Saduak");
        CafeList.add("Chalatte");
        ArrayList<String> Cafeimage_path = new ArrayList<>();
        Cafeimage_path.add("http://localhost/UPint/public/images/uploads/Cafe_image/Sweet_Stories.jpg");
        Cafeimage_path.add("http://localhost/UPint/public/images/uploads/Cafe_image/DNSD.jpg");
        Cafeimage_path.add("http://localhost/UPint/public/images/uploads/Cafe_image/Chalatte_Chaingmai.jpg");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        amvMenu = (ActionMenuView) toolbar.findViewById(R.id.amvMenu);
        amvMenu.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(Html.fromHtml("<<font color='#ffffff'><i>All Best Cafe in Chiang Mai<i></font>"));
        Drawable drawable= getResources().getDrawable(R.drawable.ic_arrow_back_white);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 75, 75, true));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MyRecyclerViewAdapter(CafeList,Cafeimage_path, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        v.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in));
        adapter = new MyRecyclerViewAdapter(getApplicationContext(), this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.back_menu, amvMenu.getMenu());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
