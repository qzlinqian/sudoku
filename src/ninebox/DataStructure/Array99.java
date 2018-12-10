package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Collections;

abstract public class Array99{
  protected ArrayList<Integer> boxContents;
  private static int init = 0;
  private static int full = 0b111111111;
  private static int num[] = {1,2,4,8,16,32,64,128,256};
  ArrayList<Integer> rowState;
  ArrayList<Integer> columnState;
  ArrayList<Integer> boxState;

  Array99(){
    boxContents = new ArrayList<>(Collections.nCopies(81,init));
    rowState = new ArrayList<>(Collections.nCopies(9,init));
    columnState = new ArrayList<>(Collections.nCopies(9,init));
    boxState = new ArrayList<>(Collections.nCopies(9,init));
  }

  abstract boolean checkRow(int row, int column);
  abstract boolean checkColumn(int row, int column);
  abstract boolean checkBox(int row, int column);

  Array99(Array99 array99){
    this.boxContents = new ArrayList<>(array99.boxContents);
    this.boxState = new ArrayList<>(array99.boxState);
    this.columnState = new ArrayList<>(array99.columnState);
    this.rowState = new ArrayList<>(array99.rowState);
  }

  int getContent(int row, int column){
    return boxContents.get(row * 9 + column);
  }

  void setContent(int row, int column, int content){
    setContent(row * 9 + column, content);
  }

  public void setContent(int index, int content){
    this.boxContents.set(index, num[content]);
  }

  void resetContent(int row, int column){
    resetContent(row * 9 + column);
  }

  void resetContent(int index){
    this.boxContents.set(index, init);
  }

  public boolean isFinished_boxCheck(){
    for (int temp:boxState)
      if (temp < full)
        return false;
    return true;
  }

  public boolean stateCheck(int row, int column){
    boolean boxStat = checkBox(row, column);
    boolean colStat = checkColumn(row, column);
    boolean rowStat = checkRow(row, column);
    return (boxStat && colStat && rowStat);
  }

  public boolean stateCheck(int index){
    return stateCheck(index/9, index%9);
  }
}
