package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerFrameBackgroundColor implements ChangeListener {

	private Properties mProperties;
	private UserFrame mUserFrame;

	public ListenerFrameBackgroundColor(Properties properties, UserFrame userFrame) {
		mProperties = properties;
		mUserFrame = userFrame;
	}

	public void stateChanged(ChangeEvent e) {
		mProperties.setBackgroundColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mUserFrame.getObservableBackgroundColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
	}
}
