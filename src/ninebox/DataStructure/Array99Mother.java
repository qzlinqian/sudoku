package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Collections;

abstract public class Array99Mother extends Array99{


  Array99Mother(){
    this(new Array99());
  }

  abstract boolean checkRow(int row, int column);
  abstract boolean checkColumn(int row, int column);
  abstract boolean checkBox(int row, int column);

  Array99Mother(Array99Mother array99Mother){
    super(array99Mother);
    this.boxState = new ArrayList<>(array99Mother.boxState);
    this.columnState = new ArrayList<>(array99Mother.columnState);
    this.rowState = new ArrayList<>(array99Mother.rowState);
  }

  Array99Mother(Array99 array99){
    super(array99);
    rowState = new ArrayList<>(Collections.nCopies(9,init));
    columnState = new ArrayList<>(Collections.nCopies(9,init));
    boxState = new ArrayList<>(Collections.nCopies(9,init));
  }


  public boolean isFinished(){
    for (int temp:boxState)
      if (temp < full)
        return false;
    for (int temp:columnState)
      if (temp < full)
        return false;
    for (int temp:rowState)
      if (temp < full)
        return false;
    return true;
  }

  public static int findNumber(int numberInput){ // if return with i = 1~9, it is a single number, 2^(i-1) = num[i-1], representing i
    int lo=0,hi=9;
    int mi = (lo+hi)/2;
    while (lo<mi){
//      if (num[mi] == numberInput) // it's highly possible, so we can put it here
//        return mi+1;
      if (num[mi] > numberInput)
        hi = mi;
      else
        lo = mi;
      mi = (lo+hi)/2;
    }
    return lo; // it is not an element of num[]
  }

  public int possibleNumbers(int numberInput){ // TODO: Can use mod 2 to compute
    if (numberInput < 1) return 0;
    int sum = 1;
    int degree = (int) Math.floor(Math.log(numberInput)/Math.log(2));
    int number = (int) Math.pow(2,degree);
    while (number < numberInput){
      numberInput -= number;
      degree = (int) Math.floor(Math.log(numberInput)/Math.log(2));
      number = (int) Math.pow(2,degree);
      sum++;
    }
    return sum;
  }

  @Override
  public boolean setContent(int row, int column, int content){
    // set number & check valid can be combined
    //    int originNum = getContent(row, column);
    boxContents.set(row * 9 + column, content);
    boolean rowStat = checkRow(row, column);
    boolean colStat = checkColumn(row, column);
    boolean boxStat = checkBox(row, column);

    return (boxStat && colStat && rowStat);
  }

  public boolean setWithIndex(int row, int column, int contentIndex){
    return setContent(row, column, num[contentIndex]);
  }

  public boolean setWithIndex(int index, int contentIndex){
    return setWithIndex(index/9, index%9, contentIndex);
  }

  public void statesUpd(){
    // init the states
    for (int i=0;i<9;i++){
      int auxRow = 0, auxCol = 0;
      for (int j=0;j<9;j++) {
        auxRow |= getContent(i, j);
        auxCol |= getContent(j, i);
      }
      rowState.set(i,auxRow);
      columnState.set(i,auxCol);
    }
    /*for (int j=0;j<9;j++){
      int aux = 0;
      for (int i=0;i<9;i++)
        aux |= getContent(i,j);
      columnState.set(j,aux);
    }*/
    for (int i=0;i<3;i++){
      for (int j=0;j<3;j++){
        int aux = 0;
        for (int k=0;k<3;k++){
          for (int l=0;l<3;l++)
            aux |= getContent(3*i+k,3*j+l);
        }
        boxState.set(i*3+j,aux);
      }
    }
  }

}
