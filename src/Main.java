import java.util.Scanner;

public class Main {
    /*
    TODO:
      * fixa rita ruta med linjer
      * fixa input system för ruta
      * fixa algorytm för att avgöra vinnare
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        boolean running = true;
        char PLAYERCHAR, COMPUTERCHAR;

        Square sqr = new Square();
        // jag vill göra ett tictactoe spel som på det smartaste vägen möjligt kollar vem som vunnit
        // allt annat e bara onödigt,

        System.out.println("Du ska nu få spela TIC TAC TOE, Välj vilken Symbol du vill vara:");
        String tempstr = scan.nextLine();
        // Ser till att man inte kan stoppa in nått äckligt såsom ingeting / mellanslag och att det inte är samma tecken som datorn
        if(tempstr.isEmpty() || tempstr.charAt(0) == ' ') {
            PLAYERCHAR = 'X';
            COMPUTERCHAR = 'O';

        } else {
            PLAYERCHAR = tempstr.charAt(0);
            if(PLAYERCHAR == 'O') {
                COMPUTERCHAR = 'X';
            }
        }

        while(running) {
            System.out.println(sqr.gridString());
            char winner = sqr.checkWinner();
            if(winner != ' ') {
                running = false;
                System.out.println("Winner is: " + winner + "\r\n");
            } else {
                System.out.println("Select where to place square (Y then X, ex A1): ");
                String nxtPlSqr = scan.nextLine();
                sqr.grid[(int)(nxtPlSqr.toCharArray()[0]-'A')][(int) (nxtPlSqr.charAt(1)-'1')] = PLAYERCHAR;

            }






        }

    }
    public static class Opponent {
        // hur ska ait fungera? Min tanke

    }

    public static class Square {
        // finns bara 3 olika täcken som kan vara i en square, "o", "x" eller " "
        char[][] grid = new char[3][3];
        public Square() {
            initSquare();
        }
        void initSquare(){
            for(char[] y_axis : grid) {
                for(int i = 0; i < 3; i++) {
                    y_axis[i] = ' ';
                }
            }
        }
        public String gridString() {
            String grdStr = "";
            char a = 'A';
        // top part
            grdStr += "    1   2   3  \r\n";
            for(int i = 0; i < 3; i++) {
                grdStr += "  -------------\r\n";
                grdStr +=  (a) + " | " + grid[i][0] + " | "+ grid[i][1] + " | "+ grid[i][2] + " |\r\n";
                a++;

            }
            grdStr += "  -------------";
            return(grdStr);
        }
        // kollar vinnaren av en viss ruta
        public char checkWinner() {
            return(' ');
        }
    }
}