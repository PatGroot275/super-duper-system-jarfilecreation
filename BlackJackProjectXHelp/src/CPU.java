import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class CPU extends SuperPlayer{

    public int chipBetOn = 0;
    public int betMoney = 0;

    public CPU(int playerNumber, PlayerGUI gameScreenPanel, int money) {
        super(playerNumber, gameScreenPanel, money);
    }


    @Override
    public void bet() {
        chipBetOn = 6;
        generateChips();
        gameScreenPanel.setComponentZOrder(playerScreen, 0);
        gameScreenPanel.repaint();
        gameScreenPanel.revalidate();
        betButtonScreen.setVisible(false);
        Random random = new Random();
        //pick random amoujnt of money to bet anywhere from 3% to 15% of the CPU's money
        betMoney  = (int) (money * random.nextDouble(0.03,0.15));
        betMoney = Math.max(5, betMoney);

        //starts a timer that will bet each type of chip when it goes off
        for (int i =6; i >-1; i --) {
            Timer timer = new Timer(i * 500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    betChips();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }


    }


    public void betChips(){
        //bets the most it can of the chip speciefid
        int chipsBetting = betMoney / chipValues[chipBetOn];
        betMoney -= (chipsBetting * chipValues[chipBetOn]);

        generateChips();

        //bet the chips
        if( chipsBetting != 0){
            for (int i = 0; i < chipsBetting; i++) {
                bets[0].add(chipBetOn);
            }
        }
        //dispaly them
        chipBetOn -=1;
        displayBets();
        betButtonScreen.setVisible(false);
        gameScreenPanel.repaint();
        gameScreenPanel.revalidate();
        if (chipBetOn==-1){
            //if its down to the last chip to display then signal end of turn
            finishedTurn("placed bet");
        }
    }



    @Override
    public void play(){
        removeMessage();
        Random random = new Random();
        //craetes a lowerbound that it will bet until it is over it
        final int LOWERBOUND = random.nextInt(14,20);
        userButtonPanel.setVisible(false);
        cards[0].get(0).setFaceUp(true);
        displayCards();
        gameScreenPanel.repaint();
        gameScreenPanel.revalidate();
        //create a timer for it drawing cards
        javax.swing.Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if its less than the lowerbound then draw a card  and play again
                if (getHandNumber(cards[0]) < LOWERBOUND){
                    addCard(Main.deck.get(0),true,cardPileOn);
                    Main.deck.remove(0);
                    displayCards();
                    if (getHandNumber(cards[0]) >21){
                        createMessage("BUSTED");
                    }
                    gameScreenPanel.repaint();
                    gameScreenPanel.revalidate();
                    //call function again
                    play();
                } else{
                    //if over lowerbound then end turn
                    finishedTurn("played turn");
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }



}
