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

import org.rogatio.remarkable.desktop.DrawerManager;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Class SettingsPresenter.
 */
public class SettingsPresenter {

	/** The settings. */
	@FXML
	private View settings;

	/**
	 * Initialize.
	 */
	public void initialize() {
		
		DrawerManager.setViewTitle(settings, FxView.SETTINGS);
		
		settings.setShowTransitionFactory(BounceInRightTransition::new);

		FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.INFO.text,
				e -> {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Remarkable Desktop \n\n\u00a9 Matthias Wegner");
					alert.showAndWait();
				});
		fab.showOn(settings);

		settings.showingProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				AppBar appBar = MobileApplication.getInstance().getAppBar();
				appBar.setNavIcon(
						MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().getDrawer().open()));
				appBar.getActionItems().add(MaterialDesignIcon.FAVORITE.button(e -> System.out.println("Favorite")));
			}
		});
	}
}
