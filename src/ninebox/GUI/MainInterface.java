package ninebox.GUI;

import ninebox.DataStructure.Array99Compute;
import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainInterface {
  // My components
  Array99Compute computer;
  Array99Solve solving, filling;
  Array99Generate generator, generator2;
  NineBlockBoxFill solveFillBox;
  NineBlockBoxFill freeFillBox;
  NineBlockBoxDisplay displayBox;
  NumberInputPanel numberPanel, freeNumberPanel;
  QuestionGenerateButton generateProbButton;
  SudokuGenerateButton generateDispButton;

  // Display frame & panel
  JFrame GUIMain;
  JPanel solvePanel, displayPanel, freePanel;
  JTabbedPane tab;

  public MainInterface(){
    // init along with create relevance
    computer = new Array99Compute();
    solving = new Array99Solve();
    filling = new Array99Solve();
    generator = new Array99Generate();
    generator2 = new Array99Generate();
    solveFillBox = new NineBlockBoxFill();
    freeFillBox = new NineBlockBoxFill();
    displayBox = new NineBlockBoxDisplay();

    numberPanel = new NumberInputPanel(solveFillBox,solving);
    freeNumberPanel = new NumberInputPanel(freeFillBox,filling);
    generateProbButton = new QuestionGenerateButton(solveFillBox,generator,solving);
    generateDispButton = new SudokuGenerateButton(displayBox,generator2);

    GUIMain = new JFrame("Sudoku");

    // Layout config
    solvePanel = new JPanel(new BorderLayout());
//    displayPanel = new JPanel(new BoxLayout(Box.createVerticalBox(),BoxLayout.Y_AXIS));

    displayPanel = new JPanel();
    BoxLayout boxLayout = new BoxLayout(displayPanel,BoxLayout.Y_AXIS);
    displayPanel.setLayout(boxLayout);

    freePanel = new JPanel(new BorderLayout());
//    BoxLayout boxLayout1 = new BoxLayout(freePanel,BoxLayout.Y_AXIS);
//    freePanel.setLayout(boxLayout1);

    tab = new JTabbedPane(SwingConstants.TOP);

    // Components setup
    // Solve Mode
    solvePanel.add(solveFillBox, BorderLayout.CENTER);
    solvePanel.add(numberPanel, BorderLayout.SOUTH);
    solvePanel.add(generateProbButton, BorderLayout.NORTH);
    // TODO: add timer, hint button, reset button, generate button, difficulty setting etc.
    tab.addTab("Solve Problem",solvePanel);

    // Display Mode
    displayPanel.add(generateDispButton);
    displayPanel.add(displayBox);
    tab.addTab("Example Display",displayPanel);

    // Free Fill Mode
    freePanel.add(freeFillBox, BorderLayout.CENTER);
    freePanel.add(freeNumberPanel, BorderLayout.SOUTH);
    tab.addTab("Free Fill",freePanel);

    GUIMain.add(tab);
    GUIMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GUIMain.setBounds(20,20, 500,500);
    GUIMain.setVisible(true);
  }


  // That's for test
  /*public static void main(String[] args){
    NineBlockBoxFill box = new NineBlockBoxFill();
    Array99Solve content = new Array99Solve();
    JFrame f = new JFrame();
    JPanel p = new JPanel();
    NumberInputPanel number = new NumberInputPanel(box,content);
    Container contentPane = f.getContentPane();
    p.add(box);
    p.add(number);
    contentPane.add(p);
    f.pack();
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }*/

  public static void main(String[] args){
    MainInterface test = new MainInterface();
  }

}
