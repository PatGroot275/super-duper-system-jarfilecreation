import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class Player extends SuperPlayer {


    public Player(int playerNumber, PlayerGUI gameScreenPanel, int money) {
        super(playerNumber, gameScreenPanel, money);
    }

    @Override
    public void bet() {
        //make the onnly button visible the "next" button
        boolean[] buttonsVisible = {true, false, false, false, false};
        buttonsVisbile = buttonsVisible;
        displayUserButtons();
        generateChips();
        //hihglight player
        gameScreenPanel.setComponentZOrder(playerScreen, 0);
        betButtonPanel.setVisible(true);
        userButtonPanel.setVisible(true);
        userButtonPanel.getComponent(0).setEnabled(false);
        connectButtons();
    }

    @Override
    public void play() {
        gameScreenPanel.setComponentZOrder(playerScreen,0);
        int betMoney = getBetMoney(0);
        boolean[] buttonsVisible;
        //if player has enough money left over to double down or split  then show double down button else onyl show stand/hit butotn
        if (money >= betMoney){
            buttonsVisible = new boolean[] {false, true, true, true, true};
        }
        else{
            buttonsVisible = new boolean[] {false, true, true, false, false};
        }
        //if the player doesn't have the same 2 cards then dsiable split button
        if (cards[0].get(0).cardNumber != cards[0].get(1).cardNumber){
            buttonsVisible[4] = false;
        }
        //display the btutons
        buttonsVisbile = buttonsVisible;
        displayUserButtons();
        connectButtons();

    }



    public void connectButtons(){
        //connect all the buttons in the button panel  to an action listner
        for (int i = 0; i < userButtonPanel.getComponentCount(); i++) {
            JButton button = (JButton) userButtonPanel.getComponent(i);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //if next pressed
                    if (button.getName().equalsIgnoreCase("next")) {
                        //hide buttons, signal next turn
                        userButtonPanel.setVisible(false);
                        betButtonScreen.setVisible(false);
                        if (playerNumber == 2) {
                            betButtonPanel.setVisible(false);
                        }
                        finishedTurn("placed bet");
                    } else if( button.getName().equalsIgnoreCase("split")){
                        //if split pressed
                        //mmove the 2nd card to the 2nd hand and delete it from the first hand
                        Card card = cards[0].get(1);
                        cards[1].add(card);
                        cards[0].remove(1);
                        displayCards();
                        //add the bets for the split hand
                        int betMoney = 0;
                        for (int i = 0; i < bets[0].size(); i++){
                            bets[1].add(bets[0].get(i));
                            betMoney +=   chipValues[  (int)bets[0].get(bets[0].size()-1-i) ];
                        }
                        money-=betMoney;
                        displayBets();
                        generateChips();
                        //dispable the split button now
                        boolean[] buttonsVisible;
                        betMoney = getBetMoney(1);
                        //disable the double down depnding on how much money player still has
                        if (money >= betMoney){
                            buttonsVisible = new boolean[] {false, true, true, true, false};
                        }
                        else{
                            buttonsVisible = new boolean[] {false, true, true, false, false};
                        }
                        buttonsVisbile = buttonsVisible;
                        displayUserButtons();
                        connectButtons();
                        gameScreenPanel.repaint();
                        gameScreenPanel.revalidate();

                    } else if (  button.getName().equalsIgnoreCase("hit") || ( button.getName().equalsIgnoreCase("double down") )  ){
                        //if hti button pressed or double down button pressed
                        if ( button.getName().equalsIgnoreCase("double down")){
                            //double the bet pile if its double down
                            int betMoney = 0;
                            int length = bets[cardPileOn].size();
                            for (int i = 0; i < length; i++){
                                bets[cardPileOn].add(bets[cardPileOn].get(i));
                                betMoney +=   chipValues[  (int) (bets[cardPileOn].get(i)) ];
                            }
                            money-=betMoney;
                            displayBets();
                            generateChips();
                        }

                        //draw a card
                        addCard(Main.deck.get(0),true,cardPileOn);
                        Main.deck.remove(0);
                        displayCards();
                        gameScreenPanel.repaint();
                        gameScreenPanel.revalidate();

                        //if bust display busted message
                        if (getHandNumber(cards[cardPileOn]) > 21){
                                userButtonPanel.setVisible(false);
                                gameScreenPanel.setComponentZOrder(playerScreen,1);

                                PlayerGUI bustedPanel = createMessage("BUSTED");
                               // gameScreenPanel.setComponentZOrder(busted,0);
                                playerScreen.repaint();
                                playerScreen.revalidate();
                                Timer timer = new Timer(1500, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        System.out.println("time off");
                                        gameScreenPanel.remove(bustedPanel);
                                        gameScreenPanel.repaint();
                                        gameScreenPanel.revalidate();
                                        //go to the next pile after the busted messsage shows
                                        nextPile();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                        } else {
                            if (button.getName().equalsIgnoreCase("double down")) {
                                nextPile();
                                //go to next hand  if doubled down
                            }


                        }

                    } else if( button.getName().equalsIgnoreCase("stand")) {
                        // if stand
                        if (cardPileOn == 1 || cards[1].isEmpty()){
                            //if didn't split then signal next turn
                            userButtonPanel.setVisible(false);
                            gameScreenPanel.repaint();
                            gameScreenPanel.revalidate();
                            finishedTurn("played turn");
                        } else{
                            //if did split and on first hand  go to next hand
                            cardPileOn +=1;
                        }
                }

                }
            });
        }
    }

    public void nextPile(){
        if (cardPileOn == 1 || cards[1].isEmpty()) {
            //if its already on last hand or the next hand is empty then end turn
            userButtonPanel.setVisible(false);
            finishedTurn("played turn");
        } else {
            //go to next hand and update double down button based on the player's money
            cardPileOn += 1;
            //if have money
            boolean[] buttonsVisible;
            int betMoney = getBetMoney(1);
            if (money >= betMoney){
                buttonsVisible = new boolean[] {false, true, true, true, false};
            }
            else{
                buttonsVisible = new boolean[] {false, true, true, false, false};
            }
            buttonsVisbile = buttonsVisible;
            displayUserButtons();
            connectButtons();
            userButtonPanel.setVisible(true);
            gameScreenPanel.setComponentZOrder(playerScreen,0);
            gameScreenPanel.repaint();
            gameScreenPanel.revalidate();
        }
    }
}