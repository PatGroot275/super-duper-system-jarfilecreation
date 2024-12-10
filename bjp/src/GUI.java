import javax.swing.*;
import java.awt.*;


public class GUI {
    //declare all these public screens and buttons so that main has access to connect to them
    static PlayerGUI loginScreen;
    static PlayerGUI homeScreen;
    static PlayerGUI gameScreen;
    static JFrame jFrame;
    static JButton logIn;
    static JButton signUp;
    static TextField password;
    static TextField username;
    static JLabel loginErrorMessage;
    static PlayerGUI currentPanel;
    static JButton buyChipsButton;
    static TextField buyChipsField;
    static JLabel buyingChipErrorMessage;
    static JLabel currentChips;
    static JButton playButton;
    static JButton joinButton;
    static JButton createGameButton;
    static PlayerGUI chosePlayersScreen;
    static PlayerGUI shadowFocusPanel;
    static JButton playAgainButton;
    static JButton returnHome;



    static Choice[] playerTypes = new Choice[4];

    //chat gpt code for gettin screen dimestion with adjustment fro screen insets
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gd = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gd.getDefaultConfiguration();

    // Get full screen dimensions
    Rectangle fullScreenBounds = gc.getBounds();

    // Get insets (e.g., taskbar height or other system UI elements)
    Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

    int usableWidth = fullScreenBounds.width - screenInsets.left - screenInsets.right;
    int usableHeight = fullScreenBounds.height - screenInsets.top - screenInsets.bottom;
    //end of chat gpt code

    int screenWidth = usableWidth; //(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int screenHeight = usableHeight;//(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();



    public GUI(){
        //making login screen GUI
        int width;
        int height;

        loginScreen = new PlayerGUI();
        currentPanel = loginScreen;
        jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
        jFrame.setBounds(0,0,screenWidth,screenHeight);

        loginScreen.setLayout(null);
        loginScreen.setBounds(0,0,screenWidth,screenHeight);
        jFrame.add(loginScreen);

        //create longin screen buttons (dynamic relative positions on screen)
        signUp = new JButton();
        width = 100;
        height = 50;
        signUp.setBounds((screenWidth/2) - (width +(width/2)), 9* screenHeight/16 - (height/2), width,height);
        signUp.setText("Sign Up");
        loginScreen.add(signUp);

        logIn = new JButton();
        width = 100;
        height = 50;
        logIn.setBounds((screenWidth/2) + (width/2), 9* screenHeight/16 - (height/2), width,height);
        logIn.setText("Login");
        loginScreen.add(logIn);




        width = 300;
        height = 30;
        username = new TextField();
        username.setBounds( (screenWidth/2) - (width/2), 7* screenHeight/16 - (height/2), width,height);
        loginScreen.add(username);


        width = 200;
        height = 30;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        usernameLabel.setBounds( (screenWidth/2) - (width/2), 13* screenHeight/32 - (height/2), width,height);

        loginScreen.add(usernameLabel);


        width = 300;
        height = 30;
        password = new TextField();
        password.setBounds( (screenWidth/2) - (width/2), 8* screenHeight/16 - (height/2), width,height);
        loginScreen.add(password);


        width = 200;
        height = 30;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        passwordLabel.setBounds( (screenWidth/2) - (width/2), 15* screenHeight/32 - (height/2), width,height);
        loginScreen.add(passwordLabel);


        width = 800;
        height = 160;
        JLabel title = new JLabel("Black Jack");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("ariel",Font.PLAIN,96) );
        title.setBounds( (screenWidth/2) - (width/2), (4*(screenHeight/16)) - (height/2), width,height);
        loginScreen.add(title);

        width = 600;
        height = 20;
        loginErrorMessage = new JLabel();
        loginErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        loginErrorMessage.setForeground(new Color (180,10,10));
        loginErrorMessage.setFont(new Font("ariel",Font.PLAIN,16) );
        loginErrorMessage.setBounds( (screenWidth/2) - (width/2), 10* screenHeight/16 - (height/2), width,height);
        loginScreen.add(loginErrorMessage);



        //making homeScreen Gui
        homeScreen = new PlayerGUI();
        homeScreen.setBounds(0,0,screenWidth,screenHeight);


        homeScreen.setLayout(null);

        playButton = new JButton("Play Game");
        width = 180;
        height = 60;
        playButton.setFont(new Font("ariel",Font.PLAIN,24) );
        playButton.setBounds((screenWidth/2) - (width/2), 13* screenHeight/32 - (height/2), width,height);
        homeScreen.add(playButton);

        joinButton = new JButton("Join Game");
        joinButton.setFont(new Font("ariel",Font.PLAIN,24) );
        joinButton.setBounds((screenWidth/2) - (width/2), 17* screenHeight/32 - (height/2), width,height);
        homeScreen.add(joinButton);

        width = 800;
        height = 160;
        JLabel title2 = new JLabel("Black Jack");
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setFont(new Font("ariel",Font.PLAIN,96) );
        title2.setBounds( (screenWidth/2) - (width/2), (4*(screenHeight/16)) - (height/2), width,height);
        homeScreen.add(title2);


        height = 20;
        width = 300;
        currentChips = new JLabel("Current Chips: 10,000");
        currentChips.setHorizontalAlignment(SwingConstants.CENTER);
        currentChips.setBounds((screenWidth/2) - (width/2), 10* screenHeight/16 - (height/2), width,height);
        homeScreen.add(currentChips);

        height = 30;
        width = 70;
        buyChipsButton = new JButton("Buy");
        buyChipsButton.setBounds((screenWidth/2) - (width/2) +130, 11* screenHeight/16 - (height/2), width,height);
        homeScreen.add(buyChipsButton);


        width = 150;
        buyChipsField = new TextField("");
        buyChipsField.setFont(new Font("ariel",Font.PLAIN,16));
        buyChipsField.setBounds((screenWidth/2) - (width/2), 11* screenHeight/16 - (height/2), width,height);
        homeScreen.add(buyChipsField);


        width = 70;
        JLabel buyChipsFieldLabel = new JLabel("Buy Chips:");
        buyChipsFieldLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buyChipsFieldLabel.setBounds((screenWidth/2) - (width/2) -130, 11* screenHeight/16 - (height/2), width,height);
        homeScreen.add(buyChipsFieldLabel);

        width = 600;
        height = 20;
        buyingChipErrorMessage = new JLabel();
        buyingChipErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
        buyingChipErrorMessage.setForeground(new Color (180,10,10));
        buyingChipErrorMessage.setFont(new Font("ariel",Font.PLAIN,16) );
        buyingChipErrorMessage.setBounds( (screenWidth/2) - (width/2), 12* screenHeight/16 - (height/2), width,height);
        homeScreen.add(buyingChipErrorMessage);



        homeScreen.setVisible(false);
        jFrame.add(homeScreen);

        //chosing player sreen
        chosePlayersScreen = new PlayerGUI();
        chosePlayersScreen.setBounds(0,0,screenWidth,screenHeight);
        chosePlayersScreen.setLayout(null);
        int spacing = screenHeight/8;
        int intialHeight = 2*screenHeight/7;
        width = 200;
        height = 50;

        //looping to create choice boxes to chose wwhich typies of players to play against
        for (int i = 0; i < 4; i ++){
            Choice button = new Choice();
            button.setBounds(screenWidth/2 , intialHeight + (i*spacing),width, height );
            button.setFont(new Font("ariel",Font.PLAIN,30));
            if (i < 2){

                if (i == 0){
                    button.add("You");
                }
                else {
                    button.add("Dealer");
                }
                button.setEnabled(false);
            }
            button.add("empty");
            button.add("CPU");
            button.add("local player");
            button.add("online player");

            chosePlayersScreen.add(button);

            JLabel playerLabel = new JLabel();
            playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            playerLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            playerLabel.setText("Player" + (i+1) + ":");
            playerLabel.setBounds(screenWidth/2 - width , intialHeight + (i*spacing),width, height );
            playerLabel.setFont(new Font("ariel",Font.PLAIN,50));

            chosePlayersScreen.add(playerLabel);

            playerTypes[i]  = button;
        }


        width = 800;
        height = 160;
        JLabel title3 = new JLabel("Black Jack");
        title3.setHorizontalAlignment(SwingConstants.CENTER);
        title3.setFont(new Font("ariel",Font.PLAIN,96) );
        title3.setBounds( (screenWidth/2) - (width/2), (2*(screenHeight/16)) - (height/2), width,height);
        chosePlayersScreen.add(title3);

        width = 180;
        height = 60;
        createGameButton = new JButton();
        createGameButton.setText("Play Game");
        createGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        createGameButton.setFont(new Font("ariel",Font.PLAIN,16) );
        createGameButton.setBounds( (screenWidth/2) - (width/2), (13*(screenHeight/16)) - (height/2), width,height);
        chosePlayersScreen.add(createGameButton);






        chosePlayersScreen.setVisible(false);
        jFrame.add(chosePlayersScreen);


        //creating game screen
        gameScreen = new PlayerGUI();
        gameScreen.setBounds(0,0,screenWidth, screenHeight);
        gameScreen.setLayout(null);
        gameScreen.setVisible(false);


        PlayerGUI background = new PlayerGUI();
        background.setBounds(60,60,screenWidth-120,screenHeight-140);
        background.setBackground(new Color(15,90,15));
        gameScreen.add(background);

        PlayerGUI border = new PlayerGUI();
        border.setBounds(0,0,screenWidth,screenHeight);
        border.setBackground(new Color(0,0,0));
        gameScreen.add(border);

        shadowFocusPanel = new PlayerGUI();
        shadowFocusPanel.setBounds(0,0,screenWidth,screenHeight);
        shadowFocusPanel.setBackground(new Color(0,0,0,70));
        gameScreen.add(shadowFocusPanel);
        gameScreen.setComponentZOrder(shadowFocusPanel,0);


        playAgainButton = new JButton("Play Again");
        width = 180;
        height = 60;
        playAgainButton.setFont(new Font("ariel",Font.PLAIN,24) );
        playAgainButton.setBounds((screenWidth/2) - (width/2), 13* screenHeight/32 - (height/2), width,height);
        playAgainButton.setVisible(false);
        gameScreen.add(playAgainButton);

        returnHome = new JButton("Home");
        returnHome.setFont(new Font("ariel",Font.PLAIN,24) );
        returnHome.setBounds((screenWidth/2) - (width/2), 17* screenHeight/32 - (height/2), width,height);
        returnHome.setVisible(false);
        gameScreen.add(returnHome);

        jFrame.add(gameScreen);



        //on inital loading, set login to uccrent screen and display it
        loginScreen.setVisible(true);
        gameScreen.setVisible(false);

        jFrame.setVisible(true);



    }


    //switch screen to a new screen
    public static void setScreen(PlayerGUI screen){
        currentPanel.setVisible(false);
        currentPanel = screen;
        screen.setVisible(true);
        playAgainButton.setVisible(false);
        returnHome.setVisible(false);


    }

    //on game over update the game screen
    public static void gameOver(boolean canPlayAgain){
        playAgainButton.setVisible(true);
        returnHome.setVisible(true);

        playAgainButton.setEnabled(canPlayAgain);

        gameScreen.setComponentZOrder(playAgainButton,0);
        gameScreen.setComponentZOrder(returnHome,0);
    }

}


