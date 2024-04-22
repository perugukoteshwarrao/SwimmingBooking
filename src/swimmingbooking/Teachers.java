
package swimmingbooking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Teachers {
    
    private int teacherID;
    private String teacherName;
    private String gender;
    private int age;
    private String joinedAt;

   public static ArrayList <Teachers> teacherRecords = new ArrayList<>();

    public Teachers(int teacherID, String teacherName, String gender, int age, String joinedAt) {
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.gender = gender;
        this.age = age;
        this.joinedAt = joinedAt;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public static ArrayList<Teachers> getTeacherRecords() {
        return teacherRecords;
    }
         
    //read Text file
    public static void readFromTxtFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("text_files/coach.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int teacherID = Integer.parseInt(data[0]);
                String teacherName = data[1];
                String gender = data[2];
                int age = Integer.parseInt(data[3]);
                String joinedAt = data[4];
                
                Teachers timetable = new Teachers(teacherID, teacherName, gender, age, joinedAt);
                teacherRecords.add(timetable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Is teacher found
    public static boolean isTeacherFound(int teacherID){
        boolean isFound = false;
        for (Teachers teacherObj : teacherRecords) {
            if(teacherObj.getTeacherID() == teacherID){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
    
    
}
