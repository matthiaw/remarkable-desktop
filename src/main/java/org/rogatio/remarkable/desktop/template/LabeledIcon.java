package org.rogatio.remarkable.desktop.template;

import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LabeledIcon extends VBox {

	private final Label title = new Label();
	private final Label subTitle = new Label();
	private MaterialDesignIcon thumb = MaterialDesignIcon.EMPTY;

	public LabeledIcon(String title, String subTitle, MaterialDesignIcon icon) {
		setTitle(title);
		setSubTitle(subTitle);
		this.thumb = icon;
		init();
	}
	
	public LabeledIcon(String title, MaterialDesignIcon icon) {
		setTitle(title);
		setSubTitle("");
		this.thumb = icon;
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
