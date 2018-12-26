package ninebox.GUI;

import ninebox.DataStructure.Array99Solve;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class ResetFilledButton extends JButton {
  ResetFilledButton(Array99Solve boxContents, NineBlockBoxFill box){ // For solve and fill mode only
    super("Reset");
    this.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        for (int index=0;index<81;index++){
          if (box.writable.get(index)){
            box.resetCellNumber(index);
            boxContents.setContent(index, 0);
          }
        }
        box.validCell = new ArrayList<>(Collections.nCopies(81,true));
        boxContents.statesUpd();
      }
    });
  }
}
