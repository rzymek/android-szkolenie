package tumblr.viewer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsFragment extends Fragment {

	public static DetailsFragment newInstance(String url) {
		DetailsFragment f = new DetailsFragment();
		Bundle args = new Bundle();
		args.putString("url", url);
		f.setArguments(args);
		return f;
	}

	public DetailsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_details, container, false);
		String url = getArguments().getString("url");
		
		WebView web = (WebView) rootView.findViewById(R.id.detailt_webview);
		web.loadUrl(url);
		return rootView;
	}
}