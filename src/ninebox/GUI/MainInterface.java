package ninebox.GUI;

import ninebox.DataStructure.Array99Compute;
import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Mother;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainInterface {
  // My components
//  Array99Compute computer;
  Array99Solve solving, filling;
  Array99Generate generator, generator2;

  NineBlockBoxFill solveFillBox;
  NineBlockBoxFill freeFillBox;
  NineBlockBoxDisplay displayBox;

  NumberInputPanel numberPanel, freeNumberPanel;

  QuestionGenerateButton generateProbButton;
  SudokuGenerateButton generateDispButton;
  HintButton hintButton;
  QuickSolveButton quickSolveButton;
  JButton solveButton;  // A "fake" quickSolveButton under freeFill mode, for the auto-compute algorithm won't survive
  ResetFilledButton resetButton1, resetButton2;

  // Display frame & panel
  JFrame GUIMain;
  JPanel solvePanel, displayPanel, freePanel;
  JTabbedPane tab;

  public MainInterface(){
    // init along with create relevance
//    computer = new Array99Compute();  // Used only in QuickSolveButton
    solving = new Array99Solve();
    filling = new Array99Solve();
    generator = new Array99Generate();  // Generate question
    generator2 = new Array99Generate();  // Generate new scheme to display
    solveFillBox = new NineBlockBoxFill();
    freeFillBox = new NineBlockBoxFill();
    displayBox = new NineBlockBoxDisplay();

    numberPanel = new NumberInputPanel(solveFillBox, solving);
    freeNumberPanel = new NumberInputPanel(freeFillBox, filling);
    generateProbButton = new QuestionGenerateButton(solveFillBox, generator, solving);
    generateDispButton = new SudokuGenerateButton(displayBox, generator2);
    hintButton = new HintButton(solving, generator, solveFillBox);
    quickSolveButton = new QuickSolveButton(solveFillBox, solving);
    solveButton = new JButton("Solve");
    resetButton1 = new ResetFilledButton(solving,solveFillBox);
    resetButton2 = new ResetFilledButton(filling,freeFillBox);

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
    // Solve mode auxiliary bar
    JPanel solveAuxPanel = new JPanel();
    BoxLayout horBoxLayout = new BoxLayout(solveAuxPanel,BoxLayout.X_AXIS);
    solveAuxPanel.setLayout(horBoxLayout);
    solveAuxPanel.add(generateProbButton);
    solveAuxPanel.add(quickSolveButton);
    solveAuxPanel.add(resetButton1);
    solveAuxPanel.add(hintButton);
    // TODO: add timer, difficulty setting etc.
    solvePanel.add(solveAuxPanel, BorderLayout.NORTH);
    tab.addTab("Solve Problem",solvePanel);


    // Display Mode
    displayPanel.add(generateDispButton);
    displayPanel.add(displayBox);
    tab.addTab("Example Display",displayPanel);

    // Free Fill Mode
    JPanel fillAuxPanel = new JPanel();
    BoxLayout horBoxLayout2 = new BoxLayout(fillAuxPanel,BoxLayout.X_AXIS);
    fillAuxPanel.setLayout(horBoxLayout2);
    fillAuxPanel.add(solveButton);
    fillAuxPanel.add(resetButton2);
    freePanel.add(freeFillBox, BorderLayout.CENTER);
    freePanel.add(freeNumberPanel, BorderLayout.SOUTH);
    freePanel.add(fillAuxPanel, BorderLayout.NORTH);
    tab.addTab("Free Fill",freePanel);

    GUIMain.add(tab);
    GUIMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GUIMain.setBounds(20,20, 800,800);
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

}
