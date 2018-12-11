/*
package ninebox;

public class Array99Fill extends Array99Mother {

  Array99Fill(){
    super();
  }

  @Override
  boolean checkRow(int row){
    int aux = 0;
    for (int i=0;i<9;i++){
      if ((aux & getContent(row,i)) != 0) return false;
      aux |= getContent(row,i);
    }
    return true;
  }

  @Override
  boolean checkColumn(int column){
    int aux = 0;
    for (int i=0;i<9;i++){
      if ((aux & getContent(i,column)) != 0) return false;
      aux |= getContent(i,column);
    }
    return true;
  }

  @Override
  boolean checkBox(int row, int column){
    int aux = 0;
    int rowStart = (row/3)*3;
    int columnStart = (column/3)*3;
    for (int i=rowStart;i<rowStart+3;i++){
      for (int j=columnStart;j<columnStart+3;j++){
        if ((aux & getContent(i,j)) != 0) return false;
        aux |= getContent(i,j);
      }
    }
    return true;
  }

  boolean stateUpdate(int row, int column){
    rowState.set(row,checkRow(row));
    columnState.set(column,checkColumn(column));
    int aux = (row/3)*3 + column/3;
    boxState.set(aux,checkBox(row, column));
    return (rowState.get(row) && columnState.get(column) && boxState.get(aux));
  }

  boolean stateUpdate(){
    boolean valid = true;
    for (int i=0;i<9;i++){
      rowState.set(i,checkRow(i));
      columnState.set(i,checkColumn(i));
      boxState.set(i,checkBox(i/3,i%3));
      if (!rowState.get(i) || !columnState.get(i) || !boxState.get(i)) valid = false;
    }
    return valid;
  }
}
*/
