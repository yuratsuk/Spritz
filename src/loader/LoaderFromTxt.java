package loader;

import text.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * @author Lana
 * */
public class LoaderFromTxt extends Loader {

    private Scanner scanner;

    /**
     * @param file - txt file
     * @throws FileNotFoundException
     */
    LoaderFromTxt(File file) throws FileNotFoundException
    {
        scanner = new Scanner(file);
        currentParagraph = 0;


    }

    @Override
    public Paragraph nextParagraph() throws NoSuchElementException
    {
        //currentParagraph ++;
        String s = scanner.nextLine();
        s = s.replaceAll("\\s+", " ");
        return new Paragraph(currentParagraph++, s);
    }


}
