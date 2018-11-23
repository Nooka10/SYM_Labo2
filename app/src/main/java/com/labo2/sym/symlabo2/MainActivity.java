package com.labo2.sym.symlabo2;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.labo2.sym.symlabo2.GraphQLFragment.GraphQLSendFragment;

/**
 * Activité principale démarrée automatiquement au lancement de l'application.
 */
public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener, MainFragment.OnFragmentInteractionListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		if (savedInstanceState == null) {
			try {
				// initialisation du MainFragment
				Fragment fragment = MainFragment.class.newInstance();
				FragmentManager fragmentManager = getSupportFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		// initialisation du drawer
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		
		// initialisation de la NavigationView
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.getMenu().getItem(0).setChecked(true);
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Sélectionne le bouton du menu correspondant à l'id reçu en paramètre.
	 * @param idItemToSelect, l'id de l'item du menu à sélectionner.
	 */
	void setSelectedNavigationItem(int idItemToSelect) {
		NavigationView navigationView = findViewById(R.id.nav_view);
		onNavigationItemSelected(navigationView.getMenu().getItem(idItemToSelect).setChecked(true));
	}
	
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		Fragment fragment = null;
		Class fragmentClass = null;
		if (id == R.id.mainSelected) {
			fragmentClass = MainFragment.class;
		} else if (id == R.id.asyncSend) {
			fragmentClass = AsyncSendFragment.class;
		} else if (id == R.id.delayedSend) {
			fragmentClass = DelayedSendFragment.class;
		} else if (id == R.id.jsonObjectSend) {
			fragmentClass = JsonObjectSendFragment.class;
		} else if (id == R.id.xmlObjectSend) {
			fragmentClass = XmlObjectSendFragment.class;
		} else if (id == R.id.graphqlObjectSend) {
			fragmentClass = GraphQLSendFragment.class;
		} else if (id == R.id.compressedSend) {
			fragmentClass = CompressedSendFragment.class;
		}
		
		try {
			if (fragmentClass != null) {
				fragment = (Fragment) fragmentClass.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		if (fragment != null) {
			fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
		}
		
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	
	@Override
	public void onFragmentInteraction(Uri uri) {
	
	}
}
