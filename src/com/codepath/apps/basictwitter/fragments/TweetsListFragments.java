package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TweetsListFragments extends Fragment {

	protected ArrayList<Tweet> tweets;
	protected ArrayAdapter<Tweet> aTweet;
	protected ListView lvTweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		tweets = new ArrayList<Tweet>();
		aTweet = new TweetArrayAdapter(getActivity(), tweets);
	}

	public void addAll(ArrayList<Tweet> tweets) {
		aTweet.addAll(tweets);
	}

	public void clear() {
		aTweet.clear();
	}
}
