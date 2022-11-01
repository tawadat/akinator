import java.util.ArrayList;

public class Subject {
    String name;
    ArrayList<String> keywords = new ArrayList<>();

    public Subject(String name) {
        this.name = name;
    }


    public void addKeyWord(String keyword){
        keywords.add(keyword);
    }
}