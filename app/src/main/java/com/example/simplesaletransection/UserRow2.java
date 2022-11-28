package com.example.simplesaletransection;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class UserRow2 extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private List<UserRow> userRowList = new ArrayList<>();
    RecyclerView rv;
    MenuView.ItemView delete_menu;


    /*public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_row2);

       /* menu1=findViewById(R.id.menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,RepairMenu2.class);

            }
        });*/


        rv = findViewById(R.id.rv);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navview);


        UserRow r1 = new UserRow();
        r1.setTitle("Emailid");
        r1.setTitle1("Username");
        r1.setSubTitle("Password");
        r1.setId(1);

        userRowList.add(r1);
        showRepairListView(userRowList);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu2:
                        Toast.makeText(getApplicationContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                        //close drawer
//                        Intent intent1 = new Intent(getApplicationContext(), RepairMenu2.class);
//                        startActivity(intent1);
//                        break;


                }
                return true;
            }
        });

    }

    public void showRepairListView(List<UserRow> menuList) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        RepairMenuAdapter repairMenuAdapter = new RepairMenuAdapter(new RepairMenuAdapter.OnItemClickListener() {
            @Override
            public void onUserItemClicked(UserRow userRow) {
                switch (userRow.getTitle()) {
//                    case "Refund":
//                        Intent intent1 = new Intent(getApplicationContext(), RepairMenu3.class);
//                        startActivity(intent1);
//                        break;
//                    case "Sale":
//                        Intent intent2 = new Intent(getApplicationContext(),RepairMenu2.class);
//                        startActivity(intent2);
//                        break;
                }
            }

        });
        repairMenuAdapter.setData(userRowList);
        rv.setAdapter(repairMenuAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu2) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu2);
        return true;
    }}
