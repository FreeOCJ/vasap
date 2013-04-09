package ch.jesc.vasap.web.ui.common;

import com.vaadin.navigator.View;

public interface WebView extends View {

	boolean isAllowed();

	String getTitle();

}
