//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;

package syntaxTree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextHitInfo;
import java.awt.font.TextLayout;
import java.awt.font.TransformAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;
import java.awt.im.InputMethodRequests;
import java.io.IOException;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import staticFunctions.Sizer;
import userInterface.UserInternalFrame;
import enumerators.SyntacticFeatureType;
import enumerators.SyntacticLevel;
import enumerators.SyntacticStructureType;

/**
 *
 * @author Donald Derrick
 * @version 0.1 <br>
 *          date: 19-Aug-2004 <br>
 *          <br>
 *          A subclass of JComponent with editable Graphics2D text. <br>
 *          This type of class became necessary to accomplish easy printing and
 *          picture exporting. This class contains important listeners that
 *          communicate hit test and key listeners. Also, there are commands for
 *          detecting and relaying text width.
 */
public class EditableComponent extends JComponent {

    private static final long serialVersionUID = 1L;

    private int mTextHeight;

    private int mTextWidth;

    private boolean mCaratTimer;

    private UserInternalFrame mUserInternalFrame;

    private TextLayout mTextLayoutHead;

    private AttributedString mHead;

    private int mHeadLength;

    private boolean mOver;

    private boolean mCarat;

    private int mRealTextWidth;

    private int mInsertionIndex;

    private int mHighlightBegin;

    private int mHighlightEnd;

    private Color mFontColor;

    private Color mBackgroundColor;

    private int mDelete;

    private SyntaxFacade mSyntaxFacade;

    private static final Color STRONG_CARET_COLOR = Color.black;

    private static final Color WEAK_CARET_COLOR = Color.black;

    private static final Color HIGHLIGHT_COLOR = new Color(36, 139, 192, 90);

    private static final Color TEXT_HIGHLIGHT_COLOR = new Color(0, 0, 255, 80);

    private static final Color NULL_COLOR = new Color(223, 61, 51, 90);

    private static final Color FEATURE_COLOR = new Color(50, 171, 90, 90);

    private static final Color ASSOCIATION_COLOR = new Color(166, 127, 190, 90);

    private class UserDropTarget extends DropTarget
    implements DropTargetListener, Serializable {

        private static final long serialVersionUID = 1L;
        public void dragOver(DropTargetDragEvent dtde)
        {
            setClicked(pointTestXY(dtde.getLocation().x,dtde.getLocation().y),1,dtde.getSource());
            //System.out.println("here");
            repaint();
        }
        //@SupressWarnings("unchecked")
        public void drop(DropTargetDropEvent dtde) {
            setClicked(pointTestXY(dtde.getLocation().x,dtde.getLocation().y),1,dtde.getSource());
            Object lObject;
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                lObject = dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
                if (lObject instanceof String)
                {
                    //System.out.println("item dropped");
                    deleteHead();
                    AttributedString lAT = new AttributedString((String)lObject);
                    lAT.addAttributes(mUserInternalFrame.getAttributes(), 0, ((String)lObject).length());
                    insertHead(lAT, getInsertionIndex());
                    setInsertionIndex(getInsertionIndex() + ((String)lObject).length());
                    setHighlightBegin(getInsertionIndex());
                    setHighlightEnd(getInsertionIndex());
                    mUserInternalFrame.getSyntaxFacade().displayTree();
                }
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private class UserInputMethodListener implements InputMethodListener{

        public void caretPositionChanged(InputMethodEvent arg0) {
        }

        //@SupressWarnings("unchecked")
        public void inputMethodTextChanged(InputMethodEvent event) {
            int start = event.getText().getBeginIndex();
            int end = event.getText().getEndIndex();
            int commit = event.getCommittedCharacterCount();
            //System.out.println("start = " + start);
            //System.out.println("end = " + end);
            //System.out.println("commit = " + commit);
            deleteHead();
            if(mDelete > 0)
            {
                setHighlightBegin(getInsertionIndex());
                setHighlightEnd(getInsertionIndex() - mDelete);
                deleteHead();
            }
            mDelete = end - commit;
            if(start != end)
            {
                AttributedString lAT = new AttributedString(event.getText());
                lAT.addAttributes(mUserInternalFrame.getAttributes(), start, end);
                insertHead(lAT, getInsertionIndex());
                setInsertionIndex(getInsertionIndex() + (end-start));
                setHighlightBegin(getInsertionIndex());
                setHighlightEnd(getInsertionIndex());
                mUserInternalFrame.getSyntaxFacade().displayTree();
            }
        }
    }
    private class UserKeyListener implements KeyListener {

        public void keyPressed(KeyEvent pKE) {

            int location = pKE.getKeyCode();
            if (location == KeyEvent.VK_LEFT) {

                setInsertionIndex(getInsertionIndex() - 1);
                setHighlightEnd(getInsertionIndex());
                if ((pKE.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0)
                {
                    setCarat(true);
                    setHighlightBegin(getInsertionIndex());
                }
                else
                {
                    setCarat(false);
                }
                setHeadObservers();
                repaint();
            } else if (location == KeyEvent.VK_RIGHT) {
                setInsertionIndex(getInsertionIndex() + 1);

                setHighlightEnd(getInsertionIndex());
                if ((pKE.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == 0)
                {
                    setHighlightBegin(getInsertionIndex());
                    setCarat(true);
                }
                else
                {
                    setCarat(false);
                }
                setHeadObservers();
                repaint();

            } else if (location == KeyEvent.VK_DELETE) {
                if (getHighlightBegin() == getHighlightEnd()) {
                    setHighlightEnd(getHighlightBegin() + 1);
                }
                deleteHead();
                mUserInternalFrame.getSyntaxFacade().displayTree();
            } else if (location == KeyEvent.VK_BACK_SPACE) {
                if (getHighlightBegin() == getHighlightEnd()) {
                    setHighlightBegin(getInsertionIndex() - 1);
                }
                deleteHead();
                mUserInternalFrame.getSyntaxFacade().displayTree();
            } else if (pKE.getSource() instanceof SyntacticStructure) {
                SyntacticStructure ss = (SyntacticStructure) pKE.getSource();
                UserInternalFrame uif = ss.getUserInternalFrame();
                SyntaxFacade sf = uif.getSyntaxFacade();
                if (location == KeyEvent.VK_F1) {
                    try {
                        sf.addSyntacticStructure(SyntacticStructureType.HEAD,
                            uif, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F2) {
                    try {
                        sf.addSyntacticStructure(SyntacticStructureType.PHRASE,
                            uif, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F3) {
                    try {
                        sf.addSyntacticStructure(SyntacticStructureType.MORPH,
                            uif, ss);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F4) {
                    try {
                        sf.addSyntacticStructure(
                            SyntacticStructureType.TRIANGLE, uif, ss);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F5) {
                    try {
                        sf.addSyntacticFeatureToStructure(
                            SyntacticFeatureType.CASE, uif, ss);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F6) {
                    try {
                        sf.addSyntacticFeatureToStructure(
                            SyntacticFeatureType.THETA, uif, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F7) {
                    try {
                        sf.addSyntacticFeatureToStructure(
                            SyntacticFeatureType.FEATURE, uif, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F8) {
                    try {
                        sf.addSyntacticStructure(SyntacticStructureType.UNARY,
                            uif, ss);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F9) {
                    try {
                        sf.addSyntacticStructure(SyntacticStructureType.BINARY,
                            uif, ss);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F10) {
                    try {
                        sf.addSyntacticStructure(
                            SyntacticStructureType.TRINARY, uif, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F11) {
                    try {
                        sf.addSyntacticStructure(
                            SyntacticStructureType.ADJUNCT, uif, ss);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (location == KeyEvent.VK_F12) {
                    try {
                        sf.addSyntacticStructure(SyntacticStructureType.X_BAR,
                            uif, ss);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }

        }

        /**
         * nothing
         */
        public void keyReleased(KeyEvent pKE) {
            //System.out.println("key released");
        }

        /**
         * if the key is NOT a CTRL masked key, and not a delete or backspace
         * key, insert the text, with the current observed text attributes, into
         * the visible textstring. Then redisplay the tree.
         */
        //@SupressWarnings("unchecked")
        public void keyTyped(KeyEvent pKE) {
            //System.out.println("key typed");
            int location = pKE.getKeyChar();
            setCarat(true);
            if (location != KeyEvent.VK_BACK_SPACE && location != KeyEvent.VK_DELETE &&
                location != KeyEvent.VK_ENTER
                && (pKE.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == 0
                && (pKE.getModifiersEx() & InputEvent.META_DOWN_MASK) == 0) {
                // System.out.println
                deleteHead();
                AttributedString lAT = new AttributedString(String.valueOf(pKE
                    .getKeyChar()));
                lAT.addAttributes(mUserInternalFrame.getAttributes(), 0, 1);
                insertHead(lAT, getInsertionIndex());
                setInsertionIndex(getInsertionIndex() + 1);
                setHighlightBegin(getInsertionIndex());
                setHighlightEnd(getInsertionIndex());
                mUserInternalFrame.getSyntaxFacade().displayTree();
            }
        }
    }

    private class HitTestMouseListener implements MouseInputListener {

        private boolean mMove;

        private boolean mTrace;

        public void mouseClicked(MouseEvent pME) {
            setClicked(pointTest(pME),pME.getClickCount(),pME.getSource());
        }

        public void mouseEntered(MouseEvent arg0) {
            if (mUserInternalFrame.getCursor().getType() != Cursor.HAND_CURSOR)
            {
                Cursor lMoveCursor = new Cursor(Cursor.TEXT_CURSOR);
                mUserInternalFrame.setCursor(lMoveCursor);
            }
            setOver(true);
            if (arg0.getSource() instanceof SyntacticFeature) {
                SyntacticFeature hold = (SyntacticFeature) arg0.getSource();

                for (int i = 0; i < hold.getSyntacticAssociation().size(); i++) {
                    SyntacticAssociation w = (SyntacticAssociation) hold
                        .getSyntacticAssociation().get(i);
                    w.setOver(true);
                    w.repaint();
                }
            }
            if (arg0.getSource() instanceof SyntacticAssociation) {
                SyntacticAssociation hold1 = (SyntacticAssociation) arg0
                    .getSource();
                SyntacticFeature hold = hold1.getSyntacticFeature();
                hold.setOver(true);
                hold.repaint();
                for (int i = 0; i < hold.getSyntacticAssociation().size(); i++) {
                    SyntacticAssociation w = (SyntacticAssociation) hold
                        .getSyntacticAssociation().get(i);
                    w.setOver(true);
                    w.repaint();
                }
            }
            repaint();
        }

        public void mouseExited(MouseEvent arg0) {
            if (mUserInternalFrame.getCursor().getType() != Cursor.HAND_CURSOR)
            {
                Cursor lMoveCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                mUserInternalFrame.setCursor(lMoveCursor);
            }
            if (arg0.getSource() instanceof SyntacticFeature) {
                SyntacticFeature hold = (SyntacticFeature) arg0.getSource();
                for (int i = 0; i < hold.getSyntacticAssociation().size(); i++) {
                    SyntacticAssociation w = (SyntacticAssociation) hold
                        .getSyntacticAssociation().get(i);
                    w.setOver(false);
                    w.repaint();
                }
            }
            if (arg0.getSource() instanceof SyntacticAssociation) {
                SyntacticAssociation hold1 = (SyntacticAssociation) arg0
                    .getSource();
                SyntacticFeature hold = hold1.getSyntacticFeature();
                hold.setOver(false);
                hold.repaint();
                for (int i = 0; i < hold.getSyntacticAssociation().size(); i++) {
                    SyntacticAssociation w = (SyntacticAssociation) hold
                        .getSyntacticAssociation().get(i);
                    w.setOver(false);
                    w.repaint();
                }
            }
            setOver(false);
            repaint();
        }

        public void mousePressed(MouseEvent e) {
            if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0) {
                /* shift click and drag to add an association (like theta roles) */
                mMove = true;
                Cursor lMoveCursor = new Cursor(Cursor.HAND_CURSOR);
                mUserInternalFrame.setCursor(lMoveCursor);
            } else if ((e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0) {
                /* alt+click and drag to add a movement line */
                mTrace = true;
                Cursor lMoveCursor = new Cursor(Cursor.HAND_CURSOR);
                setCursor(lMoveCursor);
                mUserInternalFrame.setCursor(lMoveCursor);
            } else {
                if ((e.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) != 0
                    || (e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
                    getUserInternalFrame().getSyntaxFacade().getPopupMenu(
                        e.getSource()).show(e.getComponent(), e.getX(),
                            e.getY());
                }
                setOver(false);
                int lI = pointTest(e);
                setInsertionIndex(lI);
                setHighlightBegin(lI);
                setHighlightEnd(lI);
                // Repaint the Component so the new caret(s) will be displayed.
                mUserInternalFrame.getObservableClipboard().setValue(
                    e.getSource());
                mUserInternalFrame.getObservableClipboard().setIndex(
                    getInsertionIndex());
                setHeadObservers();
                setCarat(true);
                requestFocus(true);
                repaint();
            }
        }

        /**
         *
         * @param pME
         *            the mouse event you wish to test
         * @return the current insertion index (which letter) <br>
         *         <br>
         *         First you check the mouse click position relative to the
         *         center of the textlayout <br>
         *         Then you adjust the clickX and clickY position in relation to
         *         the scale of the object <br>
         *         Then you test the hit location in an unscaled environment,
         *         and return the correct index.
         */

        /**
         * if the move flag is on, releasing the mouse is the completion of a
         * move or association event <br>
         * In this case, it is important to test if the mouse was released over
         * a potention parent node. <br>
         * If yes, then test to see if a structure was moved. <br>
         * If yes, call the moveSyntacticStructure method <br>
         * If no, call the associateSyntacticStructure method. <br>
         * If the move flag was NOT on, then just set the final highlight, and
         * repaint. <br>
         */
        public void mouseReleased(MouseEvent pME) {
            Point containerPoint = SwingUtilities.convertPoint((Component) pME
                .getSource(), pME.getPoint(), mUserInternalFrame
                .getContentPane());

            if (mMove) {
                Component hold = mUserInternalFrame
                    .getSyntaxFacade().getUnder(containerPoint,
                        pME.getSource(),false);
                if (hold != null)
                {
                    ((EditableComponent) hold).setOver(false);
                    hold.repaint();
                    ((EditableComponent) pME.getSource()).setOver(false);
                    ((Component) pME.getSource()).repaint();
                }
                if (mUserInternalFrame
                    .getSyntaxFacade().getUnder(containerPoint,
                        pME.getSource(),false) instanceof SyntacticStructure)
                {

                    SyntacticStructure lSSParent = (SyntacticStructure) mUserInternalFrame
                        .getSyntaxFacade().getUnder(containerPoint,
                            pME.getSource(),(pME.getSource() instanceof SyntacticStructure ? false:true));
                    if (lSSParent != null && pME.getSource() instanceof SyntacticStructure) {
                        mUserInternalFrame.getSyntaxFacade()
                        .moveSyntacticStructure(
                            lSSParent,
                            (SyntacticStructure) pME
                            .getSource());
                    }
                    else if (lSSParent != null && pME.getSource() instanceof SyntacticFeature) {
                        mUserInternalFrame.getSyntaxFacade()
                        .associateSyntacticFeature(lSSParent,
                            (SyntacticFeature) pME.getSource());
                    }
                    else
                    {
                        mUserInternalFrame.getSyntaxFacade().displayTree();
                    }
                }
                else
                {
                    mUserInternalFrame.getSyntaxFacade().displayTree();
                }
                Cursor lDefaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                mUserInternalFrame.setCursor(lDefaultCursor);
            }
            else if (mTrace)
            {
                containerPoint = SwingUtilities.convertPoint(
                    (Component) pME.getSource(), pME.getPoint(),
                    mUserInternalFrame.getContentPane());
                Component hold = mUserInternalFrame
                    .getSyntaxFacade().getUnder(containerPoint,
                        pME.getSource(),true);
                if (hold != null)
                {
                    ((EditableComponent) hold).setOver(false);
                    hold.repaint();
                    ((EditableComponent) pME.getSource()).setOver(false);
                    ((Component) pME.getSource()).repaint();
                }
                if (hold instanceof SyntacticAssociation)
                {
                    hold = ((SyntacticAssociation)hold).getSyntacticStructure();
                }
                if (hold instanceof SyntacticFeature)
                {
                    hold = ((SyntacticFeatureSet)((SyntacticFeature)hold).getSyntacticFeatureSet()).getSyntacticStructure();
                }
                if (hold instanceof SyntacticStructure)
                {
                    SyntacticStructure lSSEnd = (SyntacticStructure) hold;
                    if (lSSEnd != null && pME.getSource() instanceof SyntacticStructure) {
                        mUserInternalFrame.getSyntaxFacade().addTrace(
                            lSSEnd,
                            (SyntacticStructure) pME.getSource());
                    }
                }
                mUserInternalFrame.getSyntaxFacade().displayTree();

                Cursor lDefaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                mUserInternalFrame.setCursor(lDefaultCursor);
            }

            else
            {
                setHighlightEnd(pointTest(pME));
                repaint();
            }
            mMove = false;
            mTrace = false;
        }

        /**
         * test to see if the SHIFT key is pressed. if it is, test to see what
         * subclass of EditableComponent was called. <br>
         * If it is a SyntacticStructure, translate the Structure (move all of
         * it's children, features, and associations with it). <br>
         * If it is a SyntacticFeature, translate the feature only. <br>
         * Otherwise the mouseDragged event is an attempt to highlight text, so
         * reset the highlightEnd position based on the results of a pointTest.
         */
        public void mouseDragged(MouseEvent pME) {

            if ((pME.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0) {
                Point containerPoint = SwingUtilities.convertPoint((Component) pME
                    .getSource(), pME.getPoint(), mUserInternalFrame
                    .getContentPane());
                Component hold = mUserInternalFrame
                    .getSyntaxFacade().getUnder(containerPoint,
                        pME.getSource(),(pME.getSource() instanceof SyntacticStructure ? false:true));
                if (hold != null)
                {
                    mUserInternalFrame.getSyntaxFacade().setHighlight(
                        hold);
                }
                if (pME.getSource() instanceof SyntacticStructure) {
                    mUserInternalFrame.getSyntaxFacade()
                    .translateSyntacticSubtree(
                        (SyntacticStructure) pME.getSource(), pME);
                }
                if (pME.getSource() instanceof SyntacticFeature) {
                    mUserInternalFrame.getSyntaxFacade()
                    .translateSyntacticFeature(
                        (SyntacticFeature) pME.getSource(), pME);
                }
            }
            else if ((pME.getModifiersEx() & InputEvent.ALT_DOWN_MASK) != 0
                && pME.getSource() instanceof SyntacticStructure) {
                Point containerPoint = SwingUtilities.convertPoint((Component) pME
                    .getSource(), pME.getPoint(), mUserInternalFrame
                    .getContentPane());
                Component hold = mUserInternalFrame
                    .getSyntaxFacade().getUnder(containerPoint,
                        pME.getSource(),true);
                if (hold != null )
                {
                    mUserInternalFrame.getSyntaxFacade().setHighlight(
                        hold);
                }
                mUserInternalFrame.getSyntaxFacade()
                .translateSyntacticStructure(
                    (SyntacticStructure) pME.getSource(), pME);
            }
            else {
                setOver(false);
                setCarat(false);
                setHighlightEnd(pointTest(pME));
                repaint();
            }
        }

        public void mouseMoved(MouseEvent arg0) {

        }
    }

    /**
     * Constructor
     *
     * @param pUserInternalFrame
     *            The UserInternalFrame that will hold this EditableComponent
     *            <br>
     *            This constructor sets default heads, and adds the mouse,
     *            mousemotion, and key listeners.
     */
    public EditableComponent(UserInternalFrame pUserInternalFrame) {
        setUserInternalFrame(pUserInternalFrame);
        setSyntaxFacade(pUserInternalFrame.getSyntaxFacade());
        mHead = new AttributedString(" ");
        Font lFont = new Font("Doulos SIL", Font.BOLD, getUserInternalFrame()
            .getProperties().getDefaultFontSize());
        mHead.addAttribute(TextAttribute.FONT, lFont);
        DropTarget dtl = (DropTarget) new UserDropTarget();
        this.setDropTarget(dtl);
        HitTestMouseListener hitTest = new HitTestMouseListener();
        this.addMouseListener(hitTest);
        this.addMouseMotionListener(hitTest);
        UserKeyListener key = new UserKeyListener();
        this.addKeyListener(key);
        this.addInputMethodListener(new UserInputMethodListener());
        int delay = 500;
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mCaratTimer = !mCaratTimer;
                if (getCarat() == true) {
                    repaint();
                }
            }
        };
        new Timer(delay, taskPerformer).start();

    }

    /**
     * @param pG
     *            The graphics object passed into the paint command (usually the
     *            same as the graphics object for this Component) <br>
     *            <br>
     *            This component makes a point based on the text width and
     *            height, <br>
     *            Then it makes a scaled point based on the screen scale. <br>
     *            Then it passes the scaling to the graphics object to the
     *            component scales the vector drawing correctly. <br>
     *            The color and anti-aliasing settings are then turned on <br>
     *            Then the textLayout is generated and rendered in the graphics
     *            object <br>
     *            If this box is highlighted, a light blue highlight is drawn
     *            filling the entire box <br>
     *            If the carat is turned on, a black line is drawn appropriately
     *            within the text <br>
     *            Lastly, if text is highlighted, the colors for that text are
     *            inverted using the XOR painting call <br>
     */
    public void paint(Graphics pG) {
        Graphics lGraphics = pG;
        Graphics2D lGraphics2D = (Graphics2D) lGraphics;
        // get the dimension of the button
        Float lPoint2D = new Point2D.Float(this.getTextWidth(), this
            .getTextHeight());

        Float lPoint2DScaled = new Point2D.Float((int) (lPoint2D.getX()
            * Sizer.scaleWidth() * this.getUserInternalFrame().getScale()),
            (int) (lPoint2D.getY() * Sizer.scaleHeight() * this
                .getUserInternalFrame().getScale()));

        lGraphics2D.scale(Sizer.scaleWidth()
            * this.getUserInternalFrame().getScale(), Sizer.scaleHeight()
            * this.getUserInternalFrame().getScale());
        // set the font
        // Set the g2D to antialias.
        lGraphics2D.setColor(Color.BLACK);
        lGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        lGraphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        // get the font render context.
        FontRenderContext lFrc = lGraphics2D.getFontRenderContext();
        // create a textlayout from the font, string, and font render context.
        mTextLayoutHead = new TextLayout(this.getHead().getIterator(), lFrc);
        // draw the font
        mTextLayoutHead.draw(lGraphics2D, (float) (getZero(this.getTextWidth()
            - mTextLayoutHead.getBounds().getWidth()) / 2),
            (float) mTextLayoutHead.getAscent());

        // highlighting
        this.setSize((int) lPoint2DScaled.getX(), (int) lPoint2DScaled.getY());
        if (this.getOver()) {
            lGraphics2D.setColor(HIGHLIGHT_COLOR);
            if (this instanceof SyntacticStructure) {
                SyntacticStructure hold = (SyntacticStructure) this;
                if (hold.getSyntacticLevel() == SyntacticLevel.NULL) {
                    lGraphics2D.setColor(NULL_COLOR);
                }
            }
            if (this instanceof SyntacticFeature) {
                lGraphics2D.setColor(FEATURE_COLOR);
            }
            if (this instanceof SyntacticAssociation) {
                lGraphics2D.setColor(ASSOCIATION_COLOR);
            }
            lGraphics2D.fillRect(0, 0, (int) lPoint2D.getX(), (int) lPoint2D
                .getY());
        }
        if (getHighlightBegin() != getHighlightEnd()) {
            Shape lHilite = mTextLayoutHead.getLogicalHighlightShape(
                getHighlightBegin(), getHighlightEnd());
            lGraphics2D.setColor(TEXT_HIGHLIGHT_COLOR);
            float rx = (float) (getZero(this.getTextWidth()
                - mTextLayoutHead.getBounds().getWidth()) / 2);
            float ry = (float) (mTextLayoutHead.getAscent());
            AffineTransform at = AffineTransform.getTranslateInstance(rx,
                ry);
            lHilite = at.createTransformedShape(lHilite);
            lGraphics2D.fill(lHilite);
        }
        if (this.getCarat() && mCaratTimer && isEnabled()) {
            lGraphics2D.translate(getZero(this.getTextWidth()
                - mTextLayoutHead.getBounds().getWidth()) / 2,
                mTextLayoutHead.getAscent());
            Shape[] carets = mTextLayoutHead
                .getCaretShapes(getInsertionIndex());

            // Draw the carets. carets[0] is the strong caret, and
            // is never null. carets[1], if it is not null, is the
            // weak caret.
            lGraphics2D.setColor(STRONG_CARET_COLOR);
            lGraphics2D.draw(carets[0]);

            if (carets[1] != null) {
                lGraphics2D.setColor(WEAK_CARET_COLOR);
                lGraphics2D.draw(carets[1]);
            }
        }
    }

    /**
     * @param d
     * @return
     */
    private double getZero(double d) {
        if (d < 0) {
            return 0;
        } else {
            return d;
        }
    }

    /**
     *
     * @param pWidth
     *            Sets the text width as discovered in the testXY method.
     */
    public void setTextWidth(int pWidth) {
        mTextWidth = pWidth;
    }

    /**
     *
     * @return Returns the width of the displayed text as the advance.
     */
    public int getTextWidth() {
        return mTextWidth;
    }

    /**
     *
     * @param pTextHeight
     *            Sets the text height as discovered in the testXY method.
     */
    public void setTextHeight(int pTextHeight) {
        mTextHeight = pTextHeight;

    }

    /**
     *
     * @return Returns the text height as the ascent and descent.
     */
    public int getTextHeight() {
        return mTextHeight;
    }

    /**
     *
     * @param pUserInternalFrame
     *            Sets the InternalFrame for this component - for convenience
     *
     */
    public void setUserInternalFrame(UserInternalFrame pUserInternalFrame) {
        mUserInternalFrame = pUserInternalFrame;
    }

    /**
     *
     * @return Returns the InternalFrame for this component
     */
    public UserInternalFrame getUserInternalFrame() {
        return mUserInternalFrame;
    }

    /**
     *
     * @return Sets the text (AttributedString) for this box
     */
    public AttributedString getHead() {
        return mHead;
    }

    /**
     *
     * @param pHead
     *            All insertion of heads (text) involves recalibrating the size
     *            and setting the headlength value.
     */
    public void setHead(AttributedString pHead) {
        mHead = pHead;
        AttributedCharacterIterator lIterator = getHead().getIterator();
        int lI = 0;
        for (char c = lIterator.first(); c != CharacterIterator.DONE; c = lIterator
            .next()) {
            lI++;
        }
        testXY();
        setHeadLength(lI);
        // mUserInternalFrame.getSyntaxFacade().displayTree();
    }

    /**
     *
     * @param pHeadLength
     *            Sets the number of characters in the text
     */
    public void setHeadLength(int pHeadLength) {
        mHeadLength = pHeadLength;
    }

    /**
     *
     * @return Returns the number of characters in the text.
     */
    public int getHeadLength() {
        return mHeadLength;
    }

    /**
     *
     * @return gets whether highlighting of the box is turned on or not.
     */
    public boolean getOver() {
        return mOver;
    }

    /**
     *
     * @param pB
     *            sets whether the box is highlighted blue or not.
     */
    public void setOver(boolean pB) {
        mOver = pB;
    }

    /**
     *
     * @return Returns a boolean indicating whether the Carat is visible or not.
     */
    private boolean getCarat() {
        return mCarat;
    }

    /**
     *
     * @param pCarat
     *            Sets the visibility of the Carat.
     */
    public void setCarat(boolean pCarat) {
        mCarat = pCarat;
    }

    /**
     *
     * @param pWidth
     *            Sets the real text width, which may be different from the
     *            artificially minimum 30 pixel text width.
     */
    public void setRealTextWidth(int pWidth) {
        mRealTextWidth = pWidth;
    }

    /**
     *
     * @return Returns the real text width, which may be different from the
     *         artificually minimum 30 pixel text width.
     */
    public int getRealTextWidth() {
        return mRealTextWidth;
    }

    /**
     *
     * @param pInsertionIndex
     *            Sets the insertion index, no less than 0, nor more than the
     *            maximum character length.
     */
    public void setInsertionIndex(int pInsertionIndex) {
        if (pInsertionIndex < 0) {
            pInsertionIndex = 0;
        }
        if (pInsertionIndex > getHeadLength()) {
            pInsertionIndex = getHeadLength();
        }
        mInsertionIndex = pInsertionIndex;

    }

    /**
     *
     * @return Returns the insertion index, or location of the Carat in the
     *         text.
     */
    public int getInsertionIndex() {
        return mInsertionIndex;
    }

    /**
     *
     * @param pHighlightBegin
     *            Sets the point at which text highlighting begins.
     */
    public void setHighlightBegin(int pHighlightBegin) {
        if (pHighlightBegin < 0) {
            pHighlightBegin = 0;
        }
        if (pHighlightBegin > getHeadLength()) {
            pHighlightBegin = getHeadLength();
        }
        mHighlightBegin = pHighlightBegin;
    }

    /**
     *
     * @return Returns the beginning point of highlighting
     */
    public int getHighlightBegin() {
        if (mHighlightBegin < mHighlightEnd) {
            return mHighlightBegin;
        } else {
            return mHighlightEnd;
        }
    }

    /**
     *
     * @param pHighlightEnd
     *            Sets the point at which text highlighting ends.
     */
    public void setHighlightEnd(int pHighlightEnd) {

        if (pHighlightEnd < 0) {
            pHighlightEnd = 0;
        }
        if (pHighlightEnd > getHeadLength()) {
            pHighlightEnd = getHeadLength();
        }
        mHighlightEnd = pHighlightEnd;
    }

    /**
     *
     * @return Returns the point at which text highlighting ends.
     */
    public int getHighlightEnd() {
        if (mHighlightBegin < mHighlightEnd) {
            return mHighlightEnd;
        } else {
            return mHighlightBegin;
        }
    }

    /**
     *
     * @return Returns the text under the highlight.
     */
    public AttributedString getClip() {
        return new AttributedString(mHead.getIterator(null, mHighlightBegin,
            mHighlightEnd));
    }

    /**
     *
     * @param pInsert
     *            The new piece of Attributed Text to be inserted into the head.
     * @param pIndex
     *            The beginning of the insertion point. <br>
     *            <br>
     *            This formula cycles through the original head and the newly
     *            inserted piece, pulling out the UNICODE text. <br>
     *            Then a new string of text is built with the old string to the
     *            insertion point, the new string, and the rest of the old
     *            string. <br>
     *            Then the attributedString of the old head is stepped through,
     *            one step at a time, the attributes are copied out and pasted
     *            into the new AttributedString. <br>
     *            This process inserts the old Map information into the correct
     *            index locations of the new AttributedString, one character at
     *            a time. <br>
     *            The result is a new AttributedString with all font and format
     *            information preserved.
     */
    public void insertHead(AttributedString pInsert, int pIndex) {
        String lString = "";
        AttributedCharacterIterator lIterator;
        AttributedCharacterIterator lInsertIterator;
        String lInsertString = "";

        lIterator = getHead().getIterator();
        for (char c = lIterator.first(); c != CharacterIterator.DONE; c = lIterator
            .next()) {
            lString += c;
        }

        lInsertIterator = pInsert.getIterator();
        for (char c = lInsertIterator.first(); c != CharacterIterator.DONE; c = lInsertIterator
            .next()) {
            lInsertString += c;
        }
        String lFinalString = lString.substring(0, pIndex) + lInsertString
            + lString.substring(pIndex, lString.length());
        AttributedString lAT = new AttributedString(lFinalString);

        lIterator.first();
        char lHold = 0;
        for (int i = 0; i < pIndex; i++) {
            lAT.addAttributes(lIterator.getAttributes(), i, i + 1);
            lHold = lIterator.next();
        }
        int j = 0;
        for (char c = lInsertIterator.first(); c != CharacterIterator.DONE; c = lInsertIterator
            .next()) {
            lAT.addAttributes(lInsertIterator.getAttributes(), pIndex + j,
                pIndex + j + 1);
            j += 1;
        }
        while (lHold != CharacterIterator.DONE) {
            lAT.addAttributes(lIterator.getAttributes(), pIndex + j, pIndex + j
                + 1);
            j += 1;
            lHold = lIterator.next();
        }

        setHead(lAT);
    }

    /**
     * This Operation deletes highlighted text from the head (AttributedString
     * text). <br>
     * <br>
     * The old AttributedString is iterated, and a new text string is generated
     * with only the correct Text. <br>
     * Then the old AttributedString is iterated again, and Map information
     * containing the font and transformations is pasted into the new string,
     * one character at a time. <br>
     * Finally, the new AttributedString is stored using setHead to replace the
     * old one.
     *
     */
    public void deleteHead() {
        if (getHighlightBegin() != getHighlightEnd()) {
            String lString = "";
            AttributedCharacterIterator lIterator = getHead().getIterator();
            int lI = 0;
            for (char c = lIterator.first(); c != CharacterIterator.DONE; c = lIterator
                .next()) {
                if (lI < getHighlightBegin() || lI >= getHighlightEnd()) {
                    lString += c;
                }
                lI++;
            }
            AttributedString lAttributedString = new AttributedString(lString);
            lI = 0;
            int lJ = 0;
            for (char c = lIterator.first(); c != CharacterIterator.DONE; c = lIterator
                .next()) {
                if (lI < getHighlightBegin() || lI >= getHighlightEnd()) {

                    lAttributedString.addAttributes(lIterator.getAttributes(),
                        lJ, lJ + 1);
                    lJ++;
                }
                lI++;
            }
            int lHighlight = getHighlightBegin();
            setHighlightEnd(lHighlight);
            setHighlightBegin(lHighlight);
            setInsertionIndex(lHighlight);
            setHead(lAttributedString);
        }
    }

    /**
     *
     * @param map
     *            sets the attributes of highlighted texts with the passed in
     *            attribute set.
     */
    public void addAttribute(Attribute string, Object object) {
        if (getHighlightBegin() != getHighlightEnd()) {

            getHead().addAttribute(string,object,getHighlightBegin(),getHighlightEnd());
            testXY();
            mUserInternalFrame.getSyntaxFacade().displayTree();
        }
    }
    public boolean testAttribute()
    {
        if (getHighlightBegin() != getHighlightEnd())
        {
            return true;
        }
        return false;
    }
    public void removeAttribute()
    {
        if (getHighlightBegin() != getHighlightEnd()) {
            getHead().addAttribute(TextAttribute.TRANSFORM,new AffineTransform(),getHighlightBegin(),getHighlightEnd());
            testXY();
        }
    }

    /**
     * Tests the textWidth and textHeight and returns the correct values to the
     * EditableComponent Object. <br>
     * <br>
     * TextHeight is based on the ascent + descent + 1 pixel, TextWidth is based
     * on the Advance + 4 pixels. (I would like a better formula, but this will
     * have to do for now.)
     *
     */
    public void testXY() {
        int lWidth = 0;
        AttributedCharacterIterator iter = this.getHead().getIterator();
        FontRenderContext frc = ((Graphics2D) getUserInternalFrame()
            .getGraphics()).getFontRenderContext();
        TextLayout tl;
        try {
            tl = new TextLayout(iter, frc);
        } catch (IllegalArgumentException e) {
            AttributedString lAT = new AttributedString(" ");
            Font lFont = new Font("Doulos SIL", Font.PLAIN,
                getUserInternalFrame().getProperties().getDefaultFontSize());
            lAT.addAttribute(TextAttribute.FONT, lFont);
            setHead(lAT);

            tl = new TextLayout(getHead().getIterator(), frc);
        }

        lWidth = (int) tl.getAdvance() + 4;
        this.setRealTextWidth(lWidth);
        lWidth = lWidth < getUserInternalFrame().getProperties()
            .getMinLineWidth() ? getUserInternalFrame().getProperties()
                .getMinLineWidth() : lWidth;
                this.setTextWidth(lWidth);
                this.setTextHeight((int) (tl.getAscent() + tl.getDescent()) + 1);
    }

    //@SupressWarnings("unchecked")
    public void setHeadObservers() {
        AttributedCharacterIterator lIterator = mHead.getIterator();
        lIterator.setIndex(getInsertionIndex());
        Map lAttributes = lIterator.getAttributes();

        Font lFont = (Font) lAttributes.get(TextAttribute.FONT);
        if (lFont != null) {
            mUserInternalFrame.getObservableFont().setValue(lFont.getName());
            mUserInternalFrame.getObservableFontSize()
            .setValue(lFont.getSize());
            mUserInternalFrame.getObservableFontBold().setValue(lFont.isBold());
            mUserInternalFrame.getObservableFontItalic().setValue(
                lFont.isItalic());
            if (lAttributes.get(TextAttribute.FOREGROUND) != null) {
                mUserInternalFrame.getUserFrame().getObservableFontColor()
                .setValue(
                    (Color) lAttributes
                    .get(TextAttribute.FOREGROUND));
            } else {
                mUserInternalFrame.getUserFrame().getObservableFontColor()
                .setValue(new Color(0, 0, 0));

            }
            if (lAttributes.get(TextAttribute.BACKGROUND) != null) {
                mUserInternalFrame.getUserFrame()
                .getObservableBackgroundColor().setValue(
                    (Color) lAttributes
                    .get(TextAttribute.BACKGROUND));
            } else {
                mUserInternalFrame.getUserFrame()
                .getObservableBackgroundColor().setValue(
                    new Color(255, 255, 255));
            }
            if ((TransformAttribute) lFont.getAttributes().get(TextAttribute.TRANSFORM) != null
                &&((TransformAttribute) lFont.getAttributes().get(
                    TextAttribute.TRANSFORM)).getTransform().getTranslateY() == 1)
            {
                mUserInternalFrame.getObservableFontSubscript().setValue(true);
                mUserInternalFrame.getObservableFontSuperscript().setValue(
                    false);
            }
            else if ((TransformAttribute) lFont.getAttributes().get(TextAttribute.TRANSFORM) != null
                && ((TransformAttribute) lFont.getAttributes().get(
                    TextAttribute.TRANSFORM)).getTransform().getTranslateY() == -3)
            {
                mUserInternalFrame.getObservableFontSubscript().setValue(false);
                mUserInternalFrame.getObservableFontSuperscript()
                .setValue(true);
            }
            else
            {
                mUserInternalFrame.getObservableFontSubscript().setValue(false);
                mUserInternalFrame.getObservableFontSuperscript().setValue(
                    false);
            }
            Object lObject = lAttributes.get(TextAttribute.UNDERLINE);
            if (lObject == TextAttribute.UNDERLINE_ON) {
                mUserInternalFrame.getObservableFontUnderline().setValue(true);
            } else {
                mUserInternalFrame.getObservableFontUnderline().setValue(false);
            }
            lObject = lAttributes.get(TextAttribute.STRIKETHROUGH);
            if (lObject == TextAttribute.STRIKETHROUGH_ON) {
                mUserInternalFrame.getObservableFontStrikethrough().setValue(
                    true);
            } else {
                mUserInternalFrame.getObservableFontStrikethrough().setValue(
                    false);
            }
        } else {
            mUserInternalFrame.getObservableFontSubscript().setValue(false);
            mUserInternalFrame.getObservableFontSuperscript().setValue(false);
        }
    }

    public void setFontColor(Color color) {
        mFontColor = color;
    }

    public void setBackgroundColor(Color color) {
        mBackgroundColor = color;
    }

    public Color getFontColor() {
        return mFontColor;
    }

    public Color getBackgroundColor() {
        return mBackgroundColor;
    }
    public SyntaxFacade getSyntaxFacade()
    {
        return mSyntaxFacade;
    }
    public void setSyntaxFacade(SyntaxFacade syntaxFacade)
    {
        mSyntaxFacade = syntaxFacade;
    }
    
    public void selectAll(boolean carat) {
        setOver(false);
        setCarat(carat);
        setHighlightBegin(0);
        setHighlightEnd(getHeadLength());
        repaint();
    }
    
    public void selectAll() {
        selectAll(false);
    }
    
    /** gets the focus, selects text and deselects everything else */
    public void selectAndFocus() {
        getSyntaxFacade().deselectTree();
        setInsertionIndex(getHeadLength());
        
        requestFocus(true);
        selectAll(true);
    }

    public InputMethodRequests getInputMethodRequests()
    {
        return (InputMethodRequests) this.getInputContext();
    }
    public void setDropTarget(DropTarget dropTarget)
    {
        super.setDropTarget(dropTarget);
    }
    public DropTarget getDropTarget()
    {
        return super.getDropTarget();
    }
    public void setClicked(int pME, int clickCount, Object source)
    {
        mSyntaxFacade.deselectTree();
        setInsertionIndex(pME);
        if (clickCount > 1) {
            selectAll();
        } else {
            setHighlightBegin(pME);
            setHighlightEnd(pME);
        }
        mUserInternalFrame.getObservableClipboard().setValue(
            source);
        mUserInternalFrame.getObservableClipboard().setIndex(
            getInsertionIndex());
        setCarat(true);
        requestFocus(true);
    }
    private int pointTest(MouseEvent pME) {

        float clickX = (float) (pME.getX());
        float clickY = (float) (pME.getY());
        // Get the character position of the mouse click.
        return pointTestXY(clickX,clickY);
    }
    private int pointTestXY(float clickX, float clickY)
    {
        Point2D origin = computeLayoutOrigin();
        clickX -=origin.getX();
        clickY -=origin.getY();
        Rectangle2D lRectangle = mTextLayoutHead.getBounds();
        if (clickX < (lRectangle.getWidth() / 2)) {
            clickX = (float) ((lRectangle.getWidth() / 2) - (((lRectangle
                .getWidth() / 2) - clickX) / (Sizer.scaleWidth() * getUserInternalFrame()
                    .getScale())));
        } else {
            clickX = (float) ((lRectangle.getWidth() / 2) + ((clickX - (lRectangle
                .getWidth() / 2)) / (Sizer.scaleWidth() * getUserInternalFrame()
                    .getScale())));
        }
        if (clickY < (lRectangle.getHeight() / 2)) {
            clickY = (float) ((lRectangle.getHeight() / 2) - (((lRectangle
                .getHeight() / 2) - clickY) / (Sizer.scaleHeight() * getUserInternalFrame()
                    .getScale())));
        } else {
            clickY = (float) ((lRectangle.getHeight() / 2) + ((clickY - (lRectangle
                .getHeight() / 2)) / (Sizer.scaleHeight() * getUserInternalFrame()
                    .getScale())));
        }

        TextHitInfo currentHit = mTextLayoutHead
            .hitTestChar(clickX, clickY);
        return currentHit.getInsertionIndex();
    }
    private Point2D computeLayoutOrigin() {

        Dimension size = getSize();

        Point2D.Float origin = new Point2D.Float();

        origin.x = (float) (size.width - mTextLayoutHead.getAdvance()) / 2;
        origin.y = (float) (size.height - mTextLayoutHead.getDescent() + mTextLayoutHead
            .getAscent()) / 2;
        return origin;
    }
}
