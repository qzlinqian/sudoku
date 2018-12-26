## Sudoku

It's the final course project of JAVA Programming, THU 2018 Fall. I was a aficionado of Sudoku when I was little, so I implemented a Sudoku demo, which can be used to learning, solving and generating sudoku.

### Demo

![Load History](fig/history.png)![Solve Problem](fig/solve.png)
![Example Display](fig/display.png)![Free Fill](fig/free.png)

### Implement Details

#### GUI

* *NineBlockBox*: create a panel with 9\*9 buttons
* *NineBlockBoxFill*: extends NineBlockBox; listen to press button. Implemented for *Solve Mode* and *Free (Fill) Mode*.
* *NineBlockBoxDisplay*: extends NineBlockBox; display generated schemes. Implemented for *Display Mode*.
* *NumberInputPanel*: input 1~9 number to the selected button in NineBlockBoxFill in *Solve Mode* and *Free (Fill) Mode*; can also choose to *Mark UP*.
* *HistoryLoaderPanel*: load and display the files saved previously in *Solve Mode*, so that we can continue with the unsolved problems.
* The implementation of some button is a little complex, so I wrote some class out of the MainGUI, e.g., *QuestionGeneratorButton* to generate new questions in *Solve Mode*, *QuickSolveButton* to solve the remaining blocks in *Solve Mode* and *Free (Fill) Mode*, *ResetFilledButton* to reset the filled blocks in *Solve Mode* and *Free (Fill) Mode*, *SudokuGenerateButton* to generate a new completed scheme in *Display Mode*, *SaveButton* to save the current box state in *Solve Mode*.
* *MainInterface*: the main GUI. Organize all the mode (*Load History*, *Solve Problem*, *Example Display*, *Free Fill*) with a TabbedPanel. There's also a 

#### DataStructure

* *Array99*: use *ArrayList[81]* to realize 9\*9 box, can also be declared with genericity to support further usage.

* *Array99Mother*: the base class of *Array99Solve*, *Array99Compute*, *Array99Generate*.

* *Array99Solve*: 9\*9 box for the user to solve the sudoku question.

* *Array99Compute*: 9\*9 box for the app compute all the solutions of the sudoku. Because of the limited time, I only employed a **Backtracking** algorithm, which can be very time and space consuming sometimes. So the solve button in *Free Fill* Mode cannot really work temporarily, or the app would run into a long recursion. To accelerate the checking of whether a number has been filled in a row/column/box, the number **i** is represented by **2^(i-1)**, so that the checking process can be completed with bit operation.

* *Array99Generate*: generate valid sudoku questions. As is shown in the above picture, I used an algorithm metioned [here](https://blog.csdn.net/qq_26822029/article/details/81129701) to generate a new scheme.

   ![](fig/generate.png)

  That's very efficient, although it can be solved very quickly if know the scheme. In the *Solve Problem* Mode, the generate process also need to decide which blocks should be set to null for the user to fill. The difficulty SilerBar in the *Solve Problem* Panel decides a difficulty constant **F**. I used to chosen **floor(F\*81)** blocks randomly to achieve that, but that's too time consuming, for the already chosen blocks can be chosen at a very high possiblity. So I select every block with a possibility of **F** instead. Although the total selected blocks may not be that accurate, it's considerably efficient.

#### Auxiliary

* *StopWatch*: a timer record class extends from Thread. It starts a sub-thread to count the consumed time from the start. It can also be controled to pause, reset or continue.
* *HistoryArrayLoader*: it's actually a file reader loading data from the chosen file in the *Load History* Panel. The sudoku origin data, the used time, the last modified time and the former color (presents its state) of the blocks are all recorded.