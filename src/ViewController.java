
import fitnessapp.ModelController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alan
 */
public class ViewController {
    private ModelController modelController;
    private JFrame frame;
    private MainPanel mp;
    private ExercisePanel ep;
    private ExerciseRecordPanel erp;
    private CalorieRecordPanel crp;
    private BodyweightRecordPanel bwrp;
    private AddExercisePanel addExPanel;
    private AddExerciseRecordPanel addExRecordPanel;
    private AddCalorieRecordPanel addCalRecordPanel;
    private AddBodyweightRecordPanel addBwRecordPanel;
    ViewController(){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                //init model controller
                modelController = new ModelController();
                
                //init JFrame
                frame = new JFrame("FitnessApp");
                frame.setVisible(true);
                frame.setSize(380, 600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //Init JPanels
                mp = new MainPanel();
                ep = new ExercisePanel();
                erp = new ExerciseRecordPanel();
                crp = new CalorieRecordPanel();
                bwrp = new BodyweightRecordPanel();
                addExPanel = new AddExercisePanel();
                addExRecordPanel = new AddExerciseRecordPanel(modelController.g_ExerciseList());
                addCalRecordPanel = new AddCalorieRecordPanel();
                addBwRecordPanel = new AddBodyweightRecordPanel();
                
                //Initialize Buttons for each JPanel
                initMainPanel();
                initExercisePanel();
                initExerciseRecordPanel();
                initCalorieRecordPanel();
                initBodyweightRecordPanel();
                initAddCalorieRecordPanel();
                initAddExerciseRecordPanel();
                initAddExercisePanel();
                initAddBodyweightRecordPanel();
                

                
                //Set content to main panel as default
                frame.setContentPane(mp);
                
            }
        });
    }
    private void initMainPanel(){
        mp.getExBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(ep);
                ep.repaint();
                ep.revalidate();
            }
        });
        mp.getExrBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(erp);
                erp.repaint();
                erp.revalidate();
            }
        });
        mp.getCrBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(crp);
                crp.repaint();
                crp.revalidate();
            }
        });
        mp.getBwrBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(bwrp);
                bwrp.repaint();
                bwrp.revalidate();
            }
        });
    }
    private void initExercisePanel(){
        ep.getBackBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
            }
        });
        ep.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(addExPanel);
            }
        });
        ep.setExerciseNames(modelController.g_ExerciseList());
    }
    private void initExerciseRecordPanel(){
        erp.getBackBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
            }
        });
        erp.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(addExRecordPanel);
            }
        });
        DefaultComboBoxModel cmbModel = 
                new DefaultComboBoxModel(modelController.g_ExerciseList());
        erp.getExerciseCmb().setModel(cmbModel);
        String selected = (String)erp.getExerciseCmb().getSelectedItem();
        double[] x = modelController.g_ERWeight(selected);
        if (!(x.length==0)){
            erp.getChart().addSeries(selected, x, x);
        }
        erp.getExerciseCmb().addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange());
                if (e.getStateChange() == 1){
                    String selected = (String)erp.getExerciseCmb().getSelectedItem();
                    double[] x = modelController.g_ERWeight(selected);
                    String[] exNames = modelController.g_ExerciseList();
                    try{
                        for (String ex : exNames){
                            erp.getChart().removeSeries(ex);
                        }
                        if (!(x.length == 0)){
                            erp.getChart().addSeries(selected,x);
                        }
                        erp.repaint();
                        erp.revalidate();
                    }catch(IllegalArgumentException exception){
                        //erp.getChart().updateXYSeries(selected, x, x, x);
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });
        
    }
    private void initCalorieRecordPanel(){
        crp.getBackBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
            }
        });
        crp.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(addCalRecordPanel);
            }
        });
        String dailyCals =  String.valueOf(modelController.g_DailyCalories(new Date()));
        crp.getCaloriesLbl().setText(dailyCals);
    }
    private void initBodyweightRecordPanel(){
        bwrp.getBackBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
            }
        });
        bwrp.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(addBwRecordPanel);
            }
        });
        double[] x = modelController.g_BWRWeight();
        if (x.length != 0){
            bwrp.getChart().addSeries("Bodyweight", x);
        }
    }
    
    private void initAddCalorieRecordPanel(){
        addCalRecordPanel.getCancelBtn()
                .addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(crp);
            }
        });
        addCalRecordPanel.getSubmitBtn()
                .addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String weightInput = addCalRecordPanel.getCaloriesTxt().getText();
                //send this value to the model controller
                int weight = Integer.parseInt(weightInput);
                if (!modelController.i_CalorieRecord(weight)){
                    JOptionPane.showMessageDialog(addCalRecordPanel,
                            "Failed to add to database.",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String dailyCals =  String.valueOf(modelController.g_DailyCalories(new Date()));
                    crp.getCaloriesLbl().setText(dailyCals);
                    frame.setContentPane(crp);
                    JOptionPane.showMessageDialog(crp, "Added Record"
                            ,"Success",JOptionPane.PLAIN_MESSAGE);
                    crp.repaint();
                    crp.revalidate();
                }
                
            }
        });
    }
    private void initAddExerciseRecordPanel(){
        addExRecordPanel.getCancelBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(erp);
            }
        });
        addExRecordPanel.getSubmitBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String exName = (String)addExRecordPanel.getExNameCb().getSelectedItem();
                String weight = addExRecordPanel.getWeightTxt().getText();
                if (exName.isEmpty() || weight.isEmpty()){
                    JOptionPane.showMessageDialog(addExPanel, "Blank fields");
                }
                else{
                    double d = Double.parseDouble(weight);
                    if(modelController.i_ExerciseRecord(exName, d)){
                        JOptionPane.showMessageDialog(addExPanel, "Success");
                        
                        
                        
                        frame.setContentPane(erp);
                        erp.repaint();
                        erp.revalidate();
                        
                        String selected = (String)erp.getExerciseCmb().getSelectedItem();
                        double[] x = modelController.g_ERWeight(selected);
                        double[] emptyArr = new double[x.length];
                        erp.getChart().updateXYSeries(selected, x, x, emptyArr);
                    }
                    else{
                        JOptionPane.showMessageDialog(addExPanel, "Failed to add to Database");
                    }
                }
            }
        });
        //add arrays to chart!
    }
    private void initAddExercisePanel(){
        addExPanel.getCancelBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(ep);
            }
        });
        addExPanel.getSubmitBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String exName = addExPanel.getNameTxt().getText();
                if (exName.isEmpty()){
                    JOptionPane.showMessageDialog(addExPanel,
                            "Name cannot be left blank",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if (modelController.i_Exercise(exName)){
                        JOptionPane.showMessageDialog(addExPanel, 
                                "Success");
                        String[] exNames = modelController.g_ExerciseList();
                        ep.getExerciseList().setListData(exNames);
                        frame.setContentPane(ep);
                        ep.repaint();
                        ep.revalidate();
                    }
                    else{
                        JOptionPane.showMessageDialog(addExPanel, 
                                "Failed to add to Database");
                    }
                }
            }
        });
    }
    private void initAddBodyweightRecordPanel(){
        addBwRecordPanel.getCancelBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(bwrp);
            }
        });
        addBwRecordPanel.getSubmitBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String weight = addBwRecordPanel.getWeightTxt().getText();
                if (weight.isEmpty()){
                    JOptionPane.showMessageDialog(addBwRecordPanel,
                            "Weight cannot be left blank");
                }
                else{
                    double weightVal = Double.parseDouble(weight);
                    if (modelController.i_BodyWeightRecord(weightVal)){
                        JOptionPane.showMessageDialog(addBwRecordPanel,
                                "Success");
                        double[] x = modelController.g_BWRWeight();
                        double[] errBar = new double[x.length];
                        try
                        {
                            bwrp.getChart().updateXYSeries("Bodyweight", x, x, errBar);
                        }
                        catch(IllegalArgumentException err){
                            System.out.println("Series was empty, now initialized");
                            
                            bwrp.getChart().addSeries("Bodyweight", x,x);
                        }
                        frame.setContentPane(bwrp);
                        bwrp.repaint();
                        bwrp.revalidate();
                    }
                    else {
                        JOptionPane.showMessageDialog(addBwRecordPanel,
                                "Failed to add to the Database");
                    }
                }
            }
        });
    }
    public static void main(String[] args){
        ViewController vc = new ViewController();

    }
}
