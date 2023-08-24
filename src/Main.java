import java.util.Scanner;

public class Main {
    /*
    TODO:
      * fixa algorytm för att avgöra vinnare
      * fixa ett ai som kan agera som motståndare
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        boolean running = true;
        char PLAYERCHAR, COMPUTERCHAR;
        Opponent comp = new Opponent();
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
            } else {
                COMPUTERCHAR = 'O';
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
                int[] cords = new int[2];
                cords[0] = (int)(nxtPlSqr.toCharArray()[0]-'A');
                cords[1] = (int) (nxtPlSqr.charAt(1)-'1');
                if(sqr.grid[cords[0]][cords[1]] == ' ') {
                    sqr.grid[cords[0]][cords[1]] = PLAYERCHAR;
                    comp.increaseChance(cords);
                    comp.makeMove(COMPUTERCHAR,sqr);
                } else {
                    break;
                }


            }






        }

    }
    public static class Opponent {
        // hur ska ait fungera? Min tanke är rutnät av bästa ställen att placera saker
        int[][] chancegrid = new int[3][3];

        public Opponent() {
            this.chancegrid[0] = new int[]{2, 1, 2};
            this.chancegrid[1] = new int[]{1, 3, 1};
            this.chancegrid[2] = new int[]{2, 1, 2};
        }
        void makeMove(char compSym, Square sqr) {
            int[] cords = selectSquare();
            increaseChance(cords);
            sqr.grid[cords[0]][cords[1]] = compSym;
        }
        void increaseChance(int[] cord) {
            // sätter så att den rutan aldrig kan vara vald av ait eftrsom den är 0, och alla rutor har ett högre värde än 0.
            this.chancegrid[cord[0]][cord[1]] = 0;
            int[] startcords = new int[] {(cord[0]-1), (cord[1]-1)};
            // man kan ha som en forloop som går i en circel runt hela kordinaten, och om kordinaten är 2 eller under 0 så väljer den en annan ruta
                for(int y = 0; y < 3; y++) {
                    if (startcords[0] + y >= 0 && startcords[0] + y < 3) {
                        for (int x = 0; x < 3; x++) {
                            if ((startcords[1] + x) >= 0 && startcords[1] + x < 3) {
                                if (this.chancegrid[startcords[0] + y][startcords[1] + x] != 0) {
                                    this.chancegrid[startcords[0] + y][startcords[1] + x]++;
                                }
                            }
                        }
                    }
                }
        }

        int[] selectSquare() {
            int highest = 0;
            int[] chords = new int[] {0,0};
            for(int y = 0; y < 3; y++) {
                for(int x = 0; x < 3; x++) {
                    int cncInt = this.chancegrid[y][x];
                    if(cncInt > highest) {
                        highest = cncInt;
                        chords = new int[]{y, x};
                    }
                }
            }
            return(chords);
        }

    }

    public static class Square {
        // finns bara 3 olika täcken som kan vara i en square, "o", "x" eller " "
        char[][] grid = new char[3][3];
        public Square() {
            initSquare();
        }
        void initSquare(){
            for(char[] y_axis : this.grid) {
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
                grdStr +=  (a) + " | " + this.grid[i][0] + " | "+ this.grid[i][1] + " | "+ this.grid[i][2] + " |\r\n";
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