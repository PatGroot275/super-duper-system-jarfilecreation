# super-duper-system-jarfilecreation

This will help create a .jar file for your project provided that you follow the directions PRECISELY

# Reminders:
- ### **Since you will be forking the repository (making your own copy), it will be PUBLIC**
- ### **Do not put your name or any identifying information in your code at all**
- ### **Do not put any api keys, passwords or anything of similar type in your code at all**

# Setup your code:
- Ensure you have a Main.java file
- Ensure you have a manifest.txt file in the root of your directory that has the content "Main-Class: Main\n" (\n means a new line, not literally '\n' written)
- Place all your dependencies (.csv, .png, .jpg, etc.) in properly named folders **within** your src folder
- You should have a "Images" folder for images, do not leave any depenedncies outside a folder in src, you could even create a "dependencies" folder in src and put everything in there
- To refer to your dependencies, you should be currently using: "new ImageIcon("src\\\images\\\image1.png")" (The images and image1.png are part of the example and will be replaced with your "src\\\\[FOLDER NAME]\\\\[FILE NAME]")
- Change this to: "new ImageIcon(Main.class.getResource("images/image1.png"))"
- "Main.class.getResource" should be the only syntax you use across all classes/files, you shouldn't have to change it to "[NAME OF YOUR CLASS].class.getResource"
- Your code **will not work** when being ran/debugged with this change but this is necessary for the .jar file creation
- Feel free to look through the example provided I've provided for the location/contents of the manifest file, where the images/dependencies should be and how the filepaths should look

# Directions to run the action:
- Create a fork of this repository by clicking the 'Fork' button in the upper right-hand corner, this will create your own copy of this repository. You will make your edits in this new repository, not this one
- Delete the PhotoViewerAssignment directory
- Upload your project, use the Github website's uploader, command line (instructions below) or visual studio code
- Complete the "**Modifications to main.yml section**", before moving on
- Double check that your structure is similar to the example structure and you've followed the directions in setting up your code
- Navigate to the "Actions" tab in Github
- Click on "Build JAR File" and run the workflow, watch for if it is successful or not
- If successful, you will find your new Jar file in the folder "JARFiles", it will replace the old myproject.jar
- If not successful, **check your filepaths, dependencies, and modifications to the main.yml again, and try to rerun**, if still not successful, follow the directions under the bug reporting section. 

# Modifications to main.yml
- Follow these directions after uploading your project
- Navigate to the main.yml file in .github/workflows/
- Edit line 30 from "cd 10-16-2024-PhotoViewerAssignment" to "cd [YOUR PROJECT NAME]"
- You will need to repeat lines 39-40 **for each dependency folder you have** like "Card images" or "Chip images" (The example only has one dependency folder so it's only lines 39-40)
- Edit line 39 from "mkdir -p out/src/images" to "mkdir -p out/src/[YOUR DEPENDENCY FOLDER NAME]"
- Edit line 40 from "cp -r src/images/* out/src/images/" to "cp -r src/[YOUR DEPENDENCY FOLDER NAME]/* out/src/[YOUR DEPENDENCY FOLDER NAME]/"
- Do the above two things for **each dependency folder you have**

# Bug reporting
- For random suggestions or other bugs, submit an issue however you'd like but make sure to do it in the **ORIGINAL REPOSITORY**
- For failures in running, click on the "Build JAR File" run instance that failed for you
- You will be in the summary tab (look at the left side of your screen), click on the "build" tab (under the Jobs section)
- Click on the settings icon on the right side of your screen
- Click "view raw logs" (check screenshot below)
- Copy the content
- Open a new issue in the **ORIGINAL REPOSITORY**, title it "Failure to build" and paste the logs into the content
- I will try to get back to you with a solution or follow-ups
![Wheretofindlogs](https://github.com/user-attachments/assets/020a9407-26ad-41d8-a3e7-11aa80e90cb7)


# Uploading with the command line
- ...

# Example Structure:
![File Structure](https://github.com/user-attachments/assets/13c90e71-ed55-43c6-8221-badf88978c48)
