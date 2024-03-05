package PEGGAME2;

import java.util.Collection;

public interface FileHandler {
    public abstract Collection<Location> readFromFile(String fileName);
    public String askUserForFileNameAndAddContent();

}
