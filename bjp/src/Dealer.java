import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dealer extends SuperPlayer {

        public Dealer(int playerNumber, PlayerGUI gameScreenPanel, int money){
            super(playerNumber,gameScreenPanel,money);
        }

        @Override
        public void bet(){
            //dont do anything for betting just signal
            userButtonPanel.setVisible(false);
            finishedTurn("placed bet");
        }

        @Override
        public void play(){
            userButtonPanel.setVisible(false);
            cards[0].get(0).setFaceUp(true);
            displayCards();
            gameScreenPanel.repaint();
            gameScreenPanel.revalidate();
            javax.swing.Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //have a 1.5 second timer for drawing a card
                    if (getHandNumber(cards[0]) < 17){
                        //if its under 17 then hit and and rerun the fun ction
                        addCard(Main.deck.get(0),true,cardPileOn);
                        Main.deck.remove(0);
                        displayCards();
                        gameScreenPanel.repaint();
                        gameScreenPanel.revalidate();
                        play();
                    } else{
                        //if over 16 end dealers turn
                        finishedTurn("played turn");
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }

    }

