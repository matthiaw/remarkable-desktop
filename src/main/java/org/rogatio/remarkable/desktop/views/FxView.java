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
package org.rogatio.remarkable.desktop.views;

import java.io.IOException;

import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * The Enum FxView.
 */
public enum FxView {

	/** The home. */
	HOME(" Remarkable Desktop", "Home", "home", MaterialDesignIcon.HOME),

	/** The settings. */
	SETTINGS("Settings View", "Settings", "settings", MaterialDesignIcon.SETTINGS),

	/** The register. */
	REGISTER("Register Device", "Register", "register", MaterialDesignIcon.LAPTOP);

	/** The display name. */
	private String displayName;

	/** The name. */
	private String name;

	/** The fxml. */
	private String fxml;

	/** The icon. */
	private MaterialDesignIcon icon;

	/**
	 * Instantiates a new fx view.
	 *
	 * @param displayName the display name
	 * @param name        the name
	 * @param fxml        the fxml
	 * @param icon        the icon
	 */
	private FxView(final String displayName, final String name, final String fxml, final MaterialDesignIcon icon) {
		this.displayName = displayName;
		this.name = name;
		this.fxml = fxml;
		this.icon = icon;
	}

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public MaterialDesignIcon getIcon() {
		return icon;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the fxml.
	 *
	 * @return the fxml
	 */
	public String getFxml() {
		return fxml;
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		try {
			View view = FXMLLoader.load(FxView.class.getResource(fxml + ".fxml"));

//			AnchorPane pane = new AnchorPane();
//			pane.getChildren().add(view);
//
//			AnchorPane.setTopAnchor(view, 0.0);
//			AnchorPane.setRightAnchor(view, 0.0);
//			AnchorPane.setLeftAnchor(view, 0.0);
//			AnchorPane.setBottomAnchor(view, 0.0);

			return view;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
			return new View();
		}
	}

	/**
	 * Gets the item.
	 *
	 * @param policy the policy
	 * @return the item
	 */
	public ViewItem getItem(ViewStackPolicy policy) {
		return new ViewItem(getName(), getIcon().graphic(), getFxml(), policy);
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public ViewItem getItem() {
		return new ViewItem(getName(), getIcon().graphic(), getFxml());
	}

}
