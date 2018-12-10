package ninebox.GUI;

import ninebox.DataStructure.Array99;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberInputPanel {
//  int newNumber;
  public JPanel panel;
  JButton[] numbers = new JButton[9];

  public NumberInputPanel(NineBlockBoxFill box, Array99 boxContents){
    panel = new JPanel();

    GridLayout gridLayout = new GridLayout(1,9);
    panel.setLayout(gridLayout);
    for (int i=0;i<9;i++){
      numbers[i] = new JButton(Integer.toString(i+1));
      panel.add(numbers[i]);

      int FinalI = i+1;
      numbers[i].addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
          int index = box.getActiveCellIndex();
          boxContents.setContent(index, FinalI);
          boolean valid = boxContents.stateCheck(index);

          box.setCellNumber(index, FinalI);
          if (!valid)
            box.cellIsWrong(index);
          else
            box.cellIsCorrect(index);
        }
      });
    }
  }
}
