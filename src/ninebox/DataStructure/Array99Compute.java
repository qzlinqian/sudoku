package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Array99Compute extends Array99Mother{
  private ArrayList<Integer> possibleContents;
//  private Stack<Integer> stepStack; // in case of multi-solution
  private ArrayList<Array99> solutions; // in case of multi-solution
  private int minCasesAt, minCases;
  int i,j;

  Array99Compute(Array99 array99, boolean init){
    super(array99);
    this.minCases = 9;
    this.minCasesAt = 81;

    this.possibleContents = new ArrayList<>(Collections.nCopies(81,0));
    this.solutions = new ArrayList<>();
    // init the states
    for (int i=0;i<9;i++){
      int aux = 0;
      for (int j=0;j<9;j++)
        aux |= getContent(i,j);
      rowState.set(i,aux);
    }
    for (int j=0;j<9;j++){
      int aux = 0;
      for (int i=0;i<9;i++)
        aux |= getContent(i,j);
      columnState.set(j,aux);
    }
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
    for (int i=0;i<81;i++)
      if (getContent(i) == 0)
        markUp(i/9,i%9);
  }

  Array99Compute(Array99Compute array99Compute){ // No need to update State arrays
    super(array99Compute);
    this.solutions = new ArrayList<>();
    this.possibleContents = new ArrayList<>(array99Compute.possibleContents);
  }

  Array99Compute(){
    this(new Array99(),true);
  }

  // markUp should call after all the state updated !!!!!!
  private void markUp(int row, int column){
    int state = rowState.get(row) | columnState.get(column) | boxState.get((row/3)*3 + column/3);
    possibleContents.set(row*9+column, full-state);
  }

  @Override
  boolean checkRow(int row, int column) {
    rowState.set(row, rowState.get(row)|getContent(row, column));
    return (rowState.get(row) == full);
  }

  @Override
  boolean checkColumn(int row, int column) {
    columnState.set(column, columnState.get(column)|getContent(row, column));
    return (columnState.get(column) == full);
  }

  @Override
  boolean checkBox(int row, int column) {
    int index = (row/3)*3 + column/3;
    boxState.set(index, boxState.get(index)|getContent(row, column));
    return (boxState.get(index) == full);
  }

  @Override
  public boolean setContent(int row, int column, int content){
    boxContents.set(row*9 + column, content);
    checkRow(row, column);
    checkColumn(row, column);
    checkBox(row, column);
    for (int j=0;j<9;j++)
      if (getContent(row,j) == 0) // need to fill
        markUp(row,j);
    for (int i=0;i<9;i++)
      if (getContent(i,column) == 0)
        markUp(i,column);
    for (int i=(row/3)*3;i<(row/3)*3+3;i++)
      for (int j=(column/3)*3;j<(column/3)*3+3;j++)
        if (getContent(i,j) == 0)
          markUp(i,j);
    return true;
  }

  // flag = 0: no solution; 1: updated; 2: no update and no multi-cases, i.e., finish; 3: need to enumerate
  // Too time & space consuming in the difficult cases!!!
  int traverse(){
    int flag = 2;
    minCases = 9;
    minCasesAt = 81;
    for (int i=0;i<9;i++){ // row
      for (int j=0;j<9;j++){ // column
        if (getContent(i,j) > 0) continue;
        int state = possibleContents.get(i*9+j);
        if (state == 0) {
          this.i = i;
          this.j = j;
//          System.out.println("2");
          return 0; // cannot fill anything but the box is empty, no solution
        }
        int cases = possibleNumbers(state);
        if (cases == 1){ // Then fill it!
          setContent(i,j,state);
          flag = 1;
        } else {
          int excluded = exclude(i,j);
          if (excluded == 0) return 0; // no solution
          if (excluded == 2){ // filled a block
            flag = 1;
            continue;
          }
          if (flag > 1 && cases<minCases){ // lazy principle
            minCases = cases;
            minCasesAt = i*9+j;
            flag = 3;
          }
        }
      }
    }
    return flag;
  }

  // 0: no solution; 1: cannot exclude; 2: exclude successfully
  int exclude(int row, int column){
    int aux = possibleContents.get(row*9 + column);
    if (row % 3 != 0) aux &= rowState.get((row/3)*3);
    if (row % 3 != 1) aux &= rowState.get((row/3)*3 + 1);
    if (row % 3 != 2) aux &= rowState.get((row/3)*3 + 2);
    if (column % 3 != 0) aux &= columnState.get((column/3)*3);
    if (column % 3 != 1) aux &= columnState.get((column/3)*3 + 1);
    if (column % 3 != 2) aux &= columnState.get((column/3)*3 + 2);
    if (aux == 0) return 1;
    if (possibleNumbers(aux)>1) {
//      System.out.println("1");
      return 0;
    }
    setContent(row,column,aux);
    return 2;
  }

  // TODO: move solve() to outside class?
  void solve(){
    while (true){
      int flag = traverse();
      if (flag == 0) return;
      if (flag == 1) continue;
      if (flag == 2){
        this.solutions.add(new Array99Compute(this)); // Finished, add solution
        return;
      }
      // flag == 3, enumerate
      int possibleNumber = possibleContents.get(minCasesAt);
      do {
        int number19 = findNumber(possibleNumber);
        possibleNumber -= num[number19];
        Array99Compute oneCase = new Array99Compute(this);
        oneCase.setContent(minCasesAt,num[number19]);
        oneCase.solve();
        this.solutions.addAll(oneCase.solutions);
        System.out.println(oneCase.solutions.size());
      } while ( possibleNumber > 0 );
      return;
    }
  }

  @Override
  public String toString() {
    StringBuilder print = new StringBuilder();
    for (Array99 temp:this.solutions){
      print.append(temp);
    }
    return print.toString();
  }

  // define a private class to record the solution steps
  private class Steps{
    int index;
    int content;

    public Steps(int index, int content) {
      this.index = index;
      this.content = content;
    }
  }

  public static void main(String[] args){
    /*Integer[] temp =
        {0,0,6,0,0,1,3,4,0,
         9,0,4,6,3,0,0,0,0,
         1,0,0,0,0,7,0,5,2,
         8,0,1,0,0,0,0,9,0,
         0,6,0,0,5,0,0,1,0,
         0,9,0,0,0,0,7,0,4,
         5,2,0,8,0,0,0,0,1,
         0,0,0,0,7,5,9,0,6,
         0,8,9,3,0,0,5,0,0};*/
    Integer[] temp =
        {5,8,0,0,0,9,0,0,0,
         6,0,0,5,0,8,9,0,4,
         2,0,0,0,7,0,0,0,0,
         8,0,0,9,0,0,0,6,7,
         0,1,0,0,8,0,0,5,2,
         3,2,0,0,0,7,0,0,8,
         0,0,0,0,2,0,0,0,0,
         7,0,2,8,0,5,0,0,3,
         0,0,0,3,0,0,2,7,5};
    /*Integer[] temp =
        {5,3,0,0,7,0,0,0,0,
         6,0,0,1,9,5,0,0,0,
         0,9,8,0,0,0,0,6,0,

         8,0,0,0,6,0,0,0,3,
         4,0,0,8,0,3,0,0,1,
         7,0,0,0,2,0,0,0,6,

         0,6,0,0,0,0,2,8,0,
         0,0,0,4,1,9,0,0,5,
         0,0,0,0,8,0,0,7,9};*/
    ArrayList<Integer> boxCon = new ArrayList<>();
    Collections.addAll(boxCon,temp);
    Array99 t = new Array99(boxCon);
    Array99Compute test = new Array99Compute(t, true);
    test.solve();
    System.out.println(test);
  }
}
