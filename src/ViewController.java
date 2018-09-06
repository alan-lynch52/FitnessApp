
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
    private LogInPanel lp;
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
                frame.setSize(380, 700);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                lp = new LogInPanel();

                frame.setContentPane(lp);
                initLogInPanel();
            }
        });
    }
    private void init(){
        //Init JPanels
        mp = new MainPanel();
        ep = new ExercisePanel();
        erp = new ExerciseRecordPanel();
        crp = new CalorieRecordPanel();
        bwrp = new BodyweightRecordPanel();
        addExPanel = new AddExercisePanel();
        addExRecordPanel = new AddExerciseRecordPanel();
        addCalRecordPanel = new AddCalorieRecordPanel();
        addBwRecordPanel = new AddBodyweightRecordPanel();

        //Initialize Buttons for each JPanel
        initMainPanel();
        
        



        //Set content to main panel as default
        frame.setContentPane(mp);
    }
    private void initLogInPanel(){
        lp.getSubmitBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameVal = lp.getUsernameTxt().getText();
                String passwordVal = lp.getPasswordTxt().getText();
                if (!usernameVal.matches("") && !passwordVal.matches("")){
                    try{
                        if (modelController.userSignIn(usernameVal, passwordVal)){
                            init();
                        }
                        else{
                            JOptionPane.showMessageDialog(lp, "Username or password incorrect");
                        }
                        
                    }
                    catch(Exception ex){ex.printStackTrace();}
                }
                else{
                    System.out.println("Password or Username empty");
                }
            }
        });
    }
    private void initMainPanel(){
        
        
        
        
        mp.getExBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event hit");
                initExercisePanel();
                frame.setContentPane(ep);
                ep.repaint();
                ep.revalidate();
            }
        });
        mp.getExrBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event hit");
                initExerciseRecordPanel();
                frame.setContentPane(erp);
                erp.repaint();
                erp.revalidate();
            }
        });
        mp.getCrBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event hit");
                initCalorieRecordPanel();
                frame.setContentPane(crp);
                crp.repaint();
                crp.revalidate();
            }
        });
        mp.getBwrBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Event hit");
                initBodyweightRecordPanel();
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
                frame.repaint();
                frame.revalidate();
            }
        });
        ep.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                initAddExercisePanel();
        
                frame.setContentPane(addExPanel);
                frame.repaint();
                frame.revalidate();
            }
        });
        ep.setExerciseNames(modelController.getExerciseList());
        ep.getRemoveBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(ep, 
                        "Are you sure you want to remove this exercise?");
                if (choice == JOptionPane.YES_OPTION){
                    String exName = (String)ep.getExerciseList().getSelectedValue();
                    if (modelController.deleteExercise(exName)){
                        JOptionPane.showMessageDialog(ep, "Exercise Removed");
                        String[] exNames = modelController.getExerciseList();
                        ep.getExerciseList().setListData(exNames);
                        ep.repaint();
                        ep.revalidate();
                        
                    }
                    else{
                        JOptionPane.showMessageDialog(ep, "Failed to remove exercise");
                    }
                }
                
            }
        });
        
    }
    private void initExerciseRecordPanel(){
        erp.getBackBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
                frame.repaint();
                frame.revalidate();
            }
        });
        erp.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                initAddExerciseRecordPanel();
        
                frame.setContentPane(addExRecordPanel);
                frame.repaint();
                frame.revalidate();
            }
        });
        DefaultComboBoxModel cmbModel = 
                new DefaultComboBoxModel(modelController.getExerciseList());
        erp.getExerciseCmb().setModel(cmbModel);
        String selected = (String)erp.getExerciseCmb().getSelectedItem();
        double[] x = modelController.getERWeightList(selected);
        try{
        if (!(x.length==0)){
            erp.getChart().addSeries(selected, x, x);
        }
        }
        catch(NullPointerException e){
            System.out.println("Null pointer exception");
        }
        erp.getExerciseCmb().addItemListener(new ItemListener(){
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println(e.getStateChange());
                if (e.getStateChange() == 1){
                    String selected = (String)erp.getExerciseCmb().getSelectedItem();
                    //change exrList items
                    Object[] records = modelController.getExerciseRecordList(selected);
                    if (records != null)erp.getExrList().setListData(records);
                    //update graph
                    double[] x = modelController.getERWeightList(selected);
                    if (x==null)return;
                    String[] exNames = modelController.getExerciseList();
                    if (exNames==null)return;
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
                        System.out.println("Empty Series");
                    }
                }
            }
        });
        erp.getRemoveBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = erp.getExrList().getSelectedValue();
                System.out.println(o);
                if (modelController.deleteExerciseRecord(o)){
                    JOptionPane.showMessageDialog(erp, "Record removed");
                    String selected = (String)erp.getExerciseCmb().getSelectedItem();
                    erp.getExrList().setListData(modelController.getExerciseRecordList(selected));
                    double[] weights = modelController.getERWeightList(selected);
                    erp.getChart().removeSeries(selected);
                    erp.getChart().addSeries(selected, weights);
                    erp.repaint();
                    erp.revalidate();
                }
                else {
                    JOptionPane.showMessageDialog(erp, "Failed to remove record");
                }
            }
        });
    }
    private void initCalorieRecordPanel(){
        crp.getBackBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
                frame.repaint();
                frame.revalidate();
            }
        });
        crp.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                initAddCalorieRecordPanel();
                
                frame.setContentPane(addCalRecordPanel);
                frame.repaint();
                frame.revalidate();
                
            }
        });
        String dailyCals =  String.valueOf(modelController.getDailyCalories(new Date()));
        crp.getCaloriesLbl().setText(dailyCals);
        Object[] crList = modelController.getCalorieRecordList();
        if (crList!=null)crp.getCrList().setListData(crList);
        crp.getRemoveBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Object selected = crp.getCrList().getSelectedValue();
                if (modelController.deleteCalorieRecord(selected)){
                    JOptionPane.showMessageDialog(crp, "Removed record");
                    //update calorie list
                    Object[] crList = modelController.getCalorieRecordList();
                    if (crList!=null)crp.getCrList().setListData(crList);
                    String dailyCals = String.valueOf(modelController.getDailyCalories(new Date()));
                    crp.getCaloriesLbl().setText(dailyCals);
                    crp.repaint();
                    crp.revalidate();
                    
                }
                else{
                    JOptionPane.showMessageDialog(crp, "Failed to remove record");
                }
            }
        });
    }
    private void initBodyweightRecordPanel(){
        bwrp.getBackBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(mp);
                frame.repaint();
                frame.revalidate();
            }
        });
        bwrp.getAddBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                initAddBodyweightRecordPanel();
                frame.setContentPane(addBwRecordPanel);
                frame.repaint();
                frame.revalidate();
            }
        });
        double[] x = modelController.getBWRWeightList();
        if (x.length != 0){
            bwrp.getChart().addSeries("Bodyweight", x);
        }
        Object[] list = modelController.getBodyweightRecordList();
        bwrp.getBwrList().setListData(list);
        bwrp.getRemoveBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Object o = bwrp.getBwrList().getSelectedValue();
                if (modelController.deleteBodyWeightRecord(o)){
                    JOptionPane.showMessageDialog(bwrp, "Removed record");
                    Object[] list = modelController.getBodyweightRecordList();
                    double[] weight = modelController.getBWRWeightList();
                    bwrp.getBwrList().setListData(list);
                    bwrp.getChart().removeSeries("Bodyweight");
                    if (weight.length != 0){
                        bwrp.getChart().addSeries("Bodyweight", weight);
                    }
                    bwrp.repaint();
                    bwrp.revalidate();
                    
                }
                else{
                    JOptionPane.showMessageDialog(bwrp, "Failed to remove record");
                }
            }
        });
    }
    
    private void initAddCalorieRecordPanel(){
        addCalRecordPanel.getCancelBtn()
                .addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(crp);
                frame.repaint();
                frame.revalidate();
            }
        });
        addCalRecordPanel.getSubmitBtn()
                .addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String weightInput = addCalRecordPanel.getCaloriesTxt().getText();
                //send this value to the model controller
                int weight = Integer.parseInt(weightInput);
                if (!modelController.createCalorieRecord(weight)){
                    JOptionPane.showMessageDialog(addCalRecordPanel,
                            "Failed to add to database.",
                            "Error",JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String dailyCals =  String.valueOf(modelController.getDailyCalories(new Date()));
                    crp.getCaloriesLbl().setText(dailyCals);
                    Object[] crList = modelController.getCalorieRecordList();
                    crp.getCrList().setListData(crList);
                    frame.setContentPane(crp);
                    frame.repaint();
                    frame.revalidate();
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
                frame.repaint();
                frame.revalidate();
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
                    if(modelController.createExerciseRecord(exName, d)){
                        JOptionPane.showMessageDialog(addExPanel, "Success");
                        frame.setContentPane(erp);
                        erp.repaint();
                        erp.revalidate();
                        
                        String selected = (String)erp.getExerciseCmb().getSelectedItem();
                        double[] x = modelController.getERWeightList(selected);
                        double[] emptyArr = new double[x.length];
                        erp.getChart().updateXYSeries(selected, x, x, emptyArr);
                    }
                    else{
                        JOptionPane.showMessageDialog(addExPanel, "Failed to add to Database");
                    }
                }
            }
        });
        //add exercise names to combo box
        String[] exNames = modelController.getExerciseList();
        addExRecordPanel.setExNameCb(exNames);
    }
    private void initAddExercisePanel(){
        addExPanel.getCancelBtn().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(ep);
                frame.repaint();
                frame.revalidate();
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
                    if (modelController.createExercise(exName)){
                        JOptionPane.showMessageDialog(addExPanel, 
                                "Success");
                        String[] exNames = modelController.getExerciseList();
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
                frame.repaint();
                frame.revalidate();
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
                    if (modelController.createBodyWeightRecord(weightVal)){
                        JOptionPane.showMessageDialog(addBwRecordPanel,
                                "Success");
                        Object[] bwrList = modelController.getBodyweightRecordList();
                        double[] x = modelController.getBWRWeightList();
                        double[] errBar = new double[x.length];
                        try
                        {
                            bwrp.getChart().updateXYSeries("Bodyweight", x, x, errBar);
                            bwrp.getBwrList().setListData(bwrList);
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
