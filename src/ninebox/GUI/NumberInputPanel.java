package ninebox.GUI;

import ninebox.Auxiliary.StopWatch;
import ninebox.DataStructure.Array99Mother;
import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberInputPanel extends JPanel{
//  int newNumber;
//  public JPanel panel;
  private JButton[] numbers = new JButton[9];
  private JButton markUpButton, clearButton;
  private boolean markUp;

  public NumberInputPanel(NineBlockBoxFill box, Array99Mother boxContents, StopWatch timer, JPanel panel){
//    panel = new JPanel();
    markUp = false;

//    inform = new JDialog();

    BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
    this.setLayout(layout);
    for (int i=0;i<9;i++){
      numbers[i] = new JButton(Integer.toString(i+1));
      this.add(numbers[i]);

      int FinalI = i;
      numbers[i].addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
          int index = box.getActiveCellIndex();

          if (markUp){
            box.markCellNumber(index, FinalI+1);
            boxContents.setContent(index, 0);
            box.validCell.set(index, true);
          } else {
//          int originNum = boxContents.getContent(index);
            boolean valid = boxContents.setWithIndex(index, FinalI);
//           boxContents.stateCheck(index, originNum);

            box.setCellNumber(index, FinalI+1);
            if (!valid)
              box.cellIsWrong(index);
            else
              box.cellIsCorrect(index);
          }

          if (boxContents.isFinished()){
            timer.stopCommand();
            JOptionPane.showMessageDialog(panel, "You've completed this in "+timer, "Congrats!", JOptionPane.PLAIN_MESSAGE);
            // That won't show after "QuickSolve" action, for that just update GUI and Array99Compute without change Array99Fill
          }
        }
      });
    }

    clearButton = new JButton("Clear");
    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = box.getActiveCellIndex();
        box.resetCellNumber(index);
        boxContents.setContent(index, 0);
        box.validCell.set(index, true);
      }
    });
    this.add(clearButton);

    // Mark Up selection
    markUpButton = new JButton("Mark");
    markUpButton.setBorderPainted(false);
    markUpButton.setOpaque(true);
    markUpButton.setBackground(NineBlockBox.inactiveCellColor);
    markUpButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        markUp = !markUp;
        if (markUp) markUpButton.setBackground(NineBlockBox.activeCellColor);
        else markUpButton.setBackground(NineBlockBox.inactiveCellColor);
      }
    });
    this.add(markUpButton);
  }
}
