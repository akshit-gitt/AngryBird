Static GUI

Following screens/features have been implemented with the help of GDX libraries -
- buttons on screens with input listeners
- Introduction screen
- Level Select screen
- 3 levels
- 3 types of birds,pigs and obstacles
- Pause menu, pasue button and back button in each level
- Lose and Win screen using dummy buttons

Project is launched using the Lwjgl3Launcher located at angrybird\lwjgl3\src\main\java\com\angrybird\lwjgl3

All the assets used for the game are taken from existing online sources and belong to their respective owners/creators.
some useful websites- https://pngtree.com/ , https://www.cleanpng.com/ , https://toppng.com/

Link for UML class diagram- https://lucid.app/lucidchart/282fcf31-6552-4fa8-a15a-bbbc9a7f314c/edit?viewport_loc=-4358%2C-197%2C5378%2C2397%2C0_0&invitationId=inv_7529f8d8-5b22-4390-b634-67dacaec2ede


Angry Birds: Functioning Game
Angry Birds game is a libgdx project made using box2D physics engine which follows the gameplay similar to the original Angry Birds game. It has the following features-
1)	3 Levels, each with unique birds, pigs and obstacles.
2)	There are 3 types of birds, pigs and obstacles in the game.
3)	Different types of pigs have different healths, Pigs can take damage in various ways, direct contact with bird, fall damage, collision with obstacles. Pigs disappear from the level once their health becomes zero. Similar logic for obstacles.
4)	Bird launch: The bird on the slingshot can be launched, its speed and trajectory depends on the way the its dragged. Birds disappear from the level after 15 seconds of their launch, the next bird takes their place on the slingshot. Each bird have different speed.
5)	The level is won when there are no pigs left and the level is lost when all birds have been used, but some pigs still remain. User is greeted by a win/lose screen depending on the outcome.
6)	Implemented score and high score for each level .
7)	Bonus: Implemented special abilites for birds which can be used after they have been launched using spacebar. They include:
i)	Size increase for red bird.
ii)	Yellow birds trajectory becomes horizontal
iii)	Blue bird falls down almost immediately with high speed

8)	Serialisation for each level. There is an option to save the game and exit the level in the pause menu. The saved games can be loaded in the load game screen, which has 3 slots, one slot for each level.
GitHub repository- akshit-gitt/AngryBird

Demo video link - https://drive.google.com/file/d/1G7f4tOtJ5TFM5n9r3n5ogbDd2qvW0Ug9/view?usp=drive_link


Project can be launched via the Lwjgl3Launcher.
Updated UML classes and UML use case diagrams have been added with the project as well.
Sources- All the assets used such as level backgrounds, birds, pigs, obstacles and music/sound effects belong to their respective owners. Box2D documentation and some YouTube videos were really helpful in applying physics and game logic in the code.

