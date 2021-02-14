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

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.rogatio.remarkable.desktop.views.FxView;

import com.gluonhq.attach.lifecycle.LifecycleService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.NavigationDrawer;
import com.gluonhq.charm.glisten.control.NavigationDrawer.Item;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;

/**
 * The Class DrawerManager.
 */
public class DrawerManager {

	/**
	 * Builds the drawer.
	 *
	 * @param app the app
	 */
	public static void buildDrawer(MobileApplication app) {
		NavigationDrawer drawer = app.getDrawer();

		NavigationDrawer.Header header = new NavigationDrawer.Header("Remarkable Desktop", "A remarkable client",
				new Avatar(21, new Image(DrawerManager.class.getResourceAsStream("/icon.png"))));
		drawer.setHeader(header);

		final Item homeItem = FxView.HOME.getItem(ViewStackPolicy.SKIP);
		final Item secondaryItem = FxView.SETTINGS.getItem();
		// final Item registerItem = FxView.REGISTER.getItem();

		drawer.getItems().addAll(homeItem, secondaryItem);

		if (Platform.isDesktop()) {
			final Item quitItem = new Item("Quit", MaterialDesignIcon.EXIT_TO_APP.graphic());
			quitItem.selectedProperty().addListener((obs, ov, nv) -> {
				if (nv) {
					Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
				}
			});
			drawer.getItems().add(quitItem);
		}
	}

	/**
	 * Sets the view title.
	 *
	 * @param view the view
	 * @param fxview the fxview
	 */
	public static void setViewTitle(View view, FxView fxview) {
		view.showingProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				AppBar appBar = MobileApplication.getInstance().getAppBar();
				appBar.setTitleText(fxview.getDisplayName());
			}
		});
	}

	/**
	 * Creates the download icon.
	 *
	 * @param type the type
	 * @param tooltipText the tooltip text
	 * @return the node
	 */
	public static Node createDownloadIcon(String type, String tooltipText) {
		VBox stack = new VBox();
		Label label = new Label(type);
		label.setStyle("-fx-text-fill: white;");
		stack.getChildren().add(label);
		Node i = MaterialDesignIcon.FILE_DOWNLOAD.graphic();
		i.setStyle("-fx-text-fill: white;");
		stack.getChildren().add(i);
		
		Tooltip tooltip = new Tooltip();
		tooltip.setText(tooltipText);
		Tooltip.install(stack, tooltip);
		
		return stack;
	}

	/**
	 * File opener.
	 *
	 * @param node the node
	 * @param file the file
	 */
	public static void fileOpener(Node node, File file) {
		node.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (file.exists()) {
						if (Desktop.isDesktopSupported()) {
							Desktop desktop = Desktop.getDesktop();
							try {
								desktop.open(file);
							} catch (IOException ex) {
							}
						}
					}
				}
			}
		});
	}

	/**
	 * Sets the hyperlink on click.
	 *
	 * @param link the link
	 * @param url the url
	 */
	public static void setHyperlinkOnClick(Hyperlink link, String url) {
		link.setBorder(Border.EMPTY);
		link.setPadding(new Insets(4, 0, 4, 0));
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(new URI(url));
					} catch (IOException ex) {
					} catch (URISyntaxException ex) {
					}
				}
			}
		});
	}

	/**
	 * Switch view.
	 *
	 * @param view the view
	 */
	public static void switchView(FxView view) {
		MobileApplication.getInstance().switchView(view.getFxml());
	}

	/**
	 * Creates the image view.
	 *
	 * @param imageFile the image file
	 * @param size the size
	 * @return the image view
	 */
	public static ImageView createImageView(File imageFile, int size) {
		try {
			Image image = new Image(new FileInputStream(imageFile), 0, size, true, true);

			if (image.getHeight() > image.getWidth()) {
				image = new Image(new FileInputStream(imageFile), size, 0, true, true);
			}
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(image.getWidth());

			return imageView;

		} catch (FileNotFoundException e) {
		}
		return null;
	}

}