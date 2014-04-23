package text;

/**
 * @author Lana
 * This class contains paragraph as one string
 * id - paragraph number in text
 */
public class Paragraph {

    private String paragraph;
    private int id;


   public Paragraph(int id, String paragraph)
   {
       this.id = id;
       this.paragraph = paragraph;
   }

    public String getParagraph() {
        return paragraph;
    }

    public int getId() {
        return id;
    }
}
