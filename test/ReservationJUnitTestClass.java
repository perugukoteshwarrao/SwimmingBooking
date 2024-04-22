

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import swimmingbooking.Reservations;
import static swimmingbooking.Reservations.reservationRecords;
import swimmingbooking.Teachers;
import swimmingbooking.Timetables;
import swimmingbooking.Users;

public class ReservationJUnitTestClass {
    
    private static final String slotNo =  "808";
    private static final String userID =  "201";
    private static String reservationID = "RV_"+slotNo+userID+"_80";
    
    @Test
    public void test_filter_day() {
        String weekDay = "saturday";
         
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();

        System.out.println("\n***********************************************************************************************************************************");
        System.out.printf("%-10s %-30s %-15s %-15s %-15s %-15s %-15s %-30s \n","SlotNo","Lesson Name","WeekDay","SlotTime",
                "Grade Level","No Of Seats","Class On","Teacher");
        System.out.println("***********************************************************************************************************************************");

        Set<Timetables> uniqueTimetables = new HashSet<>();

        for(Timetables obj : timetableRecords){
            String uniqueData = String.valueOf(obj.getSlotNo());
            if(!uniqueTimetables.contains(uniqueData)){
                uniqueTimetables.add(obj);
            }
        }

        for(Timetables obj : uniqueTimetables){
            String teacher =  "";
            if(obj.getDay().equalsIgnoreCase(weekDay)){
                for(Teachers teacherObj : teacherRecords){                
                    if(teacherObj.getTeacherID() == obj.getTeacher()){
                        teacher = teacherObj.getTeacherName();
                    }

                }
                System.out.printf("%-10s %-30s %-15s %-15s %-15s %-15s %-15s %-30s \n",obj.getSlotNo(),obj.getLessonName(),obj.getDay(),obj.getSlot(),
                        obj.getClassGradeLevel(),obj.getNumOfSeats(),obj.getClassOn(),teacher);
            }
        }
    }
    
    
    @Test
    public void test_book(){
        Timetables.readFromTxtFile();
        Teachers.readFromTxtFile();
        Users.readFromTxtFile();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        Set<Timetables> uniqueTimetables = new HashSet<>();
        for(Timetables obj : timetableRecords){
            String uniqueData = String.valueOf(obj.getSlotNo());
            if(!uniqueTimetables.contains(uniqueData)){
                uniqueTimetables.add(obj);
            }
        }
        int seats = 0;
        for(Timetables obj : uniqueTimetables){
            if(String.valueOf(obj.getSlotNo()).equalsIgnoreCase(slotNo)){
                seats = obj.getNumOfSeats();
            }
        }
        System.out.println("Number of Seats before reservation : "+seats);
        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMM, yyyy h:mma");
        String formattedReservedOn = currentDateTime.format(dateFormatter);

        Reservations obj = new Reservations(reservationID,Integer.parseInt(slotNo),Integer.parseInt(userID),"Booked",formattedReservedOn);
        reservationRecords.add(obj);
         //Decrease seats in new reserved class
        Timetables.decSeats(Integer.parseInt(slotNo));
        System.out.println("Success : Reservation Done by you");
        System.out.println("\n----------------------------------------------");
        assertTrue(!Reservations.getReservationRecords().isEmpty());
    }
    
    @Test
    public void test_cancel(){
        boolean isCancel = false;
        
        //Update status of reservation
        ArrayList<Reservations> reservationRecords = Reservations.getReservationRecords();
        for(Reservations obj : reservationRecords){
            if(obj.getReservationID().equalsIgnoreCase(reservationID)){
                obj.setReservationStatus("Cancelled");
                if(obj.getReservationStatus().equalsIgnoreCase("Cancelled")){
                    isCancel = true;
                }
                break;
            }
        }
        //Increase seats after cancelling class       
        Timetables.incSeats(Integer.parseInt(slotNo));
        
        System.out.println("\nSuccess : Reservation Cancelled by you");
        System.out.println("\n----------------------------------------------");
        assertTrue(isCancel);
    }
    
    
    @Test
    public void seatsDecreaseAfterReservation(){
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        
        Set<Timetables> uniqueTimetables = new HashSet<>();

        for(Timetables obj : timetableRecords){
            String uniqueData = String.valueOf(obj.getSlotNo());
            if(!uniqueTimetables.contains(uniqueData)){
                uniqueTimetables.add(obj);
            }
        }
        
        int seats = 0;
        for(Timetables obj : uniqueTimetables){
            if(String.valueOf(obj.getSlotNo()).equalsIgnoreCase(slotNo)){
                seats = obj.getNumOfSeats();
            }
        }
        System.out.println("\nNumber of Seats after reservation : "+seats);
        System.out.println("\n----------------------------------------------");
        
    }
    
    
    @Test
    public void test_isIncreasedSeatAfterCancel(){
         ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        
        Set<Timetables> uniqueTimetables = new HashSet<>();

        for(Timetables obj : timetableRecords){
            String uniqueData = String.valueOf(obj.getSlotNo());
            if(!uniqueTimetables.contains(uniqueData)){
                uniqueTimetables.add(obj);
            }
        }
        
        int seats = 0;
        for(Timetables obj : uniqueTimetables){
            if(String.valueOf(obj.getSlotNo()).equalsIgnoreCase(slotNo)){
                seats = obj.getNumOfSeats();
            }
        }
        System.out.println("\nNumber of Seats after cancellation : "+seats);
        System.out.println("\n----------------------------------------------");

    }
    
}
