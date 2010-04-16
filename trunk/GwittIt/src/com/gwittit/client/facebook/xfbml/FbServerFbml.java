package com.gwittit.client.facebook.xfbml;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
/**
 * Renders the FBML on a Facebook server inside an iframe. You must use this tag
 * to render elements on Facebook like fb:request-form and fb:connect-form (for
 * friend selectors). The tags that don't need to be used with fb:serverfbml are
 * listed on the XFBML page.
 * 
 * This container tag holds display/FBML elements that must be served in a
 * Facebook iframe for security reasons. For instance, the fb:request-form tag
 * must be wrapped inside this tag in order to work on a Facebook Connect site,
 * since we need to be able to verify that the user performed the actions on the
 * form. http://wiki.developers.facebook.com/index.php/Fb:serverFbml
 * 
 * @author ola
 * 
 */
public class FbServerFbml extends ComplexPanel {

    
    /**
     * Fbml
     * @param fbml
     */
    public FbServerFbml( String fbml ) {
        super.setElement(DOM.createElement("fb:serverFbml"));
        DOM.setInnerHTML ( getElement(), fbml );
    }

}
