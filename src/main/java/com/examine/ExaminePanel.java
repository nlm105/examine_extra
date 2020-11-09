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


@Singleton
public class ExaminePanel extends PluginPanel
{
	private static final String RUNELITE_LOGIN = "https://runelite_login/";

	private static final ImageIcon ARROW_RIGHT_ICON;
//	private static final ImageIcon GITHUB_ICON;
//	private static final ImageIcon DISCORD_ICON;
//	private static final ImageIcon PATREON_ICON;
//	private static final ImageIcon WIKI_ICON;
//	private static final ImageIcon IMPORT_ICON;

	private final JLabel loggedLabel = new JLabel();
	private final JRichTextPane emailLabel = new JRichTextPane();

	static
	{
		ARROW_RIGHT_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "/util/arrow_right.png"));
//		GITHUB_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "github_icon.png"));
//		DISCORD_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "discord_icon.png"));
//		PATREON_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "patreon_icon.png"));
//		WIKI_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "wiki_icon.png"));
//		IMPORT_ICON = new ImageIcon(ImageUtil.getResourceStreamFromClass(ExaminePanel.class, "import_icon.png"));
	}

	@Inject
	public static final JLabel type = new JLabel(htmlLabel("Type: ", ""));
	public static final JLabel name = new JLabel(htmlLabel("Name: ", ""));
	public static final JRichTextPane wikiLnk = new JRichTextPane();


	private JPanel syncPanel;
	private static JPanel actionsContainer;

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

		JLabel heading = new JLabel(htmlLabel("Examine Info", ""));
		heading.setFont(smallFont);


		type.setFont(smallFont);
		name.setFont(smallFont);
		wikiLnk.setFont(smallFont);

		type.setVisible(false);
		name.setVisible(false);

		wikiLnk.setVisible(false);
//		wikiLnk.enableAutoLinkHandler(true);



//		emailLabel.setForeground(Color.WHITE);
//		emailLabel.setFont(smallFont);
//		emailLabel.enableAutoLinkHandler(false);
//		emailLabel.addHyperlinkListener(e ->
//		{
//			if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType()) && e.getURL() != null)
//			{
//				if (e.getURL().toString().equals(RUNELITE_LOGIN))
//				{
//					executor.execute(sessionManager::login);
//				}
//			}
//		});

		examinePanel.add(heading);
		examinePanel.add(type);
		examinePanel.add(name);
		examinePanel.add(wikiLnk);
		examinePanel.add(Box.createGlue());



		actionsContainer = new JPanel();
		actionsContainer.setBorder(new EmptyBorder(10, 0, 0, 0));
		actionsContainer.setLayout(new GridLayout(0, 1, 0, 10));
//
//		syncPanel = buildLinkPanel(IMPORT_ICON, "Import local settings", "to remote RuneLite account", () ->
//		{
//			final int result = JOptionPane.showOptionDialog(syncPanel,
//				"This will replace your current RuneLite account settings with settings from your local profile.",
//				"Are you sure?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
//				null, new String[]{"Yes", "No"}, "No");
//
//			if (result == JOptionPane.YES_OPTION)
//			{
//				configManager.importLocal();
//			}
//		});

//		actionsContainer.add(buildLinkPanel(GITHUB_ICON, "Report an issue or", "make a suggestion", RuneLiteProperties.getGithubLink()));
//		actionsContainer.add(buildLinkPanel(DISCORD_ICON, "Talk to us on our", "Discord server", RuneLiteProperties.getDiscordInvite()));
//		actionsContainer.add(buildLinkPanel(PATREON_ICON, "Become a patron to", "help support RuneLite", RuneLiteProperties.getPatreonLink()));
//		actionsContainer.add(buildLinkPanel(WIKI_ICON, "Information about", "RuneLite and plugins", RuneLiteProperties.getWikiLink()));

//		actionsContainer.setVisible(false);
//
//		JPanel build = buildLinkPanel(ARROW_RIGHT_ICON, "Test","test",
//				"https://oldschool.runescape.wiki/w/Goblin");
//		actionsContainer.add(build);
//
//		System.out.println(build.getComponent(0).toString());

		add(examinePanel, BorderLayout.NORTH);
//		add(actionsContainer, BorderLayout.CENTER);

		updateLoggedIn();
		eventBus.register(this);
	}

	public static void rebuildOnMenuClick(String examineType, String examineName){
		type.setVisible(true);
		name.setVisible(true);
		wikiLnk.setVisible(true);
		type.setText(htmlLabel("Type: ", examineType));
		name.setText(htmlLabel("Name: ", examineName));
		wikiLnk.setContentType("text/html");
		wikiLnk.setText("Wiki Link: <a href=\"https://oldschool.runescape.wiki/w/" + examineName + "\">"
				+ examineName + "</a>");

//		JPanel panel = (JPanel)actionsContainer.getComponent(1);
//		JLabel label1 = (JLabel)panel.getComponent(0);
//		JLabel label2 = (JLabel)panel.getComponent(1);
////		actionsContainer.add(buildLinkPanel(ARROW_RIGHT_ICON, "Test","test",
////				"https://oldschool.runescape.wiki/w/"+examineName));
//		JPanel panel = (JPanel)actionsContainer.getComponent(0);
//		panel = buildLinkPanel(ARROW_RIGHT_ICON, "Test","test",
//				"https://oldschool.runescape.wiki/w/"+examineName);

//		actionsContainer.remove(0);
//		actionsContainer.add(buildLinkPanel(ARROW_RIGHT_ICON, "Test","test",
//				"https://oldschool.runescape.wiki/w/"+examineName));
//		actionsContainer.validate();
//		actionsContainer.repaint();

		actionsContainer.setVisible(true);
	}

	private void drawThing(String examineName){
		revalidate();
		repaint();
	}

	/**
	 * Builds a link panel with a given icon, text and url to redirect to.
	 */
	private static JPanel buildLinkPanel(ImageIcon icon, String topText, String bottomText, String url)
	{
		return buildLinkPanel(icon, topText, bottomText, () -> LinkBrowser.browse(url));
	}

	/**
	 * Builds a link panel with a given icon, text and callable to call.
	 */
	private static JPanel buildLinkPanel(ImageIcon icon, String topText, String bottomText, Runnable callback)
	{
		JPanel container = new JPanel();
		container.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		container.setLayout(new BorderLayout());
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		final Color hoverColor = ColorScheme.DARKER_GRAY_HOVER_COLOR;
		final Color pressedColor = ColorScheme.DARKER_GRAY_COLOR.brighter();

		JLabel iconLabel = new JLabel(icon);
		container.add(iconLabel, BorderLayout.WEST);

		JPanel textContainer = new JPanel();
		textContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		textContainer.setLayout(new GridLayout(2, 1));
		textContainer.setBorder(new EmptyBorder(5, 10, 5, 10));

		container.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				container.setBackground(pressedColor);
				textContainer.setBackground(pressedColor);
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				callback.run();
				container.setBackground(hoverColor);
				textContainer.setBackground(hoverColor);
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				container.setBackground(hoverColor);
				textContainer.setBackground(hoverColor);
				container.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				container.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				textContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
				container.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		});

		JLabel topLine = new JLabel(topText);
		topLine.setForeground(Color.WHITE);
		topLine.setFont(FontManager.getRunescapeSmallFont());

		JLabel bottomLine = new JLabel(bottomText);
		bottomLine.setForeground(Color.WHITE);
		bottomLine.setFont(FontManager.getRunescapeSmallFont());

		textContainer.add(topLine);
		textContainer.add(bottomLine);

		container.add(textContainer, BorderLayout.CENTER);
		JLabel arrowLabel = new JLabel(ARROW_RIGHT_ICON);
		container.add(arrowLabel, BorderLayout.EAST);

		return container;
	}


	private void updateLoggedIn()
	{
		final String name = sessionManager.getAccountSession() != null
			? sessionManager.getAccountSession().getUsername()
			: null;

		if (name != null)
		{
			emailLabel.setContentType("text/plain");
			emailLabel.setText(name);
			loggedLabel.setText("Logged in as");
//			actionsContainer.add(syncPanel, 0);
		}
		else
		{
			emailLabel.setContentType("text/html");
			emailLabel.setText("<a href=\"" + RUNELITE_LOGIN + "\">Login</a> to sync settings to the cloud.");
			loggedLabel.setText("Not logged in");
//			actionsContainer.remove(syncPanel);
		}
	}

	public static String htmlLabel(String key, String value)
	{
		return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + value + "</span></body></html>";
	}

	@Subscribe
	public void onSessionOpen(SessionOpen sessionOpen)
	{
		updateLoggedIn();
	}

	@Subscribe
	public void onSessionClose(SessionClose e)
	{
		updateLoggedIn();
	}
}
