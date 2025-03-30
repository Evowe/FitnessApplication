package fitness.app.CreateExercise;


import com.formdev.flatlaf.FlatClientProperties;
import fitness.app.Objects.Account;
import fitness.app.Statistics.StatsModel;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateExcerciseView {
    private static JFrame window;
    private static JPanel mainPanel;

    public CreateExcerciseView(Account acc) {
        //Application window
        window = new JFrame("Create Exercise");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(1200, 700));
        window.setLocationRelativeTo(null);


        mainPanel = new JPanel(new GridLayout(3, 3));


        //DISPLAY CALORIES

        /*
        JPanel excDis = new JPanel(new MigLayout("fill,insets 20", "left", "Top"));
        JPanel EdisMen = new JPanel(new MigLayout("wrap,fillx,insets 30", "fill,275"));
        EdisMen.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");
        JLabel EdisTitle = new JLabel("New Exercise: ");
        EdisTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");
        EdisMen.add(EdisTitle);
        excDis.add(EdisMen);
        mainPanel.add(excDis);


         */



        //Calorie Panel

        JLabel EdisTitle = new JLabel("New Exercise: ");
        EdisTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JPanel excPanel = new JPanel( new MigLayout("fill,insets 0", "center"));

        //JPanel ExcMenu = new JPanel(new MigLayout("wrap,fillx,insets 0", "fill,275"));
        JPanel ExcMenu = new JPanel(new MigLayout("wrap,fill,insets 0", "fill,500"));
        ExcMenu.putClientProperty(FlatClientProperties.STYLE, "" + "arc:20;" + "background:lighten(@background,5%)");

        JLabel exName = new JLabel("Exercise Name");
        exName.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JLabel Edescription = new JLabel("*All fields required");
        Edescription.putClientProperty(FlatClientProperties.STYLE, "" + "foreground:darken(@foreground,33%)");

        JTextField NameField = new JTextField();
        NameField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Name");

        JLabel desTitle = new JLabel("Exercise Description");
        exName.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JTextField DescriptionField = new JTextField();
        DescriptionField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Description");

        JLabel dropdownTitle = new JLabel("Exercise Type");
        dropdownTitle.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        String[] options = {"No Selection", "Sets", "Sets w/ Weight", "Sets w/o Weight"};
        JComboBox<String> dropdown = new JComboBox<>(options);

        JLabel repAmount = new JLabel("Rep Amount");
        repAmount.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JTextField RepAmountField = new JTextField();
        RepAmountField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Rep Amount");

        JLabel weightAmount = new JLabel("Weight Amount");
        weightAmount.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +6");

        JTextField WeightAmountField = new JTextField();
        WeightAmountField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter Weight Amount");


        JButton eSubmitButton = new JButton("Submit");
        eSubmitButton.putClientProperty(FlatClientProperties.STYLE, "" + "background:lighten(@background,10%);");
        eSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String Name = NameField.getText();
                String Description = DescriptionField.getText();
                int RepAmount = Integer.parseInt(RepAmountField.getText());
                double WeightAmount = Double.parseDouble(WeightAmountField.getText());
            }
        });

        ExcMenu.add(EdisTitle, "gapy 0");
        ExcMenu.add(exName, "gapy 0");
        ExcMenu.add(NameField, "gapy 0");
        ExcMenu.add(NameField, "gapy 0");
        ExcMenu.add(desTitle, "gapy 0");
        ExcMenu.add(DescriptionField, "gapy 0");
        ExcMenu.add(DescriptionField, "gapy 0");
        ExcMenu.add(dropdownTitle, "gapy 0");
        ExcMenu.add(dropdown, "gapy 0");
        ExcMenu.add(repAmount, "gapy 0");
        ExcMenu.add(RepAmountField, "gapy 0");
        ExcMenu.add(weightAmount, "gapy 0");
        ExcMenu.add(WeightAmountField, "gapy 0");
        ExcMenu.add(eSubmitButton, "gapy 0");
        excPanel.add(ExcMenu, "gapy 0");

        mainPanel.add(excPanel, "span, growy, gap 10 10 10 10");

        window.add(new JLabel (""));
        window.add(mainPanel);
        window.setVisible(true);
    }





    public JPanel getViewPanel()
    {
        return mainPanel;
    }
}



