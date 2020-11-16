package com.pjxpro.swingio;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;
import javax.swing.border.Border;

import com.pjxpro.swingio.QuizCardBuilder.NewMenListener;

public class QuizCardPlayer {
	private JTextArea display;
	private ArrayList<QuizCard> cardList;
	private QuizCard currentCard;
	private int currentCardIndex;
	private JFrame frame;
	private JButton nextButton;
	private boolean isShowAnswer;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}

	public void go() {
		// TODO Auto-generated method stub
		frame = new JFrame("Quiz Card Player");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sacnserif", Font.BOLD, 24);

		display = new JTextArea(10, 20);
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setFont(bigFont);

		JScrollPane qScorller = new JScrollPane(display);
		qScorller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScorller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		nextButton = new JButton("Show Question");
		mainPanel.add(qScorller);
		mainPanel.add(nextButton);
		nextButton.addActionListener(new NextCardListener());

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem loadMenuItem = new JMenuItem("Load card set");
		loadMenuItem.addActionListener(new OpenMenuListener());
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);

		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(640, 500);
		frame.setVisible(true);

	}

	public class NextCardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (isShowAnswer) {
				display.setText(currentCard.getAnswer());
				nextButton.setText("next Card");
				isShowAnswer = false;
			} else {
				if (currentCardIndex < cardList.size()) {
					showNextCard();
				} else {
					display.setText("That was last card");
					nextButton.setEnabled(false);
				}
			}
		}

		
	}

	public class OpenMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JFileChooser fileOpen = new JFileChooser();
			fileOpen.showOpenDialog(frame);
			loadFile(fileOpen.getSelectedFile());
		}
	}

	private void loadFile(File file) {
		// TODO Auto-generated method stub
		cardList = new ArrayList<QuizCard>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				makeCard(line);
			}
			reader.close();
		} catch (Exception ex) {
			System.out.println("couldn't read the card file");
			ex.printStackTrace();
		}
	}

	private void makeCard(String lineToParse) {
		String[] result = lineToParse.split("/");
		QuizCard card = new QuizCard(result[0], result[1]);
		cardList.add(card);
		System.out.println("made a card");
	}

	private void showNextCard() {
		currentCard = cardList.get(currentCardIndex);
		currentCardIndex++;
		display.setText(currentCard.getQuestion());
		nextButton.setText("Show Answer");
		isShowAnswer = true;
	}
}
