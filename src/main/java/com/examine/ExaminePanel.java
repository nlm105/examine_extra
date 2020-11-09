/*
 * Copyright (c) 2018 Abex
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.examine;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.account.SessionManager;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.SessionClose;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ScheduledExecutorService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


@Singleton
public class ExaminePanel extends PluginPanel
{
	private static final String RUNELITE_LOGIN = "https://runelite_login/";

	private static final ImageIcon ARROW_RIGHT_ICON;


	static
	{
		ARROW_RIGHT_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "/util/arrow_right.png"));
	}

	@Inject
	public static JLabel heading = new JLabel();
	public static final JLabel type = new JLabel(htmlLabel("Type: ", ""));
	public static final JLabel name = new JLabel(htmlLabel("Name: ", ""));
	public static final JRichTextPane wikiLnk = new JRichTextPane();


	private JPanel syncPanel;

	@Inject
	@Nullable
	private Client client;

	@Inject
	private EventBus eventBus;

	@Inject
	private SessionManager sessionManager;

	@Inject
	private ScheduledExecutorService executor;

	@Inject
	private ConfigManager configManager;



	void init()
	{
		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel examinePanel = new JPanel();
		examinePanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		examinePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		examinePanel.setLayout(new GridLayout(0, 1));

		final Font smallFont = FontManager.getRunescapeSmallFont();

		heading = new JLabel(htmlLabel("Examine Info", ""));
		heading.setFont(smallFont);


		type.setFont(smallFont);
		name.setFont(smallFont);
		wikiLnk.setFont(smallFont);

		type.setVisible(false);
		name.setVisible(false);

		wikiLnk.setVisible(false);
//		wikiLnk.enableAutoLinkHandler(true);

		examinePanel.add(heading);
		examinePanel.add(type);
		examinePanel.add(name);
		examinePanel.add(wikiLnk);
		examinePanel.add(Box.createGlue());

		add(examinePanel, BorderLayout.NORTH);

		eventBus.register(this);
	}

	public static void rebuildOnMenuClick(String examineType, String examineName){
		type.setVisible(true);
		name.setVisible(true);
		wikiLnk.setVisible(true);
		type.setText(htmlLabel("Type: ", examineType));
		name.setText(htmlLabel("Name: ", examineName));
		wikiLnk.setContentType("text/html");
		wikiLnk.setText("Wiki Link: " + getWikiLink(examineName));
	}

	public static String getWikiLink(String examineName){
		String examineNameURL = examineName.replaceAll(" ", "_");
		return "<a href=\"https://oldschool.runescape.wiki/w/" + examineNameURL + "\">"
				+ examineName + "</a>";
	}

	public static String htmlLabel(String key, String value)
	{
		return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + value + "</span></body></html>";
	}

//	@Subscribe
//	public void onSessionOpen(SessionOpen sessionOpen)
//	{
//		updateLoggedIn();
//	}
//
//	@Subscribe
//	public void onSessionClose(SessionClose e)
//	{
//		updateLoggedIn();
//	}
}
