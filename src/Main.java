import Data.User;
import Model.UserDAO;
import Model.Validator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input;
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        while (isRunning){
            try {
                System.out.println("--------------------------------------------");
                System.out.println("| User Management Program                  |");
                System.out.println("| 1. Create user account                   |");
                System.out.println("| 2. Check exist user                      |");
                System.out.println("| 3. Search user information by name       |");
                System.out.println("| 4. Update user                           |");
                System.out.println("| 5. Save to file                          |");
                System.out.println("| 6. Print all list from file              |");
                System.out.println("| 7. Exit                                  |");
                System.out.println("--------------------------------------------");
                input = scanner.next();
                int choice = Integer.parseInt(input);
                switch (choice){
                    case 1:
                        userDAO.createUserAccount(1);
                        break;
                    case 2:
                        userDAO.checkExistUser();
                        break;
                    case 3:
                        userDAO.searchUserInformation();
                        break;
                    case 4:
                        System.out.println("--------------------------------------");
                        System.out.println("| 1. Update user information         |");
                        System.out.println("| 2. Delete user information         |");
                        System.out.println("--------------------------------------");
                        String updateBy = Validator.getString("Input your choice: ", "Please enter 1 or 2", "[12]");
                        userDAO.updateUser(updateBy);
                        break;
                    case 5:
                        for (User user : UserDAO.userList){
                            Field[] fields = user.getClass().getDeclaredFields();
                            List<String> list = new ArrayList<>();
                            for (Field field : fields) {
                                field.setAccessible(true);
                                String fieldName = field.getName();
                                Object value = field.get(user);
                                list.add(value.toString());
                            }
                            userDAO.saveToFile(list);
                        }
                        break;
                    case 6:
                        userDAO.printAllListFromFile();
                        break;
                    case 7:
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Error");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage() + ". You must enter integer");
            }
            catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}