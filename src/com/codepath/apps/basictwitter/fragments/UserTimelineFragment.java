package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragments {
	@Override
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		populateTimeline();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);

		// assign our view reference
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweet);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d("debug", "page=" + page);
				Log.d("debug", "totalItemsCount=" + totalItemsCount);

				// Tweet firstTweet = tweets.get(1);
				Tweet lastTweet = tweets.get(totalItemsCount - 1);
				// Log.d("debug", "max id=" + firstTweet.getId_str());
				Log.d("debug", "since id=" + lastTweet.getId_str());
				long max_id = lastTweet.getUid() - 1;

				TwitterClient client = TwitterApplication.getRestClient();
				client.getUserTimeline(new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray json) {
						// aTweet.addAll(Tweet.fromJSONArray(json));

						tweets.addAll(Tweet.fromJSONArray(json));
						Log.d("debug", "size of tweets is=" + tweets.size());
						aTweet.notifyDataSetChanged();
					}

					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
				}, String.valueOf(max_id));
			}
		});
		return v;
	}
	
	public void populateTimeline() {
		TwitterClient client = TwitterApplication.getRestClient();
		client.getUserTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("debug", "get mentions data");
				Log.d("debug", json.toString());
				addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
}
