package  gtu.kurt.muaz.hms;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        try {
            Hotel hotel_base = new Hotel();
            Scanner user_input = new Scanner(System.in);
            User user = null;
            System.out.println("Please chose your user kind.");
            System.out.println("1 - Guest\t2 - Receptionist");
            do {

                switch (user_input.nextInt()) {
                    case 1:
                        System.out.println("Name: ");
                        String name = user_input.next();
                        System.out.println("Surname: ");
                        String surname = user_input.next();
                        user = new Guest(name, surname, hotel_base);
                        break;
                    case 2:
                        user = new Receptionist(hotel_base);
                        break;
                    default:
                        System.err.println("There is no such option.");
                        System.err.println("Please select carefully.");
                        System.err.println("1 - Guest\t2 - Receptionist");
                        break;
                }
            } while (user == null);
            while (user.show_options()) ;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }

    }
}