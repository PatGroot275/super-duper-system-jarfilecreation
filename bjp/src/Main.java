import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static ArrayList<Integer> deck = new ArrayList<Integer>();
    public static GUI gui;
    public static String playerName;
    public static int playerChips;
    public static SuperPlayer[]  players = new SuperPlayer[4];
    public static int dealerScore;
    public static boolean dealerBusted;
    public static boolean dealerBlackJack;

    public static void main(String[] args) {
        gui = new GUI();

        //connects all of the GUI screen buttons to the main class as a listener
        gui.logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] userData = getUserInfo(gui.username.getText());
                //if users is in system
                if ( userData != null){
                    //if passowrd is right
                    if (userData[1].equals(gui.password.getText())){
                        login(Integer.parseInt(userData[2]));
                    }
                    //passowrd is wrong
                    else{
                        gui.loginErrorMessage.setText("sorry your password is incorrect");
                    }
                } //username doesn't exist
                else {
                    gui.loginErrorMessage.setText("sorry your that usernamne is not registered");
                }
            }
        });

        gui.signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gui.username.getText().equalsIgnoreCase("")){
                    gui.loginErrorMessage.setText("sorry is an invalid username");
                }
                //if username exists
                else if (getUserInfo(gui.username.getText()) != null){
                    gui.loginErrorMessage.setText("sorry that username already exists");
                }
                //add user to system
                else{
                    addUser(gui.username.getText(), gui.password.getText(), "0");
                    login(0);
                }

            }
        });


        gui.buyChipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    int adddChips = Integer.parseInt(gui.buyChipsField.getText());
                    //if not a negative input
                    if (adddChips > -1) {
                        int currentChips = Integer.parseInt(getUserInfo(playerName)[2]);
                        int totalChips = adddChips + currentChips;
                        //if chip number exceeds the limits of memory of integer set it to max value
                        if (totalChips < 0){
                            totalChips = 2147483647;
                        }
                        //update the login screen GUI
                        updateUserChips(playerName, "" + totalChips);
                        gui.buyingChipErrorMessage.setText("");
                        gui.currentChips.setText("Current Chips: " + formatMoney(totalChips+""));
                        gui.playButton.setEnabled(true);
                        gui.joinButton.setEnabled(true);
                        //allow playing if they have enough money to place bets
                        if (totalChips > 4){
                            gui.playButton.setEnabled(true);
                            gui.joinButton.setEnabled(true);
                        }
                        else {
                           gui.buyingChipErrorMessage.setText("Please buy chips to play");
                        }

                    }else{
                        gui.buyingChipErrorMessage.setText("sorry invalid input");
                    }
                } catch (Exception e){
                    gui.buyingChipErrorMessage.setText("sorry invalid input");
                }


            }
        });



        gui.playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //switch to next screen
                gui.setScreen(gui.chosePlayersScreen);
            }
        });

        gui.joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //wouldve went to game lobby for networking
                System.out.println("join game");
            }
        });

        gui.playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //deletes current game and starts a new one
                gui.playAgainButton.setVisible(false);
                gui.returnHome.setVisible(false);
                for (int i = 0; i < players.length; i ++){
                    players[i].clearCards();
                }
                playGame();
            }
        });

        gui.returnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //removes all player objects and resets the GUI and updates player money accordingly
                playerChips = players[2].money;
                gui.playerTypes[2].select(0);
                gui.playerTypes[3].select(0);
                gui.currentChips.setText("Current Chips: "+formatMoney(""+playerChips));
                for (int i = 0; i < players.length; i ++){
                    //no way to actually delete player obj?
                    gui.gameScreen.remove(players[i].playerScreen);
                    players[i] = null;
                }
               if (playerChips < 5){
                   gui.buyingChipErrorMessage.setText("Please buy chips to play");
                   gui.playButton.setEnabled(false);
                   gui.joinButton.setEnabled(false);
                }
                gui.setScreen(gui.homeScreen);
            }
        });


        gui.createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //creates a new game
                gui.setScreen(gui.gameScreen);
                //loops through the players selected to get player tyoes playing
                for (int i = 0; i < gui.playerTypes.length; i++) {
                    SuperPlayer player = null;
                    String playerType = gui.playerTypes[i].getSelectedItem();
                    int playerNumber;
                    //playe 0 uis dealer player 2 is user and 1 and 3 are the cpu or secondary players
                    if (players[1] == null) {
                        playerNumber = 1;
                    } else {
                        playerNumber = 3;
                    }
                    if (playerType.equalsIgnoreCase("CPU")) {
                        player = new CPU(playerNumber, gui.gameScreen, 10000);
                    } else if (playerType.equalsIgnoreCase("online player")) {

                    } else if (playerType.equalsIgnoreCase("local player")) {
                        player = new Player(playerNumber, gui.gameScreen, 10000);
                    } else if (playerType.equalsIgnoreCase("Dealer")) {
                        player = new Dealer(0, gui.gameScreen, 0);
                        players[0] = player;
                    } else if (playerType.equalsIgnoreCase("You")) {
                        player = new Player(2, gui.gameScreen, Integer.parseInt(getUserInfo(playerName)[2]));
                        players[2] = player;
                    } else {

                    }



                    //adds the player to the players array in the correct position
                    if (player != null) {
                        if (!playerType.equalsIgnoreCase("You") && !playerType.equalsIgnoreCase("Dealer")) {
                            if (players[1] == null) {
                                players[1] = player;
                            } else {
                                players[3] = player;
                            }
                        }
                        player.displayCards();
                    }
                }
                //starts playing
                playGame();
            }
        });
    }


    //gets user info from CVS based on its username, returns a string array of [username, password, chip amount]
    public static String[] getUserInfo(String username){
        File file = new File("CSVS/userData.csv");
        String password = "";
        String chips = "";
        String userName= "";
        boolean found = false;
        //scans until user found then breaks, returns null if user not found
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String userData = scanner.nextLine();
                Scanner dataScanner = new Scanner(userData).useDelimiter(",");
                userName = dataScanner.next();
                if (userName.equals(username)){
                    password = dataScanner.next();
                    chips = dataScanner.next();
                    found = true;
                    break;
                }

            }
        }
        catch (Exception e){
            return null;
        }
        if (found){
            return  new String[] {userName,password,chips};
        }
        else {
            return  null;
        }


    }

    //adds a username to the data base given usernamne password and chip number
    public static void addUser(String username, String password, String chips){

        //System.out.println("adding userrrrrrrrrrr");
        File file = new File("CSVS/userData.csv");
        /*
        if (! file.exists() ){
            try {
                //creates file if file didnt exist yet
                //System.out.println("File donest exitttttt");
                FileWriter fileWriter = new FileWriter("CSVS/userData.csv");
                fileWriter.write("testingplayer,123, 0");
            }  catch (Exception e){

            }
        }

         */
    String fileData = "";
    try {
        //copies pre-exsiting data
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            fileData += scanner.nextLine();
            fileData += "\n";
        }
        //writes new data
        fileData += username + "," + password  +"," + chips;
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(fileData);
        fileWriter.close();

    } catch (Exception e){

    }
}


    public static void updateUserChips(String username, String chips){
        File file = new File("CSVS/userData.csv");
        String fileData = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                //loops thru adn copies whole data set, andn update it if its the specified user
                String userData = scanner.nextLine();
                Scanner dataScanner = new Scanner(userData).useDelimiter(",");
                if (dataScanner.next().equals(username)){
                    String password  = dataScanner.next();
                    fileData += (username + ","  + password + "," + chips);
                } else{
                    fileData += userData;
                }
                fileData += "\n";
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileData);
            fileWriter.close();

        } catch (Exception e) {

        }
    }


    public static void login(int chips){
        //updatse next screen
        playerName = gui.username.getText();
        playerChips = chips;
        gui.currentChips.setText("Current Chips: "+formatMoney(""+chips));
        //disable play and join game butotns if not enough money
        if (chips < 5){
            gui.buyingChipErrorMessage.setText("Please buy chips to play");
            gui.playButton.setEnabled(false);
            gui.joinButton.setEnabled(false);
        }
        gui.setScreen(gui.homeScreen);
    }

public static String formatMoney(String chipNumber) {
        //formtas money to have commas
        String out = "";
        for(int i = 0; i< chipNumber.length(); i ++) {
            if ((chipNumber.length()-i)%3 ==0 & (! out.equals(""))) {
                out += ",";
            }
            out+= chipNumber.charAt(i);
        }
        return out;
    }


    public static void shuffleDeck(){
        //creates ordered deck
        ArrayList<Integer> orderedDeck = new ArrayList<Integer>();
        for (int i =0; i < 52; i ++){
            orderedDeck.add(i+1);
        }
        //adds a random position of deck into the real shuffled deck
        for (int i = 0; i <52; i++){
            Random random = new Random();
            if (orderedDeck.size()-1 == 0){
                deck.add(0);
            }
            else {
                //add the deck's random index
                int randInt = random.nextInt(0, orderedDeck.size() - 1);
                deck.add(orderedDeck.get(randInt));
                //delete the index chosen
                orderedDeck.remove(randInt);
            }
        }
    }

    //threads made it a little complicated to work with GUI
    public static void playGame(){
        shuffleDeck();
        int lastPlayer= -1;
        int firstPlayer =- 1;
        //sets the first and last player to correct values for easier looping (excludes dealer)
        for (int i =1; i< players.length; i++ ){
            if (players[i] != null){
                lastPlayer = i;
                if (firstPlayer == -1){
                    firstPlayer = i;
                }
            }
        }
        //had to declare as final to work with timer threads
        final int LASTPLAYER = lastPlayer;
        final int FIRSTPLAYER = firstPlayer;

        //GUI has a shadow panel to act as a screen darkener to make it hihglight which player is going through z-order
        GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);
        //loops through players that exist
        for (int j = 0; j < players.length; j++) {
            if (players[j] != null) {
                //creates a turn finished action that is connected to every player
                // once a player finishes their turn it emits  a turnfinished signal which then this function processes
                TurnFinished tf = new TurnFinished() {
                    public void turnFinished(int playerNumber, String action) {
                        //if the player finished placing their bet
                        if (action.equalsIgnoreCase( "placed bet")){
                            //reset highlight
                            GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);

                            //if this isn't the last player, make the next player bet, else start next step
                            if (LASTPLAYER != playerNumber) {
                                //if theres a next player, then find the next player and call their bet funciton
                                for (int t = playerNumber + 1; t < players.length + 1; t++) {
                                    if (players[t] != null) {
                                        players[t].bet();
                                        gui.jFrame.revalidate();
                                        gui.jFrame.repaint();
                                        break;
                                    }
                                }
                            } else{ //go to next stage and give dealer next card
                                GUI.gameScreen.setComponentZOrder(players[0].playerScreen,0);
                                players[0].addCard(deck.get(0), false, 0);
                                deck.remove(0);
                                players[0].displayCards();
                                gui.jFrame.repaint();
                                gui.jFrame.revalidate();

                            }
                            //if the player was just dealt an inital card (either thier first or second)
                        } else if (action.equalsIgnoreCase( "dealt initial card")){
                            //if its not the last player to get cards
                            if (LASTPLAYER != playerNumber) {
                                for (int t = playerNumber + 1; t < players.length + 1; t++) {
                                    //find next player whos non dealer then breaks
                                    if (players[t] != null) {
                                        //deal a card to this player with 1.5 second delay
                                        int timeWait = 1500;
                                        Timer timer = new Timer(timeWait, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                for (int playerNum = playerNumber+1; playerNum < players.length; playerNum ++){
                                                    //deals card to the next non null player
                                                    if (players[playerNum] != null){
                                                        players[playerNum].addCard(deck.get(0), true, 0);
                                                        deck.remove(0);
                                                        players[playerNum].displayCards();
                                                        gui.jFrame.repaint();
                                                        gui.jFrame.revalidate();
                                                        break;
                                                    }
                                                }
                                            }
                                        });
                                        timer.setRepeats(false);
                                        timer.start();
                                        //hihglight this player
                                        GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);
                                        GUI.gameScreen.setComponentZOrder(players[t].playerScreen,0);
                                        //breaks so it dones't loop and the next player only goes when turn finsih action fires
                                        break;
                                    }
                                }
                                //else its the last player to get card, deal second round of cards
                            } else {
                                //if the dealer has already recived 2 cards, then so must have all the other players given the last player in order just recieved a card
                                if (players[0].cards[0].size() !=2) {
                                    //give dealer their second card, because the last player just got a card but the dealer only has 1 card
                                    int timeWait = 1500;
                                    Timer timer = new Timer(timeWait, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            players[0].addCard(deck.get(0), true, 0);
                                            deck.remove(0);
                                            players[0].displayCards();
                                            gui.jFrame.repaint();
                                            gui.jFrame.revalidate();
                                        }
                                    });
                                    GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);
                                    GUI.gameScreen.setComponentZOrder(players[0].playerScreen,0);
                                    timer.setRepeats(false);
                                    timer.start();

                                } else{
                                    //everyone has been dealth their inital cards, its time to play
                                    GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);
                                    GUI.gameScreen.setComponentZOrder(players[FIRSTPLAYER].playerScreen,0);
                                    //instruct first player to go
                                    players[FIRSTPLAYER].play();
                                }
                            }

                        } else if (action.equalsIgnoreCase( "played turn")){
                            //if player just finished turn and it wasn't the dealer or last player
                            if (playerNumber != LASTPLAYER && playerNumber !=0) {
                                for (int playerNum = playerNumber + 1; playerNum < players.length; playerNum++) {
                                    if (players[playerNum] != null) {
                                        //find the next non0-null player and tell them to go
                                        GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel, 0);
                                        GUI.gameScreen.setComponentZOrder(players[playerNum].playerScreen, 0);
                                        players[playerNum].play();
                                        break;
                                    }
                                }
                            } else if (playerNumber == LASTPLAYER){
                                //if the last player just made their turn then its time for the dealer to go
                                Timer timer = new Timer(1500, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        players[0].play();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                            } else{ //if the dealer has played their turn
                                //score the game

                                //did dealer bust
                                dealerScore = players[0].getHandNumber(players[0].cards[0]);
                                dealerBusted = false;
                                if (dealerScore >21){
                                    dealerBusted = true;
                                }
                                //did dealer get a natural blackJack (10+ace)
                                dealerBlackJack = false;
                                if (players[0].cards[0].size() == 2 && dealerScore == 21){
                                    dealerBlackJack = true;
                                }

                                //loop through players until it finds the first non null one (redudant but messed caused by weird GUI stuff)
                                for (int p = FIRSTPLAYER; p < LASTPLAYER +1; p++){
                                    //hilight the first player
                                    GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);
                                    GUI.gameScreen.setComponentZOrder(players[p].playerScreen,0);
                                    //hilight that player's first hand, because players have muliple hands due to splits
                                    players[p].cardPanels[0].setBackground(new Color (0,0,0));
                                    players[p].cardPanels[0].setSize( (players[p].cards[0].size() * 20 +70), players[p].cardPanels[0].getHeight());
                                    GUI.gameScreen.repaint();
                                    GUI.gameScreen.revalidate();
                                    //have a delay
                                    Timer timer = new Timer(1500, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            //score the game to tell user if they lost won or tied
                                            players[p].scoreGame(dealerScore,dealerBusted,dealerBlackJack);
                                        }
                                    });
                                    timer.setRepeats(false);
                                    timer.start();
                                    break;
                                }
                            }
                        } else if (action.equalsIgnoreCase( "player scored")){
                            //score the next player if the last player wasn't just scored
                            if (playerNumber != LASTPLAYER) {
                                //find next non-null player
                                for (int playerNum = playerNumber + 1; playerNum < players.length; playerNum++) {
                                    if (players[playerNum] != null) {
                                        //hihglight thge player
                                        GUI.gameScreen.setComponentZOrder(GUI.shadowFocusPanel,0);
                                        GUI.gameScreen.setComponentZOrder(players[playerNum].playerScreen,0);
                                        //hihglith the players first hand (two hands bc of splits)
                                        players[playerNum].cardPanels[0].setBackground(new Color (0,0,0));
                                        players[playerNum].cardPanels[0].setSize( (players[playerNum].cards[0].size() * 20 +70), players[playerNum].cardPanels[0].getHeight());
                                        final int PLAYERNUM = playerNum;
                                        Timer timer = new Timer(1500, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                //score their hand after a delay
                                                players[PLAYERNUM].scoreGame(dealerScore, dealerBusted, dealerBlackJack);
                                            }
                                        });
                                        timer.setRepeats(false);
                                        timer.start();
                                        break;
                                    }
                                }
                            } else{
                                //otherwise, means last player was just scored and time to play again or start a new game
                                updateUserChips(playerName,"" + players[2].money);
                                boolean canPlayAgain = true;
                                //if players still have money left then enable playagain button, else disbale it
                                for (int i = FIRSTPLAYER; i < LASTPLAYER; i++){
                                    if (players[i].money<5){
                                        canPlayAgain = false;
                                    }
                                }
                                GUI.gameOver(canPlayAgain);
                            }
                        }
                    }
                };
                //connecting all the players to the turnFinihsed action
                players[j].addTurnFinisherEvent(tf);
            }


        }
        //find the first non null player and tell them to bet
        players[FIRSTPLAYER].bet();
                /*
        for (int j = 0; j < players.length; j++) {
            if (players[j] != null) {
                players[j].bet();
                break;
            }
       }*/
    }
}



