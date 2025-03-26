import javax.annotation.processing.FilerException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

class MyFileException extends RuntimeException {
    public MyFileException(String msg, Throwable ex){
        super(msg, ex);
    }
}
class MyFile{
    private final Path filePath;
    public MyFile(String filePath){
        this.filePath = Path.of(filePath);
    }
    public void writeFile(String data) throws MyFileException{
        try{
            Files.writeString(filePath, data, StandardOpenOption.CREATE);
            System.out.println("Данные успешно записаны.");
        } catch (IOException e) {
            throw new MyFileException("Ошибка при записи", e);
        }
    }
    public List<String> readFile() throws MyFileException{
        try{
            return Files.readAllLines(filePath, StandardCharsets.UTF_8);
        }
        catch (NoSuchFileException e){
            throw new MyFileException("Файл не найден", e);
        }
        catch (IOException e){
            throw new MyFileException("Ошибка при чтении файла", e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
            String data = "Здесь какие-то данные\nКоторые нужно записать в файл. \nHello World!";
            String filePath = "file1.txt";
            MyFile myFile = new MyFile(filePath);
            try{
                myFile.writeFile(data);
                List<String> lines = myFile.readFile();
                System.out.println("Содержимое файла: ");
                lines.stream()
                        .forEach(System.out::println);
            }
            catch (MyFileException e){
                System.out.println("Ошибка при работе с файлом: "+e.getMessage());
            }
        }
    }