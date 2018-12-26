package ninebox.GUI;

import ninebox.DataStructure.Array99;
import ninebox.DataStructure.Array99Compute;
import ninebox.DataStructure.Array99Generate;
import ninebox.DataStructure.Array99Mother;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class NineBlockBox extends JPanel{
  static Color activeCellColor = new Color(0.9f,0.8f,0.8f,0.8f);  // selected box
  static Color inactiveCellColor = new Color(0.95f,0.9f,0.9f,0.4f);  // unselected box
  static Color wrongCellColor = new Color(1.0f, 0.2f,0.2f,0.95f);  // box with wrong number
  static Color inertCellColor = new Color(0.6f,0.8f,0.9f,0.95f);  // unchangeable box
  JPanel[] panels = new JPanel[9];
  JButton[] cells = new JButton[81];
  int activeCellIndex;

  // Use in NineBlockBoxFill, but should be put here
  ArrayList<Boolean> writable;
  ArrayList<Boolean> validCell;


  NineBlockBox(){
    GridLayout generalGridLayout = new GridLayout(3,3,10,10);
    GridLayout subGridLayout = new GridLayout(3,3,1,1);
    this.setLayout(generalGridLayout);
    for (int i=0;i<9;i++){
      panels[i] = new JPanel();
      panels[i].setLayout(subGridLayout);
      this.add(panels[i]);
    }

    activeCellIndex = -1; // init

    for (int i=0;i<9;i++) {
      for (int j = 0; j < 9; j++) {
        int index = i * 9 + j;
        int panelIndex = (i / 3) * 3 + j / 3;

        // button appearance setup
        // text
        cells[index] = new JButton("");

        // add to panel
        panels[panelIndex].add(cells[index]);
      }
    }
  }

  public void setCellNumber(int index, int i){
    cells[index].setText(Integer.toString(i));
  }

  public void markCellNumber(int index, int i){
    String prevMark = cells[index].getText();
    cells[index].setText(prevMark+i);
  }

  public void resetCellNumber(int index){
    cells[index].setText("");
    cells[index].setBackground(inactiveCellColor);
  }

  public int getActiveCellIndex() {
    return activeCellIndex;
  }

  // No promise on order < solution.size()-1. Use carefully
  void boxContnentFill(Array99Compute computeContents, int order){
    Array99 solution = computeContents.solutions.get(order);
    for (int index=0;index<81;index++){
      int temp = solution.getContent(index);
      temp = Array99Mother.findNumber(temp);
      this.setCellNumber(index,temp+1);
    }
  }

  interface boxContentsUpdate{
    void boxContentsUpdate(Array99Generate generatedContents);
  }
}
