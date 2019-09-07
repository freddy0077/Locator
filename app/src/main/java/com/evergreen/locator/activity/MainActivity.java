package com.evergreen.locator.activity;

import android.app.Activity;
import android.content.Context;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.ads.AdListener;
import com.evergreen.locator.R;
import com.evergreen.locator.fragment.AllStoreFragment;
import com.evergreen.locator.fragment.MyMapsFragment;
import com.evergreen.locator.fragment.NewsFragment;
import com.evergreen.locator.utility.ActivityUtils;
import com.evergreen.locator.utility.AdUtils;
import com.evergreen.locator.utility.AppUtils;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    public DrawerLayout drawerLayout;
    private FloatingSearchView floatingSearchView;
    private Activity mActivity;
    private Context mContext;
    private MyMapsFragment myMapsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intVariable();
        intView();
        intListener();

    }

    public void intView() {
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        drawerLayout = findViewById(R.id.mDrawerLayout);
        navigationView = findViewById(R.id.main_drawer);

        floatingSearchView = findViewById(R.id.floating_search_view);
        floatingSearchView.attachNavigationDrawerToMenuButton(drawerLayout);

        myMapsFragment = new MyMapsFragment();
        initFragment(myMapsFragment, getString(R.string.map_fragmentt));

    }

    public void intVariable() {
        mActivity = MainActivity.this;
        mContext=getApplicationContext();
        AdUtils.getInstance(mContext).loadFullScreenAd(mActivity);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
             /* Main menu selector*/
            Menu mainMenu = navigationView.getMenu();
            switch (item.getItemId()) {
                case R.id.action_map:
                    mainMenu.findItem(R.id.nav_home).setChecked(true);
                    floatingSearchView.setVisibility(View.VISIBLE);
                    myMapsFragment = new MyMapsFragment();
                    initFragment(myMapsFragment, getString(R.string.map_fragmentt));
                    return true;

                case R.id.action_allStore:

                    mainMenu.findItem(R.id.nav_allStore).setChecked(true);

                    if (floatingSearchView.getVisibility()==View.VISIBLE){
                        floatingSearchView.setVisibility(View.GONE);
                    }
                    initFragment(new AllStoreFragment(), getString(R.string.alls_store_fragment));
                    return true;
                case R.id.action_news:

                    mainMenu.findItem(R.id.nav_news).setChecked(true);

                    if (floatingSearchView.getVisibility()==View.VISIBLE){
                        floatingSearchView.setVisibility(View.GONE);
                    }
                    initFragment(new NewsFragment(),  getString(R.string.news_fragment));
                    return true;
            }
            return true;
        }
    };

    private void initFragment(Fragment fragment, String fragmentName) {
        refreshFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, fragmentName).commit();
    }

    private void refreshFragment() {
        Fragment mapFragment = getSupportFragmentManager().findFragmentByTag( getString(R.string.map_fragmentt));
        if (mapFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        }

        Fragment allStoreFragment = getSupportFragmentManager().findFragmentByTag( getString(R.string.alls_store_fragment));
        if (allStoreFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(allStoreFragment).commit();
        }

        Fragment newsFragment = getSupportFragmentManager().findFragmentByTag( getString(R.string.news_fragment));
        if (newsFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(newsFragment).commit();
        }
    }


    public void intListener() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                /*bottom menu selector*/
                Menu bottomMenu = bottomNavigationView.getMenu();

                int id = item.getItemId();
                if (id == R.id.nav_home) {

                    /*bottom menu select*/
                    bottomMenu.findItem(R.id.action_map).setChecked(true);

                    floatingSearchView.setVisibility(View.VISIBLE);
                    myMapsFragment = new MyMapsFragment();
                    initFragment(myMapsFragment,  getString(R.string.map_fragmentt));

                } else if (id == R.id.nav_allStore) {
                    /*bottom menu select*/
                    bottomMenu.findItem(R.id.action_allStore).setChecked(true);

                    if (floatingSearchView.getVisibility() == View.VISIBLE) {
                        floatingSearchView.setVisibility(View.GONE);
                    }
                    initFragment(new AllStoreFragment(),  getString(R.string.alls_store_fragment));
                } else if (id == R.id.nav_news) {
                    /*bottom menu select*/
                    bottomMenu.findItem(R.id.action_news).setChecked(true);

                    if (floatingSearchView.getVisibility() == View.VISIBLE) {
                        floatingSearchView.setVisibility(View.GONE);
                    }
                    initFragment(new NewsFragment(), getString(R.string.news_fragment));
                } else if (id == R.id.nav_setting) {
                    showAdThenActivity(SettingActivity.class);
                } else if (id == R.id.nav_share) {
                    AppUtils.shareApp(mActivity);
                } else if (id == R.id.nav_rate) {
                    AppUtils.rateThisApp(mActivity);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.settings) {
                    showAdThenActivity(SettingActivity.class);
                }
            }
        });

        floatingSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                floatingSearchView.setVisibility(View.GONE);
            }

            @Override
            public void onFocusCleared() {
                if(myMapsFragment != null) {
                    myMapsFragment.showAutocompleteLayout();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getResources().getString(R.string.dialoge_text));
                alertDialogBuilder.setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });

        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void showFloatingSearchView() {
        if(floatingSearchView != null) {
            floatingSearchView.setVisibility(View.VISIBLE);
        }
    }

    public void showAdThenActivity(final Class<?> activity) {
        if (AdUtils.getInstance(mContext).showFullScreenAd()) {
            AdUtils.getInstance(mContext).getInterstitialAd().setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    ActivityUtils.getInstance().invokeActivity(mActivity, activity, true);
                }
            });
        } else {
            ActivityUtils.getInstance().invokeActivity(mActivity, activity, true);
        }
    }
}
