package com.gwittit.client.facebook.xfbml;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;

public class Xfbml {
	
	
	/**
	 * We need facebook to parse the DOM tree after we have added stuff to it.
	 */
	public static void parse ( final Element element) {
		DeferredCommand.addCommand( new Command () {
			public void execute() {
				parseDomTree (  );
			}
		});
	}
	protected static native void parseDomTree(  ) /*-{		
		$wnd.FB_RequireFeatures(["Api"], function(){	
			try{
				$wnd.FB.XFBML.Host.parseDomTree();
			}catch(err){
				alert(err+"");
			}
		});
	}-*/;
}
