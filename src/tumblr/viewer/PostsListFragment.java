package tumblr.viewer;

import java.util.List;

import tumblr.viewer.json.Original_size;
import tumblr.viewer.json.Photo;
import tumblr.viewer.json.Post;
import tumblr.viewer.json.PostResponse;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostsListFragment extends ListFragment {
	private static final String TUMBLR_API_KEY = "fD0HOvNDa2z10uyozPZNnjeb4fEFGVGm58zttH6cXSe4K0qC64";

	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PostsListFragment newInstance(int sectionNumber) {
		PostsListFragment fragment = new PostsListFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	private ArrayAdapter<Post> adapter;

	public PostsListFragment() {
	}

	class ViewHolder {
		TextView text;
		ImageView image;
	}

	public interface Callback {
		void openUrl(String url);
	}

	Callback callback;

	@Override
	public void onDetach() {
		callback = new Callback() {
			public void openUrl(String url) {
			};
		};
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Post post = adapter.getItem(position);
		String url = post.getPost_url();
		callback.openUrl(url);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			adapter = new ArrayAdapter<Post>(getActivity(), R.layout.posts_list_item, R.id.post_caption) {

				@Override
				public long getItemId(int position) {
					// zwracanie unikalny id to optymalizacja
					// @see: http://www.youtube.com/watch?v=wDBM6wVEO70
					return super.getItemId(position);
				}

				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					if (convertView == null) {
						LayoutInflater inf = LayoutInflater.from(getContext());
						convertView = inf.inflate(R.layout.posts_list_item, parent, false);
					}
					ViewHolder holder = (ViewHolder) convertView.getTag();
					if (holder == null) {
						convertView.setTag(holder = new ViewHolder());
					}
					Post post = getItem(position);
					holder.text = (TextView) convertView.findViewById(R.id.post_caption);
					holder.image = (ImageView) convertView.findViewById(R.id.post_photo);

					holder.text.setText(Html.fromHtml(post.getCaption()));
					AQuery q = new AQuery(convertView);
					List<Photo> photos = post.getPhotos();
					if (photos != null && photos.size() > 0) {
						Original_size photo = photos.get(0).getOriginal_size();
						q.id(holder.image).image(photo.getUrl(), true, true, convertView.getWidth(), 0);
					}
					return convertView;
				}
			};
			// adapter = new ArrayAdapter<Post>(getActivity(),
			// android.R.layout.simple_list_item_1);
			setListAdapter(adapter);
		}
		AQuery q = new AQuery(getActivity().getApplicationContext());
		String tumblrName = "wehavethemunchies";
		String url = "http://api.tumblr.com/v2/blog/" + tumblrName + ".tumblr.com/posts?api_key=" + TUMBLR_API_KEY
				+ "&limit=20&" + "offset=0";
		System.out.println(url);
		adapter.clear();
		q.ajax(url, String.class, new AjaxCallback<String>() {
			@SuppressLint("NewApi")
			@Override
			public void callback(String url, String json, AjaxStatus status) {
				super.callback(url, json, status);
				Log.w("XXX", "" + json);
				PostResponse response = new Gson().fromJson(json, PostResponse.class);
				Log.w("XXX", "" + response);
				List<Post> posts = response.getResponse().getPosts();
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					adapter.addAll(posts);
				} else {
					for (Post post : posts) {
						adapter.add(post);
					}
				}
			}

		});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((TumblrListActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));

		callback = (Callback) activity;
	}
}