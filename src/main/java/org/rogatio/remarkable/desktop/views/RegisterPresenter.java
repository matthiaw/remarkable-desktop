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

import static org.rogatio.remarkable.api.io.PropertiesCache.DEVICETOKEN;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.rogatio.remarkable.api.io.PropertiesCache;
import org.rogatio.remarkable.api.io.RemarkableClient;
import org.rogatio.remarkable.desktop.DrawerManager;

import com.gluonhq.charm.glisten.mvc.View;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

/**
 * The Class RegisterPresenter.
 */
public class RegisterPresenter  {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(RegisterPresenter.class);

	/** The register. */
	@FXML
	private View register;

	/** The onetimecode. */
	@FXML
	private TextField onetimecode;

	/** The register device link. */
	@FXML
	private Hyperlink registerDeviceLink;

	/**
	 * Initialize.
	 */
	public void initialize() {
		DrawerManager.setViewTitle(register, FxView.REGISTER);
		DrawerManager.setHyperlinkOnClick(registerDeviceLink, "https://my.remarkable.com/connect/desktop");
	}

	/**
	 * Button ok.
	 */
	@FXML
	void buttonOk() {

		String code = null;
		if (onetimecode.getText() != null) {
			if (!onetimecode.getText().equals("")) {
				code = onetimecode.getText();
			}
		}

		if (code != null) {
			RemarkableClient rc = new RemarkableClient();

			try {
				String createdToken = rc.newDeviceToken(code);
				// write token to client
				PropertiesCache.getInstance().setProperty(DEVICETOKEN, createdToken);
			} catch (IOException e1) {
				logger.error("Error requesting device token");
			}

			try {
				PropertiesCache.getInstance().flush();
			} catch (FileNotFoundException e) {
				logger.error("Error setting device token to properties");
			} catch (IOException e) {
				logger.error("Error setting device token to properties");
			}

			logger.info("New Device Token for client created");
		} else {
			logger.error("Device Token not found. Skip.");
			System.exit(0);
		}

		DrawerManager.switchView(FxView.HOME);
	}

	/**
	 * Button canceled.
	 */
	@FXML
	void buttonCanceled() {
		DrawerManager.switchView(FxView.HOME);
	}

}
