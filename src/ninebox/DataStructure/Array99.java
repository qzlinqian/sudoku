package ninebox.DataStructure;

import java.util.ArrayList;
import java.util.Collections;

public class Array99 {
  ArrayList<Integer> boxContents;
  static int init = 0;
  static int full = 0b111111111;
  static int num[] = {1,2,4,8,16,32,64,128,256};
  ArrayList<Integer> rowState;
  ArrayList<Integer> columnState;
  ArrayList<Integer> boxState;

  Array99(){
    boxContents = new ArrayList<>(Collections.nCopies(81, init));
  }

  Array99(Array99 array99){
    this.boxContents = new ArrayList<>(array99.boxContents);
  }

  Array99(ArrayList<Integer> boxContents){
    this.boxContents = new ArrayList<>();
    for (int i:boxContents){
      if (i==0) this.boxContents.add(0);
      else this.boxContents.add(num[i-1]);
    }
  }


  public int getContent(int row, int column){
    return boxContents.get(row * 9 + column);
  }

  public int getContent(int index){
    return boxContents.get(index);
  }

  public int getContent(int box_row, int box_column, int row_in_box, int column_in_box){
    return getContent(box_row * 3 + row_in_box, box_column * 3 + column_in_box);
  }

  public void resetContent(int row, int column){
    resetContent(row * 9 + column);
  }

  public void resetContent(int index){
    this.boxContents.set(index, init);
  }

  public boolean setContent(int row, int column, int content){
    boxContents.set(row * 9 + column, content);
    return true;
  }

  public boolean setContent(int box_row, int box_column, int row_in_box, int column_in_box, int content){
    setContent(box_row * 3 + row_in_box, box_column * 3 + column_in_box, content);
    return true;
  }

  public boolean setContent(int index, int content){
    return setContent(index/9, index%9, content);
  }

  @Override
  public String toString() {
    StringBuilder print = new StringBuilder();
    for (int i=0;i<9;i++){
      for (int j=0;j<9;j++){
        print.append(getContent(i,j)).append(" ");
      }
      print.append("\n");
    }
    return print.toString();
  }
}
