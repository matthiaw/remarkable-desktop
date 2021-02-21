/*
 * Remarkable API - Copyright (C) 2021 Matthias Wegner
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
package org.rogatio.remarkable.desktop.template;

import java.io.Serializable;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

@Plugin(name = "TextAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class TextAppender extends AbstractAppender {

	public static TextFlow textFlowPane;

	private ConcurrentMap<String, LogEvent> eventMap = new ConcurrentHashMap<>();

	@SuppressWarnings("deprecation")
	protected TextAppender(String name, Filter filter) {
		super(name, filter, null);
	}

	@SuppressWarnings("deprecation")
	protected TextAppender(String name, Filter filter, Layout<? extends Serializable> layout,
			boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions);
	}

	@PluginFactory
	public static TextAppender createAppender(@PluginAttribute("name") String name,
			@PluginElement("Filter") Filter filter) {
		return new TextAppender(name, filter);
	}

	@Override
	public void append(LogEvent event) {
		eventMap.put(Instant.now().toString(), event);

		if (textFlowPane != null) {

			Text level = new Text();
			if (event.getLevel() == Level.ERROR) {
				level = new Text();
				level.setFill(Color.RED);
			}
			if (event.getLevel() == Level.WARN) {
				level = new Text();
				level.setFill(Color.ORANGE);
			}
			if (event.getLevel() == Level.INFO) {
				level = new Text();
				level.setFill(Color.GREEN);
			}
			if (event.getLevel() == Level.DEBUG) {
				level = new Text();
				level.setFill(Color.BLUE);
			}
			level.setText("[" + event.getLevel().name() + "] ");

			textFlowPane.getChildren().add( level);
			textFlowPane.getChildren().add( new Text(event.getMessage().getFormattedMessage() + "\n"));
			
		}
	}

}
