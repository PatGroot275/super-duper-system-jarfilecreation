public class Card {
    //file path for the gui to refrence
    String filePath;
    //cards number to refrence for splits
    int cardNumber = 0;

    //save these filepaths to toggle
    String faceDownPath = "CardImages/b1fv.png";
    String faceUpPath= "CardImages/0";

    //actual card value
    int cardValue;
    boolean faceUp = true;

    public String getFilePath() {
        return filePath;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
        //toggle filepath depending on face up or down
        if (faceUp){
            filePath = faceUpPath;
        }
        else {
            filePath = faceDownPath;
        }
    }

    public Card (int cardNumber){
        this.cardNumber = cardNumber %13;
        faceUpPath += (cardNumber) +".png";
        filePath = faceUpPath;
        cardValue = Math.min(cardNumber%13,10);
        //.clamp(cardNumber%13,1,10); (works in home vesrion not older ones
        if (cardValue<2){
            //for aces or kings
            cardValue+=10;
        }
    }
}
