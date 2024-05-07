package Model;

import java.util.List;

public interface IUserDAO {
    public void createUserAccount(int root);
    public void checkExistUser();
    public boolean checkExist(String id_Vehicle);
    public void searchUserInformation();
    public void updateUser(String updateBy);

    void updateUserInformation();

    void deleteUserInformation(String username, int root);

    public void saveToFile(List<String> line);
    public void printAllListFromFile();
}
