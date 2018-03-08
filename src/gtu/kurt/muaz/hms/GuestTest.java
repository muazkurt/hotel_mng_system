package gtu.kurt.muaz.hms;

import org.junit.Assert;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class GuestTest {
    private static final String filename_db = "src/databases/guest.csv";
    private static final String filename_booking = "tests/booking_test_input";
    private static final String filename_cancel = "tests/cancel_room_act_like_booked";
    private static final String filename_showopt_ = "tests/show_opt_guest_output_expected";
    private static final String filename_showoptI = "tests/show_opt_guest_test";

    private static final String name = "muaz";
    private static final String surname = "kurt";

    @org.junit.Test
    public void search_db() throws FileNotFoundException
    {
        Guest tries = new Guest(name, surname, new Hotel());
        boolean foundin_file = false;
        Scanner file = new Scanner(new FileInputStream(filename_db));
        file.nextLine();
        while(file.hasNextLine() && !foundin_file)
        {
            String [] guest_info = file.nextLine().split("\"");
            if(guest_info[1].equals(name) && guest_info[3].equals(surname))
                foundin_file = true;
        }
        file.close();
        Assert.assertEquals(foundin_file, tries.search_db());
    }

    @org.junit.Test
    public void book_room() throws FileNotFoundException
    {
        Guest tries = new Guest(name, surname, new Hotel());
        boolean [] expected = new boolean[tries.working_on.size()];
        for(int i = 0; i < tries.working_on.size(); ++i)
        {
            if(tries.working_on.get(i).get_situation() == 0)
                expected[i] = true;
            else
                expected[i] = false;
        }
        Scanner filein = new Scanner(new FileInputStream(filename_booking));
        ByteArrayInputStream in;
        boolean [] result =  new boolean[tries.working_on.size()];
        int i = 0;
        do {
            in = new ByteArrayInputStream(filein.nextLine().getBytes());
            System.setIn(in);
            result[i] = tries.book_room();
            ++i;
        } while(i < tries.working_on.size());
        System.setIn(System.in);
        filein.close();
        Assert.assertArrayEquals(expected, result);

    }

    @org.junit.Test
    public void graceful_ending() throws FileNotFoundException
    {
        Guest tries = new Guest(name, surname, new Hotel());
        LinkedList<String> expected = new LinkedList<>();
        expected.add("\"Name\", \"Surname\", \"Room no\"");
        for(int i = 0; i < tries.working_on.size(); ++i)
            if(tries.working_on.get(i).get_situation() > 0)
                expected.add("\"" + tries.working_on.get(i).get_name() + "\", \"" + tries.working_on.get(i).get_surname() + "\", \"" + tries.working_on.get(i).room_number() + "\"");
        Scanner file = new Scanner(new FileInputStream(filename_db));
        tries.graceful_ending();
        LinkedList<String> actual = new LinkedList<>();
        while(file.hasNextLine())
            actual.add(file.nextLine());
        file.close();
        Assert.assertEquals(expected,actual);
    }

    @org.junit.Test
    public void cancel_room() throws FileNotFoundException
    {
        Scanner file = new Scanner(new FileInputStream(filename_cancel));
        String temp = file.nextLine();
        file.close();
        Guest new_tester = new Guest(temp.split("\"")[1], temp.split("\"")[3], new Hotel());
        new_tester.cancel_room();
        Assert.assertEquals(null, new_tester.booked_room);
    }

    @org.junit.Test
    public void show_options()  throws  FileNotFoundException
    {
        Scanner out = new Scanner(new FileInputStream(filename_showopt_));
        String expected = "";
        while (out.hasNextLine())
        {
            expected += out.nextLine() + "\n";
        }
        out.close();


        Guest trier = new Guest(name, surname, new Hotel());
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PrintStream constant = System.out;
        InputStream constant2 = System.in;

        System.setOut(new PrintStream(actual));
        System.setIn(new FileInputStream(filename_showoptI));
        Scanner x = new Scanner(System.in);

        int q = x.nextInt();
        System.err.println(q + "\n " + c.aviable);
/*
        while (trier.show_options())
            System.err.println("fuqq");

        System.setIn(constant2);
        System.setOut(constant);



        Assert.assertEquals(expected, actual.toString());
*/
    }
}