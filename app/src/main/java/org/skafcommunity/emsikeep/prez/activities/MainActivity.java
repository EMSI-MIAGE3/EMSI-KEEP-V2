package org.skafcommunity.emsikeep.prez.activities;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.skafcommunity.emsikeep.R;
import org.skafcommunity.emsikeep.models.User;
import org.skafcommunity.emsikeep.models.UserRole;
import org.skafcommunity.emsikeep.prez.fragments.ManageAdministratorFragment;
import org.skafcommunity.emsikeep.prez.fragments.ManageProfessorFragment;
import org.skafcommunity.emsikeep.prez.fragments.ManageSchoolClassesFragment;
import org.skafcommunity.emsikeep.prez.fragments.ManageStudentFragment;
import org.skafcommunity.emsikeep.prez.fragments.ProfileFragment;
import org.skafcommunity.emsikeep.staticDATA.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ManageProfessorFragment.OnFragmentInteractionListener,
        ManageAdministratorFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        ManageStudentFragment.OnFragmentInteractionListener,
        ManageSchoolClassesFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomAppBar bottomAppBar = findViewById(R.id.bar);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, bottomAppBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView appLogo = findViewById(R.id.top_bar_logo_img);
        appLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        ImageView topBarSearch = findViewById(R.id.top_bar_search_img);
        topBarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch(true);
            }
        });

        ImageView topBarSearchBack = findViewById(R.id.top_bar_search_back_img);
        topBarSearchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSearch(false);
            }
        });

        Fragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        super.onStart();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.getMenu().clear();
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser.getRole().equals(UserRole.superadministrator)) {
            navigationView.inflateMenu(R.menu.activity_super_admin_drawer);
        } else if (currentUser.getRole().equals(UserRole.administrator)) {
            navigationView.inflateMenu(R.menu.activity_admin_drawer);
        } else if (currentUser.getRole().equals(UserRole.professor)) {
            navigationView.inflateMenu(R.menu.activity_professor_drawer);
        } else if (currentUser.getRole().equals(UserRole.student)) {
            navigationView.inflateMenu(R.menu.activity_student_drawer);
        } else {
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        }
        TextView fullNameTextView = headerView.findViewById(R.id.current_user_fullname);

        fullNameTextView.setText(currentUser.getFirstName() + " " + currentUser.getLastName());

        TextView emailTextView = headerView.findViewById(R.id.current_user_email);
        emailTextView.setText(currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        else if(id == R.id.action_search){
            onClickSearch(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        User currentUser = Session.getInstance().getCurrentUser();
        if (currentUser.getRole().equals(UserRole.superadministrator)) {
            fragment = onNavigationItemSelectedSuperAdmin(id);
        }


        if(fragment != null){
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private Fragment onNavigationItemSelectedSuperAdmin(int id){
        Class fragmentClass = null;
        Fragment fragment = null;

        if (id == R.id.nav_admins) {
            fragmentClass = ManageAdministratorFragment.class;
        } else if (id == R.id.nav_professors) {
            fragmentClass = ManageProfessorFragment.class;
        } else if (id == R.id.nav_students) {
            fragmentClass = ManageStudentFragment.class;
        } else if (id == R.id.nav_school_class) {
            fragmentClass = ManageSchoolClassesFragment.class;
        } else if (id == R.id.nav_places) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_profil) {
            fragmentClass = ProfileFragment.class;

        } else if (id == R.id.nav_out) {

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }


    private void onClickSearch(boolean isPerform){
        RelativeLayout searchContainer = findViewById(R.id.top_bar_search_container);
        RelativeLayout titleContainer = findViewById(R.id.top_bar_title_container);
        EditText searchEditText = findViewById(R.id.search_edit_text);
        if (isPerform){
            searchContainer.setVisibility(View.VISIBLE);
            titleContainer.setVisibility(View.INVISIBLE);
            searchEditText.requestFocus();
        }
        else {
            searchContainer.setVisibility(View.INVISIBLE);
            titleContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
