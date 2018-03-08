package  gtu.kurt.muaz.hms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Extends from Guest. Can do everything that guest does. Also does check-in & check-out operations.
 */
public class Receptionist extends Guest
{
    /**
     * Id input.
     */
    private String username;

    /**
     * pw input.
     */
    private String pw;

    /**
     * Main db file for user id/pw pairs.
     */
    private final String Filename = "src/databases/user.csv";

    /**
     * Constructor. Guests one parameter constructer called.
     * Checks user id/ps. If found, checks guest db for booked/check-inned room.
     * Catches FilenotfoundException and exits.
     * @param working Currently working hotel db.
     */
    public Receptionist(Hotel working)
    {
        super(working);

        try {
            if (get_id())
            {
                if (!search_db())
                    booked_room = null;
                System.out.println(String.format("Welcome, %s!", get_name()));
            }
            else
            {
                System.err.println("There is no such account.");
                System.exit(0);
            }
        }
        catch (FileNotFoundException signal)
        {
            System.err.println("There is no receptionist db. Contact system admin.");
        }
    }

    /**
     * Gets id/pw from system user. Calls check_user method.
     * @return  Check_user method's return value.
     * @throws FileNotFoundException for user_db
     */
    private boolean get_id() throws FileNotFoundException
    {
        Scanner system_user = new Scanner(System.in);
        System.out.println("Please log in to your account.\n\tUsername: ");
        username = system_user.next();
        System.out.println("\tPassword: ");
        pw = system_user.next();
        return check_user();
    }

    /**
     * Opens the db file for given id, pw. If there is not a id, pw pair in db: blocks the user to log in system.
     * @return True, there is  a id/pw pair. False, there is not.
     * @throws FileNotFoundException for user_db.
     */
    private boolean check_user() throws FileNotFoundException
    {
        boolean found = false;
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
        return found;
    }

    /**
     * Check in ability of the Receptionist. Calls working hotel's show operation for booked rooms.
     *  Then updates shown rooms with checked-in information.
     * @return True: operation made. False: otherwise.
     */
    private boolean check_in()
    {
        boolean made = false;
        if(working_on.show(1) > 0)
        {
            int checker = input_no();
            if(checker < working_on.size() && checker > -1)
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

    /**
     * Check out ability of the Receptionist. Calls working hotel's show operaiton for check inned rooms.
     * Then updates shown rooms with new empty rooms.
     * If the filler user is this, updates booked room with null.
     * @return True: operation made. False: otherwise.
     */
    private boolean check_out()
    {
        boolean made = false;
        if(working_on.show(2) > 0)
        {
            int checker = input_no();
            if(checker < working_on.size() && checker > -1)
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

    /**
     * Show all possible options to user.
     * 0 - Exit (False situation occures) Gracefully ending operations called. Room infos will update. User infos will update.
     * 1 - Book a Room : Calls book room function.
     * 2 - Check in: Check in for a booked room in hotel.
     * 3 - Check out: Check out for check inned rooms.
     * 4 - Cancel Room : If user booked a room. The opration will shown to user and works to cancel the booked room. If not then will hidden.
     * Inputs N/[0, 5) will be unknown and ignored.
     * @return True in (1, 2, 3, 4). False only in (0)
     * @throws FileNotFoundException for room_db.
     */
    public boolean show_options() throws FileNotFoundException
    {
        System.out.println("The options that you can do:\n");
        boolean return_val = true;
        System.out.println("0 - Exit.\n1 - Book a room.\n" + "2 - Check in.\n3 - Check out\n" + (booked_room == null ? "" : "4 - Cancel a room"));
        switch (input_no()) {
            case 0:
                System.out.println("Thank you " + get_name() + " " + get_surname() + ".");
                working_on.graceful_ending();
                super.graceful_ending();
                return_val = false;
                break;
            case 1:
                if (booked_room == null) {
                    System.out.println("These are empty rooms. Please select a room in them.");
                    book_room();
                } else
                    System.err.println("You have already booked a room.");
                break;
            case 2:
                System.out.println("These are booked rooms. Please select rooms in them.");
                check_in();
                break;
            case 3:
                System.out.println("These are check-inned rooms. Please select rooms in them.");
                check_out();
                break;
            case 4:
                if(booked_room != null)
                {
                    System.out.println("Are you sure to cancel " + booked_room.room_number() + " numbered room?");
                    System.out.println("Yes (1) \t No (2)");
                    if (input_no() == 1) {
                        cancel_room();
                    } else
                        System.out.println("Operation terminated.");
                    break;
                }
            default:
                System.err.println("There is no such operation.");
                break;
        }
        return return_val;
    }
}