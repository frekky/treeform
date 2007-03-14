package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ListenerLineColor implements ActionListener {

	private UserFrame frame;

	public ListenerLineColor(UserFrame userFrame) {
		super();
		frame = userFrame;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame textFrame = new JFrame();
		JPanel textColorPanel = new JPanel();
		UserColorChooser textColor = new UserColorChooser(frame.getInternalFrame().getProperties());
		textColor.setColor(frame.getInternalFrame().getProperties().getFontColor());
		textColor.getSelectionModel().addChangeListener(new ListenerFrameLineColor(frame.getInternalFrame().getProperties(),frame));
		frame.getObservableLineColor().addObserver(textColor);
		textColorPanel.add(textColor);
		textFrame.add(textColorPanel);
		textFrame.pack();
		textFrame.setVisible(true);
	}
}