package org.rogatio.remarkable.desktop;

import static org.rogatio.remarkable.api.io.PropertiesCache.DEVICETOKEN;

import org.rogatio.remarkable.api.RemarkableManager;
import org.rogatio.remarkable.api.io.PropertiesCache;
import org.rogatio.remarkable.desktop.views.FxView;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Application extends MobileApplication {

	@Override
	public void init() {
		for (FxView view : FxView.values()) {
			addViewFactory(view.getFxml(), () -> view.getView());
		}

		DrawerManager.buildDrawer(this);
		
	}

	@Override
	public void postInit(Scene scene) {
		scene.getWindow().setWidth(1024);
		scene.getWindow().setHeight(768);

		Swatch.BLUE_GREY.assignTo(scene);

		scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());
		((Stage) scene.getWindow()).getIcons().add(new Image(Application.class.getResourceAsStream("/icon.png")));

		RemarkableManager rm = auth();
	}

	public static void main(String args[]) {
		launch(args);
	}

	private static RemarkableManager auth() {
		// check if deviceToken for this remarkable client exists
		boolean deviceTokenExists = PropertiesCache.getInstance().propertyExists(DEVICETOKEN);

		if (!deviceTokenExists) {
			DrawerManager.switchView(FxView.REGISTER);
		}
		
		return RemarkableManager.getInstance();
	}
	
	
}
