package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Collections;

public class Array99Compute extends Array99Mother{
  private ArrayList<Integer> possibleContents;
//  private Stack<Integer> stepStack; // in case of multi-solution
  public ArrayList<Array99> solutions; // in case of multi-solution
  private int minCasesAt, minCases;
  int i,j;

  public Array99Compute(Array99 array99){
    super(array99);
    this.minCases = 9;
    this.minCasesAt = 81;

    this.possibleContents = new ArrayList<>(Collections.nCopies(81,0));
    this.solutions = new ArrayList<>();

    this.statesUpd();
  }

  Array99Compute(Array99Compute array99Compute){ // No need to update State arrays
    super(array99Compute);
    this.solutions = new ArrayList<>();
    this.possibleContents = new ArrayList<>(Collections.nCopies(81,0));
  }

  public Array99Compute(){
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
  // Too time & space consuming in the difficult cases!!!
  int traverse(){
    int flag = 2;
    minCases = 9;
    minCasesAt = 81;
    for (int i=0;i<9;i++){ // row
      for (int j=0;j<9;j++){ // column
        if (getContent(i,j) > 0) continue;
        int state = rowState.get(i) | columnState.get(j) | boxState.get((i/3)*3 + j/3);
        if (state == full) {
          this.i = i;
          this.j = j;
          return 0; // cannot fill anything but the box is empty, no solution
        }
        int cases = possibleNumbers(full-state);
        if (cases == 1){ // Then fill it!
          setContent(i,j,full-state);
          flag = 1;
        } else if (flag>1 && cases < minCases){ // lazy principle
          // when flag=1, no need to use the minimum case
          minCases = cases;
          minCasesAt = i*9+j;
          possibleContents.set(i*9+j, full-state);
          flag = 3;
        }
      }
    }
    return flag;
  }

  // TODO: move solve() to outside class?
  public void solve(){
    while (true){
      int flag = traverse();
      if (flag == 0) return;
      if (flag == 1) continue;
      if (flag == 2){
        this.solutions.add(new Array99(this)); // solve finished, solution added
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
//        System.out.println(oneCase.solutions.size());
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
    ArrayList<Integer> boxCon = new ArrayList<>();
    Collections.addAll(boxCon,temp);
    Array99 t = new Array99(boxCon);
    Array99Compute test = new Array99Compute(t);
    test.solve();
    System.out.println(test.solutions.get(0));
  }
}
