package Marketplace;

import java.util.Scanner;

public class Marketplace {
    public static void main(String[] args) {
        boolean logedin = false; int op; Boolean Run = true;
        Scanner s = new Scanner(System.in);
        while (Run) {
            Pages.Home(logedin);
            op = s.nextInt();
            if (logedin) {
                switch (op) {
                    case 1:
                    Pages.Search();
                    s.nextLine();
                    String Query = s.nextLine();
                    break;
                    case 4:
                    Pages.Logout();
                    logedin = false;
                    break;
                    case 5:
                    Run = false;
                    break;
                    default:
                    break;
                }
            } else {
                if (op == 1) {
                    String[] credentials = Pages.Login(s);

                    Pages.Status(logedin);
                } else if (op == 2) {
                    Run = false;
                    break;
                }
                
            }
        }
        s.close();
    }
}
