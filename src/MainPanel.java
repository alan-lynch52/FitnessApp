
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
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
public class MainPanel extends JPanel{
    private JButton exBtn;
    private JButton exrBtn;
    private JButton crBtn;
    private JButton bwrBtn;
    MainPanel(){
        this.setSize(380,800);
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        
        exBtn = new JButton("Exercises");
        exrBtn = new JButton("Exercise Records");
        crBtn = new JButton("Calorie Records");
        bwrBtn = new JButton("Bodyweight Records");
        
        
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        //change Button colour
        exBtn.setBackground(btnColor);
        exrBtn.setBackground(btnColor);
        crBtn.setBackground(btnColor);
        bwrBtn.setBackground(btnColor);
        //change button text color
        exBtn.setForeground(txtColor);
        exrBtn.setForeground(txtColor);
        crBtn.setForeground(txtColor);
        bwrBtn.setForeground(txtColor);
        
        //change button font
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,20);
        
        exBtn.setFont(font);
        exrBtn.setFont(font);
        crBtn.setFont(font);
        bwrBtn.setFont(font);
        
        //Add Swing components
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 20;
        c.weighty = 0.5;
        add(exBtn, c);
        
        c.gridy = 1;
        add(exrBtn, c);
        
        c.gridy = 2;
        add(crBtn, c);
        
        c.gridy = 3;
        add(bwrBtn, c);
        
    }

    public JButton getExBtn() {
        return exBtn;
    }

    public JButton getExrBtn() {
        return exrBtn;
    }

    public JButton getCrBtn() {
        return crBtn;
    }

    public JButton getBwrBtn() {
        return bwrBtn;
    }
}
