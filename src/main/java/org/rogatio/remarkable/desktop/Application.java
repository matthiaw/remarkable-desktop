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
package org.rogatio.remarkable.desktop;

import static org.rogatio.remarkable.api.io.PropertiesCache.DEVICETOKEN;

import java.io.InputStream;

import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.rogatio.remarkable.api.RemarkableManager;
import org.rogatio.remarkable.api.io.PropertiesCache;
import org.rogatio.remarkable.desktop.views.FxView;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The Class Application.
 */
public class Application extends MobileApplication {

	/**
	 * Overwrite log4j2 configuration.
	 */
	static {
		try {
			InputStream inputStream = Application.class.getResourceAsStream("/log4j2.xml");
			ConfigurationSource source = new ConfigurationSource(inputStream);
			Configurator.initialize(null, source);
		} catch (Exception ex) {
		}
	}

	/**
	 * Inits the application
	 */
	@Override
	public void init() {
		for (FxView view : FxView.values()) {
			addViewFactory(view.getFxml(), () -> view.getView());
		}

		DrawerManager.buildDrawer(this);

	}

	/**
	 * Post init application
	 *
	 * @param scene the scene
	 */
	@Override
	public void postInit(Scene scene) {
		scene.getWindow().setWidth(1024);
		scene.getWindow().setHeight(768);

		Swatch.BLUE_GREY.assignTo(scene);

		scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());
		((Stage) scene.getWindow()).getIcons().add(new Image(Application.class.getResourceAsStream("/icon.png")));

		RemarkableManager rm = auth();
	}

	/**
	 * The main method of the aplication
	 *
	 * @param args the arguments
	 */
	public static void main(String args[]) {
		launch(args);
	}

	/**
	 * Authenticate application
	 *
	 * @return the remarkable manager
	 */
	private static RemarkableManager auth() {
		// check if deviceToken for this remarkable client exists
		boolean deviceTokenExists = PropertiesCache.getInstance().propertyExists(DEVICETOKEN);

		if (!deviceTokenExists) {
			DrawerManager.switchView(FxView.REGISTER);
		}

		return RemarkableManager.getInstance();
	}

}
