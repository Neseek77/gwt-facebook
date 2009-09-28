package com.gwittit.client.facebook.xfbml;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;

public class Xfbml {
	
	
	/**
	 * We need facebook to parse the DOM tree after we have added stuff to it.
	 */
	public static void parse ( final Element element) {
		//Window.alert ( "ParseDomElement " + (element == null ? "null" : element.getId() ) );

		DeferredCommand.addCommand( new Command () {
			public void execute() {
				if ( element != null && element.getId() != null ) {
					if ( "".equals ( element.getId() ) ) {
						parseDomTree();
					} else {
						GWT.log( "ParseDomElement: " + element.getId() , null);
						parseDomElement ( element.getId() ); 
					}
				} else {
					parseDomTree (  );
				}
			}
		});
	}

	protected static native void parseDomElement( String id ) /*-{		
	$wnd.FB_RequireFeatures(["Api"], function(){	
		try{
			$wnd.FB.XFBML.Host.parseDomElement( $wnd.document.getElementById(id) );
		}catch(err){
			alert(err+"");
		}
	});
}-*/;

	protected static native void parseDomTree(  ) /*-{		
		$wnd.FB_RequireFeatures(["Api"], function(){	
			try{
				alert ( 'start parse dom tree' );
				$wnd.FB.XFBML.Host.parseDomTree();
				alert ( 'donw parse dom tree' );
			}catch(err){
				alert(err+"");
			}
		});
	}-*/;
}
