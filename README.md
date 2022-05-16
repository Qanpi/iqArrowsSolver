# iq-arrows-solver
A unique implementaion of Donald Knuth's algorithm and data structure to compute solutions at lightning speeds for the [IQ-arrows puzzle game](https://www.smartgamesandpuzzles.com/iq-arrows.html).

![image](https://user-images.githubusercontent.com/61239034/168665890-f4d5d832-4d84-40a0-a996-b3b0d806b298.png)

## Introduction 
### What & Why?
This project seeks to tackle the root cause of my frustration. Once, when walking in a book store, I noticed an IQ puzzle game on sale. It was labelled as extremely difficult and I felt up for the challenge. However, I must admit that after trying the harder levels of the game, I felt defeated. But I wasn't about to give up this easily, so I devised a plan to create an algorithm that would obliterate this game. Coincidentally, I was also brainstorming ideas for my personal MYP project and this seemed like a perfect opportunity. You can read my report on the project [here](https://suomalaisenyhteiskou117-my.sharepoint.com/:w:/g/personal/terin_aleksei_syk_fi/ESSVr5DqayJFs4K69r9oUrABoEaTQvtEbUlRh3WekEI-zw?e=GIUAEW). 

### Technical specifications
- written in Java from scratch
- implements Donald Knuth's [Algorithm X](https://arxiv.org/pdf/cs/0011047.pdf)
- for improved efficiency and time, relies on the [Dancing Links](https://arxiv.org/pdf/cs/0011047.pdf) data structure
- GUI based on Java Swing

## Documentation
### Game overview
As mentioned in the introduction, the ultimate purpose of this project is to compute solutions to a puzzle game. 

![image](https://user-images.githubusercontent.com/61239034/168668280-d991054a-a63e-41d3-9f61-298b92770f7d.png)

The game consists of a 6x3 board, 6 differently shaped pieces with arrows on them, and a booklet with challenges.

![image](https://user-images.githubusercontent.com/61239034/168668502-96b9fca8-fe93-4fb5-8e60-612425c9b416.png)

Each challenge in the booklet is presented in the form of the blank board with arrows drawn on it (left). The aim of the game is to position the puzzle pieces in such a manner that the arrows on them line up with those given by the challenge (right). Further information about the game can be found [here](https://www.smartgamesandpuzzles.com/iq-arrows.html).


This program allows the user to enter the starting position of the challenge through an intuitive user interface. Then, in a matter of milliseconds, it computes the appropriate answer that fulfills the starting criteria.

### Example
1. Download or clone the repository to your local machine. 
2. If necessary, configure the starting point to `Solver.java`
3. Run the code to see a GUI representing the physical board.
![image](https://user-images.githubusercontent.com/61239034/168669922-842990b8-d05b-4e3c-b5ca-0dbb0b9e9b49.png)
4. Enter a combination of arrows either from a challenge or randomly. Clicking again on an arrow will rotate its direction clockwise.
![image](https://user-images.githubusercontent.com/61239034/168670252-840a565a-82b6-4076-8668-25435479aef2.png)
6. Press solve to generate the solution. If not possible, an error will be displayed.
![image](https://user-images.githubusercontent.com/61239034/168670335-4098950c-9d36-4e97-b654-63aa50f8b10f.png)
![image](https://user-images.githubusercontent.com/61239034/168670385-ed79a025-8572-4ccb-84f2-ef6bc60b91cc.png)
 
## Reflection
### How has this project changed me?
This was a milestone project for me as it taught me to develop my own, independent solution. While algorithms for cracking puzzle games have existed and developed for decades, as far as I am aware, no one has applied one to this specific game. As such, I was forced to roam through scientific papers in order to fully grasp the inner-workings of an algorithm and apply it to this problem. It was no easy task, but it felt extremely rewarding to have personally developed a unique solution to a unique problem with almost no outside reference.

### What would I improve?
Honestly, I'm rather proud and happy with this project. That being said, I believe I could've structured and documented my code more clearly. In addition, I would also like to explore customizations of the GUI and perhaps incorporating some additional features to the program. Such as for instance being able to interpret a challenge through image recognition from the picture of a page of a booklet. 
