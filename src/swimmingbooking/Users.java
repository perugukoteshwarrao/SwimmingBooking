
package swimmingbooking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import static swimmingbooking.SwimmingBooking.validateInput;

public class Users {
    
    private int userID;
    private String fullname;
    private int age;
    private int gradeLevel;
    private String contact;
    private String gender;
    
    public static ArrayList <Users> userRecords = new ArrayList<>();

    public Users(int userID, String fullname, int age, int gradeLevel, String contact, String gender) {
        this.userID = userID;
        this.fullname = fullname;
        this.age = age;
        this.gradeLevel = gradeLevel;
        this.contact = contact;
        this.gender = gender;
    }

    public int getUserID() {
        return userID;
    }

    public String getFullname() {
        return fullname;
    }

    public int getAge() {
        return age;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }

    public static ArrayList<Users> getUserRecords() {
        return userRecords;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
    
    //read Text file
    public static void readFromTxtFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("text_files/user.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int userID = Integer.parseInt(data[0]);
                String userFullName = data[1];
                int age = Integer.parseInt(data[2]);
                int grade = Integer.parseInt(data[3]);
                String contact = data[4];
                String gender = data[5];
                
                Users user = new Users(userID, userFullName,age, grade,contact,gender);
                userRecords.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Is user found
    public static boolean isUserFound(int userID){
        boolean isFound = false;
        for (Users userObj : userRecords) {
            if(userObj.getUserID() == userID){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
    
    
     
    //Get user grade
    public static int getUserGrade(int userID){
        int grade = 0;
         for (Users obj : userRecords) {
            if(obj.getUserID() == userID){
               grade = obj.getGradeLevel();
                break;
            }
        }
        return grade;
    }
    
     
    //Add Learner
    public static void addLearner(){
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter Name to add new Learner : ");
        String name = sc.nextLine();
        
        if(name.equalsIgnoreCase("")){
            do{
                System.out.print("\nEnter Name : ");
                name = sc.nextLine();
            }while(name.equalsIgnoreCase(""));
        }
        

        System.out.print("\nEnter Contact : ");
        String contact = sc.nextLine();
        
        if(contact.equalsIgnoreCase("")){
            do{
                System.out.print("\nEnter Contact : ");
                contact = sc.nextLine();
            }while(contact.equalsIgnoreCase(""));
        }
        
        System.out.print("\nEnter gender : ");
        System.out.println("\nEnter 1 for Male");
        System.out.println("Enter 2 for Female");
        System.out.print("\nEnter your choice : ");
        String gender = sc.nextLine();
          
        if(gender.equalsIgnoreCase("") || (!gender.equalsIgnoreCase("1") && !gender.equalsIgnoreCase("2"))){
            do{
                System.out.print("\nEnter Valid Choice for Gender : ");
                gender = sc.nextLine();
            }while(gender.equalsIgnoreCase("") || (!gender.equalsIgnoreCase("1") && !gender.equalsIgnoreCase("2")));
        }
        
        System.out.print("\nEnter Age (4 to 11) : ");
        String age = sc.nextLine();
         
        if(age.equalsIgnoreCase("") || !validateInput(age) || Integer.parseInt(age) < 4 || Integer.parseInt(age) > 11){
            do{
                System.out.print("\nEnter Age (4 to 11) : ");
                age = sc.nextLine();
            }while(age.equalsIgnoreCase("") || !validateInput(age) || Integer.parseInt(age) < 4 || Integer.parseInt(age) > 11);
        }
    

        System.out.print("\nEnter Grade Level (1 to 5) : ");
        String grade = sc.nextLine();
        
         
        if(grade.equalsIgnoreCase("") || !validateInput(grade) || (Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 5)){
            do{
                System.out.print("\nEnter Grade Level (1 to 5) : ");
                grade = sc.nextLine();
            }while(grade.equalsIgnoreCase("") || !validateInput(grade) || (Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 5));
        }
    
        Random random = new Random();
        int userID = random.nextInt(88) + 212; 
        
        //User Added
        Users obj = new Users(userID,name,Integer.parseInt(age),Integer.parseInt(grade),contact,gender);
        Users.userRecords.add(obj);
        System.out.println("\nSuccess : Learner Added");
        
    }

    
     
    //users
    public static void users(){
        System.out.println("\n**********************************************************************************************************");
        System.out.printf("%-10s %-25s %-20s %-15s %-15s %-20s \n","User ID","Full Name","Contact","Age",
                "Gender","Current Grade");
        System.out.println("**********************************************************************************************************");

        Set<Users> uniqueUsers = new HashSet<>();

        for(Users obj : userRecords){
            String uniqueData = String.valueOf(obj.getUserID());
            if(!uniqueUsers.contains(uniqueData)){
                uniqueUsers.add(obj);
            }
        }

        for(Users obj : uniqueUsers){
              System.out.printf("%-10s %-25s %-20s %-15s %-15s %-20s \n",obj.getUserID(),obj.getFullname(),obj.getContact(),obj.getAge()+" yrs.",
                obj.getGender(),obj.getGradeLevel());
        }
    }
    
    
    
    
}
