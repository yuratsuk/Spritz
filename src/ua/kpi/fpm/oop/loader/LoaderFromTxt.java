package ua.kpi.fpm.oop.loader;

import ua.kpi.fpm.oop.text.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Yura
 * This class is used for working with .txt files
 */
public class LoaderFromTxt extends Loader {

    private Scanner scanner;

    /**
     * @param file - txt file
     * @throws FileNotFoundException
     */
    LoaderFromTxt(File file) throws FileNotFoundException
    {
        
    	this.file = file;
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

	@Override
	public void setCurrentparagraph(int paragraph) throws FileNotFoundException 
	{
		currentParagraph = paragraph;
		scanner = new Scanner(file);
		String s;
		for (int i=0; i < currentParagraph; i++)
		{
			s = scanner.nextLine();
		}
		
	}


}
