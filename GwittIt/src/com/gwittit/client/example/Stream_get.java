package com.gwittit.client.example;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.FbName;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Showcase for method call <code>stream.get</code>
 * 
 * @author olamar72
 */
public class Stream_get extends Showcase {

	static String method  = "stream.get";
	
	
	public Stream_get () {
		super ( method );
	}
	
	
	@Override
	public Widget createWidget () {
		
		final VerticalPanel outer = new VerticalPanel();
		outer.getElement().setId ( method );
		
		addLoader ( outer );
		
		apiClient.stream_get(null, new AsyncCallback<List<Stream>> () {
			public void onFailure(Throwable caught) {
				handleFailure ( caught );
			}
			public void onSuccess(List<Stream> result) {
				removeLoader ( outer );
				for ( Stream s : result ) {
					outer.add ( new HTML ( s.getMessage()  + " from " + new FbName ( s.getSourceId() ) ) );
				}
				
				Xfbml.parse ( outer );
			}
			
		});
		
		
		return outer;
	}
	
	
}
