
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alan
 */
public class AddCalorieRecordPanel extends JPanel{
    private JButton submitBtn;
    private JButton cancelBtn;
    private JTextField caloriesTxt;
    private JLabel caloriesLbl;
    AddCalorieRecordPanel(){
        this.setSize(380,800);
        this.setLayout(new GridBagLayout());
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
        submitBtn = new JButton("Submit");
        cancelBtn = new JButton("Cancel");
        caloriesTxt = new JTextField(5);
        caloriesLbl = new JLabel("Calories:");
        
        submitBtn.setBackground(btnColor);
        cancelBtn.setBackground(btnColor);
        
        submitBtn.setForeground(txtColor);
        cancelBtn.setForeground(txtColor);
        caloriesLbl.setForeground(txtColor);
        
        submitBtn.setFont(font);
        cancelBtn.setFont(font);
        caloriesLbl.setFont(font);
        caloriesTxt.setFont(font);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridx = 0;
        c.gridy = 0;
        add(caloriesLbl, c);
        
        c.gridx = 1;
        c.gridy = 0;
        add(caloriesTxt, c);
        
        c.gridx = 0;
        c.gridy = 1;
        add(submitBtn, c);
        
        c.gridx = 1;
        c.gridy = 1;
        add(cancelBtn, c);
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JTextField getCaloriesTxt() {
        return caloriesTxt;
    }

    public JLabel getCaloriesLbl() {
        return caloriesLbl;
    }
    
}
