User: 009864439 (Bariana, Saajan)
User: 009981959 (Jose, Jeff)
User: 009766731 (Choy, Alexander)

Creates an application that allows you to choose between a client mode, offline, and server mode.

Offline mode:
Allows you to create, delete, and move around a rectangle, ellipse, line, or some text. It also allows you to change their colors. If you click on the text object, you have the option to change what the text says and the font it is written in. Once the objects are on the canvas, you can save that canvas as an XML or PNG file into any directory you choose. You also have the option to open up previous XML files and portray those shapes into your canvas. You can resize the shapes by dragging the knobs located at their corners after you click them.

Host mode:
This allows you to do the same things as the offline mode, however, the application prompts you to enter a local port on your computer to serve at the servlet. An array of the objects and their positions is sent to this servlet, which will then send it to the clients.

Client mode:
This does not allow you to affect the canvas in any way. When you click this mode, you will be prompted to enter a local host port number to which you want to connect to. All the objects sent to the servlet will appear on the canvas. You can not move, delete, change the color, or resize any of the objects in the canvas, but you can select them. 

You can run this in command line or as a jar file.
