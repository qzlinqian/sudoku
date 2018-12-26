package ninebox.GUI;

import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Mother;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class NineBlockBoxFill extends NineBlockBox implements NineBlockBox.boxContentsUpdate{

  NineBlockBoxFill(){
    super();
//    boxContent = new Array99Solve();
    writable = new ArrayList<>(Collections.nCopies(81,true));
    validCell = new ArrayList<>(Collections.nCopies(81,true));
    for (int i=0;i<9;i++){
      for (int j=0;j<9;j++){
        int index = i * 9 + j;
        int panelIndex = (i/3) * 3 + j/3;

        // button appearance setup
        // text
//        cells[index] = new JButton("");
        // color
        cells[index].setOpaque(true);
        if (writable.get(index))
          cells[index].setBackground(inactiveCellColor);
        else
          cells[index].setBackground(inertCellColor);
        cells[index].setBorderPainted(false);

        // The non-writable cells also has listener in case of further change of writable[]
        cells[index].addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (!writable.get(index)) return; // it is given
            if (activeCellIndex > -1) // there is already an active cell
              if (validCell.get(activeCellIndex)) // is not wrongCell
                cells[activeCellIndex].setBackground(inactiveCellColor);
              else
                cells[activeCellIndex].setBackground(wrongCellColor);
            cells[index].setBackground(activeCellColor);
            activeCellIndex = index;
            // TODO: make button release
          }
        });
      }
    }
  }


  @Override
  public void boxContentsUpdate(Array99Generate generatedContents){
    for (int index=0;index<81;index++){
      boolean canWrite = !(generatedContents.getVisible(index));
      writable.set(index,canWrite);
      if (canWrite) {
        cells[index].setBackground(inactiveCellColor);
        resetCellNumber(index);
      }
      else {
        cells[index].setBackground(inertCellColor);
        int number = generatedContents.getContent(index);
        number = Array99Mother.findNumber(number);
        setCellNumber(index,number+1);
      }
    }
  }


  public void cellIsWrong(int index){
    cells[index].setBackground(wrongCellColor);
    validCell.set(index, false);
  }

  public void cellIsCorrect(int index){
    cells[index].setBackground(inactiveCellColor);
    validCell.set(index, true);
  }

  public void cellIsGiven(int index){
    cells[index].setBackground(inertCellColor);
    validCell.set(index, true);
  }

  public void setWritable(int index, boolean writable){
    this.writable.set(index, writable);
  }

  public String toPlainText(){
    StringBuilder print = new StringBuilder();
    for (int i=0;i<81;i++){
      if (writable.get(i)) // Filled by user
        if (validCell.get(i))
          print.append(1).append(" "); // Inactive cell
        else
          print.append(2).append(" "); // Wrong cell
      else
        print.append(0).append(" "); // Inert cell
    }
    return print.toString();
  }
}
