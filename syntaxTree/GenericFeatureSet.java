
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

import java.util.LinkedList;

/**
 * @author Donald Derrick * @version 0.1 * <br> * date: 19-Aug-2004 * <br> * <br> * A Case Feature Set. * <br> * Currently CaseFeatureSet, GenericFeatureSet, and ThetaRoleFeatureSet are * identical.  They exist as separate entitites to allow the construction of  * rules for enforcing behaviour in later versions.
 * 
 * @uml.stereotype name="tagged" isDefined="true" 
 * @uml.stereotype name="entity" 
 */

public class GenericFeatureSet implements SyntacticFeatureSet {

	/**
	 * 
	 * @uml.property name="mSyntacticFeature"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticFeature" multiplicity="(0 -1)" elementType="syntaxTree.SyntacticFeature"
	 */
	@SuppressWarnings("unchecked")
	private LinkedList mSyntacticFeature;

	/**
	 * 
	 * @uml.property name="mSyntacticStructure"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntacticStructure" multiplicity="(0 1)"
	 */
	private SyntacticStructure mSyntacticStructure;

	/**
	 * 
	 * @uml.property name="visibility" 
	 */
	private boolean visibility;

/**
 * Constructor
 *
 */	
	@SuppressWarnings("unchecked")
	public GenericFeatureSet()
	{
		mSyntacticFeature = new LinkedList();
	}
/**
 * @return Returns the syntacticStructure associated with this featureSet
 */	
	public SyntacticStructure getSyntacticStructure() {
		return mSyntacticStructure;
	}
/**
 * @param pSyntacticStructure sets the associated SyntacticStrucutre - needed to move featureSets.
 */
	public void setSyntacticStructure(SyntacticStructure pSyntacticStructure) {
		mSyntacticStructure = pSyntacticStructure;
	}
/**
 * @return gets the SyntacticFeature LinkedList
 */
	@SuppressWarnings("unchecked")
	public LinkedList getSyntacticFeature() {
		return mSyntacticFeature;
	}
/**
 * @param pSyntacticFeature sets a new LinkedList of SyntacticFeatures
 */
	@SuppressWarnings("unchecked")
	public void setSyntacticFeature(LinkedList pSyntacticFeature) {
		mSyntacticFeature = pSyntacticFeature;
	}

	/**
	 * @return UNUSED - gets visibility
	 * 
	 * @uml.property name="visibility"
	 */
	public boolean getVisibility() {
		return visibility;
	}

	/**
	 * @param pVisibility
	 * UNUSED - sets visibility
	 * 
	 * @uml.property name="visibility"
	 */
	public void setVisibility(boolean pVisibility) {
		visibility = pVisibility;
	}

/**
 * @return UNUSED - gets the text height of the feature set
 */
	public int getHeight() {
		int lI = 0;
		for (int i = 0; i < getSyntacticFeature().size(); i++)
		{
			SyntacticFeature lSF = (SyntacticFeature) getSyntacticFeature().get(i);
			lI = lI > lSF.getTextHeight() ? lI : lSF.getTextHeight();
		}
		return lI;
	}

/**
 * @return gets the text width of the reature set
 */
	public int getWidth() {
		int lI = 0;
		for (int i = 0; i < getSyntacticFeature().size(); i++)
		{
			SyntacticFeature lSF = (SyntacticFeature) getSyntacticFeature().get(i);
			lI += lSF.getTextWidth();
		}
		return lI;
	}
/**
 * Tests the text width and text height of the text in this feature set.
 */
	
	public void testXY() {
		getSyntacticStructure().testXY();
	}
}