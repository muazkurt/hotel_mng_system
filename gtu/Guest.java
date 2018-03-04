import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Guest extends User
{
    private final String guest_db = new String("databases/guest.csv");
    private boolean foundin_file = false;
    protected Guest(Hotel working)
    {
        working_on = working;
    }

    public Guest(String name, String surname, Hotel working)
    {
        this.name = name;
        this.surname = surname;
        working_on = working;
        if(search_db())
        {
            System.out.println("Welcome back, " + get_name() + "!");
        }
        else
            booked_room = null;
    }

    
    public Room get_room() 
    {
        return booked_room;
    }


    public boolean search_db()
    {
        try 
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
        }
        catch (Exception e) 
        {
            System.err.println("There is no db file for user info.");
        }
        return foundin_file;
    }

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

    protected void graceful_ending()
    {
        if(foundin_file)
        {
            try {
                Scanner reader = new Scanner(new FileInputStream(guest_db));
                LinkedList<String> input = new LinkedList<>();
                while(reader.hasNextLine())
                    input.add(reader.nextLine());
                reader.close();

                PrintWriter output = new PrintWriter(guest_db);
                while(input.size() > 0)
                {
                    String temp_tostr = input.pop();
                    String [] parsed = temp_tostr.split("\"");
                    if(parsed[1].equals(get_name()) && parsed[3].equals(get_surname()))
                    {
                        if(booked_room != null)
                            output.println(toString());
                    }
                    else
                        output.println(temp_tostr);
                }
                output.close();
            }
            catch (Exception e) {
                System.err.println(e.getCause().getMessage());
            }
        }
        else if(booked_room != null)
        {
            try {                
                BufferedWriter output = new BufferedWriter(new FileWriter(guest_db, true));
                output.append(toString() + "\n");
                output.close();
            } catch (Exception e) {
                System.err.println(e.getCause().getMessage());
            }
        }
    }


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

    public boolean show_options()
    {
        System.out.println("The options that you can do:\n");        
        boolean return_val = true;
        System.out.println("0 - Exit.\n1 - Book a room.\n"+ (booked_room == null ? "" : "2 - Cancel a room"));
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
                }
                break;
                default:
                    System.err.println("There is no such operation.");
                break;
        }
        return return_val;
    }

    public String toString()
    {
        return String.format("\"%s\", \"%s\", \"%d\"", get_name(), get_surname(), get_room().room_number());
    }
}