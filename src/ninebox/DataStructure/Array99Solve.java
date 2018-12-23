package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Array99Solve extends Array99Mother {
//  private ArrayList<Integer> possibleContents;
//  private ArrayList<Stack<Steps>> stepStack; // in case of multi-solution
//  private ArrayList<Array99> solutions; // in case of multi-solution

  public Array99Solve(Array99 array99){
    super(array99);
//    possibleContents = new ArrayList<>(Collections.nCopies(81,init));
//    stepStack = new ArrayList<>(new Stack<>());
//    solutions = new ArrayList<>();
  }

  public Array99Solve(){
    this(new Array99());
  }

  public void contentsUpd(Array99Generate newContents){
    for (int index=0;index<81;index++){
      if (newContents.getVisible(index))
        this.boxContents.set(index,newContents.getContent(index));
      else this.boxContents.set(index,0);
    }
  }

  @Override
  boolean checkRow(int row, int column){
//    if ((getContent(row, column) & rowState.get(row)) != 0)
//      return false;
//    rowState.set(row, (rowState.get(row) & ~originNum) | getContent(row, column));
    // 考虑到有时候原来的数是错的，就是跟某处有重复，然后再位运算的话，就把跟它重复的那个数也去掉了，
    // 这样会出错，反正才9个数也不长，干脆全部遍历，每次update了
    int aux = init;
    for (int i=0;i<9;i++){
      if (i == column) continue;
      aux |= getContent(row,i);
    }
    rowState.set(row,aux|getContent(row,column));
    return ((getContent(row, column) & aux) == 0);
  }

  @Override
  boolean checkColumn(int row, int column) {
//    columnState.set(column, (columnState.get(column) & ~originNum) | getContent(row, column));
    int aux = 0;
    for (int i=0;i<9;i++){
      if (i == row) continue;
      aux |= getContent(i,column);
    }
    columnState.set(column,aux|getContent(row, column));
    return ((getContent(row, column) & aux) == 0);
  }

  @Override
  boolean checkBox(int row, int column) {
    int index = (row/3) * 3 +column/3;
//    if ((getContent(row, column) & boxState.get(index)) != 0)
//      return false;
//    boxState.set(index, (boxState.get(index) & ~originNum) | getContent(row, column));
    int aux = 0, rowStart = row/3, colStart = column/3;
    for (int i=rowStart;i<rowStart+3;i++){
      for (int j=colStart;j<colStart+3;j++){
        if (i==row && j==column) continue;
        aux |= getContent(i,j);
      }
    }
    boxState.set(index,aux|getContent(row,column));
    return ((getContent(row, column) & aux) == 0);
  }

  // TODO
//  public int getHint(){ // Remember every time you change the content of the NineBox,
//    // the stateCheck will call findPossibleContents, so the possibleContents is up-to-date
//    return canBeFilled.pop();
//  }

//  public boolean stateCheck(int row, int column){
//    // state have already been updated
//  }

//  @Override
//  public void postProcess(int row, int column){
//    int posCon = (rowState.get(row) & columnState.get(column) & boxState.get((row/3) * 3 +column/3));
//    possibleContents.set(row * 9 + column,posCon);
//    if (possibleNumbers(full - posCon) == 1)
//      stepStack.get(0).push(new Steps(row * 9 + column, findNumber(full-posCon)));
//  }

  /*public void findPossibleContents(int index){
    findPossibleContents(index/9,index%9);
  }*/

//  public boolean stateCheck(int index){
//    return stateCheck(index/9, index%9);
//  }

//  public void solve(){
//    while ( !stepStack.get(0).empty() ){
//      Stack<Steps> step = stepStack.get(0);
//      Array99 thisArray = new Array99(this);  // init with the array in this class
//      while ( !step.empty() ){
//        Steps tempStep = step.pop();
//        thisArray.setContent(tempStep.index, tempStep.content);
//      }
//      stepStack.remove(0); // then stepStack[0] is another case (or empty)
//    }
//  }
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


//  // define a private class to record the solution steps
//  private class Steps{
//    int index;
//    int content;
//
//    public Steps(int index, int content) {
//      this.index = index;
//      this.content = content;
//    }
//  }

}
