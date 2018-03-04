import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Receptionist extends Guest
{
    private String username;
    private String pw;
    private final String Filename = new String("databases/user.csv");

    public Receptionist(Hotel working)
    {
        super(working);
        if(get_id())
        {
            if(!search_db())
                booked_room = null;
            System.out.println(String.format("Welcome, %s!", get_name()));
        }
        else
        {
            System.err.println("There is no such account.");
            System.exit(0);
        }
        
    }

    private boolean get_id()
    {
        Scanner system_user = new Scanner(System.in);
        System.out.println("Please log in to your account.\n\tUsername: ");
        username = system_user.next();
        System.out.println("\tPassword: ");
        pw = system_user.next();
        return check_user();
    }

    private boolean check_user()
    {
        boolean found = false;
        try
        {
            Scanner searcher = new Scanner(new FileInputStream(Filename));
            while(searcher.hasNextLine() && !found)
            {
                String parser = searcher.nextLine();           
                String [] watch =  parser.split("\"");
                if(watch[1].equals(username) && watch[3].equals(pw))
                {
                    name = watch[5];
                    surname = watch[7];
                    found = true;
                }
            }
            searcher.close();
        }
        catch (FileNotFoundException signal)
        {
            System.err.println("There is no receptionist db. Contact system admin.");
        }
        return found;
    }

    public boolean check_in()
    {
        boolean made = false;
        if(working_on.show(1) > 0)
        {
            int checker = input_no();
            if(checker < working_on.data_set.size() && checker > -1)
            {
                if(working_on.data_set.get(checker).get_situation() == 2)
                    System.out.println("Sorry, this room is already checked in.");
                else if(working_on.data_set.get(checker).get_situation() == 0)
                    System.out.println("Sorry, this room is empty. You can book it.");
                else
                {
                    working_on.data_set.get(checker).set_situation(2);
                    made = true;
                }
            }
            else if(checker == -1)
                System.out.println("Cancelled.");
        }
        else
            System.err.println("There is no booked room.");
        return made;
    }
    
    public boolean check_out()
    {
        boolean made = false;
        if(working_on.show(2) > 0)
        {
            int checker = input_no();
            if(checker < working_on.data_set.size() && checker > -1)
            {
                if(working_on.data_set.get(checker).get_situation() == 0)
                    System.out.println("Sorry, this room is already empty.");
                else if(working_on.data_set.get(checker).get_situation() == 1)
                    System.out.println("Sorry, this room is booked. You can cancel your book.");
                else
                {
                    if(working_on.data_set.get(checker).equals(booked_room))
                        booked_room = null;
                    working_on.data_set.set(checker, new Room(checker));
                    made = true;
                }
            }
            else if(checker == -1)
                System.out.println("Cancelled.");
        }
        return made;        
    }

    public boolean show_options()
    {
        System.out.println("The options that you can do:\n");
        boolean return_val = true;
        System.out.println("0 - Exit.\n1 - Book a room.\n" + (booked_room == null ? "" : "2 - Cancel a room\n") + "3 - Check in.\n4 - Check out");
        switch (input_no())
        {
            case 0:
                System.out.println("Thank you " + get_name() + " " + get_surname() + ".");
                working_on.graceful_ending();
                super.graceful_ending();
                return_val = false;
                break;
            case 1:
                if(booked_room == null)
                {
                    System.out.println("These are empty rooms. Please select a room in them.");
                    book_room();
                }
                else
                    System.err.println("You have already booked a room.");
                break;
            case 2:
                System.out.println("Are you sure to cancel " + booked_room.room_number() + " numbered room?");
                System.out.println("Yes (1) \t No (2)");
                if(input_no() == 1)
                    cancel_room();
                else 
                    System.out.println("Operation terminated.");
                break;
            case 3:
                System.out.println("These are booked rooms. Please select rooms in them.");
                check_in();
                break;
            case 4:
                System.out.println("These are check-inned rooms. Please select rooms in them.");
                check_out();
                break;
            default:
                System.err.println("There is no such operation.");
                break;
        }
        return return_val;
    }
}