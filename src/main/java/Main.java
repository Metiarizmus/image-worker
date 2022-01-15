
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {


        Map<String, String> map = System.getenv();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Variable Name:- " + entry.getKey() + " Value:- " + entry.getValue());
        }


    }
}
