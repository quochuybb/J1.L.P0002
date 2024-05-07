package Model;

import Data.User;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class UserDAO implements IUserDAO{
    public static List<User> userList = new ArrayList<>();
    String line;
    Scanner scanner = new Scanner(System.in);
    List<String> userDeleted = new ArrayList<>();
    @Override
    public void createUserAccount(int root) {
        boolean isCountinue = true;
        while (isCountinue) {
            try {
                System.out.println("Input information user.");
                if (root == 1){
                    String username = Validator.getString("Input Username: ", "Please enter must be at least five characters and no spaces", "^[a-zA-Z][^\\s]{4,}$");
                    String firstname = Validator.getString("Input Firstname User: ", "Please enter name and don't have number", "^[A-Z][a-z]*(?:\\s[A-Z][a-z]*)*$");
                    String lastname = Validator.getString("Input Lastname User: ", "Please enter name and don't have number", "^[A-Z][a-z]*(?:\\s[A-Z][a-z]*)*$");
                    String phone = Validator.getString("Input Phone User: ", "Please enter phone must contain 10 numbers", "^0\\d{9,}$");
                    String email = Validator.getString("Input Email User: ", "Please enter email must follow standard email format", "^[a-zA-Z0-9._%+-]+@gmail.com$");
                    String password = Validator.getString("Input Password User: ", "Password must be at least six characters and no spaces", "^[a-zA-Z0-9][^\\s]{5,}$");
                    String confirmPassword = Validator.getString("Input Confirm Password: ", "Confirm password must equal password", password);
                    if (!checkExist(username)){
                        User user = new User(username,firstname,lastname,phone,email,password,confirmPassword);
                        userList.add(user);
                    }
                    else {
                        System.out.println("User " + username + " exist!!!");
                        continue;
                    }
                    isCountinue = Validator.getBoolen("Continue add (Y/N)");
                }
                else {
                    String username = Validator.getString("Input Username: ", "Please enter must be at least five characters and no spaces", "^([a-zA-Z][^\\s]{4,})?$");
                    String firstname = Validator.getString("Input Firstname User: ", "Please enter name and don't have number", "^([A-Z][a-z]*(?:\\s[A-Z][a-z]*))*$");
                    String lastname = Validator.getString("Input Lastname User: ", "Please enter name and don't have number", "^([A-Z][a-z]*(?:\\s[A-Z][a-z]*))*$");
                    String phone = Validator.getString("Input Phone User: ", "Please enter phone must contain 10 numbers", "^(0\\d{9,})*$");
                    String email = Validator.getString("Input Email User: ", "Please enter email must follow standard email format", "^([a-zA-Z0-9._%+-]+@gmail.com)*$");
                    String password = Validator.getString("Input Password User: ", "Password must be at least six characters and no spaces", "^([a-zA-Z0-9][^\\s]{5,})*$");
                    String confirmPassword = Validator.getString("Input Confirm Password: ", "Confirm password must equal password", password);
                    if (!checkExist(username)){
                        User user = new User(username,firstname,lastname,phone,email,password,confirmPassword);
                        userList.add(user);
                    }
                    else {
                        System.out.println("User " + username + " exist!!!");
                        continue;
                    }
                }

            }
            catch (Exception e) {
                System.out.println("Error input");
            }
        }
    }

    @Override
    public void checkExistUser() {
        boolean isContinue = true;
        while (isContinue){
            String username = Validator.getString("Input Username: ", "Please enter must be at least five characters and no spaces", "^[a-zA-Z][^\\s]{4,}$");
            if (checkExist(username)){
                System.out.println("Exist User");
            }
            else {
                System.out.println("No User Found!");
            }
            isContinue = Validator.getBoolen("Continue check exist (Y/N)");
        }
    }

    @Override
    public boolean checkExist(String id_Vehicle) {
        boolean isFound = false;
        try {
            FileReader fileReader = new FileReader("User.dat");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split(" - ");
                if (id_Vehicle.equals(parts[0])){
                    isFound = true;
                    break;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return  isFound;
    }
    @Override
    public void searchUserInformation() {
        boolean isContinue = true;
        while (isContinue){
            try {
                HashMap<String, String> listUser = new HashMap<>();
                FileReader fileReader = new FileReader("User.dat");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                System.out.println("Enter Firstname or Lastname: ");
                String Name = scanner.next();
                while ((line = bufferedReader.readLine()) != null){
                    String[] parts = line.split(" - ");
                    if (parts[1].toUpperCase().contains(Name.toUpperCase()) || parts[2].toUpperCase().contains(Name.toUpperCase())){
                        listUser.put(parts[1],String.join(" - ",parts));
                    }
                }
                if (listUser.size() == 0){
                    System.out.println("Have no any user");
                    return;
                }
                TreeMap<String, String> sortedMap = new TreeMap<>(listUser);
                for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                    System.out.println(entry.getValue());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            isContinue = Validator.getBoolen("Continue check exist (Y/N)");
        }

    }

    @Override
    public void updateUser(String updateBy) {
        if (updateBy.equals("1")){
            updateUserInformation();
        }
        else {
            String username = Validator.getString("Input Username: ", "Please enter must be at least five characters and no spaces", "^[a-zA-Z][^\\s]{4,}$");
            deleteUserInformation(username,1);
        }
    }

    @Override
    public void updateUserInformation() {
        List<List<String>> lines = new ArrayList<>();
        String username = Validator.getString("Input Username: ", "Please enter must be at least five characters and no spaces", "^[a-zA-Z][^\\s]{4,}$");
        deleteUserInformation(username,2);
        createUserAccount(2);
        for (User user : userList){
            Field[] fields = user.getClass().getDeclaredFields();
            List<String> list = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = null;
                try {
                    value = field.get(user);
                    if (value.toString().equals("")){
                        if (fieldName.equals("userName")){
                            value = userDeleted.get(0);
                        } else if (fieldName.equals("firstName")) {
                            value = userDeleted.get(1);
                        } else if (fieldName.equals("lastName")) {
                            value = userDeleted.get(2);
                        } else if (fieldName.equals("phone")) {
                            value = userDeleted.get(3);
                        } else if (fieldName.equals("email")) {
                            value = userDeleted.get(4);
                        } else if (fieldName.equals("password")) {
                            value = userDeleted.get(5);
                        } else if (fieldName.equals("confirmPassword")) {
                            value = userDeleted.get(6);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                list.add(value.toString());
            }
            saveToFile(list);

        }
    }
    @Override
    public void deleteUserInformation(String username, int root) {
        boolean isContinue = true;
        while (isContinue){
            List<List<String>> lines = new ArrayList<>();
            if (!checkExist(username)){
                System.out.println("User does not exist");
                return;
            }
            try {
                FileReader fileReader = new FileReader("User.dat");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while ((line = bufferedReader.readLine()) != null){
                    String[] parts = line.split(" - ");
                    if (username.equals(parts[0])){
                        userDeleted = Arrays.asList(parts);
                        continue;
                    }
                    lines.add(Arrays.asList(parts));
                }
                FileWriter clearFile = new FileWriter("User.dat", false);
                clearFile.flush();
                clearFile.close();
                for (List<String> strings : lines) {
                    saveToFile(strings);
                }
                System.out.println("Success");
                if (root == 1){
                    isContinue = Validator.getBoolen("Continue check exist (Y/N)");
                }
            } catch (IOException e) {
                System.out.println("Fail");
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void saveToFile(List<String> line) {
        if (!checkExist(line.get(0))){
            for (int i =0 ; i < line.size(); i++){
                try {
                    FileWriter printWrite = new FileWriter("User.dat", true);
                    if (i == line.size() - 1){
                        printWrite.write(line.get(i) + "\n");
                    }
                    else {
                        printWrite.write(line.get(i) + " - ");
                    }
                    printWrite.flush();
                    printWrite.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        else {
            System.out.println("User " + line.get(0) + " exist!!!");
        }
    }

    @Override
    public void printAllListFromFile() {
        try {
            HashMap<String, String> listInfo = new HashMap<>();
            FileReader fileReader = new FileReader("User.dat");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null){
                String[] parts = line.split(" - ");
                listInfo.put(parts[0],String.join(" - ",parts));
            }
            TreeMap<String, String> sortedMap = new TreeMap<>(listInfo);
            for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
                System.out.println(entry.getValue());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
