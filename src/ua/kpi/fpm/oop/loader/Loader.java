package ua.kpi.fpm.oop.loader;

import ua.kpi.fpm.oop.text.Paragraph;

import java.io.*;
import java.util.*;

/**
 * @author Yura
 * This class is parent for all type of loaders
 * It contains fabric method to to determine the loader type
 * and abstract method nextParagraph
 */
abstract public class Loader {

    protected int currentParagraph;

    /**
     *
     * @param fileName - file that contains text
     * @return loader that corresponds to the file type
     * @throws FileNotFoundException
     */
    static public Loader getLoader(String fileName) throws FileNotFoundException, UnsupportedFileFormatException
    {
        File file = new File(fileName);
        if (fileName.endsWith(".txt"))
        {
            return new LoaderFromTxt(file);
        }
        else
        {
            throw new UnsupportedFileFormatException("This file format is unsupported");
        }


        //return null;

    }

    /**
     * @return next paragraph of text
     * @throws NoSuchElementException
     */
    abstract public Paragraph nextParagraph()  throws NoSuchElementException;

}
