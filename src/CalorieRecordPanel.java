
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alan
 */
public class CalorieRecordPanel extends JPanel{
    private JButton backBtn;
    private JButton addBtn;
    private JList crList;
    private JLabel caloriesNameLbl;
    private JLabel caloriesLbl;
    CalorieRecordPanel(){
        backBtn = new JButton("Back");
        addBtn = new JButton("Add");
        crList = new JList();
        caloriesNameLbl = new JLabel("Calories:");
        caloriesLbl = new JLabel();
        
        
        //setLayout(new GridLayout());
        this.setSize(380,800);
        this.setLayout(new GridBagLayout());
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
        GridBagConstraints c = new GridBagConstraints();
        
        //add components
        
        backBtn.setBackground(btnColor);
        addBtn.setBackground(btnColor);
        
        
        backBtn.setForeground(txtColor);
        addBtn.setForeground(txtColor);
        caloriesLbl.setForeground(txtColor);
        caloriesNameLbl.setForeground(txtColor);
        
        backBtn.setFont(font);
        addBtn.setFont(font);
        caloriesLbl.setFont(font);
        caloriesNameLbl.setFont(font);
        
        
        
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(backBtn, c);
        
        c.gridx = 2;
        c.gridy = 0;
        this.add(addBtn, c);
        
        c.gridx = 1;
        c.gridy = 1;
        //this.add(crList, c);
        
        c.gridx = 1;
        c.gridy = 2;
        this.add(caloriesNameLbl, c);
        
        c.gridx = 2;
        c.gridy = 2;
        this.add(caloriesLbl, c);
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JList getCrList() {
        return crList;
    }

    public JLabel getCaloriesLbl() {
        return caloriesLbl;
    }
    
}
