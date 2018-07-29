
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
public class AddExercisePanel extends JPanel{
    private JButton submitBtn;
    private JButton cancelBtn;
    private JTextField nameTxt;
    private JLabel nameLbl;
    AddExercisePanel(){
        this.setSize(380,800);
        this.setLayout(new GridBagLayout());
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
        GridBagConstraints c = new GridBagConstraints();
        
        submitBtn = new JButton("Submit");
        cancelBtn = new JButton("Cancel");
        nameTxt = new JTextField(5);
        nameLbl = new JLabel("Name:");
        
        submitBtn.setBackground(btnColor);
        cancelBtn.setBackground(btnColor);
        
        submitBtn.setForeground(txtColor);
        cancelBtn.setForeground(txtColor);
        nameLbl.setForeground(txtColor);
        
        submitBtn.setFont(font);
        cancelBtn.setFont(font);
        nameLbl.setFont(font);
        nameTxt.setFont(font);
        
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        //c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridx = 2;
        c.gridy = 0;
        add(nameLbl, c);
        
        c.gridx = 3;
        c.gridy = 0;
        add(nameTxt, c);
        
        c.gridx = 2;
        c.gridy = 1;
        add(submitBtn, c);
        
        c.gridx = 3;
        c.gridy = 1;
        add(cancelBtn, c);
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JTextField getNameTxt() {
        return nameTxt;
    }

    public JLabel getNameLbl() {
        return nameLbl;
    }
    
}
