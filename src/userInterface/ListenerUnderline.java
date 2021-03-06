
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;

import javax.swing.JToggleButton;

/**
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * This is one of several Listener classes (part of the Java Command design pattern
 * interface) designed to fire UserControl commands that operate non-sentence
 * GUI interaction in TreeFrom
 *
 */
public class ListenerUnderline implements ActionListener {

    /**
     * Constructor
     * @param pUserFrame - Passes a copy of the user frame (which currently works
     * as the facade for this program
     * <br>
     * NOTE: This is not strictly speaking the correct way to do things, and
     * future revisions should involve implementing a joined facade class instead of
     * using the UserFrame and the UserInternalFrame as the two facades.
     *
     * @uml.property name="mUserFrame"
     * @uml.associationEnd
     * @uml.property name="mUserFrame" multiplicity="(1 1)"
     */
    private UserFrame mUserFrame;

    public ListenerUnderline(UserFrame pUserFrame) {
        super();
        mUserFrame = pUserFrame;
    }

    /**
     * @param pAE Passes a mouse event to the listener
     * <br>
     * This command calls the ObservableUnderline that underline is toggled
     * <br>
     * This command then calls the ObservableClipboard and changes the highlighted text.
     * <br>
     *
     */
    public void actionPerformed(ActionEvent pAE) {
        mUserFrame.getObservableFontUnderline().setValue(((JToggleButton)pAE.getSource()).isSelected());
        if (mUserFrame.getObservableFontUnderline().getValue())
        {
            mUserFrame.getInternalFrame().getSyntaxFacade().changeAttributes(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
        }
        else
        {
            mUserFrame.getInternalFrame().getSyntaxFacade().changeAttributes(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE);
        }
    }
}
