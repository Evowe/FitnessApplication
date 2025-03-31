package fitness.app.CreateExercise;

import com.formdev.flatlaf.FlatClientProperties;
import fitness.app.Objects.Account;
import fitness.app.Statistics.StatsModel;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateExcerciseViewMain {

    public static void main(String[] args) {
        /*
        JFrame frame = new JFrame("CreateExerciseInterface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

         */
        CreateExcerciseView excerciseView = new CreateExcerciseView(new Account("rylan", "djranch1122"));

    }
}
