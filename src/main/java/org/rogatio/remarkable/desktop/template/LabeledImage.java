package org.rogatio.remarkable.desktop.template;

import java.io.File;

import org.rogatio.remarkable.desktop.DrawerManager;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LabeledImage extends VBox {

	private final Label title = new Label();
	private final Label subTitle = new Label();
	private ImageView thumb = new ImageView();
	private int size = 0;
	
	public LabeledImage(String titleString, File thumb, int size) {
		setTitle(titleString);
		setSubTitle("");
		this.size = size;
		this.thumb = DrawerManager.createImageView(thumb, size);
		init();
	}
	
	public LabeledImage(String titleString, String subString, File thumb, int size) {
		setTitle(titleString);
		setSubTitle(subString);
		this.thumb = DrawerManager.createImageView(thumb, size);
		this.size = size;
		init();
	}

	private void setSubTitle(String sub) {
		subTitle.setText(sub);
	}

	private void setTitle(String titleString) {
		title.setText(titleString);
		title.setStyle("-fx-font-weight: bold;");
	}

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
