package Application.TheSwoleSection.ExerciseLibrary;

import javax.swing.*;
import java.awt.*;

public class ExerciseLibraryMain {

    public static void main(String[] args) {
        ExerciseLibraryView excerciseLibraryView = new ExerciseLibraryView();

        JFrame frame = new JFrame("CreateExerciseInterface");
        frame.add(excerciseLibraryView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBackground(new Color(255,255,255));
        frame.setVisible(true);
    }
}
