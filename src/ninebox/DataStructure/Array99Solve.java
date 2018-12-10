package ninebox.DataStructure;

public class Array99Solve extends Array99 {
  public Array99Solve(){
    super();
  }

  @Override
  boolean checkRow(int row, int column){
    if ((getContent(row, column) & rowState.get(row)) != 0)
      return false;
    rowState.set(row, rowState.get(row) | getContent(row, column));
    return true;
  }

  @Override
  boolean checkColumn(int row, int column) {
    if ((getContent(row, column) & columnState.get(column)) != 0)
      return false;
    columnState.set(column, columnState.get(column) | getContent(row, column));
    return true;
  }

  @Override
  boolean checkBox(int row, int column) {
    int index = (row/3) * 3 +column/3;
    if ((getContent(row, column) & boxState.get(index)) != 0)
      return false;
    boxState.set(index, boxState.get(index) | getContent(row, column));
    return true;
  }
}
