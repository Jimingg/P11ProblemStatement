package sg.edu.rp.c346.p11problemstatement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> aa;
    String currentTitle;
    Button btnedit;
    ActionBar ab;
    private DrawerItem[] drawerItem;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    private String m_Text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);
        drawerItem = new DrawerItem[4];
        btnedit = findViewById(R.id.buttonEdit);
        drawerItem[0] = new DrawerItem(R.drawable.ic_info_white_24dp, "Bio");
        drawerItem[1] = new DrawerItem(R.drawable.ic_linear_scale_white_24dp, "Vaccination");
        drawerItem[2] = new DrawerItem(R.drawable.ic_perm_contact_calendar_white_24dp, "Anniversary");
        drawerItem[3] = new DrawerItem(R.drawable.ic_stars_white_24dp, "About Us");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        ab = getSupportActionBar();
        DrawerAdapter aa = new DrawerAdapter(this, R.layout.rows, drawerItem);

        drawerList.setAdapter(aa);

        // Set the list's click listener
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int
                    position, long arg3) {

                Intent i = new Intent(getBaseContext(), AboutUs.class);
                if (position == 3){
                    startActivity(i);
                }else {
                    Fragment fragment = null;
                    if (position == 0)
                        fragment = new BioFragment();
                    else if (position == 1)
                        fragment = new VaccinationFragment();
                    else if (position == 2)
                        fragment = new AnniversaryFragment();



                    FragmentManager fm = getSupportFragmentManager();

                    FragmentTransaction trans = fm.beginTransaction();
                    trans.replace(R.id.content_frame, fragment);

                    trans.commit();

                    // Highlight the selected item,
                    //  update the title, and close the drawer
                    drawerList.setItemChecked(position, true);
                    drawerList.setSelection(position);
                    ab.setTitle(currentTitle);
                    drawerLayout.closeDrawer(drawerList);
                }

            }
        });
        currentTitle = this.getTitle().toString();

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            /** Would be called when a drawer has completely closed */
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ab.setTitle(currentTitle);
            }

            /** Would be called when a drawer has completely open */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ab.setTitle("Make a selection");
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(drawerToggle);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync toggle state so the indicator is shown properly.
        //  Have to call in onPostCreate()
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

}
