package com.gwittit.client.facebook.xfbml;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;


/**
 * Wrapper class for javascript API XFBML.Host. 
 * In general try to avoid parsing the whole domtree, and instead limit it to
 * a specific widget.  This way you will get a smoother feeling when displaying
 * facebook photos and tags.
 * 
 *  http://wiki.developers.facebook.com/index.php/JS_API_T_FB.XFBML.Host XFBML Host
 */
public class Xfbml {
	
	
	/**
     * Parse the children under the specified DOM Element to search for XFBML tags and render them. 	 
     */
	public static void parse ( final Widget w ) {
		parse ( w.getElement() );
	}

	
	/**
     *  Start parse the DOM tree to search for XFBML tags and render them. This will be invoked automatically unless FB.XFBML.Host.autoParseDomTree is set to false. 
	 */
	public static void parse ( final Element element) {
	    
		DeferredCommand.addCommand( new Command () {
			public void execute() {
				if ( element != null && element.getId() != null ) {
					if ( "".equals ( element.getId() ) ) {
						parseDomTree();
					} else {
						GWT.log( "ParseDomElement: " + element.getId() , null);
						parseDomElement ( element.getId() ); 
						GWT.log( Xfbml.class + "Done ", null);
					}
				} else {
					parseDomTree (  );
				}
			}
		});
	}

	private static native void parseDomElement( String id ) /*-{		
    	$wnd.FB_RequireFeatures(["Api"], function(){	
    		try{
    			$wnd.FB.XFBML.Host.parseDomElement( $wnd.document.getElementById(id) );
    		}catch(err){
    			alert(err+"");
    		}
    	});
    }-*/;

	/**
	 * Facebook will
	 */
	private static native void parseDomTree(  ) /*-{		
		$wnd.FB_RequireFeatures(["Api"], function(){	
			try{
				$wnd.FB.XFBML.Host.parseDomTree();
			}catch(err){
				alert(err+"");
			}
		});
	}-*/;
}
