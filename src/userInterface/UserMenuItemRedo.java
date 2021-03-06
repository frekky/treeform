
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

import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JMenuItem;

/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
 * The MenuItem Redo
 */
public class UserMenuItemRedo extends JMenuItem implements Observer{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     * @uml.property name="mObservableStack"
     * @uml.associationEnd
     * @uml.property name="mObservableStack" multiplicity="(1 1)"
     */
    private ObservableStack mObservableStack;
    private ObservableNew mObservableNew;
    /**
     *
     * @param pString The Title
     * @param pIcon The Icon
     * @param pObservableStack The Observer containing the stack of undo/redo options.
     */
    public UserMenuItemRedo(String pString, Icon pIcon, ObservableStack pObservableStack, ObservableNew pObservableNew) {
        super(pString, pIcon);
        mObservableStack = pObservableStack;
        mObservableNew = pObservableNew;
    }

    /**
     * Implements Observer
     */
    public void update(Observable pObservable, Object pObject) {
        if (pObservable == mObservableStack)
        {
            if (mObservableStack.getValue() == null)
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }
        }
        if (pObservable == mObservableNew)
        {
            if (mObservableNew.getValue() == 0)
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }
        }
    }
}
