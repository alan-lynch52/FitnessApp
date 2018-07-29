
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alan
 */
public class ExerciseRecordPanel extends JPanel {
    private JButton backBtn;
    private JButton addBtn;
    private JList exrList;
    private XYChart chart;
    private JComboBox exerciseCmb;
    ExerciseRecordPanel(){
        this.setSize(380,800);
        this.setLayout(new GridBagLayout());
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
        GridBagConstraints c = new GridBagConstraints();
        
        chart = new XYChart(300,200);
        chart.setTitle("Records visually:");

        JPanel jp = new JPanel();
        XChartPanel xcp = new XChartPanel(chart);
        jp.add(xcp);
        jp.setBackground(darkBlue);
        
        backBtn = new JButton("Back");
        addBtn = new JButton("Add");
        exrList = new JList();
        exerciseCmb = new JComboBox();
        
        backBtn.setBackground(btnColor);
        addBtn.setBackground(btnColor);
        
        backBtn.setForeground(txtColor);
        addBtn.setForeground(txtColor);
        
        backBtn.setFont(font);
        addBtn.setFont(font);
        
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridx = 0;
        c.gridy = 0;
        add(backBtn, c);
        
        c.gridx = 2;
        c.gridy = 0;
        add(addBtn, c);
        
        c.gridx = 1;
        c.gridy = 1;
        add(exerciseCmb,c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = GridBagConstraints.REMAINDER;
        c.gridwidth  = GridBagConstraints.REMAINDER;
        add(jp, c);
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JList getExrList() {
        return exrList;
    }

    public XYChart getChart() {
        return chart;
    }

    public JComboBox getExerciseCmb() {
        return exerciseCmb;
    }

    
}
