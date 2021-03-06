
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

package userInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.HashMap;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import syntaxTree.SyntacticStructure;

/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * This is the glass pane used to draw all the pretty red/green dots used for
 * positioning/repositioning subtrees.
 */
public class UserGlassPane extends JComponent {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="mSSChild"
     * @uml.associationEnd
     * @uml.property name="mSSChild" multiplicity="(1 1)"
     */
    private SyntacticStructure mSSChild;

    private int mMaxPosition;

    /**
     *
     * @uml.property name="mPositions"
     * @uml.associationEnd
     * @uml.property name="mPositions" multiplicity="(0 1)" qualifier="new:java.lang.Integer
     * value:java.lang.Float"
     */
    //@SupressWarnings("unchecked")
    private HashMap mPositions;

    /**
     *
     * @uml.property name="mPosition"
     * @uml.associationEnd
     * @uml.property name="mPosition" multiplicity="(0 -1)" elementType="syntaxTree.SyntacticStructure"
     */
    private int mPosition;

    /**
     *
     * @uml.property name="mPoint"
     * @uml.associationEnd
     * @uml.property name="mPoint" multiplicity="(0 0)"
     */
    private Point2D.Float mPoint;

    /**
     *
     * @uml.property name="mSS"
     * @uml.associationEnd
     * @uml.property name="mSS" multiplicity="(1 1)"
     */
    private SyntacticStructure mSS;

    /**
     *
     * @uml.property name="mUserFrame"
     * @uml.associationEnd
     * @uml.property name="mUserFrame" multiplicity="(1 1)"
     */
    private UserFrame mUserFrame;

    /**
     *
     * @param pUserFrame The UserFrame for this instance of TreeForm
     * @param pSS The SyntacticStructure highlighted for child insertion
     * @param pSSChild The SyntacticStructure highlighted for insertion AS a child
     *
     */
    public UserGlassPane(
        UserFrame pUserFrame,
        SyntacticStructure pSS,
        SyntacticStructure pSSChild) {
        mUserFrame = pUserFrame;
        mSS = pSS;
        mSSChild = pSSChild;

    }
    /**
     * paintComponent is the command for drawing the circles.
     * The Algoritm is simple:
     * <br><br>
     * Turn on antialiasing
     * <br>
     * Build a rectangle containing the bounds of the syntacticstructurelines
     * beneath the highlighted structure
     * <br>
     * Set the scale of the component to be painted
     * <br>
     * Draw the circles midway between each line and just below them
     * <br>
     */
    //@SupressWarnings("unchecked")
    public void paintComponent(Graphics g) {
        Graphics2D lGraphics2D = (Graphics2D) g;
        lGraphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        lGraphics2D.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        lGraphics2D.scale(
            Sizer.scaleWidth()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale(),
            Sizer.scaleHeight()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale());
        mPositions = new HashMap();
        Rectangle lRectangle = mSS.getSyntacticStructureLines().getBounds();
        mPoint =
            new Point2D
            .Float(
                lRectangle.x / (Sizer.scaleWidth() * mUserFrame.getDesktopPane().getInternalFrame().getScale()),
                (lRectangle.y + lRectangle.height -4)/ (Sizer.scaleHeight() * mUserFrame.getDesktopPane().getInternalFrame().getScale())
                //((SyntacticStructure) mSS.getChildren().getFirst()).getY() + ((SyntacticStructure) mSS.getChildren().getFirst()).getButtonHeight()/2
                );
        SyntacticStructure left = (SyntacticStructure) mSS.getChildren().getFirst();
        SyntacticStructure right = (SyntacticStructure) mSS.getChildren().getLast();
        int lI = 0;
        int lJ = 0;
        for (int i = 0; i < mSS.getChildren().size(); i++)
        {
            SyntacticStructure w = (SyntacticStructure) mSS.getChildren().get(i);
            SyntacticStructure w1 = null;
            if(i == 0)
            {
                lI = 0;
            }
            else
            {
                w1 = (SyntacticStructure) mSS.getChildren().get(i-1);
                lI = (int) (((w.getButtonX() + w.getButtonWidth() - w1.getButtonX())/2) + w1.getButtonX() -left.getButtonX());
            }
            lJ = lI + w.getButtonWidth()/2;
            drawArc(lGraphics2D, lI,i);
            mPositions.put(new Integer(i),new Float((lJ + mPoint.x) * Sizer.scaleWidth() * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
        }

        lI = (int) (right.getButtonX()+right.getButtonWidth()-left.getButtonX());
        drawArc(lGraphics2D, lI,mSS.getChildren().size());
        mPositions.put(new Integer(mSS.getChildren().size()),new Float((lI + mPoint.x) * Sizer.scaleWidth() * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
        mMaxPosition = mSS.getChildren().size();
    }
    /**
     *
     * @param pSS Sets the parent SyntacticStructure
     */
    public void setSyntacticStructure(SyntacticStructure pSS) {
        mSS = pSS;
    }
    /**
     *
     * @param lGraphics2D draw the individual circles.
     * @param pRelativePosition The relative position needed for putting the circle in
     * the correct place on the screen
     * @param pPosition The absoluteposition of the child node - used to draw the green
     * circle if the mouse is in the correct place to select that subtree insertion point.
     */
    private void drawArc(
        Graphics2D lGraphics2D,
        int pRelativePosition,
        int pPosition) {
        Arc2D lArc;
        Color lColor;
        int startX = (int) ((mSS.getButtonWidth()/2) + (mSS.getX() / (Sizer.scaleWidth()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale())));
        int startY = (int) (mSS.getTextHeight() + (mSS.getY()) /
            (Sizer.scaleWidth()
                * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
        int endX = ((int) mPoint.x + pRelativePosition);
        int endY = (int) mPoint.y;
        if (pPosition == getPosition()) {
            lColor = new Color(0, 190, 0);
            lGraphics2D.setColor(lColor);
            lArc =
                new Arc2D.Float(
                    mPoint.x + pRelativePosition-4,
                    mPoint.y - 4,
                    8,
                    8,
                    0,
                    360,
                    Arc2D.OPEN);
            lGraphics2D.fill(lArc);
            lGraphics2D.setColor(Color.BLACK);
            float dash[] = {2.0f, 2.0f};
            lGraphics2D.setStroke(new BasicStroke(0.8f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)); 			//stroke.getDashPhase();
            lGraphics2D.drawLine(startX,
                startY,
                endX,
                endY);
            lGraphics2D.setStroke(new BasicStroke());
        } else {
            lColor = new Color(255, 00, 00);
            lGraphics2D.setColor(lColor);
            lArc =
                new Arc2D.Float(
                    mPoint.x + pRelativePosition-2,
                    mPoint.y -2,
                    4,
                    4,
                    0,
                    360,
                    Arc2D.OPEN);
            lGraphics2D.fill(lArc);
        }
        lGraphics2D.setColor(Sizer.BROWN1);
        lGraphics2D.draw(lArc);
    }
    /**
     *
     * @param pME The position of the mouse in relation to where to insert the subtree
     * if the mouse is clicked.
     */
    public void setPosition(MouseEvent pME) {
        mPosition = mMaxPosition;
        //boolean hold = false;
        for (int i = mMaxPosition; i >= 0; i--) {
            //System.out.println(i);
            if (pME.getX()
                < ((Float) mPositions.get(new Integer(i))).intValue())
            {
                mPosition = i;
            }
        }
        mSS.setOver(true);
        mSS.repaint();
        repaint();
    }
    /**
     *
     * @return Returns the selected insertion position.
     */
    public int getPosition() {
        return mPosition;
    }
    /**
     *
     * Sets the child subtree in the correct position.
     */
    //@SupressWarnings("unchecked")
    public void setChild() {
        mSS.getChildren().add(mPosition, mSSChild);
        mSSChild.setSyntacticParent(mSS);
        mUserFrame.getInternalFrame().getTrace().setDrawTrace(true);
        mUserFrame
        .getSyntaxFacade()
        .displayTree();
        mSS.setOver(false);
        mSS.repaint();
        mUserFrame.getDesktopPane().getInternalFrame().deactivateGlassPane();
    }
}