/*
 * Remarkable Desktop - Copyright (C) 2021 Matthias Wegner
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.rogatio.remarkable.desktop.template;

import java.io.File;

import org.rogatio.remarkable.desktop.DrawerManager;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * The Class LabeledImage.
 */
public class LabeledImage extends VBox {

	/** The title. */
	private final Label title = new Label();
	
	/** The sub title. */
	private final Label subTitle = new Label();
	
	/** The thumb. */
	private ImageView thumb = new ImageView();
	
	/** The size. */
	private int size = 0;
	
	/**
	 * Instantiates a new labeled image.
	 *
	 * @param titleString the title string
	 * @param thumb the thumb
	 * @param size the size
	 */
	public LabeledImage(String titleString, File thumb, int size) {
		setTitle(titleString);
		setSubTitle("");
		this.size = size;
		this.thumb = DrawerManager.createImageView(thumb, size);
		init();
	}
	
	/**
	 * Instantiates a new labeled image.
	 *
	 * @param titleString the title string
	 * @param subString the sub string
	 * @param thumb the thumb
	 * @param size the size
	 */
	public LabeledImage(String titleString, String subString, File thumb, int size) {
		setTitle(titleString);
		setSubTitle(subString);
		this.thumb = DrawerManager.createImageView(thumb, size);
		this.size = size;
		init();
	}

	/**
	 * Sets the sub title.
	 *
	 * @param sub the new sub title
	 */
	private void setSubTitle(String sub) {
		subTitle.setText(sub);
	}

	/**
	 * Sets the title.
	 *
	 * @param titleString the new title
	 */
	private void setTitle(String titleString) {
		title.setText(titleString);
		title.setStyle("-fx-font-weight: bold;");
	}

	/**
	 * Inits the labeled image
	 */
	private void init() {
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setSpacing(10);

		VBox box = new VBox();
		box.setMaxWidth(size);
		box.setMaxHeight(size);
		box.setBorder(new Border(
		new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
		box.getChildren().add(thumb);
		this.getChildren().add(box);

		this.getChildren().add(title);
		this.getChildren().add(subTitle);
	}

}
