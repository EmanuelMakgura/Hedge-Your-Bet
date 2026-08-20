/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
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

public class HedgeYourBet extends JFrame implements ActionListener {
    // The three answer choices used for every question
    private final String[] choices = {"Gauteng", "Western Cape", "KwaZulu-Natal"};

    // Five trivia questions about South African Provinces
    private final String[] questions = {
        "Which province hosts O.R. Tambo International Airport?",
        "Which province's capital is Cape Town?",
        "Which province is nicknamed \"The Garden Province\"?",
        "Which province is home to King Shaka International Airport?",
        "Which province has the highest population in Mzansi?"
    };

    // Index into 'choices' of the correct answer for each question
    // (0 = California, 1 = Florida, 2 = New York)
    private final int[] correctAnswers = {0, 1, 1, 2, 0};

    private int currentQuestion = 0;
    private int score = 0;

    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JCheckBox[] checkBoxes;
    private JButton submitButton;

    public HedgeYourBet()
    {
        setTitle("Hedge Your Bet");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Question label at the top ---
        questionLabel = new JLabel();
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

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

        // --- Submit button and score label at the bottom ---
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

        JOptionPane.showMessageDialog(this,
            "Quiz complete!\nFinal score: " + score + " out of 25\n\n" + message,
            "Results",
            JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new HedgeYourBet());
    }
}