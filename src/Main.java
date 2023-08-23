public class Main {
    /*
    TODO:
      * fixa rita ruta med linjer
      * fixa input system för ruta
      * fixa algorytm för att avgöra vinnare
     */
    public static void main(String[] args) {
        Square SQUARE = new Square();
        // jag vill göra ett tictactoe spel som på det smartaste vägen möjligt kollar vem som vunnit
        // allt annat e bara onödigt,

    }
    // denna ska rita rutan

    public static class Square {
        // finns bara 3 olika täcken som kan vara i en square, "o", "x" eller " "
        char[][] grid = new char[3][3];
        public Square() {
            initSquare();
        }
        void initSquare(){
            for(char[] y_axis : grid) {
                for(char x_axis : y_axis) {
                    x_axis = ' ';
                }
            }
        }
        public String gridString() {
            String grdStr = "";


            return(grdStr);
        }
        // kollar vinnaren av en viss ruta
        public char checkWinner() {
            return(' ');
        }
    }
}