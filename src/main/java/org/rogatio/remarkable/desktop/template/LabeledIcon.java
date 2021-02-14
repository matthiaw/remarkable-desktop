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

import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * The Class LabeledIcon.
 */
public class LabeledIcon extends VBox {

	/** The title. */
	private final Label title = new Label();
	
	/** The sub title. */
	private final Label subTitle = new Label();
	
	/** The thumb. */
	private MaterialDesignIcon thumb = MaterialDesignIcon.EMPTY;

	/**
	 * Instantiates a new labeled icon.
	 *
	 * @param title the title
	 * @param subTitle the sub title
	 * @param icon the icon
	 */
	public LabeledIcon(String title, String subTitle, MaterialDesignIcon icon) {
		setTitle(title);
		setSubTitle(subTitle);
		this.thumb = icon;
		init();
	}
	
	/**
	 * Instantiates a new labeled icon.
	 *
	 * @param title the title
	 * @param icon the icon
	 */
	public LabeledIcon(String title, MaterialDesignIcon icon) {
		setTitle(title);
		setSubTitle("");
		this.thumb = icon;
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
	 * Inits the labeled icon
	 */
	private void init() {
		this.setPadding(new Insets(5, 5, 5, 5));
		this.setSpacing(10);

		VBox box = new VBox();
		box.setMaxWidth(150);
		box.setMaxHeight(150);
		
		Node icon = thumb.graphic();
		icon.setStyle("-fx-font-size: 80px");
	       
		box.getChildren().add(icon);
		this.getChildren().add(box);

		this.getChildren().add(title);
		this.getChildren().add(subTitle);
	}

}
