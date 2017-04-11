package io.ray.hexis;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.QuadrantItemReader;
import io.ray.hexis.util.SqlLiteHelper;
import io.ray.hexis.view.MatrixFragment;
import io.ray.hexis.view.abs.IMatrixFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private FragmentManager fm;
  private SqlLiteHelper sqlHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Get current view to pass to navigation adapter object
    //View v = findViewById(android.R.id.content);

    // Initialize toolbar object
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //Initialize navigation Adapter to handle item selection
    //NavigationAdapter navAdapter = new NavigationAdapter(v);

    // Initialize drawer layout object
    // This adds the three horizontal navigation drawer bars in the toolbar
    //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //ActionBarDrawerToggle toggle =
    //    new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
    //toggle.syncState();

    // Initialize the SQL Lite Helper
    sqlHelper = new SqlLiteHelper(this);

    // Get an instance of a QuadrantReader
    QuadrantItemReader quadReader = new QuadrantItemReader(sqlHelper);

    // List of Lists. This is used for reading the data.
    List<List<QuadrantItem>> metaList = new ArrayList<>();

    // Iterate through and read each quadrant's data
    for (int i = 0; i < 4; i++) {
      metaList.add(quadReader.getItemsTextByQuadrant(0, i));
    }

    // Initiate the default matrix
    IMatrixFragment matrix = MatrixFragment.newInstance(metaList);

    fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(R.id.container_fragment, matrix.toFragment(), "0");
    ft.commit();
  }

  // Close navigation drawer when back button is pressed
  @Override
  public void onBackPressed() {
    /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }*/
  }
}
