package  gtu.kurt.muaz.hms;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class for management system.
 */
public class Hotel
{
    /**
     * Room's and their infos.
     */
    public final ArrayList<Room> data_set;

    /**
     * File db path.
     */
    private final String db_rooms = "src/databases/room.csv";

    /**
     * Constructer. Creates a ArrayList and fills it by getrooms method.
     * @throws FileNotFoundException
     */
    public Hotel() throws FileNotFoundException
    {
        data_set = new ArrayList<Room>();
        get_rooms();
    }

    /**
     * Opens room_db and gets all rooms with their situaitons.
     * @throws FileNotFoundException If there is not a file in declared path.
     */
    private void get_rooms() throws FileNotFoundException
    {
        Scanner room_all = new Scanner(new FileInputStream(db_rooms));
        room_all.nextLine();
        while(room_all.hasNextLine())
        {
            String [] searcher = (room_all.nextLine()).split("\"");
            if(!searcher[7].equals("empty"))
                data_set.add(new Room(searcher[3], searcher[5], (searcher[7].equals("booked") ? 1 : 2), Integer.valueOf(searcher[1])));
            else
                data_set.add(new Room(Integer.valueOf(searcher[1])));
        }
        room_all.close();
    }

    /**
     * Opens file and updates with current changes.
     * @throws FileNotFoundException If there is no file in declared path.
     */
    public void graceful_ending() throws FileNotFoundException
    {
        PrintWriter output = new PrintWriter(db_rooms);
        output.println(toString());
        output.close();
    }

    /**
     * Prints all rooms with declared situation.
     * 0 - empty
     * 1 - booked
     * 2 - check inned
     * @param sit_checker decleration of waiting situation
     * @return Counter for how many room in this situation.
     */
    public int show(int sit_checker)
    {
        int ret_val = 0;
        for(Room temp : data_set)
        {
            if(temp.get_situation() == sit_checker)
            {
                ++ret_val;
                System.out.print(temp.room_number() + " - ");
            }
        }
        if(ret_val > 0)
            System.out.println("\n(-1 for cancel operation)");
        return ret_val;
    }

    /**
     * Makes all its rooms printable to csv format.
     * Calls each room's toString method and updates return string with them line by line.
     * @return printable string for csv file.
     */
    public String toString()
    {
        String filler = new String("\"RoomId\", \"Name\", \"Surname\", \"Situation\"");
        for(Room temp : data_set)
        {
            filler += "\n" + temp.toString();
        }
        return filler;
    }
}