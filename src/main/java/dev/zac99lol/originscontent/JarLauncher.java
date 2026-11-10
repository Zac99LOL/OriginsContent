package dev.zac99lol.originscontent;

import javax.swing.*;

public class JarLauncher {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(
            null,
            "Why did you open the jar file?",
            "Question",
            JOptionPane.QUESTION_MESSAGE
        );
    }
}
