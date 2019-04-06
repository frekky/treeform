

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

import java.io.File;
import java.util.*;

import javax.swing.ImageIcon;
/**
 *
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * English text for the program
 */
public class TreeFormBundle_fr_CA extends ListResourceBundle {

    /**
     *
     * @uml.property name="contents"
     */
    public Object[][] getContents() {
        return contents;
    }

    /**
     *
     * @uml.property name="contents"
     */
    private Object[][] contents = {
            { "UNDO_ICON_SMALL", new ImageIcon("image" + File.separator + "undo16.gif") },
            { "UNDO_ICON_SMALL_GREY", new ImageIcon("image" + File.separator + "undo16_d.gif") },
            { "UNDO_ICON_SMALL_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "undo16_h.gif") },
            { "UNDO_ICON_LARGE", new ImageIcon("image" + File.separator + "undo24.gif") },
            { "UNDO_ICON_LARGE_GREY", new ImageIcon("image" + File.separator + "undo24_d.gif") },
            { "UNDO_ICON_LARGE_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "undo24_h.gif") },
            { "UNDO_TOOLTIP", new String("Undo Action") },
            { "UNDO_LABEL", new String("Undo") },

            { "REDO_ICON_SMALL", new ImageIcon("image" + File.separator + "redo16.gif") },
            { "REDO_ICON_SMALL_GREY", new ImageIcon("image" + File.separator + "redo16_d.gif") },
            { "REDO_ICON_SMALL_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "redo16_h.gif") },
            { "REDO_ICON_LARGE", new ImageIcon("image" + File.separator + "redo24.gif") },
            { "REDO_ICON_LARGE_GREY", new ImageIcon("image" + File.separator + "redo24_d.gif") },
            { "REDO_ICON_LARGE_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "redo24_h.gif") },
            { "REDO_TOOLTIP", new String("Redo Action") },
            { "REDO_LABEL", new String("Redo") },

            { "ITALICS_ICON_SMALL", new ImageIcon("image" + File.separator + "italics16.gif") },
            { "ITALICS_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "italics16.gif") },
            { "ITALICS_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "italics16.gif") },
            { "ITALICS_ICON_LARGE", new ImageIcon("image" + File.separator + "italics24.gif") },
            { "ITALICS_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "italics24.gif") },
            { "ITALICS_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "italics24.gif") },
            { "ITALICS_TOOLTIP", new String("Italics") },
            { "ITALICS_LABEL", new String("Italics") },

            { "SUBSCRIPT_ICON_SMALL",
                new ImageIcon("image" + File.separator + "subscript16.gif") },
            { "SUBSCRIPT_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "subscript16.gif") },
            { "SUBSCRIPT_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "subscript16.gif") },
            { "SUBSCRIPT_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "subscript24.gif") },
            { "SUBSCRIPT_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "subscript24.gif") },
            { "SUBSCRIPT_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "subscript24.gif") },
            { "SUBSCRIPT_TOOLTIP", new String("Subscript") },
            { "SUBSCRIPT_LABEL", new String("Subscript") },

            { "SUPERSCRIPT_ICON_SMALL",
                new ImageIcon("image" + File.separator + "superscript16.gif") },
            { "SUPERSCRIPT_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "superscript16.gif") },
            { "SUPERSCRIPT_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "superscript16.gif") },
            { "SUPERSCRIPT_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "superscript24.gif") },
            { "SUPERSCRIPT_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "superscript24.gif") },
            { "SUPERSCRIPT_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "superscript24.gif") },
            { "SUPERSCRIPT_TOOLTIP", new String("Superscript") },
            { "SUPERSCRIPT_LABEL", new String("Superscript") },

            { "UNDERLINE_ICON_SMALL",
                new ImageIcon("image" + File.separator + "underline16.gif") },
            { "UNDERLINE_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "underline16.gif") },
            { "UNDERLINE_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "underline16.gif") },
            { "UNDERLINE_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "underline24.gif") },
            { "UNDERLINE_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "underline24.gif") },
            { "UNDERLINE_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "underline24.gif") },
            { "UNDERLINE_TOOLTIP", new String("Underline") },
            { "UNDERLINE_LABEL", new String("Underline") },

            { "STRIKETHROUGH_ICON_SMALL",
                new ImageIcon("image" + File.separator + "strikethrough16.gif") },
            { "STRIKETHROUGH_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "strikethrough16.gif") },
            { "STRIKETHROUGH_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "strikethrough16.gif") },
            { "STRIKETHROUGH_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "strikethrough24.gif") },
            { "STRIKETHROUGH_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "strikethrough24.gif") },
            { "STRIKETHROUGH_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "strikethrough24.gif") },
            { "STRIKETHROUGH_TOOLTIP", new String("Strikethrough") },
            { "STRIKETHROUGH_LABEL", new String("Strikethrough") },

            { "BOLD_ICON_SMALL", new ImageIcon("image" + File.separator + "bold16.gif") },
            { "BOLD_ICON_SMALL_GREY", new ImageIcon("image" + File.separator + "bold16.gif") },
            { "BOLD_ICON_SMALL_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "bold16.gif") },
            { "BOLD_ICON_LARGE", new ImageIcon("image" + File.separator + "bold24.gif") },
            { "BOLD_ICON_LARGE_GREY", new ImageIcon("image" + File.separator + "bold24.gif") },
            { "BOLD_ICON_LARGE_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "bold24.gif") },
            { "BOLD_TOOLTIP", new String("Bold") },
            { "BOLD_LABEL", new String("Bold") },

            { "ZOOMPLUS_ICON_SMALL", new ImageIcon("image" + File.separator + "zoomplus16.gif") },
            { "ZOOMPLUS_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "zoomplus16_d.gif") },
            { "ZOOMPLUS_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "zoomplus16_h.gif") },
            { "ZOOMPLUS_ICON_LARGE", new ImageIcon("image" + File.separator + "zoomplus24.gif") },
            { "ZOOMPLUS_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "zoomplus24_d.gif") },
            { "ZOOMPLUS_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "zoomplus24_h.gif") },
            { "ZOOMPLUS_TOOLTIP", new String("Zoom in") },
            { "ZOOMPLUS_LABEL", new String("Zoom in") },

            { "ZOOMMINUS_ICON_SMALL",
                new ImageIcon("image" + File.separator + "zoomminus16.gif") },
            { "ZOOMMINUS_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "zoomminus16_d.gif") },
            { "ZOOMMINUS_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "zoomminus16_h.gif") },
            { "ZOOMMINUS_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "zoomminus24.gif") },
            { "ZOOMMINUS_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "zoomminus24_d.gif") },
            { "ZOOMMINUS_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "zoomminus24_h.gif") },
            { "ZOOMMINUS_TOOLTIP", new String("Zoom out") },
            { "ZOOMMINUS_LABEL", new String("Zoom out") },

            { "LOGICALFORM_ICON_SMALL",
                new ImageIcon("image" + File.separator + "logical_form16.gif") },
            { "LOGICALFORM_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "logical_form16_d.gif") },
            { "LOGICALFORM_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "logical_form16_h.gif") },
            { "LOGICALFORM_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "logical_form24.gif") },
            { "LOGICALFORM_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "logical_form24_d.gif") },
            { "LOGICALFORM_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "logical_form24_h.gif") },
            { "LOGICALFORM_TOOLTIP",
                                        new String("View Tree as Logical Form") },
            { "LOGICALFORM_LABEL", new String("Liew Logical Form") },

            { "SURFACESTRUCTURE_ICON_SMALL",
                new ImageIcon("image" + File.separator + "surface_structure16.gif") },
            { "SURFACESTRUCTURE_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "surface_structure16_d.gif") },
            { "SURFACESTRUCTURE_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "surface_structure16_h.gif") },
            { "SURFACESTRUCTURE_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "surface_structure24.gif") },
            { "SURFACESTRUCTURE_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "surface_structure24_d.gif") },
            { "SURFACESTRUCTURE_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "surface_structure24_h.gif") },
            { "SURFACESTRUCTURE_TOOLTIP",
                                        new String("View Tree as Surface Structure") },
            { "SURFACESTRUCTURE_LABEL",
                                            new String("View Surface Structure") },

            { "DEEPSTRUCTURE_ICON_SMALL",
                                                new ImageIcon("image" + File.separator + "deep_structure16.gif") },
            { "DEEPSTRUCTURE_ICON_SMALL_GREY",
                                                    new ImageIcon("image" + File.separator + "deep_structure16_d.gif") },
            { "DEEPSTRUCTURE_ICON_SMALL_HIGHLIGHT",
                                                        new ImageIcon("image" + File.separator + "deep_structure16_h.gif") },
            { "DEEPSTRUCTURE_ICON_LARGE",
                                                            new ImageIcon("image" + File.separator + "deep_structure24.gif") },
            { "DEEPSTRUCTURE_ICON_LARGE_GREY",
                                                                new ImageIcon("image" + File.separator + "deep_structure24_d.gif") },
            { "DEEPSTRUCTURE_ICON_LARGE_HIGHLIGHT",
                                                                    new ImageIcon("image" + File.separator + "deep_structure24_h.gif") },
            { "DEEPSTRUCTURE_TOOLTIP",
                                                                        new String("View Tree as Deep Structure") },
            { "DEEPSTRUCTURE_LABEL", new String("View Deep Structure") },

            { "LABELLEDBRACKET_ICON_SMALL",
                new ImageIcon("image" + File.separator + "labelled_bracket16.gif") },
            { "LABELLEDBRACKET_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "labelled_bracket16_d.gif") },
            { "LABELLEDBRACKET_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "labelled_bracket16_h.gif") },
            { "LABELLEDBRACKET_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "labelled_bracket24.gif") },
            { "LABELLEDBRACKET_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "labelled_bracket24_d.gif") },
            { "LABELLEDBRACKET_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "labelled_bracket24_h.gif") },
            { "LABELLEDBRACKET_TOOLTIP",
                                        new String("View Tree as Labelled Brackets") },
            { "LABELLEDBRACKET_LABEL", new String("View Labelled Brackets") },

            { "PROPERTIES_ICON_SMALL",
                new ImageIcon("image" + File.separator + "properties_doc16.gif") },
            { "PROPERTIES_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "properties_doc16_d.gif") },
            { "PROPERTIES_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "properties_doc16_h.gif") },
            { "PROPERTIES_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "properties_doc24b.gif") },
            { "PROPERTIES_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "properties_doc24b_d.gif") },
            { "PROPERTIES_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "properties_doc24b_h.gif") },
            { "PROPERTIES_TOOLTIP", new String("Set Properties") },
            { "PROPERTIES_LABEL", new String("Properties") },

            { "CUT_ICON_SMALL", new ImageIcon("image" + File.separator + "cut_clipboard16.gif") },
            { "CUT_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "cut_clipboard16_d.gif") },
            { "CUT_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "cut_clipboard16_h.gif") },
            { "CUT_ICON_LARGE", new ImageIcon("image" + File.separator + "cut_clipboard24.gif") },
            { "CUT_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "cut_clipboard24_d.gif") },
            { "CUT_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "cut_clipboard24_h.gif") },
            { "CUT_TOOLTIP", new String("Cut Text") },
            { "CUT_LABEL", new String("Cut") },

            { "COPY_ICON_SMALL",
                new ImageIcon("image" + File.separator + "copy_clipboard16.gif") },
            { "COPY_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "copy_clipboard16_d.gif") },
            { "COPY_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "copy_clipboard16_h.gif") },
            { "COPY_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "copy_clipboard24.gif") },
            { "COPY_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "copy_clipboard24_d.gif") },
            { "COPY_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "copy_clipboard24_h.gif") },
            { "COPY_TOOLTIP", new String("Copy Text") },
            { "COPY_LABEL", new String("Copy") },

            { "PASTE_ICON_SMALL",
                new ImageIcon("image" + File.separator + "paste_clipboard16.gif") },
            { "PASTE_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "paste_clipboard16_d.gif") },
            { "PASTE_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "paste_clipboard16_h.gif") },
            { "PASTE_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "paste_clipboard24.gif") },
            { "PASTE_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "paste_clipboard24_d.gif") },
            { "PASTE_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "paste_clipboard24_h.gif") },
            { "PASTE_TOOLTIP", new String("Paste Text") },
            { "PASTE_LABEL", new String("Paste") },

            { "COPY_TREE_ICON_SMALL", new ImageIcon("image" + File.separator + "copy_tree16.gif") },
            { "COPY_TREE_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "copy_tree16_d.gif") },
            { "COPY_TREE_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "copy_tree16_h.gif") },
            { "COPY_TREE_ICON_LARGE", new ImageIcon("image" + File.separator + "copy_tree24.gif") },
            { "COPY_TREE_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "copy_tree24_d.gif") },
            { "COPY_TREE_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "copy_tree24_h.gif") },
            { "COPY_TREE_TOOLTIP", new String("Copy Tree to clipboard, paste in MS Word or other text editor.") },
            { "COPY_TREE_LABEL", new String("Copy Tree") },

            { "PRINT_ICON_SMALL", new ImageIcon("image" + File.separator + "print16.gif") },
            { "PRINT_ICON_SMALL_GREY", new ImageIcon("image" + File.separator + "print16_d.gif") },
            { "PRINT_ICON_SMALL_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "print16_h.gif") },
            { "PRINT_ICON_LARGE", new ImageIcon("image" + File.separator + "print24.gif") },
            { "PRINT_ICON_LARGE_GREY", new ImageIcon("image" + File.separator + "print24_d.gif") },
            { "PRINT_ICON_LARGE_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "print24_h.gif") },
            { "PRINT_TOOLTIP", new String("Print Tree") },
            { "PRINT_LABEL", new String("Print") },

            { "PRINTPREVIEW_ICON_SMALL",
                new ImageIcon("image" + File.separator + "print_preview16.gif") },
            { "PRINTPREVIEW_ICON_SMALL_GREY",
                    new ImageIcon("image" + File.separator + "print_preview16_d.gif") },
            { "PRINTPREVIEW_ICON_SMALL_HIGHLIGHT",
                        new ImageIcon("image" + File.separator + "print_preview16_h.gif") },
            { "PRINTPREVIEW_ICON_LARGE",
                            new ImageIcon("image" + File.separator + "print_preview24.gif") },
            { "PRINTPREVIEW_ICON_LARGE_GREY",
                                new ImageIcon("image" + File.separator + "print_preview24_d.gif") },
            { "PRINTPREVIEW_ICON_LARGE_HIGHLIGHT",
                                    new ImageIcon("image" + File.separator + "print_preview24_h.gif") },
            { "PRINTPREVIEW_TOOLTIP", new String("Print Preview") },
            { "PRINTPREVIEW_LABEL", new String("Print Preview") },

            { "NEW_ICON_SMALL", new ImageIcon("image" + File.separator + "new_document16.gif") },
            { "NEW_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "new_document16_d.gif") },
            { "NEW_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "new_document16_h.gif") },
            { "NEW_ICON_LARGE", new ImageIcon("image" + File.separator + "new_document24.gif") },
            { "NEW_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "new_document24_d.gif") },
            { "NEW_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "new_document24_h.gif") },
            { "NEW_TOOLTIP", new String("New Tree") },
            { "NEW_LABEL", new String("New") },

            { "OPEN_ICON_SMALL", new ImageIcon("image" + File.separator + "open_document16.gif") },
            { "OPEN_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "open_document16_d.gif") },
            { "OPEN_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "open_document16_h.gif") },
            { "OPEN_ICON_LARGE", new ImageIcon("image" + File.separator + "open_document24.gif") },
            { "OPEN_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "open_document24_d.gif") },
            { "OPEN_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "open_document24_h.gif") },
            { "OPEN_TOOLTIP", new String("Open Tree") },
            { "OPEN_LABEL", new String("Open") },

            { "CLOSE_TOOLTIP", new String("Close Tree") },
            { "CLOSE_LABEL", new String("Close") },

            { "CLOSEALL_TOOLTIP", new String("Close all Trees") },
            { "CLOSEALL_LABEL", new String("Close All") },

            { "SAVE_ICON_SMALL", new ImageIcon("image" + File.separator + "save16.gif") },
            { "SAVE_ICON_SMALL_GREY", new ImageIcon("image" + File.separator + "save16_d.gif") },
            { "SAVE_ICON_SMALL_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "save16_h.gif") },
            { "SAVE_ICON_LARGE", new ImageIcon("image" + File.separator + "save24.gif") },
            { "SAVE_ICON_LARGE_GREY", new ImageIcon("image" + File.separator + "save24_d.gif") },
            { "SAVE_ICON_LARGE_HIGHLIGHT",
                new ImageIcon("image" + File.separator + "save24_h.gif") },
            { "SAVE_TOOLTIP", new String("Save Tree") },
            { "SAVE_LABEL", new String("Save") },

            { "SAVEAS_ICON_SMALL", new ImageIcon("image" + File.separator + "saveas16.gif") },
            { "SAVEAS_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "saveas16_d.gif") },
            { "SAVEAS_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "saveas16_h.gif") },
            { "SAVEAS_ICON_LARGE", new ImageIcon("image" + File.separator + "saveas24.gif") },
            { "SAVEAS_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "saveas24_d.gif") },
            { "SAVEAS_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "saveas24_h.gif") },
            { "SAVEAS_TOOLTIP", new String("Save Tree as...") },
            { "SAVEAS_LABEL", new String("Save as...") },

            { "SAVEALL_ICON_SMALL", new ImageIcon("image" + File.separator + "saveall16.gif") },
            { "SAVEALL_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "saveall16_d.gif") },
            { "SAVEALL_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "saveall16_h.gif") },
            { "SAVEALL_ICON_LARGE", new ImageIcon("image" + File.separator + "saveall24.gif") },
            { "SAVEALL_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "saveall24_d.gif") },
            { "SAVEALL_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "saveall24_h.gif") },
            { "SAVEALL_TOOLTIP", new String("Save All Trees") },
            { "SAVEALL_LABEL", new String("Save All") },

            { "EXPORT_ICON_SMALL", new ImageIcon("image" + File.separator + "export16.gif") },
            { "EXPORT_ICON_SMALL_GREY",
                new ImageIcon("image" + File.separator + "export16_d.gif") },
            { "EXPORT_ICON_SMALL_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "export16_h.gif") },
            { "EXPORT_ICON_LARGE", new ImageIcon("image" + File.separator + "export24.gif") },
            { "EXPORT_ICON_LARGE_GREY",
                new ImageIcon("image" + File.separator + "export24_d.gif") },
            { "EXPORT_ICON_LARGE_HIGHLIGHT",
                    new ImageIcon("image" + File.separator + "export24_h.gif") },
            { "EXPORT_TOOLTIP", new String("Export Picture of Tree") },
            { "EXPORT_LABEL", new String("Export Tree") },

            { "TREEFORM_ICON_SMALL", new ImageIcon("image" + File.separator + "treeform.gif") },
            { "TREEFORM_ICON_LARGE", new ImageIcon("image" + File.separator + "treeformbig.gif") },

            { "FILE_LABEL", new String("File") },
            { "FORMAT_LABEL", new String("Format") },
            { "FONT_LABEL", new String("Font...") },
            { "EDIT_LABEL", new String("Edit") },
            { "VIEW_LABEL", new String("View") },
            { "ZOOM_LABEL", new String("Zoom...") },
            { "WINDOW_LABEL", new String("Window") },
            { "HELP_LABEL", new String("Help") },
            { "CONTENTS_LABEL", new String("Help Contents") },
            { "ABOUT_LABEL", new String("About") },
            { "EXIT_LABEL", new String("Exit") },
            { "SELECTALL_LABEL", new String("Select All") },
            { "CASCADE_LABEL", new String("Cascade") },
            { "TILE_LABEL", new String("Tile") },
            { "DELETE_SUBTREE", new String("Delete Subtree")},
            { "REPOSITION_SUBTREE", new String("Reposition Subtree")},
            { "DELETE_START_TRACE", new String("Delete Start Trace")},
            { "DELETE_END_TRACE", new String("Delete End Trace")},
            { "CUSTOMIZE_START_TRACE", new String("Customize Start Trace")},
            { "CUSTOMIZE_END_TRACE", new String("Customize End Trace")},
            { "DELETE_FEATURE", new String("Delete Feature")},
            { "DELETE_FEATURE_SET", new String("Delete Feature Set")},
            { "ADD_FEATURE", new String("Add Feature")},
            { "DELETE_ASSOCIATION", new String("Delete Association")},
            { "WHAT_NEW_LABEL", new String("What's new")}
    };

}
