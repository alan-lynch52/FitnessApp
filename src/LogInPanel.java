
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
public class LogInPanel extends JPanel{
    private JButton submitBtn;
    private JButton signUpBtn;
    private JTextField usernameTxt;
    private JTextField passwordTxt;
    private JLabel usernameLbl;
    private JLabel passwordLbl;

    LogInPanel(){
        this.submitBtn = new JButton("Login");
        this.signUpBtn = new JButton("Sign Up");
        this.usernameTxt = new JTextField(5);
        this.passwordTxt = new JTextField(5);
        this.usernameLbl = new JLabel("Username");
        this.passwordLbl = new JLabel("Password");
        
        this.setSize(380, 800);
        this.setLayout(new GridBagLayout());
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
        GridBagConstraints c = new GridBagConstraints();
        
        this.submitBtn.setBackground(btnColor);
        this.signUpBtn.setBackground(btnColor);
        
        this.submitBtn.setForeground(txtColor);
        this.signUpBtn.setForeground(txtColor);
        this.usernameLbl.setForeground(txtColor);
        this.passwordLbl.setForeground(txtColor);
        
        this.submitBtn.setFont(font);
        this.signUpBtn.setFont(font);
        this.usernameLbl.setFont(font);
        this.passwordLbl.setFont(font);
        this.usernameTxt.setFont(font);
        this.passwordTxt.setFont(font);
        
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridx = 1;
        c.gridy = 0;
        this.add(usernameLbl,c);
        
        c.gridx = 3;
        c.gridy = 0;
        this.add(usernameTxt,c);
        
        c.gridx = 1;
        c.gridy = 1;
        this.add(passwordLbl, c);
        
        c.gridx = 3;
        c.gridy = 1;
        this.add(passwordTxt, c);
        
        c.gridx = 2;
        c.gridy = 3;
        this.add(submitBtn, c);
        
        c.gridx = 2;
        c.gridy = 5;
        this.add(signUpBtn, c);
    }

    public JButton getSubmitBtn() {
        return submitBtn;
    }

    public JButton getSignUpBtn() {
        return signUpBtn;
    }

    public JTextField getUsernameTxt() {
        return usernameTxt;
    }

    public JTextField getPasswordTxt() {
        return passwordTxt;
    }
    
}
