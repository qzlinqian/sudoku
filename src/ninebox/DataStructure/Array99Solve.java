package ninebox.DataStructure;

public class Array99Solve extends Array99Mother {

  public Array99Solve(Array99 array99){
    super(array99);
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
    int aux = 0, rowStart = (row/3)*3, colStart = (column/3)*3;
    for (int i=rowStart;i<rowStart+3;i++){
      for (int j=colStart;j<colStart+3;j++){
        if (i==row && j==column) continue;
        aux |= getContent(i,j);
      }
    }
    boxState.set(index,aux|getContent(row,column));
    return ((getContent(row, column) & aux) == 0);
  }
}
