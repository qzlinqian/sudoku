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
  boolean isPlaying2;

  // My components
//  Array99Compute computer;
  private Array99Solve solving, filling;
  private Array99Generate generator, generator2;

  private NineBlockBoxFill solveFillBox;
  private NineBlockBoxFill freeFillBox;
  private NineBlockBoxDisplay displayBox;

  private NumberInputPanel numberPanel, freeNumberPanel;

  private HistoryLoaderPanel historyPanel;

  private QuestionGenerateButton generateProbButton;
  private SudokuGenerateButton generateDispButton;
  private HintButton hintButton;
  private QuickSolveButton quickSolveButton;
  private JButton solveButton;  // A "fake" quickSolveButton under freeFill mode, for the auto-compute algorithm won't survive
  private ResetFilledButton resetButton1, resetButton2;
  private JButton pauseButton, convertButton, pauseButton2;

  JSlider slider;

  // Display frame & panel
  JFrame GUIMain;
  JPanel solvePanel, displayPanel, freePanel;
  JTabbedPane tab;

  StopWatch timer1, timer2;

  public MainInterface(){
    isPlaying = true;
    isPlaying2 = false;

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

    displayPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(displayPanel,BoxLayout.Y_AXIS);
    displayPanel.setLayout(boxLayout);

    freePanel = new JPanel(new BorderLayout());

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
    pauseButton2 = new JButton("Start");
    convertButton = new JButton("Solve It!");

    GUIMain = new JFrame("Sudoku");


    initHistoryPanel();
    tab.addTab("Load History",historyPanel);


    initSlider();

    // Components setup
    // Solve Mode
    initSolvePanel();

    tab.addTab("Solve Problem",solvePanel);


    // Display Mode
    displayPanel.add(generateDispButton);
    displayPanel.add(displayBox);
    tab.addTab("Example Display",displayPanel);

    // Free Fill Mode
    initFreePanel();
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

  private void initHistoryPanel(){
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

  private void initSolvePanel(){
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
    solveAuxPanel.add(solveAuxPanel1);
    solveAuxPanel.add(solveAuxPanel2);
    solvePanel.add(solveAuxPanel, BorderLayout.NORTH);
  }

  private void initFreePanel(){
    JPanel fillAuxPanel = new JPanel();
    BoxLayout horBoxLayoutFill = new BoxLayout(fillAuxPanel,BoxLayout.X_AXIS);
    fillAuxPanel.setLayout(horBoxLayoutFill);
    fillAuxPanel.add(solveButton);
    fillAuxPanel.add(resetButton2);
    fillAuxPanel.add(convertButton);
    fillAuxPanel.add(timer2.timerPanel);
    fillAuxPanel.add(pauseButton2);
    solveButton.addActionListener(timer2);
    solveButton.addActionListener(timer2);
    freePanel.add(freeFillBox, BorderLayout.CENTER);
    freeFillBox.setVisible(false);  // So that we won't see the box at first
    freePanel.add(freeNumberPanel, BorderLayout.SOUTH);
    freePanel.add(fillAuxPanel, BorderLayout.NORTH);
    pauseButton2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (isPlaying2){
          timer2.stopCommand();
          freeFillBox.setVisible(false);
          pauseButton2.setText("Continue");
          isPlaying2 = false;
        } else {
          timer2.startCommand();
          freeFillBox.setVisible(true);
          isPlaying2 = true;
        }
      }
    });
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
        timer1.startCommand();
      }
    });
  }

}
