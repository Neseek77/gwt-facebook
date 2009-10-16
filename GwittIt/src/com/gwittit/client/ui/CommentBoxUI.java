package com.gwittit.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwittit.client.facebook.entities.Stream;
import com.gwittit.client.facebook.xfbml.FbComments;
import com.gwittit.client.facebook.xfbml.FbProfilePic;
import com.gwittit.client.facebook.xfbml.Xfbml;

/**
 * Renders a comment box.
 * Configure style with "gwittit-CommentBoxUI"
 * 
 * In many cases you may want to use FbComments.
 * 
 * @see FbComments FbComments.
 */
public class CommentBoxUI extends Composite {
	
	private static final String WRITE_A_COMMENT = "Write a comment...";
	
	private VerticalPanel outer = new VerticalPanel ();
	
	private HorizontalPanel inner = new HorizontalPanel ();

	private FbProfilePic profilePic = new FbProfilePic ();
	
	private TextArea textArea = new TextArea ();
	
	private Button submit = new Button ( "Add Comment");
	

	public CommentBoxUI ( Stream stream ) {
		
		outer.getElement().setId("CommentBoxUI");
		outer.addStyleName("gwittit-CommentBoxUI");
		outer.addStyleName ( "collapsed" );
		
		textArea.setVisibleLines(1);
		textArea.addStyleName ( "textArea");
		textArea.addStyleName("collapsed");
		textArea.setValue(WRITE_A_COMMENT);

		/*
		textArea.addBlurHandler(new BlurHandler () {
			public void onBlur(BlurEvent event) {
				reset();
			}
			
		});
		*/
		inner.getElement().setId( "CommentBox-Stream-PostID" + stream.getPostId() );
		inner.add ( textArea );
		//inner.add( submit );

		outer.add ( inner );
		
		textArea.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				expand ();
			}
		});
		
		initWidget ( outer );
	}
	
	public void addClickHandler ( ClickHandler clickHandler ) {
		submit.addClickHandler(clickHandler);
	}
	public String getText () {
		return textArea.getValue();
	}
	
	public void expand () {
		textArea.setVisibleLines(4);
		textArea.setValue(null);
		textArea.removeStyleName("collapsed");
		textArea.addStyleName("expanded" );
		inner.insert(profilePic, 0);
		outer.add(submit);
		Xfbml.parse( inner.getElement() );
	}
	
	public void reset () {
		textArea.setVisibleLines(1);
		textArea.removeStyleName("expanded");
		textArea.addStyleName("collapsed");
		inner.remove(profilePic);
		textArea.setValue(WRITE_A_COMMENT);
		outer.remove(submit);
	}

}
