# super-duper-system-jarfilecreation

This will help create a .jar file for your project provided that you follow the directions PRECISELY

# Reminders:
- ### **Since you will be forking the repository, it will be PUBLIC**
- Do not put your name or any identifying information in your code at all
- Do not put any api keys, passwords or anything of similar type in your code at all

# Setup your code:
- Ensure you have a Main.java file
- Ensure you have a manifest.txt file in the root of your directory that has the content "Main-Class: Main\n" (\n means a new line, not literally '\n' written)
- Place all your dependencies (.csv, .png, .jpg, etc.) in named folders **within** your src folder
- To refer to your dependencies, you should be currently using: "new ImageIcon("src\\images\\image1.png")" (The images and image1.png are part of an example)
- Change this to: "new ImageIcon(Main.class.getResource("/src/images/image1.png"))"
- Your code **will not work** when being ran/debugged with this change but this is necessary for the .jar file creation

# Directions:
- Create a fork of this repository by clicking the 'Fork' button in the upper right-hand corner
- Delete the PhotoViewerAssignment directory
- Upload your project, use the Github website's uploader, command line (instructions below) or visual studio code
- Double check that your structure is similar to the example structure and you've followed the directions in setting up your code
- 

# New Example Structure:
### TO VIEW THE EXAMPLE STRUCTURE PROPERLY, CLICK EDIT AND VIEW THIS IN THE EDITOR
super-duper-system-jarfilecreation-main/
├── .github/
│   └── workflows/
│       └── main.yml
├── 10-16-2024-PhotoViewerAssignment/
│   ├── .gitignore
│   ├── .idea/
│   │   ├── .gitignore
│   │   ├── misc.xml
│   │   ├── modules.xml
│   │   └─] workspace.xml (ignored)
│   ├── 10-16-2024 PhotoViewierAssignment.iml
│   ├── manifest.txt
│   └── src/
│       ├── images/
│       │   ├── image1.png
│       │   ├── image2.png
│       │   └── image3.png
│       └── Main.java
├── JARFiles/
│   ├── myproject.jar
│   └── placeholder.txt
└── README.md

# Uploading with the command line
