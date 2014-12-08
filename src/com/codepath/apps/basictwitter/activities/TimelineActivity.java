package com.codepath.apps.basictwitter.activities;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {
	private User user;
	private static final int TWEET_REQUEST = 0;

	private Tab tabHome;
	private Tab tabMentions;
	
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		setupTabs();
		getVerifyCredentials();
	}
	
	private void setupTabs() {
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		tabHome = actionBar
		    .newTab()
		    .setText("Home")
		    .setIcon(R.drawable.ic_home)
		    .setTag("HomeTimelineFragment")
		    .setTabListener(new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this,"HomeTimelineFragment", HomeTimelineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.selectTab(tabHome);

		tabMentions = actionBar
		    .newTab()
		    .setText("Mentions")
		    .setIcon(R.drawable.ic_mentions)
		    .setTag("MentionsTimelineFragment")
		    .setTabListener(new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "MentionsTimelineFragment", MentionsTimelineFragment.class));
		actionBar.addTab(tabMentions);
	}
	
	public void onProfileView(MenuItem mi) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
	
	private void getVerifyCredentials() {
		TwitterClient client = TwitterApplication.getRestClient();
		client.getVerifyCredentials(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJSON(json);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	
	public void onComposeAction(MenuItem mi) {
		actionBar.selectTab(tabHome);
		Intent intent = new Intent(this, ComposeActivity.class);
		intent.putExtra("user", user);
		startActivityForResult(intent, TWEET_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == Activity.RESULT_OK && requestCode == TWEET_REQUEST) {
			// refresh
	    	HomeTimelineFragment fragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("HomeTimelineFragment");
	    	fragment.clear();
	    	fragment.populateTimeline();
	    	
	    }
	}
	
}
