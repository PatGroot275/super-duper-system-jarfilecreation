import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class SuperPlayer {
    public TurnFinished turnFinished;
    public int[] guiRotations = {180, -90, 0, 90};
    // setup as an array of 2 because of splits
    //game area of each split
    public PlayerGUI[] gamePanels = new PlayerGUI[2];
    //hand of easch split
    public PlayerGUI[] cardPanels = new PlayerGUI[2]; //use gamePanels[i].getcomponent(0) instead
    //bets of eahc split
    public PlayerGUI[] betPanels = new PlayerGUI[2];
    //total player area
    public PlayerGUI playerArea;
    //where the chips go
    public PlayerGUI chipPanel;
    //weird problems with button rotations not working resulting in two seperate mehtods for adding bet buttons
    public PlayerGUI betButtonPanel;
    public PlayerGUI betButtonScreen;
    public int playerNumber;
    public PlayerGUI playerGui;
    public PlayerGUI playerScreen;

    public int money;
    public PlayerGUI userButtonPanel;
    public int cardPileOn = 0;

    //player buttons such as hit stand ect
    public boolean[] buttonsVisbile = {false, false, false, false, false};  //next, hit, stand, double down, split
    public JButton[] buttons = new JButton[5];
    public String[] buttonNames = {"Next", "Hit", "Stand", "Double Down", "split"};


    //public ArrayList<ArrayList<Integer>> bets = new ArrayList<ArrayList<Integer>>();

    public ArrayList<Card>[] cards = new ArrayList[2];

    public ArrayList[] bets = new ArrayList[2];

    public String[] chipImages = {"ChipImages/PokerChipSet1 - 5 Full.png", "ChipImages/PokerChipSet1 - 25 Full.png", "ChipImages/PokerChipSet1 - 100 Full.png", "ChipImages/PokerChipSet1 - 500 Full.png", "ChipImages/PokerChipSet1 - 5000 Full.png", "ChipImages/PokerChipSet 1 - 25000 Full.png", "ChipImages/PokerChipSet1 - 100000 Full.png"};

    //when gernateing chips this are the minimum of each the player should have asusming they have money (7 5s, 6 25s, 4 100s, ect
    public int[] minimumChips = {7, 6, 4, 3, 2, 1, 1};
    public int[] chipValues = {5, 25, 100, 500, 5000, 25000, 100000};


    //chat gpt code (the way i was getting dimensions didnt account for insets)
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

    //gui of the player area itself
    int guiWidth = 2 * screenWidth / 5;
    int guiHeight = 2 * screenHeight / 5;
    //public PlayerGUI playerGui;
    public PlayerGUI gameScreenPanel;


    public SuperPlayer(int playerNumber, PlayerGUI gameScreenPanel, int money) {
        //make card arraylist for each hand
        cards[0] = new ArrayList<Card>();
        cards[1] = new ArrayList<Card>();

        //make bet arraylist for each hand
        for (int i = 0; i < 2; i++) {
            ArrayList<Integer> betChips = new ArrayList<Integer>();
            bets[i] = betChips;
        }


        this.money = money;
        this.gameScreenPanel = gameScreenPanel;
        this.playerNumber = playerNumber;

        //playergui has to cover the whole screen to include stand and hit buttons
        playerScreen = new PlayerGUI();
        playerScreen.setBounds(0, 0, screenWidth, screenHeight);
        playerScreen.setBackground(new Color(0, 0, 0, 0));
        playerScreen.setLayout(null);

        //for be tbuttons that rotation were breaking them
        betButtonScreen = new PlayerGUI();
        betButtonScreen.setBounds(0, 0, screenWidth, screenHeight);
        betButtonScreen.setBackground(new Color(0, 0, 0, 0));
        betButtonScreen.setLayout(null);
        gameScreenPanel.add(betButtonScreen);

        //this is the roated gui to display the cards and chips
        playerGui = new PlayerGUI();
        playerGui.setBounds(0, 0, screenWidth, screenHeight);
        playerGui.setBackground(new Color(0, 0, 0, 0));
        playerGui.setRotation(guiRotations[playerNumber]);
        playerGui.setLayout(null);



        //userButtonPanel for displaying hit, stand ect
        userButtonPanel = new PlayerGUI();
        userButtonPanel.setBounds(screenWidth / 3, screenHeight / 3, screenWidth / 3, screenHeight / 3);
        userButtonPanel.setBackground(new Color(0, 0, 0, 0));



        playerArea = new PlayerGUI();
        playerArea.setBounds((screenWidth - guiWidth) / 2, screenHeight - guiHeight - 200, guiWidth, guiHeight);

        //adjust gui based on the player number
        if (playerNumber == 1) {
            playerArea.setBounds((screenWidth - guiWidth) / 2, +screenHeight + 15, guiWidth, guiHeight);
        } else if (playerNumber == 2) {
            playerArea.setBounds((screenWidth - guiWidth) / 2, screenHeight - guiHeight - 20, guiWidth, guiHeight);
        } else if (playerNumber == 3) {
            playerArea.setBounds((screenWidth - guiWidth) / 2, +screenHeight + 15, guiWidth, guiHeight);
        } else if (playerNumber == 0) {
            playerArea.setBounds((screenWidth - guiWidth) / 2, screenHeight - guiHeight, guiWidth, guiHeight);
        }

        playerArea.setBackground(new Color(0, 0, 0, 0));
        playerArea.setLayout(null);

        //sets up game pannels for each hand/split
        for (int i = 0; i < 2; i++) {
            PlayerGUI gamePanel = new PlayerGUI();
            gamePanel.setBounds(guiWidth / 2 - (i * (guiWidth / 2)), 0, guiWidth / 2, 2 * guiHeight / 5);
            //gamePanel.setBackground(new Color (0,0,100));
            gamePanel.setLayout(null);
            gamePanel.setBackground(new Color(0, 0, 0, 0));
            gamePanels[i] = gamePanel;

            //adds a panel that display cards
            PlayerGUI cardPanel = new PlayerGUI();
            cardPanel.setBounds(3 * guiWidth / 20, 0, 7 * guiWidth / 20, 2 * guiHeight / 5);
            //cardPanel.setBackground(new Color (0,0,0));
            cardPanel.setLayout(null);
            cardPanel.setBackground(new Color(0, 0, 0, 0));
            gamePanel.add(cardPanel);
            cardPanels[i] = cardPanel;

            if (playerNumber != 0) {
                //adds a panel that diplsays bets if nit the dealer
                PlayerGUI betPanel = new PlayerGUI();
                betPanel.setBounds(0, 0, 3 * guiWidth / 20, 2 * guiHeight / 5);
                //betPanel.setBackground(new Color (100,0,0));
                betPanel.setLayout(null);
                betPanel.setBackground(new Color(0, 0, 0, 0));
                gamePanel.add(betPanel);
                betPanels[i] = betPanel;
            }

            playerArea.add(gamePanel);
        }
        if (playerNumber != 0) {
            //adds a pannel taht displays the chips each player has if no thte dealer
            chipPanel = new PlayerGUI();
            chipPanel.setLayout(null);
            chipPanel.setBounds(0, 2 * guiHeight / 5, guiWidth, 3 * guiHeight / 5);
            //chipPanel.setBackground(new Color (100,0,0));
            chipPanel.setBackground(new Color(0, 0, 0, 0));
            playerArea.add(chipPanel);

            //adds a apnel for bet buttons
            betButtonPanel = new PlayerGUI();
            betButtonPanel.setLayout(null);
            betButtonPanel.setBounds(0, 2 * guiHeight / 5, guiWidth, 3 * guiHeight / 5);

            betButtonPanel.setBackground(new Color(0, 0, 0, 0));
            betButtonPanel.setVisible(true);
            playerArea.add(betButtonPanel);
        }

        //add a stack of face card downs for the dealer to look like a deck
        if (playerNumber == 0) {
            for (int i = 0; i < 6; i++) {
                JLabel cardImage = new JLabel();
                ImageIcon test = new ImageIcon(Main.class.getResource("CardImages/b1fv.png"));
                cardImage.setIcon(test);
                cardImage.setVisible(true);
                cardImage.setBounds(guiWidth / 5, ((gamePanels[0].getHeight() - test.getIconHeight() - 6) / 2) + (2 * i), test.getIconWidth(), test.getIconHeight());

                PlayerGUI cardPanel = cardPanels[1];// (PlayerGUI) gamePanels[1].getComponent(0);
                cardPanel.add(cardImage);
                cardPanel.setComponentZOrder(cardImage, 0);
            }
        }

       // displayUserButtons();
        //add all of these elements to the main panel
        playerGui.add(playerArea);

        playerScreen.add(userButtonPanel);

        playerScreen.setComponentZOrder(userButtonPanel, 0);

        playerScreen.add(playerGui);
        gameScreenPanel.add(playerScreen);
        gameScreenPanel.setComponentZOrder(playerScreen, 0);
        playerScreen.setComponentZOrder(betButtonScreen, 0);

        userButtonPanel.setVisible(false);


        //diplsay the chips that the player has if they arent a dealer
        if (playerNumber != 0) {
            generateChips();
            displayBets();
        }

        //hid the player buttons, as they should only show when its their turn
        if (betButtonPanel != null) {
            betButtonPanel.setVisible(false);
        }
        if (betButtonScreen != null) {
            betButtonScreen.setVisible(false);
        }
        playerScreen.repaint();
        playerScreen.revalidate();
    }

    //generate's the chips that the player has based on their money
    public void generateChips() {
        //clear the past display
        chipPanel.removeAll();
        betButtonPanel.removeAll();
        betButtonScreen.removeAll();
        playerArea.setComponentZOrder(betButtonPanel, 0);
        betButtonScreen.setVisible(true);
        betButtonPanel.setVisible(true);
        playerScreen.revalidate();
        playerScreen.repaint();

        int firstChip = -1;
        int moneyLeft = money;
        //loop through chips form the most expensive to the 5$
        for (int x = 0; x < 7; x++) {
            //calcuates the minimum amount of money needed for it to be able to display the right number of chips before this chip
            int minimumMoney = 0;
            for (int j = 0; j < chipValues.length - x - 1; j++) {
                //adds the chip's value mulitpled by the minim number of that chip
                minimumMoney += minimumChips[j] * chipValues[j];
            }

            //finds the most chips that could be bought with the currnt money
            int NumberOfChips = moneyLeft / (chipValues[chipValues.length - x - 1]);
            //if it can buy a chip
            if (NumberOfChips > 0) {
                if (moneyLeft - (chipValues[chipValues.length - x - 1] * NumberOfChips) >= minimumMoney) {
                    //if all these chips can be bought and still leaves at least the mimnim amount needed for the other chips then buy them
                } else {
                    //buy less chips until theres enough money to be bigger than minimum money
                    NumberOfChips -= (int) Math.ceil((double) (minimumMoney) / (double) (chipValues[chipValues.length - x - 1]));
                    NumberOfChips = Math.max(NumberOfChips, 0);
                }
            }

            //finds the first chip so that it can be centered in the middle, eg if theres no 10,000s or 5,000s or 1,000s the whole thing can be shifted right
            if (NumberOfChips > 0 && firstChip == -1) {
                firstChip = x;

            }

            //update moneyleft based on how many of the chips bought
            moneyLeft -= (chipValues[chipValues.length - x - 1] * NumberOfChips);
            if (NumberOfChips > 0) {
                //jlabe to show case how many of the chips the player has of that type
                JLabel jLabel = new JLabel("x" + NumberOfChips);
                //align it based on how many chip types there going to be
                jLabel.setBounds(guiWidth - ((guiWidth - ((7) * 68)) / 2) + 36 + (x + 1 - (firstChip / 2)) * -68, (47 * guiHeight / 200), 50, 20);
                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel.setFont(new Font("areil", Font.PLAIN, 12));
                jLabel.setForeground(new Color(150, 150, 150));
                chipPanel.add(jLabel);

                if (cards[0].isEmpty()) {
                    //if still in the betting phase than add a bet button to each chip pile
                    JButton bet = new JButton("bet");
                    bet.setName((chipValues.length - x - 1) + "");
                    bet.setBounds((guiWidth - (guiWidth - ((7) * 68)) / 2) + 8 + (1 + x - (firstChip / 2)) * -68, (3 * guiHeight / 10) + 5, 50, 20);
                    bet.setHorizontalAlignment(SwingConstants.CENTER);
                    bet.setFont(new Font("areil", Font.PLAIN, 10));
                    betButtonPanel.add(bet);

                    //if its a raoted GUI then mnanutally update the buttons because of rotation problems
                    if (playerNumber == 1) {
                        double xPos = bet.getLocationOnScreen().getX();
                        double yPos = bet.getLocationOnScreen().getY();
                        betButtonPanel.remove(bet);
                        bet.setBounds((int) yPos + 17 * screenWidth / 80, betButtonPanel.getX() + ((x - 0) * 68) + (68 * ((7 - firstChip) / 2)), 50, 20);
                        bet.setVisible(true);
                        betButtonScreen.add(bet);
                        betButtonScreen.setComponentZOrder(bet, 0);
                    } else if (playerNumber == 3) {
                        double xPos = bet.getLocationOnScreen().getX();
                        double yPos = bet.getLocationOnScreen().getY();
                        betButtonPanel.remove(bet);
                        bet.setBounds((int) 2 * screenWidth / 80, (3 * screenHeight / 4) - betButtonPanel.getY() + (68 * ((firstChip) / 2)) - ((x - 2) * 68), 50, 20);
                        //bet.setBounds((int)  2*screenWidth/80,  betButtonPanel.getX() - ((x-1)*68) - (68*((7-firstChip)/2)) ,50,20);
                        bet.setVisible(true);
                        betButtonScreen.add(bet);
                        betButtonScreen.setComponentZOrder(bet, 0);
                    }
                    //connect the buttons to be able to add bets
                    bet.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //updates money and displays the new amount of chips
                            money -= chipValues[Integer.parseInt(bet.getName())];
                            generateChips();
                            //adds it to the bet pile and displays the bet pile
                            bets[0].add(Integer.parseInt(bet.getName()));
                            displayBets();
                            gameScreenPanel.repaint();
                            userButtonPanel.getComponent(0).setEnabled(true);
                        }
                    });
                }
                gameScreenPanel.repaint();
                //loop through the number of chips being bought and make a stack of them with the stack height limited to 7
                for (int i = 0; i < Math.min(NumberOfChips, 7); i++) {
                    ChipGui chipImage = new ChipGui();
                    //alligns it so that the chips always ends at the botton even if number of chips being bought differs
                    chipImage.setBounds((guiWidth - ((guiWidth - ((7) * 68)) / 2)) + (1 + x - (firstChip / 2)) * -68, (guiHeight / 12 + (Math.min(NumberOfChips, 7) * -3)) + (i * +3), 62, 62);
                    chipImage.setImageFilePath(chipImages[chipImages.length - 1 - x]);
                    chipImage.setVisible(true);
                    chipPanel.add(chipImage);
                }
            }
        }
    }

    //displays the bet for each card pile
    public void displayBets() {
        //for each hand (bc splits)
        for (int j = 0; j < 2; j++) {
            //remove everything
            PlayerGUI betPanel = betPanels[j];
            betPanel.removeAll();
            int betMoney = 0;
            //needed so that it can dispoaly the first bets on the bottom
            int amountCutOff = bets[j].size() - Math.min(bets[j].size(), 10);
            //loop through all the chips bet and display them
            for (int i = 0; i < Math.min(bets[j].size(), 10); i++) {
                //displays a chip
                ChipGui chipImage = new ChipGui();
                chipImage.setBounds((betPanel.getWidth() - 62) / 2, betPanel.getHeight() / 4 + (i * 3) - (Math.min(bets[j].size(), 10) * 3), 62, 62);
                chipImage.setImageFilePath(chipImages[(int) bets[j].get(bets[j].size() - 1 - i - amountCutOff)]);
                chipImage.setVisible(true);
                betPanel.add(chipImage);
            }

            //calcuate the total money bet on that hand
            for (int i = 0; i < bets[j].size(); i++) {
                betMoney += chipValues[(int) bets[j].get(bets[j].size() - 1 - i)];
            }
            //make a jlabel to show how much mooney is bet
            if (betMoney > 0) {
                JLabel jLabel = new JLabel();
                jLabel.setText(betMoney + "$");
                jLabel.setBounds((betPanel.getWidth() - 100) / 2, 32 * betPanel.getHeight() / 40, 100, 20);
                jLabel.setHorizontalAlignment(SwingConstants.CENTER);
                jLabel.setFont(new Font("areil", Font.PLAIN, 18));
                jLabel.setForeground(new Color(150, 150, 150));
                jLabel.setVisible(true);
                betPanel.add(jLabel);
            }
        }
    }

    public void displayCards() {
        //for each hand
        for (int x = 0; x < cards.length; x++) {
            //delete the hand
            if (playerNumber != 0) {
                cardPanels[x].removeAll();
            }
            //repeat for each card in hand
            for (int i = 0; i < cards[x].size(); i++) {
                //make a Jlabe that reporesens the card
                JLabel cardImage = new JLabel();
                Card card = cards[x].get(i);
                ImageIcon test = new ImageIcon(Main.class.getResource(card.getFilePath()));

                cardImage.setIcon(test);
                cardImage.setVisible(true);
                cardImage.setBounds(i * 20 + 10, (gamePanels[x].getHeight() - test.getIconHeight()) / 2, test.getIconWidth(), test.getIconHeight());
                PlayerGUI cardPanel = cardPanels[x];
                cardPanel.add(cardImage);
                cardPanel.setComponentZOrder(cardImage, 0);
            }
        }


    }

    public void addCard(int cardnum, boolean faceup, int cardPile) {
        //add a card to the hand speciefied
        //create the card object
        Card card = new Card(cardnum);
        card.setFaceUp(faceup);
        //add the card to the right hand
        cards[cardPile].add(card);

        //if the player in total oonly has 0 or 1 cards, then fire signal bc it means players are still getting dealt inital cards
        if (cards[0].size() + cards[1].size() < 3) {
            finishedTurn("dealt initial card");
        }
    }

    public void displayUserButtons() {
        //displayuers user buttons such as hit stand ect
        //deletes everything
        userButtonPanel.setVisible(true);
        userButtonPanel.setLayout(null);
        userButtonPanel.removeAll();

        //finds the number of visible buttons to center them
        int numberVisible = 0;
        for (int i = 0; i < buttonsVisbile.length; i++) {
            if (buttonsVisbile[i] == true) {
                numberVisible += 1;
            }

        }

        int spacing = 0;
        //repreats for all  buttons
        for (int i = 0; i < buttonsVisbile.length; i++) {
            //create the button and set its text
            JButton button = new JButton(buttonNames[i]);
            button.setName(buttonNames[i]);
            button.setVisible(buttonsVisbile[i]);
            if (button.getText().equalsIgnoreCase("Double Down")) {
                button.setFont(new Font("ariel", Font.PLAIN, 6));
            } else {
                button.setFont(new Font("ariel", Font.PLAIN, 8));
            }
            int buttonHeight = 40;
            int buttonWidth = 80;

            //align the button
            button.setBounds((int) (((screenWidth - buttonWidth * numberVisible * 1.2) / 2) - (screenWidth / 3) + (spacing * buttonWidth * 1.2)), (screenHeight - buttonHeight) / 2 - screenHeight / 3, buttonWidth, buttonHeight);

            //if the buttons suppose to be displyed then display it and icriment button spacing
            if (buttonsVisbile[i] == true) {
                spacing += 1;
                userButtonPanel.add(button);
                button.setVisible(true);
                buttons[i] = button;

                userButtonPanel.setVisible(true);
                playerScreen.revalidate();
                playerScreen.repaint();
            }

        }
    }

    public void bet() {
        //to be filled by subclasses
    }

    public void addTurnFinisherEvent(TurnFinished turnFinished) {
        this.turnFinished = turnFinished;
        //sets the turnfinsiedh object
    }

    public void finishedTurn(String action) {
        turnFinished.turnFinished(playerNumber, action);
        //emits signal (usualyl that turns over )
    }


    public void play() {
        //for subclasses to be filled
    }

    public int getHandNumber(ArrayList<Card> hand) {
        //gets the number of the hand
        int total = 0;
        int aceCount = 0;
        //loops thru the hand and gets count as well as counts aces
        for (int i = 0; i < hand.size(); i++) {
            total += hand.get(i).cardValue;
            if (hand.get(i).cardValue == 11) {
                aceCount += 1;
            }
        }
        //if over 21 then minus aces until under 22 or no aces left to minus
        for (int i = 0; i < aceCount; i++) {
            if (total > 21) {
                total -= 10;
            } else {
                break;
            }
        }
        return total;
    }


    public int getBetMoney(int pile) {
        //reutnrs the number of moeny bet for a speciefied hand
        int betMoney = 0;
        for (int i = 0; i < bets[pile].size(); i++) {
            betMoney += chipValues[(int) (bets[pile].get(i))];
        }
        return betMoney;
    }


    public void scoreGame(int dealerScore, boolean dealerBusted, boolean dealerBlackJack) {
        //scores a plyers hand
        //scores the players hand and returns a message either win lost or tie
        String message = scoreHand(0, dealerScore, dealerBusted, dealerBlackJack);
        //display this message to the screen
        PlayerGUI scoreMessagePanel = createMessage(message);

        Timer timer = new Timer(1500, new ActionListener() {
            //1.5 second timer to delte this message
            @Override
            public void actionPerformed(ActionEvent e) {
                //delete message
                removeMessage();
                //unhihglight the hand highlighed
                cardPanels[0].setBackground(new Color (0,0,0,0));
                if (cards[1].isEmpty() == false) {
                    //if player split and theres another deck then highlight and score that game
                    cardPanels[1].setSize( (cards[1].size() * 20 +70), cardPanels[1].getHeight());
                    cardPanels[1].setBackground(new Color (0,0,0));
                    Timer timer = new Timer(1500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //wait 1.5 seconds after hihglighting and now score the hand
                            String message = scoreHand(1, dealerScore, dealerBusted, dealerBlackJack);
                            createMessage(message);
                            //send the message
                            Timer timer2 = new Timer(1500, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //wait 1.5 seconds of displaying the message and then delete and unhighlight
                                    removeMessage();
                                    cardPanels[1].setBackground(new Color (0,0,0,0));
                                    //signal this player has finished scoreing
                                    finishedTurn("player scored");
                                }
                            });
                            timer2.setRepeats(false);
                            timer2.start();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else{
                    //singal player has finsihed scoring as donest have another hand to be scored
                    finishedTurn("player scored");
                }
            }
        });
        timer.setRepeats(false);
        timer.start();


    }

    public String scoreHand(int x, int dealerScore, boolean dealerBusted, boolean dealerBlackJack) {
        //scores hand and update player's money
            String returnMessage = "";
            int playerScore = getHandNumber(cards[x]);

            //does player have natural black jack?
            boolean playerBlackJack = false;
            if (cards[x].size() == 2 && playerScore == 21) {
                playerBlackJack = true;
            }

            if (playerScore > 21) {
                //player lost
                returnMessage =  "LOST";
            } else if (playerScore < dealerScore && dealerBusted == false) {
                //player lost, has less than dealer and dealer didnt bust
                returnMessage =  "LOST";
            } else if (playerBlackJack && !dealerBlackJack) {
                money += (int) 2.5 * (getBetMoney(x));
                //player won x2 their bet as they got natural and dealer didnt
                returnMessage =  "WON";
            } else if (playerScore > dealerScore || dealerBusted) {
                money += 2 * (getBetMoney(x));
                returnMessage = "WON";
                //player won nromal score, as higher score than dealer
            } else {
                money += (getBetMoney(x));
                //tie
                returnMessage = "TIE";
            }
            //clear bets
            bets[x].removeAll(bets[x]);
            displayBets();
            generateChips();
            GUI.jFrame.repaint();
            GUI.jFrame.revalidate();
            return  returnMessage;
    }


    public PlayerGUI createMessage(String content){
        //displayes a messsage to the screen
        //creates the jpanel
        PlayerGUI bustedPanel = new PlayerGUI();
        bustedPanel.setName("MESSAGE");
        bustedPanel.setBounds(0,0,screenWidth,screenHeight);
        bustedPanel.setBackground( new Color (0,0,0,150));
        bustedPanel.setLayout(null);
        gameScreenPanel.add(bustedPanel);
        bustedPanel.setVisible(true);
        gameScreenPanel.setComponentZOrder(bustedPanel,0);

        //creates the label
        JLabel busted = new JLabel();
        busted.setBounds((screenWidth-800)/2,(screenHeight-200)/2,800,200);
        busted.setText(content);
        busted.setFont(new Font("ariel",Font.PLAIN, 150));
        busted.setHorizontalAlignment(SwingConstants.CENTER);
        bustedPanel.add(busted);
        busted.setVisible(true);
        //retunr so it can be deleted but threading messed it up
        return bustedPanel;
    }

    public void removeMessage(){
        //loops through to find the panel namned message and deletes it
        for (int i = 0; i < gameScreenPanel.getComponentCount(); i ++){
            try {
                if (gameScreenPanel.getComponent(i).getName().equalsIgnoreCase("MESSAGE")) {
                    gameScreenPanel.remove(i);
                    gameScreenPanel.repaint();
                    gameScreenPanel.revalidate();
                    break;
                }
            } catch (Exception e){

            }
        }
    }

    public void clearCards(){
        //for each hand
        for (int i =0; i < 2; i ++){
            if (playerNumber ==0 && i == 1){
                //if its the dealers "deck" image than don't delte it

            }
            else {
                //othgherwise delte it
                cardPanels[i].removeAll();
                cards[i].removeAll(cards[i]);
                cardPanels[i].setSize(7 * guiWidth / 20, 2 * guiHeight / 5);
            }

        }
        displayCards();
        gameScreenPanel.repaint();
        gameScreenPanel.revalidate();
    }

}