
package swimmingbooking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Reservations {
    
    private String reservationID;
    private int slotNo;
    private int userID;
    private String reservationStatus;
    private String reservedDate;
   
    public static int CHANGE_RESERVATION = 0;
    public static String RESERVATION_ID = "";
    
    public static ArrayList <Reservations> reservationRecords = new ArrayList<>();

    public Reservations(String reservationID, int slotNo, int userID, String reservationStatus, String reservedDate) {
        this.reservationID = reservationID;
        this.slotNo = slotNo;
        this.userID = userID;
        this.reservationStatus = reservationStatus;
        this.reservedDate = reservedDate;
    }

    public String getReservationID() {
        return reservationID;
    }

    public int getSlotNo() {
        return slotNo;
    }

    public int getUserID() {
        return userID;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public String getReservedDate() {
        return reservedDate;
    }

    public static ArrayList<Reservations> getReservationRecords() {
        return reservationRecords;
    }

    public void setSlotNo(int slotNo) {
        this.slotNo = slotNo;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    //Is reservation found
    public static boolean isReservationFound(String reservationID){
        boolean isFound = false;
        for (Reservations reservationObj : reservationRecords) {
            if(reservationObj.getReservationID().equalsIgnoreCase(reservationID)){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
     
    //Is duplicate reservation
    public static boolean isDuplicateReservation(String slotNo, String userID){
        boolean isFound = false;
        for (Reservations reservationObj : reservationRecords) {
            if(String.valueOf(reservationObj.getSlotNo()).equalsIgnoreCase(slotNo) && 
                    String.valueOf(reservationObj.getUserID()).equalsIgnoreCase(userID) &&
                    (reservationObj.getReservationStatus().equalsIgnoreCase("Booked") || reservationObj.getReservationStatus().equalsIgnoreCase("Changed"))){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
    
    
     
    //Already attended or cancelled
    public static boolean isAllowedToChangeOrCancelOrAttend(String reservationID){
        boolean isFound = false;
        for (Reservations reservationObj : reservationRecords) {
            if(reservationObj.getReservationID().equalsIgnoreCase(reservationID) && (reservationObj.getReservationStatus().equalsIgnoreCase("Cancelled")
                    || reservationObj.getReservationStatus().equalsIgnoreCase("Attended"))){
                isFound = true;
                break;
            }
        }
        return isFound;
    }
    
    
    //Add Reservation
    public static void addReservation(String slotNo, String userID){
        String reservationID = uniqueReservationID(slotNo,userID);
        
        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMM, yyyy h:mma");
        String formattedReservedOn = currentDateTime.format(dateFormatter);

        Reservations obj = new Reservations(reservationID,Integer.parseInt(slotNo),Integer.parseInt(userID),"Booked",formattedReservedOn);
        reservationRecords.add(obj);
        
         
        //Decrease seats in new reserved class
        Timetables.decSeats(Integer.parseInt(slotNo));
    }
    
    
    
    //Update Reservation
    public static void updateReservation(String newSlotNo, String reservationID){        
        String oldSlotNo = "";
        for (Reservations reservationObj : reservationRecords) {
            if(reservationObj.getReservationID().equalsIgnoreCase(reservationID)){
                oldSlotNo = String.valueOf(reservationObj.getSlotNo());
                reservationObj.setReservationStatus("Changed");
                reservationObj.setSlotNo(Integer.parseInt(newSlotNo));
                break;
            }
        }
        
        //Increase seats in previous reserved class
        Timetables.incSeats(Integer.parseInt(oldSlotNo));
        
        //Decrease seats in new reserved class
        Timetables.decSeats(Integer.parseInt(newSlotNo));
    }
    
    
    //Unique Reservation ID
    private static String uniqueReservationID(String slotNo, String userID){
        Random random = new Random();
        int randomNo = random.nextInt(100);
        return "RV_"+slotNo+userID+"_"+randomNo;
    }
    
    
    
    //Print Reservation detail
    public static void printReservationDetail(String reservationID){
        
        ArrayList<Reservations> reservationsRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
        ArrayList<Users> userRecords = Users.getUserRecords();
        
        
        System.out.println("\n**********************************************************************************************************************************************************************");
        System.out.printf("%-17s %-10s %-20s %-10s %-12s %-10s %-12s %-12s %-12s %-12s %-10s %-20s \n","ReservationID","SlotNo","Lesson","Day",
                "ClassGrade","Slot","ClassOn","Teacher","ReservedBy","UserGrade","Status","ReservedOn");
        System.out.println("***********************************************************************************************************************************************************************");

        Set<Reservations> uniqueReservations = new HashSet<>();

        for(Reservations obj : reservationsRecords){
            String uniqueData = String.valueOf(obj.getReservationID());
            if(!uniqueReservations.contains(uniqueData)){
                uniqueReservations.add(obj);
            }
        }
        
        for(Reservations obj : uniqueReservations){
            if(obj.getReservationID().equalsIgnoreCase(reservationID)){
                String lesson = "";
                int classGrade = 0;
                String slot = "";
                String day = "";
                String classOn = "";
                String teacher = "";
                String reservedBy = "";
                int userGrade = 0;

                //get user name
                for(Users userObj : userRecords){
                    if(userObj.getUserID() == obj.getUserID()){
                        reservedBy = userObj.getFullname();
                        userGrade = userObj.getGradeLevel();
                        break;
                    }
                }

                for(Timetables timetableObj : timetableRecords){
                    //get teacher name
                    for(Teachers teacherObj : teacherRecords){
                        if(teacherObj.getTeacherID() == timetableObj.getTeacher()){
                            teacher = teacherObj.getTeacherName();
                            break;
                        }
                    }
                    //get lesson detail
                    if(timetableObj.getSlotNo() == obj.getSlotNo()){
                        lesson = timetableObj.getLessonName();
                        classGrade = timetableObj.getClassGradeLevel();
                        slot = timetableObj.getSlot();
                        classOn = timetableObj.getClassOn();
                        day = timetableObj.getDay();
                        break;
                    }

                }
                   System.out.printf("%-17s %-10s %-20s %-10s %-12s %-10s %-12s %-12s %-12s %-12s %-10s %-20s \n",obj.getReservationID(),obj.getSlotNo(),
                           lesson,day,classGrade,slot,classOn,teacher,reservedBy,userGrade,obj.getReservationStatus(),
                           obj.getReservedDate());
            }
        }
    }
    
    
}
