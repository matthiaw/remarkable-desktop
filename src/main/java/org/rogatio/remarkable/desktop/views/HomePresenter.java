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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.batik.transcoder.TranscoderException;
import org.rogatio.remarkable.api.RemarkableManager;
import org.rogatio.remarkable.api.io.PropertiesCache;
import org.rogatio.remarkable.api.io.file.Svg2Png;
import org.rogatio.remarkable.api.io.file.Util;
import org.rogatio.remarkable.api.model.content.Content;
import org.rogatio.remarkable.api.model.content.Page;
import org.rogatio.remarkable.desktop.DrawerManager;
import org.rogatio.remarkable.desktop.template.LabeledIcon;
import org.rogatio.remarkable.desktop.template.LabeledImage;

import com.gluonhq.charm.glisten.animation.BounceInRightTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.FloatingActionButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

/**
 * The Class HomePresenter.
 */
public class HomePresenter {

	/** The home. */
	@FXML
	private View home;

	/** The image pane. */
	@FXML
	private TilePane imagePane;

	/** The back button node. */
	private Button backButtonNode = MaterialDesignIcon.ARROW_BACK.button();

	/** The application bar. */
	private AppBar appBar = MobileApplication.getInstance().getAppBar();

	private boolean toggleView = false;

	private Button zoomButton = MaterialDesignIcon.ZOOM_IN.button();

	/**
	 * Initialize.
	 */
	public void initialize() {
		DrawerManager.setViewTitle(home, FxView.HOME);

		home.setShowTransitionFactory(BounceInRightTransition::new);

		home.showingProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				appBar.setNavIcon(
						MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().getDrawer().open()));

				addNavHome();
			}
		});

		clearView();

		addNotebooks();
	}

	/**
	 * Adds the navigation bar of a single notebook
	 *
	 * @param content the content
	 */
	private void addNavSingle(Content content) {
		appBar.getActionItems().clear();
		backButtonNode.setDisable(true);
		zoomButton.setDisable(true);
		appBar.getActionItems().add(backButtonNode);
		appBar.getActionItems().add(zoomButton);

		File f = new File(exportFile(content, "pdf"));
		boolean disable = false;
		if (!f.exists()) {
			disable = true;
		}

		Node pdf = DrawerManager.createDownloadIcon("PDF", "Download PDF of '" + content.getName() + "'", "white");
		pdf.setDisable(disable);
		DrawerManager.fileOpener(pdf, f);
		appBar.getActionItems().add(pdf);

	}

	/**
	 * Adds the navigation bar for a single page.
	 *
	 * @param page the page
	 */
	private void addNavSingle(Page page) {
		appBar.getActionItems().clear();
		backButtonNode.setDisable(true);
		appBar.getActionItems().add(backButtonNode);

		if (toggleView) {
			zoomButton = MaterialDesignIcon.ZOOM_OUT.button();// . .setText(MaterialDesignIcon.ZOOM_OUT.text);
		} else {
			zoomButton = MaterialDesignIcon.ZOOM_IN.button();// . .setText(MaterialDesignIcon.ZOOM_OUT.text);
		}

		appBar.getActionItems().add(zoomButton);

		FloatingActionButton right = new FloatingActionButton(MaterialDesignIcon.KEYBOARD_ARROW_RIGHT.text, e -> {
			if (page.getPageNumber() + 1 < page.getNotebook().getPages().size()) {
				try {
					drawPage(page.getNotebook().getPage(page.getPageNumber() + 1));
				} catch (Exception ex) {
				}
			}
		});

		FloatingActionButton left = new FloatingActionButton(MaterialDesignIcon.KEYBOARD_ARROW_LEFT.text, e -> {
			if (page.getPageNumber() - 1 >= 0) {
				drawPage(page.getNotebook().getPage(page.getPageNumber() - 1));
			}
		});

		left.attachTo(right, Side.LEFT);
		right.show();

//		Button pageLeft = MaterialDesignIcon.KEYBOARD_ARROW_LEFT.button();
//		pageLeft.setTooltip(new Tooltip("Page " + (page.getPageNumber() - 1)));
//		pageLeft.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//					if (page.getPageNumber() - 1 >= 0) {
//						drawPage(page.getNotebook().getPage(page.getPageNumber() - 1));
//					} else {
//						pageLeft.setDisable(true);
//					}
//				}
//			}
//		});
//		appBar.getActionItems().add(pageLeft);
//
//		Button pageRight = MaterialDesignIcon.KEYBOARD_ARROW_RIGHT.button();
//		pageRight.setTooltip(new Tooltip("Page " + (page.getPageNumber() + 1)));
//		pageRight.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent mouseEvent) {
//				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
//
//					if (page.getPageNumber() + 1 < page.getNotebook().getPages().size()) {
//						try {
//							drawPage(page.getNotebook().getPage(page.getPageNumber() + 1));
//						} catch (Exception e) {
//						}
//					} else {
//						pageRight.setDisable(true);
//					}
//
//				}
//			}
//		});
//		appBar.getActionItems().add(pageRight);

		File f = new File(Util.getFilename(page, "svg"));
		boolean disable = false;
		if (!f.exists()) {
			disable = true;
		}

		Node svg = DrawerManager.createDownloadIcon("SVG", "Download SVG of Page " + page.getPageNumber(), "white");
		svg.setDisable(disable);
		DrawerManager.fileOpener(svg, f);
		appBar.getActionItems().add(svg);

		f = new File(Util.getFilename(page, "png"));
		disable = false;
		if (!f.exists()) {
			disable = true;
		}

		Node png = DrawerManager.createDownloadIcon("PNG", "Download PNG of Page " + page.getPageNumber(), "white");
		png.setDisable(disable);
		DrawerManager.fileOpener(png, new File(Util.getFilename(page, "png")));
		appBar.getActionItems().add(png);

		f = new File(Util.getFilename(page, "pdf"));
		disable = false;
		if (!f.exists()) {
			disable = true;
		}

		Node pdf = DrawerManager.createDownloadIcon("PDF", "Download PDF of Page " + page.getPageNumber(), "white");
		pdf.setDisable(disable);
		DrawerManager.fileOpener(pdf, new File(Util.getFilename(page, "pdf")));
		appBar.getActionItems().add(pdf);
	}

	/**
	 * Export file.
	 *
	 * @param content   the content
	 * @param extension the extension
	 * @return the string
	 */
	private String exportFile(Content content, String extension) {
		String EXPORTFOLDER = PropertiesCache.getInstance().getValue(PropertiesCache.EXPORTFOLDER);

		String folders = "";
		if (content.getFolders().size() > 0) {
			for (String f : content.getFolders()) {
				folders += f + File.separatorChar;
			}
			File ff = new File(EXPORTFOLDER + File.separatorChar + folders);
			ff.mkdirs();
		}
		return EXPORTFOLDER + File.separatorChar + folders + content.getName() + "." + extension;

	}

	/**
	 * Adds the nav single.
	 */
	private void addNavSingle() {
		appBar.getActionItems().clear();
		backButtonNode.setDisable(true);
		zoomButton.setDisable(true);
		appBar.getActionItems().add(backButtonNode);
		appBar.getActionItems().add(zoomButton);
	}

	/**
	 * Adds the navigation bat of the home site, all notebooks.
	 */
	private void addNavHome() {
		addNavSingle();

		appBar.getActionItems().add(MaterialDesignIcon.FOLDER_SPECIAL.button(e -> {
			TextInputDialog dialog = new TextInputDialog("Foldername");
			dialog.setTitle("Create Folder");
			dialog.setHeaderText("Folder creation dialog");
			dialog.setContentText("Enter folder name:");
			Optional<String> result = dialog.showAndWait();
			result.ifPresent(name -> RemarkableManager.getInstance().createDir(name));
		}));

		appBar.getActionItems().add(MaterialDesignIcon.GRID_ON.button(e -> {
			RemarkableManager.getInstance().downloadTemplates();
		}));

		appBar.getActionItems().add(MaterialDesignIcon.ARCHIVE.button(e -> {
			RemarkableManager.getInstance().exportNotebooks();
			addNotebooks();
		}));

		appBar.getActionItems().add(MaterialDesignIcon.REFRESH.button(e -> {
			RemarkableManager.getInstance().readNotebookMetaDatas();
			RemarkableManager.getInstance().downloadContents();
			RemarkableManager.getInstance().readContents();
			RemarkableManager.getInstance().exportNotebookThumbnails(false);
			addNotebooks();
		}));

	}

	/**
	 * Clear view.
	 */
	private void clearView() {
		imagePane.getChildren().clear();
	}

	/**
	 * Adds the folders.
	 */
	private void addFolders() {
		RemarkableManager rm = RemarkableManager.getInstance();

		List<Content> remarkableFolders = rm.getFolders();
		List<Node> folders = new ArrayList<>();
		for (Content remarkableFolder : remarkableFolders) {
			LabeledIcon img = new LabeledIcon(remarkableFolder.getName(), MaterialDesignIcon.FOLDER);
			folders.add(img);
		}

		imagePane.getChildren().addAll(folders);

	}

	/**
	 * Adds the notebooks.
	 */
	private void addNotebooks() {
		RemarkableManager rm = RemarkableManager.getInstance();

		appBar.setTitleText(FxView.HOME.getDisplayName());

		clearView();

		addNavHome();

		rm.readContents();
		List<Content> notebooks = rm.getNotebooks();
		List<Node> images = new ArrayList<>();
		for (Content content : notebooks) {
			LabeledImage img = createNotebookImage(content);
			images.add(img);
		}

		imagePane.getChildren().addAll(images);
	}

	/**
	 * Creates the notebook image.
	 *
	 * @param content the content
	 * @return the labeled image
	 */
	private LabeledImage createNotebookImage(Content content) {

		String folders = content.getFolders().toString();
		folders = folders.replace("[", "").replace("]", "").replace(",", "/");

		LabeledImage img = new LabeledImage(content.getName(), folders, content.getThumbnail(), 150) {
			@Override
			public void clicked() {
				appBar.setTitleText(content.getName());
				addNotebook(content);
			}
		};

		return img;
	}

	/**
	 * Adds the notebook.
	 *
	 * @param notebook the notebook
	 */
	private void addNotebook(Content notebook) {

		addNavSingle(notebook);
		clearView();

		backButtonNode.setDisable(false);
		backButtonNode.setTooltip(new Tooltip("All Notebooks"));
		backButtonNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					addNotebooks();
				}
			}
		});

		RemarkableManager rm = RemarkableManager.getInstance();

		rm.readContents();
		List<Page> notebookPages = notebook.getPages();
		List<Node> pages = new ArrayList<>();
		for (Page page : notebookPages) {
			LabeledImage img = createPageImage(page);
			pages.add(img);
		}

		imagePane.getChildren().addAll(pages);
	}

	/**
	 * Creates the page image.
	 *
	 * @param page the page
	 * @return the labeled image
	 */
	private LabeledImage createPageImage(Page page) {
		LabeledImage img = new LabeledImage("Page " + page.getPageNumber(), page.getThumbnail(), 150) {
			@Override
			public void clicked() {
				drawPage(page);
			}
		};

		return img;
	}

	public void drawPage(Page page) {
		appBar.setTitleText(page.getNotebook().getName() + " - Page " + page.getPageNumber());

		addNavSingle(page);

		zoomButton.setDisable(false);
		// add toggle view button
		if (toggleView) {

//			zoomButton = MaterialDesignIcon.ZOOM_OUT.button();//. .setText(MaterialDesignIcon.ZOOM_OUT.text);
			zoomButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						toggleView = !toggleView;
						drawPage(page);
					}
				}
			});

//			zoomButton = new FloatingActionButton(MaterialDesignIcon.ZOOM_OUT.text, e -> {
//				toggleView = !toggleView;
//				drawPage(page);
//			});

		} else {
//			fab = new FloatingActionButton(MaterialDesignIcon.ZOOM_IN.text, e -> {
//				toggleView = !toggleView;
//				drawPage(page);
//			});
//			zoomButton = MaterialDesignIcon.ZOOM_IN.button(); //.setText(MaterialDesignIcon.ZOOM_IN.text);
			zoomButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
						toggleView = !toggleView;
						drawPage(page);
					}
				}
			});
		}

//		fab.show();

		File imgFile = page.getPng();

		if (imgFile == null) {
			double scale = PropertiesCache.getInstance().getDouble(PropertiesCache.PNGEXPORTSCALE);
			try {
				Svg2Png.createPngFromFile(page, scale);
			} catch (TranscoderException e) {
			} catch (IOException e) {
			}
			imgFile = page.getPng();
		}

		ImageView iv = DrawerManager.createImageView(imgFile, 750);

		// toggle image view
		if (toggleView) {
			iv.fitWidthProperty().bind(imagePane.widthProperty());
		} else {
			iv.fitWidthProperty().bind(home.widthProperty());
			iv.fitHeightProperty().bind(home.heightProperty());
		}

//		BorderPane pane = new BorderPane();
//		pane.setLeft(new Label("Left"));
//		pane.setCenter(iv);
//		pane.setRight(new Label("Right"));

		imagePane.getChildren().clear();
		imagePane.getChildren().add(iv);

		backButtonNode.setDisable(false);
		backButtonNode.setTooltip(new Tooltip("Notebook " + (page.getNotebook().getName())));
		backButtonNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					addNotebook(page.getNotebook());
				}
			}
		});
	}

}
