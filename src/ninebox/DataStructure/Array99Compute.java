package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Array99Compute extends Array99Mother{
  private ArrayList<Integer> possibleContents;
  private Stack<Integer> stepStack; // in case of multi-solution
  private ArrayList<Array99> solutions; // in case of multi-solution
  private int minCasesAt, minCases;

  Array99Compute(Array99 array99){
    super(array99);
    this.minCases = 9;
    this.minCasesAt = 81;

    this.possibleContents = new ArrayList<>(Collections.nCopies(81,0));
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
            aux |= getContent(i+k,j+l);
        }
        boxState.set(i*3+j,aux);
      }
    }
  }

  Array99Compute(Array99Compute array99Compute){
    super(array99Compute);
    this.solutions = new ArrayList<>();
  }

  Array99Compute(){
    this(new Array99());
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
    return true;
  }

  // flag = 0: no solution; 1: updated; 2: no update and no multi-cases, i.e., finish; 3: need to enumerate
  int traverse(){
    int flag = 2;
    minCases = 9;
    minCasesAt = 81;
    for (int i=0;i<9;i++){ // row
      for (int j=0;j<9;j++){ // column
        if (getContent(i,j) > 0) continue;
        int state = rowState.get(i) | columnState.get(j) | boxState.get((i/3)*3 + j/3);
        if (state == full) return 0; // cannot fill anything but the box is empty, no solution
        int cases = possibleNumbers(full | ~state);
        if (cases == 1){ // Then fill it!
          setContent(i,j,full | ~state);
          flag = 1;
        } else if (flag>1 && cases < minCases){ // lazy principle
          // when flag=1, no need to use the minimum case
          minCases = cases;
          minCasesAt = i*9+j;
          possibleContents.set(i*9+j, full | ~state);
          flag = 3;
        }
      }
    }
    return flag;
  }

  // TODO: move solve() to outside class?
  void solve(){
    while (true){
      int flag = traverse();
      if (flag == 0) return;
      if (flag == 1) continue;
      if (flag == 2) this.solutions.add(new Array99(this)); // solve finished, solution added
      // flag == 3, enumerate
      int possibleNumber = possibleContents.get(minCasesAt);
      do {
        int number19 = findNumber(possibleNumber);
        possibleNumber -= num[number19];
        Array99Compute oneCase = new Array99Compute(this);
        oneCase.setContent(minCasesAt,num[number19]);
        oneCase.solve();
        this.solutions.addAll(oneCase.solutions);
      } while ( possibleNumber > 0 );
    }
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
}
