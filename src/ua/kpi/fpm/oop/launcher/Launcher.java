package ua.kpi.fpm.oop.launcher;

//import ua.kpi.fpm.oop.controller.Controller;
import ua.kpi.fpm.oop.loader.Loader;
import ua.kpi.fpm.oop.loader.UnsupportedFileFormatException;
import ua.kpi.fpm.oop.model.WordGenerator;

import java.io.*;
//import java.util.Scanner;

/**
 * Created by Yura on 19.04.2014.
 */
public class Launcher {
    static public void main(String[] args) throws IOException{


        Loader loader = null;
        try
        {
            loader = Loader.getLoader("war.txt");
        }
        catch (FileNotFoundException e)
        {
            System.out.print(e);
            return;
        }
        catch (UnsupportedFileFormatException e)
        {
            System.out.print(e);
            return;
        }
        WordGenerator generator = new WordGenerator(loader);
        generator.setCurrentSpeed(100);

        //Controller controller = new Controller(generator);


        /*
        Scanner scan = new Scanner(new File("test.txt"));

        System.out.print(scan.nextLine());
        System.out.print(scan.nextLine());
        System.out.print(scan.nextLine());  */





    }
}

