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

public class RegisterPresenter  {

	private static final Logger logger = LogManager.getLogger(RegisterPresenter.class);

	@FXML
	private View register;

	@FXML
	private TextField onetimecode;

	@FXML
	private Hyperlink registerDeviceLink;

	public void initialize() {
		DrawerManager.setViewTitle(register, FxView.REGISTER);
		DrawerManager.setHyperlinkOnClick(registerDeviceLink, "https://my.remarkable.com/connect/desktop");
	}

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

	@FXML
	void buttonCanceled() {
		DrawerManager.switchView(FxView.HOME);
	}

}
