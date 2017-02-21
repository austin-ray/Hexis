package io.ray.hexis;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.ray.hexis.model.QuadrantItem;
import io.ray.hexis.util.SQLiteHelper;
import io.ray.hexis.model.abs.IMatrixModel;
import io.ray.hexis.model.abs.IQuadrantModel;
import io.ray.hexis.presenter.NavigationAdapter;
import io.ray.hexis.presenter.abs.IMatrixPresenter;
import io.ray.hexis.view.MatrixFragment;
import io.ray.hexis.view.abs.IMatrixFragment;
import io.ray.hexis.util.sql.QuadrantItemsContract.QuadrantItemsEntry;

public class MainActivity extends AppCompatActivity{

    private FragmentManager fm;
    private SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get current view to pass to navigation adapter object
        View v = findViewById(android.R.id.content);

        // Initialize toolbar object
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize navigation Adapter to handle item selection
        NavigationAdapter navAdapter = new NavigationAdapter(v);

        // Initialize drawer layout object
        // This adds the three horizontal navigation drawer bars in the toolbar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        toggle.syncState();

        sqLiteHelper = new SQLiteHelper(this);

        IMatrixFragment matrix = MatrixFragment.newInstance();
        //matrix.getPresenter().setQuadrantData(0, data);

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_fragment, matrix.toFragment(), "0");
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<QuadrantItem> data = readData(0, 0);

        IMatrixFragment frag = (IMatrixFragment) fm.findFragmentByTag("0");
        IMatrixPresenter pres = frag.getPresenter();

        pres.setQuadrantData(0, data);
    }

    // Close navigation drawer when back button is pressed
    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private List<QuadrantItem> readData(int goal, int quadrant) {
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();

        String[] projection = {
                QuadrantItemsEntry.COLUMN_NAME_ID,
                QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT,
                QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS
        };

        String selection = QuadrantItemsEntry.COLUMN_NAME_GOAL_ID + " = ? AND " +
                            QuadrantItemsEntry.COLUMN_NAME_QUADRANT + " = ?";

        String[] selectionArgs = {String.valueOf(goal), String.valueOf(quadrant)};

        String sortOrder = QuadrantItemsEntry.COLUMN_NAME_ID + " DESC";

        Cursor cursor = db.query(
                QuadrantItemsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List<QuadrantItem> items = new ArrayList<>();

        while (cursor.moveToNext()) {
            long id = cursor.getLong(
                    cursor.getColumnIndexOrThrow(QuadrantItemsEntry.COLUMN_NAME_ID));
            String msg = cursor.getString(
                    cursor.getColumnIndexOrThrow(QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT));

            int completion = cursor.getInt(
                    cursor.getColumnIndexOrThrow(QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS));

            items.add(new QuadrantItem(msg, id, completion));
        }

        return items;
    }

    private void saveData(IMatrixPresenter presenter, int goal) {
        IMatrixModel model = presenter.getModel();
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();

        for (int i = 0; i < 4; i++) {
            IQuadrantModel quadModel = model.getQuadrant(i);

            for (QuadrantItem quadrantItem : quadModel.getData()) {
                ContentValues values = new ContentValues();
                if (quadrantItem.getUID() != -1) {
                    values.put(QuadrantItemsEntry.COLUMN_NAME_ID, quadrantItem.getUID());
                }

                values.put(QuadrantItemsEntry.COLUMN_NAME_GOAL_ID, goal);
                values.put(QuadrantItemsEntry.COLUMN_NAME_ITEM_TEXT, quadrantItem.getMessage());
                values.put(QuadrantItemsEntry.COLUMN_NAME_QUADRANT, i);
                values.put(QuadrantItemsEntry.COLUMN_NAME_COMPLETION_STATUS,
                            quadrantItem.getCompletion());

                long UID = db.insert(QuadrantItemsEntry.TABLE_NAME, null, values);
                quadrantItem.setUID(UID);
            }
        }
    }

    @Override
    protected void onPause() {
        IMatrixFragment frag = (IMatrixFragment) fm.findFragmentByTag("0");
        IMatrixPresenter pres = frag.getPresenter();
        IMatrixModel model = pres.getModel();
        IQuadrantModel q = model.getQuadrant(0);
        List<QuadrantItem> list = q.getData();
        System.err.println(list.size());

        saveData(pres, 0);

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        IMatrixFragment frag = (IMatrixFragment) fm.findFragmentByTag("0");
        IMatrixPresenter pres = frag.getPresenter();
        saveData(pres, 0);
        super.onDestroy();
    }
}
