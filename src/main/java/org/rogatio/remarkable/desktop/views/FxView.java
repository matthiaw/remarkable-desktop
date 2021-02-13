package org.rogatio.remarkable.desktop.views;

import java.io.IOException;

import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.NavigationDrawer.ViewItem;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

import javafx.fxml.FXMLLoader;

public enum FxView {

	HOME(" Remarkable Desktop", "Home", "home", MaterialDesignIcon.HOME),
	SETTINGS("Settings View", "Settings", "settings", MaterialDesignIcon.SETTINGS),
	REGISTER("Register Device", "Register", "register", MaterialDesignIcon.LAPTOP);

	private String displayName;
	private String name;
	private String fxml;
	private MaterialDesignIcon icon;

	private FxView(final String displayName, final String name, final String fxml, final MaterialDesignIcon icon) {
		this.displayName = displayName;
		this.name = name;
		this.fxml = fxml;
		this.icon = icon;
	}

	public MaterialDesignIcon getIcon() {
		return icon;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getName() {
		return name;
	}

	public String getFxml() {
		return fxml;
	}

	public View getView() {
		try {
			View view = FXMLLoader.load(FxView.class.getResource(fxml + ".fxml"));
			return view;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
			e.printStackTrace();
			return new View();
		}
	}

	public ViewItem getItem(ViewStackPolicy policy) {
		return new ViewItem(getName(), getIcon().graphic(), getFxml(), policy);
	}

	public ViewItem getItem() {
		return new ViewItem(getName(), getIcon().graphic(), getFxml());
	}

}
