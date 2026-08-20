/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hedgeyourbet;

/**
 *
 * @author emanuel
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
public class HedgeYourBetUsingFile extends JFrame implements ActionListener
{
    // Name of the file used to persist the score between games
    private static final String SCORE_FILE = "score.txt";

    // The three answer choices used for every question
    private final String[] choices = {"Gauteng", "Western Cape", "KwaZulu-Natal"};

    // Five trivia questions about South African provinces
    private final String[] questions = {
       "Which province hosts O.R. Tambo International Airport?",
        "Which province's capital is Cape Town?",
        "Which province is nicknamed \"The Garden Province\"?",
        "Which province is home to King Shaka International Airport?",
        "Which province has the highest population in Mzansi?"
    };

    // Index into 'choices' of the correct answer for each question
    // (0 = Gauteng, 1 = Western Cape, 2 = KwaZulu-Natal)
    private final int[] correctAnswers = {0, 1, 2, 2, 0};

    private int currentQuestion = 0;
    private int score = 0;
    private int previousScore = 0;

    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JLabel previousScoreLabel;
    private JCheckBox[] checkBoxes;
    private JButton submitButton;

    public HedgeYourBetUsingFile()
    {
        previousScore = readPreviousScore();

        setTitle("Hedge Your Bet (Using File)");
        setSize(500, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Top panel: previous score + current question ---
        JPanel topPanel = new JPanel(new BorderLayout());

        previousScoreLabel = new JLabel("Previous score: " + previousScore, SwingConstants.CENTER);
        topPanel.add(previousScoreLabel, BorderLayout.NORTH);

        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(questionLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // --- Checkboxes in the center ---
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new GridLayout(3, 1, 5, 5));
        checkBoxes = new JCheckBox[3];
        for (int i = 0; i < 3; i++)
        {
            checkBoxes[i] = new JCheckBox(choices[i]);
            checkPanel.add(checkBoxes[i]);
        }
        add(checkPanel, BorderLayout.CENTER);

        // --- Submit button and current score label at the bottom ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        bottomPanel.add(submitButton, BorderLayout.NORTH);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(scoreLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        displayQuestion();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Shows the current question and clears the checkboxes
    private void displayQuestion()
    {
        questionLabel.setText("<html><body style='width:400px'>" +
            (currentQuestion + 1) + ". " + questions[currentQuestion] + "</body></html>");
        for (JCheckBox box : checkBoxes)
        {
            box.setSelected(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int correctIndex = correctAnswers[currentQuestion];
        int numChecked = 0;
        boolean correctChecked = checkBoxes[correctIndex].isSelected();

        for (JCheckBox box : checkBoxes)
        {
            if (box.isSelected())
            {
                numChecked++;
            }
        }

        int pointsEarned = 0;
        if (numChecked == 0)
        {
            pointsEarned = 0;
        }
        else if (numChecked == 3)
        {
            // All three boxes always include the correct answer
            pointsEarned = 1;
        }
        else if (correctChecked)
        {
            if (numChecked == 1)
            {
                pointsEarned = 5;
            }
            else if (numChecked == 2)
            {
                pointsEarned = 2;
            }
        }
        // else: correct answer not selected and fewer than 3 boxes checked -> 0 points

        score += pointsEarned;
        scoreLabel.setText("Score: " + score);

        currentQuestion++;

        if (currentQuestion < questions.length)
        {
            displayQuestion();
        }
        else
        {
            showFinalMessage();
        }
    }

    private void showFinalMessage()
    {
        String message;
        if (score > 21)
        {
            message = "Fantastic!";
        }
        else if (score > 15)
        {
            message = "Very good";
        }
        else
        {
            message = "OK";
        }

        // Save this game's score so it becomes "previous score" next time
        saveScore(score);

        JOptionPane.showMessageDialog(this,
            "Quiz complete!\nFinal score: " + score + " out of 25\n\n" + message,
            "Results",
            JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    // Reads the previous score from SCORE_FILE.
    // If the file doesn't exist yet (first time playing) or can't be
    // read/parsed, returns 0.
    private int readPreviousScore()
    {
        File file = new File(SCORE_FILE);
        if (!file.exists())
        {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line = reader.readLine();
            if (line != null)
            {
                return Integer.parseInt(line.trim());
            }
        }
        catch (IOException | NumberFormatException e)
        {
            // If the file is missing, unreadable, or corrupted, just
            // treat it as if there was no previous score.
            return 0;
        }

        return 0;
    }

    // Writes the given score to SCORE_FILE, overwriting any previous value
    private void saveScore(int scoreToSave)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(SCORE_FILE)))
        {
            writer.println(scoreToSave);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(this,
                "Warning: could not save your score to file.",
                "File Error",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new HedgeYourBetUsingFile());
    }
}