
package swimmingbooking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Timetables {
    
 
    private int slotNo;
    private String lessonName;
    private String day;
    private String slot;
    private String classOn;
    private int numOfSeats;
    private int classGradeLevel;
    private int teacher;

    public static ArrayList <Timetables> timetableRecords = new ArrayList<>();

        
    public Timetables(int slotNo, String lessonName, String day, String slot, String classOn, int numOfSeats, int classGradeLevel, int teacher) {
        this.slotNo = slotNo;
        this.lessonName = lessonName;
        this.day = day;
        this.slot = slot;
        this.classOn = classOn;
        this.numOfSeats = numOfSeats;
        this.classGradeLevel = classGradeLevel;
        this.teacher = teacher;
    }

    public int getSlotNo() {
        return slotNo;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getDay() {
        return day;
    }

    public String getSlot() {
        return slot;
    }

    public String getClassOn() {
        return classOn;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public int getClassGradeLevel() {
        return classGradeLevel;
    }

    public int getTeacher() {
        return teacher;
    }

    
    public void setNumOfSeats(int numOfSeats) {
         this.numOfSeats = numOfSeats;
    }

        
    public static ArrayList<Timetables> getTimetableRecords() {
        return timetableRecords;
    }
    
    
    //read Text file
    public static void readFromTxtFile(){
        try (BufferedReader br = new BufferedReader(new FileReader("text_files/timetable.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int slotNo = Integer.parseInt(data[0]);
                String lessonName = data[1];
                String day = data[2];
                String slot = data[3];
                String classOn = data[4];
                int numOfSeats = Integer.parseInt(data[5]);
                int classGradeLevel = Integer.parseInt(data[6]);
                int teacher = Integer.parseInt(data[7]);

                Timetables timetable = new Timetables(slotNo, lessonName, day, slot, classOn, numOfSeats, classGradeLevel, teacher);
                timetableRecords.add(timetable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
     
    //Is slot found
    public static boolean isSlotFound(String slotNo){
        boolean isFound = false;
        for (Timetables timetableObj : timetableRecords) {
            if(String.valueOf(timetableObj.getSlotNo()).equalsIgnoreCase(slotNo)){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
    
    
     
    //Get remaining seats
    public static int getRemainingSeats(String slotNo){
        int remaining = 0;
        for (Timetables timetableObj : timetableRecords) {
            if(String.valueOf(timetableObj.getSlotNo()).equalsIgnoreCase(slotNo)){
                remaining = timetableObj.getNumOfSeats();
                break;
            }
        }
        return remaining;
    }
    
    
    //Get class grade
    public static int getClassGrade(int slotNo){
        int grade = 0;
         for (Timetables timetableObj : timetableRecords) {
            if(timetableObj.getSlotNo() == slotNo){
               grade = timetableObj.getClassGradeLevel();
                break;
            }
        }
        return grade;
    }
    

    
    //Increase seats in class
    public static void incSeats(int slotNo){
        for (Timetables timetableObj : timetableRecords) {
            if(timetableObj.getSlotNo() == slotNo){
               int seats = timetableObj.getNumOfSeats();
               timetableObj.setNumOfSeats(seats+1);
                break;
            }
        }
    }
    
    //Decrease seats in class
    public static void decSeats(int slotNo){
        for (Timetables timetableObj : timetableRecords) {
            if(timetableObj.getSlotNo() == slotNo){
               int seats = timetableObj.getNumOfSeats();
               timetableObj.setNumOfSeats(seats-1);
                break;
            }
        }
    }

}
