
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * One of a number of listeners specific to the components of a syntax tree.
 */
public class ListenerReposition implements ActionListener {

    /**
     *
     * @uml.property name="mSS"
     * @uml.associationEnd
     * @uml.property name="mSS" multiplicity="(1 1)"
     */
    private SyntacticStructure mSS;

    /**
     *
     * @uml.property name="mSF"
     * @uml.associationEnd
     * @uml.property name="mSF" multiplicity="(1 1)"
     */
    private SyntaxFacade mSF;

    /**
     *
     * @param pSF The SyntaxFacade holding all these commands
     * @param pSS The SyntacticStructure attached to this listener.
     */
    public ListenerReposition(SyntaxFacade pSF, SyntacticStructure pSS)
    {
        mSF = pSF;
        mSS = pSS;
    }
    /**
     * @param pAE The ActionEvent
     * <br>
     * Calls repositionSyntacticStructure
     */
    public void actionPerformed(ActionEvent pAE) {
        mSF.repositionSyntacticStructure(mSS);
    }
}
