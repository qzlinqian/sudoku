package ninebox.GUI;

import ninebox.Auxiliary.HistoryArrayLoader;
import ninebox.Auxiliary.StopWatch;
import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Mother;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainInterface {
  boolean isPlaying;

  // My components
//  Array99Compute computer;
  Array99Solve solving, filling;
  Array99Generate generator, generator2;

  NineBlockBoxFill solveFillBox;
  NineBlockBoxFill freeFillBox;
  NineBlockBoxDisplay displayBox;

  NumberInputPanel numberPanel, freeNumberPanel;

  HistoryLoaderPanel historyPanel;

  QuestionGenerateButton generateProbButton;
  SudokuGenerateButton generateDispButton;
  HintButton hintButton;
  QuickSolveButton quickSolveButton;
  JButton solveButton;  // A "fake" quickSolveButton under freeFill mode, for the auto-compute algorithm won't survive
  ResetFilledButton resetButton1, resetButton2;
  JButton pauseButton, convertButton;

  JSlider slider;

  // Display frame & panel
  JFrame GUIMain;
  JPanel solvePanel, displayPanel, freePanel;
  JTabbedPane tab;

  StopWatch timer1, timer2;

  public MainInterface(){
    isPlaying = true;

    // init along with create relevance
//    computer = new Array99Compute();  // Used only in QuickSolveButton
    solving = new Array99Solve();
    filling = new Array99Solve();
    generator = new Array99Generate();  // Generate question
    generator2 = new Array99Generate();  // Generate new scheme to display
    solveFillBox = new NineBlockBoxFill();
    freeFillBox = new NineBlockBoxFill();
    displayBox = new NineBlockBoxDisplay();

    // Layout config
    solvePanel = new JPanel(new BorderLayout());
//    displayPanel = new JPanel(new BoxLayout(Box.createVerticalBox(),BoxLayout.Y_AXIS));

    displayPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(displayPanel,BoxLayout.Y_AXIS);
    displayPanel.setLayout(boxLayout);

    freePanel = new JPanel(new BorderLayout());
//    BoxLayout boxLayout1 = new BoxLayout(freePanel,BoxLayout.Y_AXIS);
//    freePanel.setLayout(boxLayout1);
    historyPanel = new HistoryLoaderPanel();

    tab = new JTabbedPane(SwingConstants.TOP);


    timer1 = new StopWatch();
    timer2 = new StopWatch();

    numberPanel = new NumberInputPanel(solveFillBox, solving, timer1, solvePanel);
    freeNumberPanel = new NumberInputPanel(freeFillBox, filling, timer2, freePanel);
    generateProbButton = new QuestionGenerateButton(solveFillBox, generator, solving);
    generateDispButton = new SudokuGenerateButton(displayBox, generator2);
    hintButton = new HintButton(solving, generator, solveFillBox);
    quickSolveButton = new QuickSolveButton(solveFillBox, solving);
    solveButton = new JButton("Solve");
    resetButton1 = new ResetFilledButton(solving,solveFillBox);
    resetButton2 = new ResetFilledButton(filling,freeFillBox);
    pauseButton = new JButton("Pause");
    convertButton = new JButton("Solve It!");


    GUIMain = new JFrame("Sudoku");

    tab.addTab("Load History",historyPanel);
    historyPanel.confirmButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          timer1.stopCommand();
          HistoryArrayLoader.loadHistory(historyPanel.getChosenFile(), solving, solveFillBox, timer1);
        } catch (IOException e1) {
          e1.printStackTrace();
        }
        tab.setSelectedIndex(1); // Go to the solve mode, under "Pause" state
        solveFillBox.setVisible(false);
        pauseButton.setText("Continue");
        isPlaying = false;
        // TODO: time set
      }
    });

    initSlider();

    // Components setup
    // Solve Mode
    pauseButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (isPlaying){
          timer1.stopCommand();
          solveFillBox.setVisible(false);
          pauseButton.setText("Continue");
          isPlaying = false;
        } else {
          timer1.continueCommand();
          solveFillBox.setVisible(true);
          pauseButton.setText("Pause");
          isPlaying = true;
        }
      }
    });
    solvePanel.add(solveFillBox, BorderLayout.CENTER);
    solvePanel.add(numberPanel, BorderLayout.SOUTH);
    // Solve mode auxiliary bar
    JPanel solveAuxPanel = new JPanel();
    JPanel solveAuxPanel1 = new JPanel();
    JPanel solveAuxPanel2 = new JPanel();

    BoxLayout verBoxLayout = new BoxLayout(solveAuxPanel,BoxLayout.Y_AXIS);
    BoxLayout horBoxLayout1 = new BoxLayout(solveAuxPanel1,BoxLayout.X_AXIS);
    BoxLayout horBoxLayout2 = new BoxLayout(solveAuxPanel2,BoxLayout.X_AXIS);
    solveAuxPanel.setLayout(verBoxLayout);
    solveAuxPanel1.setLayout(horBoxLayout1);
    solveAuxPanel2.setLayout(horBoxLayout2);
    solveAuxPanel1.add(generateProbButton);
    solveAuxPanel1.add(quickSolveButton);
    solveAuxPanel1.add(resetButton1);
    solveAuxPanel1.add(hintButton);
    solveAuxPanel1.add(timer1.timerPanel);
    solveAuxPanel2.add(new JLabel("Difficulty:"));
    solveAuxPanel2.add(slider);
    solveAuxPanel2.add(pauseButton);

    // TODO
    SaveButton saveButton = new SaveButton(solving, solveFillBox, timer1, historyPanel);
    solveAuxPanel2.add(saveButton);
    generateProbButton.addActionListener(timer1);
    resetButton1.addActionListener(timer1);
//    solvePanel.add(solveAuxPanel1, BorderLayout.NORTH);
//    solvePanel.add(solveAuxPanel2, BorderLayout.NORTH);
    solveAuxPanel.add(solveAuxPanel1);
    solveAuxPanel.add(solveAuxPanel2);
    solvePanel.add(solveAuxPanel, BorderLayout.NORTH);
    tab.addTab("Solve Problem",solvePanel);


    // Display Mode
    displayPanel.add(generateDispButton);
    displayPanel.add(displayBox);
    tab.addTab("Example Display",displayPanel);

    // Free Fill Mode
    JPanel fillAuxPanel = new JPanel();
    BoxLayout horBoxLayoutFill = new BoxLayout(fillAuxPanel,BoxLayout.X_AXIS);
    fillAuxPanel.setLayout(horBoxLayoutFill);
    fillAuxPanel.add(solveButton);
    fillAuxPanel.add(resetButton2);
    fillAuxPanel.add(convertButton);
    fillAuxPanel.add(timer2.timerPanel);
    solveButton.addActionListener(timer2);
    solveButton.addActionListener(timer2);
    freePanel.add(freeFillBox, BorderLayout.CENTER);
    freePanel.add(freeNumberPanel, BorderLayout.SOUTH);
    freePanel.add(fillAuxPanel, BorderLayout.NORTH);
    convertButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (int index=0;index<81;index++){
          int temp = filling.getContent(index);
          solving.setContent(index,temp);
          if (temp == 0){
            solveFillBox.resetCellNumber(index);
            solveFillBox.setWritable(index, true);
            solveFillBox.cellIsCorrect(index);
          } else {
            temp = Array99Mother.findNumber(temp);
            solveFillBox.setCellNumber(index, temp+1);
            solveFillBox.setWritable(index, false);
            solveFillBox.cellIsGiven(index);
          }
        }
        tab.setSelectedIndex(1);
      }
    });
    tab.addTab("Free Fill",freePanel);

    timer1.start();
    timer2.start();

    GUIMain.add(tab);
    GUIMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GUIMain.setBounds(20,20, 800,800);
    GUIMain.setVisible(true);
  }


  public static void main(String[] args){
    MainInterface test = new MainInterface();
  }


  private class HintButton extends JButton{
    HintButton(Array99Solve boxContents, Array99Generate generatedContents, NineBlockBoxFill box){
      super("Hint");
      this.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          int hintIndex = box.getActiveCellIndex();
          int temp = generatedContents.getContent(hintIndex);
          if (boxContents.getContent(hintIndex) == 0){
            boxContents.setContent(hintIndex, temp);  // Update number in array99
            temp = Array99Mother.findNumber(temp);
            box.setCellNumber(hintIndex, temp+1);  // Update display number
            box.validCell.set(hintIndex, true);
          }
        }
      });
    }
  }

  private void initSlider(){
    slider = new JSlider(SwingConstants.HORIZONTAL, 0,60,30);
    slider.setPaintTrack(true);
    slider.setPaintLabels(false);
    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
//        difficulty = slider.getValue();
        generator.setDifficulty(slider.getValue());
      }
    });
  }

}
