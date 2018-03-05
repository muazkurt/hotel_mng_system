package  gtu.kurt.muaz.hms;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Guest class. Parent of Receptionist, Extends from User.
 * Books room, cancels room.
 */
public class Guest extends User
{
    /**
     * Main guest db file.
     */
    private final String guest_db = "src/databases/guest.csv";

    /**
     * True: user found in db. This means user have had booked a room or checked a room.
     * (Booking or checked in will declared by Hotel info.)
     */
    private boolean foundin_file = false;

    /**
     * Called only from subclass Receptionist. Gets only working hotel info, and anything will be made by Receptionist.
     * @param working Currently working hotel information.
     */
    protected Guest(Hotel working)
    {
        working_on = working;
    }

    /**
     * Guests public constructer.  Gets name, surname, working hotel infos.
     * Checks for db for if name, surname are exist in db. True, booked_room will be updated as declared in db.
     * If there is not, booked_room is null.
     * @param name  Person name.
     * @param surname Person surname.
     * @param working Currently working hotel.
     */
    public Guest(String name, String surname, Hotel working) {
        this.name = name;
        this.surname = surname;
        working_on = working;
        try
        {
            if (search_db())
            {
                System.out.println("Welcome back, " + get_name() + "!");
            }
            else
                booked_room = null;
        }
        catch (FileNotFoundException e)
        {
            System.err.println("There is no db file for user info.");
        }
    }

    /**
     * Opens the db and searches all the lines for given name&surname.
     * If there is one, foundin_file and booked_room will updated.
     * For searching, readen information will parsed by (") marks.
     * Then compares every meaningfull one compared by name or surname.
     * @return True if user found, False otherwise.
     * @throws FileNotFoundException for user_db.
     */
    public boolean search_db() throws FileNotFoundException
    {
        Scanner file = new Scanner(new FileInputStream(guest_db));
        file.nextLine();
        while(file.hasNextLine() && !foundin_file)
        {
            String [] guest_info = file.nextLine().split("\"");
            if(guest_info[1].equals(this.get_name()) && guest_info[3].equals(this.get_surname()))
            {
                booked_room = working_on.data_set.get(Integer.valueOf(guest_info[5]));
                foundin_file = true;
            }
        }
        file.close();
        return foundin_file;
    }

    /**
     * Books a room, If user didn't book a room. Updates booked_room with new booked room.
     */
    public void book_room()
    {
        if(working_on.show(0) > 0)
        {
            int temp = input_no();
            if((temp < working_on.data_set.size() && temp > -1) && (working_on.data_set.get(temp).get_situation() == 0))
            {
                working_on.data_set.set(temp, new Room(this, 1, temp));
                booked_room = working_on.data_set.get(temp);
            }
            else if (temp == -1)
                System.out.println("Cancelled.");
            else
                System.out.println("Please select room correctly.");
        }
    }

    /**
     * Opens user db and updates with new information.
     * If user didn't book a room and haven't found in db, there is no necessarity for update file.
     * If user found in file but cancels it's room, then will deleted from db.
     * If user found in file and booked another room, db will updated.
     * If user didn't found but booked a room, then it will append to the end of tile.
     * @throws FileNotFoundException if guest_db didn't found.
     */
    protected void graceful_ending() throws FileNotFoundException
    {
        LinkedList<String> input = new LinkedList<>();
        input.add("\"Name\", \"Surname\", \"Room no\"");
        for(int i = 0; i < working_on.data_set.size(); ++i)
        {
            if(working_on.data_set.get(i).get_situation() > 0)
                input.add("\"" + working_on.data_set.get(i).get_name() + "\", \"" + working_on.data_set.get(i).get_surname() + "\", \"" + working_on.data_set.get(i).room_number() + "\"");
        }
        PrintWriter output = new PrintWriter(guest_db);
        while(input.size() > 0)
            output.println(input.pop());
        output.close();
    }


    /**
     * Cancels the room. If user booked a room then can do this operation.
     * If there is not a booked room. User will blocked to do this.
     */
    public void cancel_room()
    {
        if(get_room() == null)
            System.out.println("You haven't booked a room yet.");
        else
        {
            working_on.data_set.set(get_room().room_number(), new Room(get_room().room_number()));
            booked_room = null;
            System.out.println("Your cancel operation made.");
        }
    }

    /**
     * Show all possible options to user.
     * 0 - Exit (False situation occures) Gracefully ending operations called. Room infos will update. User infos will update.
     * 1 - Book a Room : Calls book room function.
     * 2 - Cancel Room : If user booked a room. The opration will shown to user and works to cancel the booked room. If not then will hidden.
     * Input N/[0,k) will be ignored. (k = 2 if there is no booked room, k = 3 if booked a room.)
     * @return False only in exit situation occures. Otherwise true.
     * @throws FileNotFoundException for U
     */
    public boolean show_options() throws FileNotFoundException {
       System.out.println("The options that you can do:\n");
        boolean return_val = true;
        System.out.println("0 - Exit.\n1 - Book a room."+ (booked_room == null ? "" : "\n2 - Cancel a room"));
        switch (input_no())
        {
            case 0:
                System.out.println("Thank you " + get_name() + " " + get_surname() + ".");
                working_on.graceful_ending();
                graceful_ending();
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
                if(booked_room != null)
                {
                    System.out.println("Are you sure to cancel " + booked_room.room_number() + " numbered room?");
                    System.out.println("Yes (1) \t No (2)");
                    if(input_no() == 1)
                        cancel_room();
                    else
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