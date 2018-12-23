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


  public boolean isFinished_boxCheck(){
    for (int temp:boxState)
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
      if (num[mi] < numberInput)
        lo = mi;
      else
        hi = mi;
      mi = (lo+hi)/2;
    }
    return lo; // it is not an element of num[]
  }

  public int possibleNumbers(int numberInput){
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
}
