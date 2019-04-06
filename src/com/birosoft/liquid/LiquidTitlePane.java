/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *        Liquid Look and Feel                                                   *
 *                                                                             *
 *  Author, Miroslav Lazarevic                                                 *
 *                                                                             *
 *   For licensing information and credits, please refer to the                *
 *   comment in file com.birosoft.liquid.LiquidLookAndFeel                     *
 *                                                                             *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.birosoft.liquid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;


/*
 * @(#)LiquidTitlePane.java        1.10 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Class that manages a JLF awt.Window-descendant class's title bar.
 * <p>
 * This class assumes it will be created with a particular window
 * decoration style, and that if the style changes, a new one will
 * be created.
 *
 * @version 1.10 12/03/01
 * @author Terry Kellerman
 * @since 1.4
 */

/**
 * LiquidTitlePane
 *
 * @version 0.1
 * @author Miroslav Lazarević
 */
class LiquidTitlePane extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int IMAGE_HEIGHT = 16;
    private static final int IMAGE_WIDTH = 16;
    private static LiquidWindowButtonUI iconButtonUI;
    private static LiquidWindowButtonUI maxButtonUI;
    private static LiquidWindowButtonUI closeButtonUI;
    private boolean prevState = false;

    /**
     * Color for the title in a normal sized internal frame
     */
    Color normalTitleColor = Color.white;

    /**
     * Color for the shadow of the title in a normal sized internal frame
     */
    Color shadowColor = new Color(10, 24, 131);

    /**
     * Color for the title in a normal sized internal frame that is not enabled
     */
    Color disabledTitleColor = new Color(216, 228, 244);

    /**
     * PropertyChangeListener added to the JRootPane.
     */
    private PropertyChangeListener propertyChangeListener;

	/**
	 * JMenuBar, typically renders the system menu items.
	 * 
	 * @uml.property name="menuBar"
	 * @uml.associationEnd 
	 * @uml.property name="menuBar" multiplicity="(1 1)"
	 */
	private JMenuBar menuBar;

	/**
	 * Action used to close the Window.
	 * 
	 * @uml.property name="closeAction"
	 * @uml.associationEnd 
	 * @uml.property name="closeAction" multiplicity="(0 1)"
	 */
	private Action closeAction;

	/**
	 * Action used to iconify the Frame.
	 * 
	 * @uml.property name="iconifyAction"
	 * @uml.associationEnd 
	 * @uml.property name="iconifyAction" multiplicity="(0 1)"
	 */
	private Action iconifyAction;

	/**
	 * Action to restore the Frame size.
	 * 
	 * @uml.property name="restoreAction"
	 * @uml.associationEnd 
	 * @uml.property name="restoreAction" multiplicity="(0 1)"
	 */
	private Action restoreAction;

	/**
	 * Action to restore the Frame size.
	 * 
	 * @uml.property name="maximizeAction"
	 * @uml.associationEnd 
	 * @uml.property name="maximizeAction" multiplicity="(0 1)"
	 */
	private Action maximizeAction;

	/**
	 * Button used to maximize or restore the Frame.
	 * 
	 * @uml.property name="toggleButton"
	 * @uml.associationEnd 
	 * @uml.property name="toggleButton" multiplicity="(0 1)"
	 */
	private JButton toggleButton;

	/**
	 * Button used to maximize or restore the Frame.
	 * 
	 * @uml.property name="iconifyButton"
	 * @uml.associationEnd 
	 * @uml.property name="iconifyButton" multiplicity="(0 1)"
	 */
	private JButton iconifyButton;

	/**
	 * Button used to maximize or restore the Frame.
	 * 
	 * @uml.property name="closeButton"
	 * @uml.associationEnd 
	 * @uml.property name="closeButton" multiplicity="(0 1)"
	 */
	private JButton closeButton;


    /**
     * Listens for changes in the state of the Window listener to update
     * the state of the widgets.
     */
    private WindowListener windowListener;
    private ComponentListener windowMoveListener;

    /**
     * Window we're currently in.
     */
    private Window window;

	/**
	 * JRootPane rendering for.
	 * 
	 * @uml.property name="rootPane"
	 * @uml.associationEnd 
	 * @uml.property name="rootPane" multiplicity="(1 1)"
	 */
	private JRootPane rootPane;


    /**
     * Buffered Frame.state property. As state isn't bound, this is kept
     * to determine when to avoid updating widgets.
     */
    private int state;

	public LiquidTitlePane(JRootPane root, LiquidRootPaneUI ui) {
        rootPane = root;
        state = -1;

        installSubcomponents();
        installDefaults();

        setLayout(createLayout());
    }

    /**
     * Installs the necessary listeners.
     */
    private void installListeners() {
        if (window != null) {
            windowListener = createWindowListener();
            window.addWindowListener(windowListener);
            propertyChangeListener = createWindowPropertyChangeListener();
            window.addPropertyChangeListener(propertyChangeListener);
            windowMoveListener = new WindowMoveListener();
            window.addComponentListener(windowMoveListener);
        }
    }

    /**
     * Uninstalls the necessary listeners.
     */
    private void uninstallListeners() {
        if (window != null) {
            window.removeWindowListener(windowListener);
            window.removePropertyChangeListener(propertyChangeListener);
            window.removeComponentListener(windowMoveListener);
        }
    }

    /**
     * Returns the <code>WindowListener</code> to add to the
     * <code>Window</code>.
     */
    private WindowListener createWindowListener() {
        return new WindowHandler();
    }

    /**
     * Returns the <code>PropertyChangeListener</code> to install on
     * the <code>Window</code>.
     */
    private PropertyChangeListener createWindowPropertyChangeListener() {
        return new PropertyChangeHandler();
    }

	/**
	 * Returns the <code>JRootPane</code> this was created for.
	 * 
	 * @uml.property name="rootPane"
	 */
	public JRootPane getRootPane() {
		return rootPane;
	}


    /**
     * Returns the decoration style of the <code>JRootPane</code>.
     */
    private int getWindowDecorationStyle() {
        return getRootPane().getWindowDecorationStyle();
    }

    public void addNotify() {
        super.addNotify();

        uninstallListeners();

        window = SwingUtilities.getWindowAncestor(this);

        if (window != null) {
            if (window instanceof Frame) {
                setState(((Frame) window).getExtendedState());
            } else {
                setState(0);
            }

            setActive(window.isActive());
            installListeners();
        }
    }

    public void removeNotify() {
        super.removeNotify();

        uninstallListeners();
        window = null;
    }

    /**
     * Adds any sub-Components contained in the <code>LiquidTitlePane</code>.
     */
    private void installSubcomponents() {
        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            createActions();
            menuBar = createMenuBar();
            add(menuBar);
            createButtons();
            add(iconifyButton);
            add(toggleButton);
            add(closeButton);
            iconifyButton.putClientProperty("externalFrameButton", Boolean.TRUE);
            toggleButton.putClientProperty("externalFrameButton", Boolean.TRUE);
            closeButton.putClientProperty("externalFrameButton", Boolean.TRUE);

            /*System.out.println(rootPane.getIgnoreRepaint());
            rootPane.setIgnoreRepaint(true);
            System.out.println(rootPane.getContentPane().getIgnoreRepaint());
            rootPane.getContentPane().setIgnoreRepaint(true);*/
        } else if (getWindowDecorationStyle() != JRootPane.NONE) {
            createActions();
            createButtons();
            add(closeButton);
            closeButton.putClientProperty("externalFrameButton", Boolean.FALSE);
        }
    }

    /**
     * Installs the fonts and necessary properties on the LiquidTitlePane.
     */
    private void installDefaults() {
        setFont(UIManager.getFont("InternalFrame.titleFont", getLocale()));
    }

    /**
     * Returns the <code>JMenuBar</code> displaying the appropriate
     * system menu items.
     */
    protected JMenuBar createMenuBar() {
        menuBar = new SystemMenuBar(createMenu());
        menuBar.setFocusable(false);
        menuBar.setBorderPainted(true);

        //menuBar.add(createMenu());
        return menuBar;
    }

    /**
     * Closes the Window.
     */
    private void close() {
        Window window = getWindow();

        if (window != null) {
            window.dispatchEvent(new WindowEvent(window,
                    WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Iconifies the Frame.
     */
    private void iconify() {
        Frame frame = getFrame();

        if (frame != null) {
            frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED);
        }
    }

    /**
     * Maximizes the Frame.
     */
    private void maximize() {
        Frame frame = getFrame();

        if (frame != null) {
            setMaximizeBounds(frame);
            frame.setExtendedState(frame.getExtendedState() |
                Frame.MAXIMIZED_BOTH);
        }
    }

    private void setMaximizeBounds(Frame frame) {
        if (frame.getMaximizedBounds() != null) {
            return;
        }

        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // spare any Systemmenus or Taskbars or ??...
        int x = screenInsets.top;
        int y = screenInsets.left;
        int w = screenSize.width - x - screenInsets.right;
        int h = screenSize.height - y - screenInsets.bottom;
        Rectangle maxBounds = new Rectangle(x, y, w, h);
        frame.setMaximizedBounds(maxBounds);
    }

    /**
     * Restores the Frame size.
     */
    private void restore() {
        Frame frame = getFrame();

        if (frame == null) {
            return;
        }

        if ((frame.getExtendedState() & Frame.ICONIFIED) == Frame.ICONIFIED) {
            frame.setExtendedState(state & ~Frame.ICONIFIED);
        } else {
            frame.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
        }
    }

    /**
     * Create the <code>Action</code>s that get associated with the
     * buttons and menu items.
     */
    private void createActions() {
        closeAction = new CloseAction();
        iconifyAction = new IconifyAction();
        restoreAction = new RestoreAction();
        maximizeAction = new MaximizeAction();
    }

    /**
     * Returns the <code>JMenu</code> displaying the appropriate menu items
     * for manipulating the Frame.
     */
    private JPopupMenu createMenu() {
        JPopupMenu menu = new JPopupMenu();

        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            addMenuItems(menu);

            // we use this property to prevent the Menu from drawing rollovers
            menu.putClientProperty("isSystemMenu", Boolean.TRUE);
        }

        return menu;
    }

    /**
     * Adds the necessary <code>JMenuItem</code>s to the passed in menu.
     */
    private void addMenuItems(JPopupMenu menu) {
        JMenuItem mi = menu.add(restoreAction);
        mi.setMnemonic('r');

        mi = menu.add(iconifyAction);
        mi.setMnemonic('e');

        if (Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
            mi = menu.add(maximizeAction);
            mi.setMnemonic('x');
        }

        menu.addSeparator();

        mi = menu.add(closeAction);
        mi.setMnemonic('c');
    }

    /**
     * Creates the buttons of the title pane and initializes their actions.
     */
    protected void createButtons() {
        if (iconButtonUI == null) {
            iconButtonUI = LiquidWindowButtonUI.createButtonUIForType(LiquidWindowButtonUI.MINIMIZE);
            maxButtonUI = LiquidWindowButtonUI.createButtonUIForType(LiquidWindowButtonUI.MAXIMIZE);
            closeButtonUI = LiquidWindowButtonUI.createButtonUIForType(LiquidWindowButtonUI.CLOSE);
        }

        iconifyButton = new SpecialUIButton(iconButtonUI,
                (java.awt.Frame) getFrame());
        iconifyButton.setAction(iconifyAction);
        iconifyButton.setRolloverEnabled(true);

        toggleButton = new SpecialUIButton(maxButtonUI,
                (java.awt.Frame) getFrame());
        toggleButton.setAction(maximizeAction);
        toggleButton.setRolloverEnabled(true);

        closeButton = new SpecialUIButton(closeButtonUI,
                (java.awt.Frame) getFrame());
        closeButton.setAction(closeAction);
        closeButton.setRolloverEnabled(true);

        closeButton.getAccessibleContext().setAccessibleName("Close");
        iconifyButton.getAccessibleContext().setAccessibleName("Iconify");
        toggleButton.getAccessibleContext().setAccessibleName("Maximize");
    }

    /**
     * Returns the <code>LayoutManager</code> that should be installed on
     * the <code>LiquidTitlePane</code>.
     */
    private LayoutManager createLayout() {
        return new TitlePaneLayout();
    }

    /**
     * Updates state dependant upon the Window's active state.
     */
    private void setActive(boolean isActive) {
        if (getWindowDecorationStyle() == JRootPane.FRAME) {
            Boolean activeB = isActive ? Boolean.TRUE : Boolean.FALSE;

            iconifyButton.putClientProperty("paintActive", activeB);
            closeButton.putClientProperty("paintActive", activeB);
            toggleButton.putClientProperty("paintActive", activeB);

            iconifyButton.setEnabled(isActive);
            closeButton.setEnabled(isActive);
            toggleButton.setEnabled(isActive);
        }

        // Repaint the whole thing as the Borders that are used have
        // different colors for active vs inactive
        getRootPane().repaint();
    }

    /**
     * Sets the state of the Window.
     */
    private void setState(int state) {
        setState(state, false);
    }

    /**
     * Sets the state of the window. If <code>updateRegardless</code> is
     * true and the state has not changed, this will update anyway.
     */
    private void setState(int state, boolean updateRegardless) {
        Window w = getWindow();

        if ((w != null) && (getWindowDecorationStyle() == JRootPane.FRAME)) {
            if ((this.state == state) && !updateRegardless) {
                return;
            }

            Frame frame = getFrame();

            if (frame != null) {
                JRootPane rootPane = getRootPane();

                if (((state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH) &&
                        ((rootPane.getBorder() == null) ||
                        (rootPane.getBorder() instanceof UIResource)) &&
                        frame.isShowing()) {
                    //rootPane.setBorder(null);
                } else if ((state & Frame.MAXIMIZED_BOTH) != Frame.MAXIMIZED_BOTH) {
                    // This is a croak, if state becomes bound, this can
                    // be nuked.
                    //rootPaneUI.installBorder(rootPane);
                }

                if (frame.isResizable()) {
                    if (((state & Frame.MAXIMIZED_VERT) == Frame.MAXIMIZED_VERT) ||
                            ((state & Frame.MAXIMIZED_HORIZ) == Frame.MAXIMIZED_HORIZ)) {
                        updateToggleButton(restoreAction);
                        maximizeAction.setEnabled(false);
                        restoreAction.setEnabled(true);
                    } else {
                        updateToggleButton(maximizeAction);
                        maximizeAction.setEnabled(true);
                        restoreAction.setEnabled(false);
                    }

                    if ((toggleButton.getParent() == null) ||
                            (iconifyButton.getParent() == null)) {
                        add(toggleButton);
                        add(iconifyButton);
                        revalidate();
                        repaint();
                    }

                    toggleButton.setText(null);
                } else {
                    maximizeAction.setEnabled(false);
                    restoreAction.setEnabled(false);

                    if (toggleButton.getParent() != null) {
                        remove(toggleButton);
                        revalidate();
                        repaint();
                    }
                }
            } else {
                // Not contained in a Frame
                maximizeAction.setEnabled(false);
                restoreAction.setEnabled(false);
                iconifyAction.setEnabled(false);
                remove(toggleButton);
                remove(iconifyButton);
                revalidate();
                repaint();
            }

            closeAction.setEnabled(true);
            this.state = state;
        }
    }

    /**
     * Updates the toggle button to contain the Icon <code>icon</code>, and
     * Action <code>action</code>.
     */
    private void updateToggleButton(Action action) {
        toggleButton.setAction(action);
        toggleButton.setText(null);
    }

    /**
     * Returns the Frame rendering in. This will return null if the
     * <code>JRootPane</code> is not contained in a <code>Frame</code>.
     */
    private Frame getFrame() {
        Window window = getWindow();

        if (window instanceof Frame) {
            return (Frame) window;
        }

        return null;
    }

	/**
	 * Returns the <code>Window</code> the <code>JRootPane</code> is
	 * contained in. This will return null if there is no parent ancestor
	 * of the <code>JRootPane</code>.
	 * 
	 * @uml.property name="window"
	 */
	private Window getWindow() {
		return window;
	}

    /**
     * Returns the String to display as the title.
     */
    private String getTitle() {
        Window w = getWindow();

        if (w instanceof Frame) {
            return ((Frame) w).getTitle();
        } else if (w instanceof Dialog) {
            return ((Dialog) w).getTitle();
        }

        return null;
    }

    public boolean isSelected() {
        Window window = getWindow();

        return (window == null) ? true : window.isActive();
    }

    public boolean isFrameMaximized() {
        Frame frame = getFrame();

        if (frame != null) {
            return ((frame.getExtendedState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH);
        }

        return false;
    }

    /**
     * Renders the TitlePane.
     */
    public void paintComponent(Graphics g) {
        if (getFrame() != null) {
            setState(getFrame().getExtendedState());
        }

        Window frame = getWindow();

        boolean leftToRight = frame.getComponentOrientation().isLeftToRight();
        boolean isSelected = isSelected();

        if (isSelected) {
            prevState = true;
        }

        if (!prevState && !isSelected) {
            isSelected = true;
        }

        int width = getWidth();
        int height = getHeight();

        drawLiquidCaption(g, isSelected, width, height);

        int titleLength = 0;
        int xOffset = leftToRight ? 2 : (width - 2);
        String frameTitle = getTitle();

        if (frameTitle != null) {
            Font f = getFont();
            g.setFont(f);

            FontMetrics fm = g.getFontMetrics();
            titleLength = fm.stringWidth(frameTitle);

            // a shadow effect for the titles in normal sized internal frames
            int yOffset = ((height - fm.getHeight()) / 2) + fm.getAscent() + 1;

            if (!leftToRight) {
                xOffset -= titleLength;
            }

            xOffset = (width / 2) - (titleLength / 2);

            if (isSelected) {
                // for an active window
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint grad = new GradientPaint(xOffset +
                        (titleLength / 2), yOffset - 15,
                        new Color(60, 144, 233), xOffset + (titleLength / 2),
                        fm.getHeight() + 6, new Color(102, 186, 255));
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(grad);

                //g2.fillRoundRect(xOffset-8, yOffset-15, titleLength+15, fm.getHeight()+1, 18, 18);
                g2.fillRoundRect(xOffset - 8, yOffset - 15, titleLength + 15,
                    height - 6, 18, 18);
                g.setColor(new Color(0, 78, 167));

                //g2.drawRoundRect(xOffset-8, yOffset-15, titleLength+15, fm.getHeight()+1, 18, 18);
                g2.drawRoundRect(xOffset - 8, yOffset - 15, titleLength + 15,
                    height - 6, 18, 18);

                g.setColor(Color.black);
                g.drawString(frameTitle, xOffset + 1, yOffset);

                g.setColor(normalTitleColor);
                g.drawString(frameTitle, xOffset, yOffset - 1);

                xOffset += (leftToRight ? (titleLength + 2) : (-2));
            } else {
                // for an inactive window
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint grad = new GradientPaint(xOffset +
                        (titleLength / 2), yOffset - 15,
                        new Color(191, 211, 233), xOffset + (titleLength / 2),
                        fm.getHeight() + 6, new Color(233, 253, 255));
                g2.setPaint(grad);

                //g2.fillRoundRect(xOffset-8, yOffset-15, titleLength+15, fm.getHeight()+1, 18, 18);
                g2.fillRoundRect(xOffset - 8, yOffset - 15, titleLength + 15,
                    height - 6, 18, 18);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(new Color(125, 145, 167));

                //g2.drawRoundRect(xOffset-8, yOffset-15, titleLength+15, fm.getHeight()+1, 18, 18);
                g2.drawRoundRect(xOffset - 8, yOffset - 15, titleLength + 15,
                    height - 6, 18, 18);

                g.setColor(Color.black);
                g.drawString(frameTitle, xOffset, yOffset - 1);

                xOffset += (leftToRight ? (titleLength + 2) : (-2));
            }
        }
    }

    private void drawLiquidCaption(Graphics g, boolean isSelected, int w, int h) {
        Color c = isSelected ? new Color(62, 145, 235) : new Color(175, 214, 255);
        g.setColor(c);
        g.fillRect(0, 0, w, h - 1);
        c = isSelected ? new Color(94, 172, 255) : new Color(226, 240, 255);
        g.setColor(c);
        g.drawLine(0, 0, w, 0);
        c = isSelected ? new Color(60, 141, 228) : new Color(170, 207, 247);
        g.setColor(c);
        g.drawLine(0, 1, w, 1);

        for (int i = 4; i < (h - 1); i += 4) {
            c = isSelected ? new Color(59, 138, 223) : new Color(166, 203, 242);
            g.setColor(c);
            g.drawLine(0, i, w, i);
            c = isSelected ? new Color(60, 141, 228) : new Color(170, 207, 247);
            g.setColor(c);
            g.drawLine(0, i + 1, w, i + 1);
        }

        c = isSelected ? new Color(47, 111, 180) : new Color(135, 164, 196);
        g.setColor(c);
        g.drawLine(0, h - 1, w, h - 1);
    }

    /**
     * Actions used to <code>close</code> the <code>Window</code>.
     */
    private class CloseAction extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CloseAction() {
            super("Close");
        }

        public void actionPerformed(ActionEvent e) {
            close();
        }
    }

    /**
     * Actions used to <code>iconfiy</code> the <code>Frame</code>.
     */
    private class IconifyAction extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public IconifyAction() {
            super("Minimize");
        }

        public void actionPerformed(ActionEvent e) {
            iconify();
        }
    }

    /**
     * Actions used to <code>restore</code> the <code>Frame</code>.
     */
    private class RestoreAction extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RestoreAction() {
            super("Restore");
        }

        public void actionPerformed(ActionEvent e) {
            restore();
        }
    }

    /**
     * Actions used to <code>restore</code> the <code>Frame</code>.
     */
    private class MaximizeAction extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MaximizeAction() {
            super("Maximize");
        }

        public void actionPerformed(ActionEvent e) {
            maximize();
        }
    }

    /**
     * Class responsible for drawing the system menu. Looks up the
     * image to draw from the Frame associated with the
     * <code>JRootPane</code>.
     */
    private class SystemMenuBar extends JMenuBar implements MouseListener {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JPopupMenu systemMenu;
        private boolean isShowed = false;

        public SystemMenuBar(JPopupMenu menu) {
            super();
            systemMenu = menu;
            addMouseListener(this);
        }

        protected void setSystemMenuVisible(boolean b) {
            isShowed = b;
        }

        public void paint(Graphics g) {
            Frame frame = getFrame();
            Image image = (frame != null) ? frame.getIconImage() : null;

            if (image != null) {
                g.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
            } else {
                Icon icon = UIManager.getIcon("InternalFrame.icon");

                if (icon != null) {
                    icon.paintIcon(this, g, 0, 0);
                }
            }
        }

        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        public Dimension getPreferredSize() {
            Icon icon = UIManager.getIcon("InternalFrame.icon");

            if (icon != null) {
                return new Dimension(icon.getIconWidth(), icon.getIconHeight());
            }

            Dimension size = super.getPreferredSize();

            return new Dimension(Math.max(IMAGE_WIDTH, size.width),
                Math.max(size.height, IMAGE_HEIGHT));
        }

        public void mouseClicked(MouseEvent e) {
            if (!isShowed) {
                systemMenu.show(this, 0, 18);
                isShowed = true;
            } else {
                isShowed = false;
                systemMenu.setVisible(isShowed);
            }
        }

        public void mouseEntered(MouseEvent e) {
            if (!systemMenu.isVisible()) {
                isShowed = false;
            }
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    /**
     * This inner class is marked &quot;public&quot; due to a compiler bug.
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <Foo>.
     */
    private class TitlePaneLayout implements LayoutManager {
        public void addLayoutComponent(String name, Component c) {
        }

        public void removeLayoutComponent(Component c) {
        }

        public Dimension preferredLayoutSize(Container c) {
            int height = computeHeight();

            return new Dimension(height, height);
        }

        public Dimension minimumLayoutSize(Container c) {
            return preferredLayoutSize(c);
        }

        private int computeHeight() {
            if (getFrame() instanceof JFrame) {
                setMaximizeBounds(getFrame());

                return 25;
            } else {
                return 25;
            }
        }

        public void layoutContainer(Container c) {
            if (getWindowDecorationStyle() == JRootPane.NONE) {
                return;
            }

            boolean leftToRight = (window == null)
                ? getRootPane().getComponentOrientation().isLeftToRight()
                : window.getComponentOrientation().isLeftToRight();

            int w = getWidth();
            int x;
            int spacing;
            int buttonHeight;
            int buttonWidth;

            if (closeButton != null) {
                buttonHeight = closeButton.getPreferredSize().height;
                buttonWidth = closeButton.getPreferredSize().width;
            } else {
                buttonHeight = IMAGE_HEIGHT;
                buttonWidth = IMAGE_WIDTH;
            }

            int y = ((getHeight() - buttonHeight) / 2) + 1;

            //if (Theme.derivedStyle[Theme.style] == Theme.WIN_STYLE) {
            //    y += 1;
            //}
            // assumes all buttons have the same dimensions
            // these dimensions include the borders
            x = leftToRight ? w : 0;

            spacing = 0;
            x = leftToRight ? spacing : (w - buttonWidth - spacing);

            if (menuBar != null) {
                // this is a JFrame
                menuBar.setBounds(x, y, buttonWidth, buttonHeight);
            }

            x = leftToRight ? w : 0;
            x += (leftToRight ? (-spacing - buttonWidth) : spacing);

            if (closeButton != null) {
                closeButton.setBounds(x, y, buttonWidth, buttonHeight);
            }

            if (!leftToRight) {
                x += buttonWidth;
            }

            if (Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
                if (toggleButton.getParent() != null) {
                    x += (leftToRight ? (-spacing - buttonWidth) : spacing);
                    toggleButton.setBounds(x, y, buttonWidth, buttonHeight);

                    if (!leftToRight) {
                        x += buttonWidth;
                    }
                }
            }

            if ((iconifyButton != null) && (iconifyButton.getParent() != null)) {
                x += (leftToRight ? (-spacing - buttonWidth) : spacing);
                iconifyButton.setBounds(x, y, buttonWidth, buttonHeight);

                if (!leftToRight) {
                    x += buttonWidth;
                }
            }
        }
    }

    /**
     * PropertyChangeListener installed on the Window. Updates the necessary
     * state as the state of the Window changes.
     */
    private class PropertyChangeHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent pce) {
            String name = pce.getPropertyName();

            // Frame.state isn't currently bound.
            if ("resizable".equals(name) || "state".equals(name)) {
                Frame frame = getFrame();

                if (frame != null) {
                    setState(frame.getExtendedState(), true);
                }

                if ("resizable".equals(name)) {
                    getRootPane().repaint();
                }
            } else if ("title".equals(name)) {
                repaint();
            } else if ("componentOrientation".equals(name)) {
                revalidate();
                repaint();
            }
        }
    }

    /**
     * WindowListener installed on the Window, updates the state as necessary.
     */
    private class WindowHandler extends WindowAdapter {
        public void windowActivated(WindowEvent ev) {
            setActive(true);
        }

        public void windowDeactivated(WindowEvent ev) {
            setActive(false);
        }
    }

    class WindowMoveListener extends ComponentAdapter {
        /**
         * @see java.awt.event.ComponentListener#componentMoved(ComponentEvent)
         */
        public void componentMoved(ComponentEvent e) {
            if (getWindowDecorationStyle() == JRootPane.NONE) {
                return;
            }

            Window w = getWindow();
            w.repaint(0, 0, w.getWidth(), 5);
        }

        /**
         * @see java.awt.event.ComponentAdapter#componentResized(ComponentEvent)
         */
        public void componentResized(ComponentEvent e) {
            if (getWindowDecorationStyle() == JRootPane.NONE) {
                return;
            }

            Window w = getWindow();
            w.repaint(0, 0, w.getWidth(), 5);
        }
    }
}