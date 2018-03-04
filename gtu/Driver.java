import java.util.Scanner;
public class Driver
{
    public static void main(String[] args)
    {
        Hotel hotel_base = new Hotel();
        Scanner user_input = new Scanner(System.in);
        User user;
        System.out.println("Please chose your user kind.");
        System.out.println("1 - Guest\t2 - Receptionist");
        int input = user_input.nextInt();
        switch (input) {
            case 1:
                System.out.println("Name: ");
                String name = user_input.next();
                System.out.println("Surname: ");
                String surname = user_input.next();
                user = new Guest(name, surname, hotel_base);
                break;
            default:
                user = new Receptionist(hotel_base);
                break;
        }
        while(user.show_options());
        
    }
}