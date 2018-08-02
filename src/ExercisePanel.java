
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
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
public class ExercisePanel extends JPanel {
    private JButton backBtn;
    private JButton addBtn;
    private JButton removeBtn;
    private JList exerciseList;
    public ExercisePanel(){
        this.setSize(380,800);
        //this.setBounds(50, 50, 380, 800);
        
        GridBagConstraints c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        Color darkBlue = new Color(34, 35, 38);
        this.setBackground(darkBlue);
        Color btnColor = new Color(20, 125, 255);
        Color txtColor = new Color(242, 243, 244);
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,15);
        
        backBtn = new JButton("Back");
        addBtn = new JButton("Add");
        removeBtn = new JButton("Remove");
        exerciseList = new JList();
        
        //change button colour
        backBtn.setBackground(btnColor);
        addBtn.setBackground(btnColor);
        removeBtn.setBackground(btnColor);
        
        exerciseList.setBackground(new Color(30, 31, 33));
        
        backBtn.setForeground(txtColor);
        addBtn.setForeground(txtColor);
        removeBtn.setForeground(txtColor);
        exerciseList.setForeground(txtColor);
        
        backBtn.setFont(font);
        addBtn.setFont(font);
        removeBtn.setFont(font);
        exerciseList.setFont(font);
        
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(backBtn, c);
        
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        this.add(addBtn, c);
        
        c.gridx = 1;
        c.gridy = 1;
        //c.weightx = 0.5;
        //c.weighty = 0.5;
        c.anchor = GridBagConstraints.BASELINE;
        this.add(exerciseList, c);
        
        c.gridx = 2;
        c.gridy = 1;
        this.add(removeBtn, c);
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JButton getAddBtn() {
        return addBtn;
    }

    public JList getExerciseList() {
        return exerciseList;
    }
    
    public void setExerciseNames(String[] names){
        exerciseList.setListData(names);
    }

    public JButton getRemoveBtn() {
        return removeBtn;
    }
    
}
