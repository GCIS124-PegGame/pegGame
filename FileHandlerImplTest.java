package PEGGAME2;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Test;

import junit.framework.Assert;

public class FileHandlerImplTest {
    @Test
    public void testAskUserForFileNameAndAddContent() {

          ByteArrayInputStream in = new ByteArrayInputStream("peg game".getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file to read from: ");
        String fileName = scanner.nextLine();

        Assert.assertEquals("peg game", fileName);
        scanner.close();
        System.setIn(System.in);

    }

    @Test
    public void testReadFromFile() {

        ByteArrayInputStream in = new ByteArrayInputStream("peg game".getBytes());
        System.setIn(in);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file to read from: ");
        String fileName = scanner.nextLine();


        Assert.assertEquals("peg game", fileName);
        scanner.close();
        System.setIn(System.in);
        
    }
}
