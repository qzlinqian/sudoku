## Java Project -- Sudoku
#### GUI
* NineBlockBox: create a panel with 9*9 buttons
* NineBlockBoxFill: extends NineBlockBox; listen to press button.
* NineBlockBox... : need to complete, show the solutions
* NumberInputPanel: input 1~9 number
* Timer, label, hint, mode selection bar ...

#### DataStructure

* Array99: use ArrayList[81] to realize 9*9 box
* Array99Mother: I think can be merged with Array99...
* Array99Solve: 9*9 box for the user to solve the sudoku
* Array99Compute: 9*9 box for the app compute all the solutions of the sudoku. (Can also give the difficulty of the question?)
* Array99Generate: generate valid sudoku questions
* (Array99Fill: 9*9 box for the user to build a question by self?)

#### Util

(Develop later, add a JFrame containing all the panels?)

#### TODO
- [x] test the DataStructure
- [ ] make the former pressed button release
- [ ] move solve() outside the Array99Compute
- [ ] merge Array99 and its kids