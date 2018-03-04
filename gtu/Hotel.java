import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Hotel
{
    public final ArrayList<Room> data_set;
    public final String db_rooms = new String("databases/room.csv");
    public Hotel()
    {
        data_set = new ArrayList<Room>();
        try 
        {
            get_rooms();        
        }
        catch (Exception e) 
        {
            System.err.println("WTF " + e.getCause().toString() + "\n" + e.getMessage());
            
        }
    }

    private void get_rooms() throws FileNotFoundException
    {
        Scanner room_all = new Scanner(new FileInputStream(db_rooms));
        room_all.nextLine();
        while(room_all.hasNextLine())
        {
            String [] searcher = (room_all.nextLine()).split("\"");
            if(!searcher[7].equals("empty"))
                data_set.add(new Room(searcher[3], searcher[5], 
                                                (searcher[7].equals("booked") ? 1 : 2), 
                                                Integer.valueOf(searcher[1])));
            else
                data_set.add(new Room(Integer.valueOf(searcher[1])));
        }
        room_all.close();
    }

    public void graceful_ending()
    {
        try
        {
            PrintWriter output = new PrintWriter(db_rooms);
            output.println(toString());
            output.close();
        }
        catch(FileNotFoundException e)
        {
            System.err.println(e.getMessage().toString());
        }
    }

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
        System.out.println("\n(-1 for cancel operation)");
        return ret_val;
    }

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