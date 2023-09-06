import java.util.Scanner;

public class Main {
    /*
    TODO:
      * Fixa ett mindre hjärndött ai genom att prioritera vissa rutor över andra. Tex flytta prion av rutan som bytes ut till rutan i vilken riktningen går.
     */
    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        int times = 0;
        char PLAYERCHAR, COMPUTERCHAR;
        Opponent comp = new Opponent();
        Square sqr = new Square();
        Player plr = new Player();

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

        while(true) {
            System.out.println(sqr.gridString());



                System.out.println("Select where to place square (Y then X, ex A1): ");
                String nxtPlSqr = scan.nextLine();
                int[] cords = new int[2];
                cords[0] = (int)(nxtPlSqr.toCharArray()[0]-'A');
                cords[1] = (int) (nxtPlSqr.charAt(1)-'1');

                if(sqr.grid[cords[0]][cords[1]] == ' ') {
                    sqr.grid[cords[0]][cords[1]] = PLAYERCHAR;
                    plr.placement(cords);
                    times++;
                    if(times >= 9) {
                        System.out.println("No one has won, Try Again!");
                        return;
                    }
                    if(sqr.checkWinner(cords, plr.grid)) {
                        System.out.println("Player has won!");
                        return;
                    }
                    comp.increaseChance(cords);
                    int[] compMove = comp.makeMove(COMPUTERCHAR,sqr);
                    if(sqr.checkWinner(compMove, comp.grid)) {
                        System.out.println("Computer has won!");
                        return;
                    }
                }









        }

    }
    // skapar en lite snabb player class bara så att den kan förvara info och liknande om spelaren.
    public static class Player extends Grid{

        public Player() {
            super();
        }
        public void placement(int[] cords) {
            this.grid[cords[0]][cords[1]] = 1;
        }

    }
    public static class  Opponent extends Grid{
        // hur ska ait fungera? Min tanke är rutnät av bästa ställen att placera saker
        // precis det är hur ait fungerar, placerar ut et rutnät med vissa värden, och den prioriterar utifrån dessa rutor vart den ska placera.
        int[][] chancegrid = new int[3][3];


        public Opponent() {
            super();
            this.chancegrid[0] = new int[]{2, 1, 2};
            this.chancegrid[1] = new int[]{1, 3, 1};
            this.chancegrid[2] = new int[]{2, 1, 2};
        }
        int[] makeMove(char compSym, Square sqr) {
            int[] cords = selectSquare();
            increaseChance(cords);
            this.grid[cords[0]][cords[1]] = 1;
            sqr.grid[cords[0]][cords[1]] = compSym;
            return(cords);
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

    public static class Square{
        // finns bara 3 olika täcken som kan vara i en square, "o", "x" eller " "
        char[][] grid = new char[3][3];
        char[][][] wingrid = new char[][][] {{ {1, 2}, {3, 6}, {4, 8}}, { {0, 2},  {4, 7}  }, { {0, 1}, {4, 6}, {5, 8} }, { {4, 5}, {0, 6}  }, {{3, 5}, {1, 7}, {0, 8}, {2, 6}}, { {2, 8}, {3, 4} }, { {0, 3}, {2, 4}, {7, 8} }, {{6, 8}, {1, 4} }, { {0, 4}, {2, 5}, {6, 7} }};

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
        public boolean checkWinner(int[] cords, int[][] grid) {
            // man kollar rutorna diagonalt, för då kollar man alla led naturligt.
            // detta är retarded jag kan ju ha en lista av möjliga vinstutfall för de olika spelarna, baserat på rutorna de har.
            /*
            {{A1,A2,A3}, {A1, B2, C3}, {A2, B2, C2}, {A3, B3, C3}, {A3, B2, C1}, {B1, B2, B3}, {C1, C2, C3}}
            söker i varje array för en ruta som matchar, tar bort den ifrån fiendes möjliga vinstutfall. Nej vänta jag kan bara Kolla matcher mellan tagna rutor och
            */
            char[][] sqr = wingrid[((cords[0]*3) + cords[1])];
            for(char[] possible : sqr) {
                char w = 1;
                int[] ch = new int[]{((possible[0]-(possible[0]%3))/3), (possible[0]%3)};

                int[] ch2 = new int[]{((possible[1]-(possible[1]%3))/3), (possible[1]%3)};
                w += grid[ch[0]][ch[1]];
                w += grid[ch2[0]][ch2[1]];
                if(w == 3) {
                    return(true);
                }
            }


            return(false);


        }
    }
}

class Grid {
    int[][] grid = new int[3][3];
    public Grid() {
        initGrid();
    }
    void initGrid() {
        for(int[] y_axis : this.grid) {
            for(int i = 0; i < 3; i++) {
                y_axis[i] = 0;
            }
        }
    }
}