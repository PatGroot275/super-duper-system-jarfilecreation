# super-duper-system-jarfilecreation

This is meant to be a repo where you can pull request your project in a certain format and it will spit out a .jar

Directions:
- This is a public repository so everything is public to the internet
- Do not put your name or any identifying information in your code, in the pull request or in any dependency 
- Ensure you have a manifest.txt file in the root of your directory that has the content "Main-Class: Main\n"
- Ensure you have all your dependencies in a "lib" folder
- Ensure you have a Main.java file
- Ensure you have made all dependency references in your code as relative filepaths
- Anything in brackets must be changed, follow directions in Modifications needed

Modifications needed:
- Ensure you have a manifest file with the content:
Manifest-Version: 1.0
Main-Class: Main
Class-Path: lib/[your image folder name]/
- Download the main.yaml file and...


Example structure: 
- project/
- ├── src/
- │   ├── Main.java
- │   ├── utils/
- │   │   └── Helper.java
- ├── lib/
- │   └── chips/
- │       ├── chip1.png
- │       ├── chip2.png
- │       └── chip3.png
- ├── manifest.txt
