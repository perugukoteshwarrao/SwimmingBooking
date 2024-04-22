
package swimmingbooking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class SwimmingBooking {

    //Main Method
    public static void main(String[] args) {
        Timetables.readFromTxtFile();
        Teachers.readFromTxtFile();
        Users.readFromTxtFile();
        int selectedOption;
        do {
            selectedOption = Menus.showMenuFunctions();
            switch (selectedOption) {
                case 1 -> Menus.adminMenu();
                case 2 -> Menus.userMenu();
                case 3 -> System.exit(0);
                default -> System.out.println("\nPlease enter a valid choice (1-8)");
            }
        } while (selectedOption != 3);
    }
      
    //Validate Input of User
    public static boolean validateInput(String str)
    {
        if (str == null || str.isEmpty()) {
             return false;
         }
         for (int i = 0; i < str.length(); i++) {
             if (!Character.isDigit(str.charAt(i))) {
                 return false;
             }
         }
         return true;
    }
    
    
    //timetable
    public static void timetable(){
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
            for(Teachers teacherObj : teacherRecords){                
                if(teacherObj.getTeacherID() == obj.getTeacher()){
                    teacher = teacherObj.getTeacherName();
                }
                
            }
            System.out.printf("%-10s %-30s %-15s %-15s %-15s %-15s %-15s %-30s \n",obj.getSlotNo(),obj.getLessonName(),obj.getDay(),obj.getSlot(),
                    obj.getClassGradeLevel(),obj.getNumOfSeats(),obj.getClassOn(),teacher);
        }
    }
    
   
    //reservations
    public static void reservations(){
        ArrayList<Reservations> reservationsRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Users> userRecords = Users.getUserRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
        
        Set<Reservations> uniqueReservations = new HashSet<>();

        for(Reservations obj : reservationsRecords){
            String uniqueData = String.valueOf(obj.getReservationID());
            if(!uniqueReservations.contains(uniqueData)){
                uniqueReservations.add(obj);
            }
        }
       
        
        if(uniqueReservations.isEmpty()){
            System.out.println("\nNo Record Found");
            return;
        }
        
        System.out.println("\n********************************************************************************************************************************************************************************");
        System.out.printf("%-17s %-10s %-30s %-10s %-12s %-10s %-12s %-12s %-12s %-12s %-10s %-20s \n","ReservationID","SlotNo","Lesson","Day",
                "ClassGrade","Slot","ClassOn","Teacher","ReservedBy","UserGrade","Status","ReservedOn");
        System.out.println("********************************************************************************************************************************************************************************");

        
        for(Reservations obj : uniqueReservations){
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
    
    
    //timetable
    public static void ratings(){
        
        ArrayList<Ratings> ratingsRecords = Ratings.getRatingRecords();
        ArrayList<Reservations> reservationsRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
        ArrayList<Users> userRecords = Users.getUserRecords();
        
        Set<Ratings> uniqueRatings = new HashSet<>();

        for(Ratings obj : ratingsRecords){
            String uniqueData = String.valueOf(obj.getReservationID());
            if(!uniqueRatings.contains(uniqueData)){
                uniqueRatings.add(obj);
            }
        }
        
        if(uniqueRatings.isEmpty()){
            System.out.println("\nNo Record Found");
            return;
        }
        
        System.out.println("\n**************************************************************************************************************************************************************************************************");
        System.out.printf("%-17s %-10s %-30s %-10s %-12s %-10s %-12s %-15s %-15s %-10s %-20s %-20s \n","ReservationID","SlotNo","Lesson","Day",
                "ClassGrade","Slot","ClassOn","Given To","Given By","Rating","Review","AttendedOn");
        System.out.println("**************************************************************************************************************************************************************************************************");

        for(Ratings ratingObj : uniqueRatings){
            for(Reservations obj : reservationsRecords){
                if(ratingObj.getReservationID().equalsIgnoreCase(obj.getReservationID())){
                        String lesson = "";
                        int classGrade = 0;
                        String slot = "";
                        String day = "";
                        String classOn = "";
                        String teacher = "";
                        String reservedBy = "";

                        //get user name
                        for(Users userObj : userRecords){
                            if(userObj.getUserID() == obj.getUserID()){
                                reservedBy = userObj.getFullname();
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
                        System.out.printf("%-17s %-10s %-30s %-10s %-12s %-10s %-12s %-15s %-15s %-10s %-20s %-20s \n",obj.getReservationID(),obj.getSlotNo(),
                                   lesson,day,classGrade,slot,classOn,teacher,reservedBy,
                                   ratingObj.getRatings(),ratingObj.getReview(), ratingObj.getAttendedOn());
                }
            }
        }
    }
    
    
    //timetable by day
    public static void timetableByDay(){
        System.out.print("\nEnter WeekDay to Search Timetable : ");
        Scanner sc = new Scanner(System.in);
        String weekday = sc.nextLine();
        
        if (weekday.equalsIgnoreCase("") || 
            !(weekday.equalsIgnoreCase("Monday") || weekday.equalsIgnoreCase("mon") ||
              weekday.equalsIgnoreCase("Wednesday") || weekday.equalsIgnoreCase("wed") ||
              weekday.equalsIgnoreCase("Friday") || weekday.equalsIgnoreCase("fri") ||
              weekday.equalsIgnoreCase("Saturday") || weekday.equalsIgnoreCase("sat"))
           ){
            System.out.println("\nDay is required and must be a valid week day value (mon, wed, fri, sat)");
            return;
        }
        
        if(weekday.equalsIgnoreCase("mon")){
            weekday = "monday";
        }else if(weekday.equalsIgnoreCase("wed")){
            weekday = "wednesday";
        }else if(weekday.equalsIgnoreCase("fri")){
            weekday = "friday";
        }else if(weekday.equalsIgnoreCase("sat")){
            weekday = "saturday";
        }
        
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
            if(obj.getDay().equalsIgnoreCase(weekday)){
                for(Teachers teacherObj : teacherRecords){                
                    if(teacherObj.getTeacherID() == obj.getTeacher()){
                        teacher = teacherObj.getTeacherName();
                    }

                }
                System.out.printf("%-10s %-30s %-15s %-15s %-15s %-15s %-15s %-30s \n",obj.getSlotNo(),obj.getLessonName(),obj.getDay(),obj.getSlot(),
                        obj.getClassGradeLevel(),obj.getNumOfSeats(),obj.getClassOn(),teacher);
            }
        }
        
        reserveClass();
    }
    
    
    //timetable by grade level
    public static void timetableByGradeLevel(){
        System.out.print("\nEnter Grade Level to Search Timetable : ");
        Scanner sc = new Scanner(System.in);
        String level = sc.nextLine();
        
        if(!validateInput(level) || level.equalsIgnoreCase("") || Integer.parseInt(level) < 0 || Integer.parseInt(level) > 5){
            System.out.println("\nGrade Level is required and must be numeric between 1 to 5");
            return;
        }
        
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
            if(String.valueOf(obj.getClassGradeLevel()).equalsIgnoreCase(level)){
                String teacher =  "";
                for(Teachers teacherObj : teacherRecords){                
                    if(teacherObj.getTeacherID() == obj.getTeacher()){
                        teacher = teacherObj.getTeacherName();
                    }
                }
                System.out.printf("%-10s %-30s %-15s %-15s %-15s %-15s %-15s %-30s \n",obj.getSlotNo(),obj.getLessonName(),obj.getDay(),obj.getSlot(),
                        obj.getClassGradeLevel(),obj.getNumOfSeats(),obj.getClassOn(),teacher);
            }
        }
        reserveClass();
    }
    
    
    //timetable by coach
    public static void timetableByTeacher(){
        
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
        
        List<Integer> teacherIDList = new ArrayList<>();

        System.out.println();
        for (Teachers teacherObj : teacherRecords) {
           System.out.println("Type "+teacherObj.getTeacherID()+" for "+teacherObj.getTeacherName());
            if (!teacherIDList.contains(teacherObj.getTeacherID())) {
                teacherIDList.add(teacherObj.getTeacherID());
            }
        }
        
        System.out.print("\nEnter Teacher ID to Search Timetable : ");
        Scanner sc = new Scanner(System.in);
        String teacherID = sc.nextLine();
        
        if(!validateInput(teacherID) || teacherID.equalsIgnoreCase("")){
            System.out.println("\nTeacher ID is required and must be valid");
            return;
        }
        
        //Check teacher exists or not
        boolean isTeacherFound = Teachers.isTeacherFound(Integer.parseInt(teacherID));
        if(!isTeacherFound){
            System.out.println("\nTeacher ID is not valid");
            return;
        }

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
            if(String.valueOf(obj.getTeacher()).equalsIgnoreCase(teacherID)){
                String teacher =  "";
                for(Teachers teacherObj : teacherRecords){                
                    if(teacherObj.getTeacherID() == obj.getTeacher()){
                        teacher = teacherObj.getTeacherName();
                    }
                }
                System.out.printf("%-10s %-30s %-15s %-15s %-15s %-15s %-15s %-30s \n",obj.getSlotNo(),obj.getLessonName(),obj.getDay(),obj.getSlot(),
                        obj.getClassGradeLevel(),obj.getNumOfSeats(),obj.getClassOn(),teacher);
            }
        }
        reserveClass();
    }
    
    
    //my reservations
    public static void myReservations(){
        Scanner sc = new Scanner(System.in);
  
        
        ArrayList<Reservations> reservationsRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
       
        Set<Reservations> uniqueReservations = new HashSet<>();

        for(Reservations obj : reservationsRecords){
            String uniqueData = String.valueOf(obj.getReservationID());
            if(!uniqueReservations.contains(uniqueData)){
                uniqueReservations.add(obj);
            }
        }
        if(uniqueReservations.isEmpty()){
            System.out.println("\nNo Record Found");
            return;
        }
        
        //take User detail
        ArrayList<Users> userRecords = Users.getUserRecords();
        
        List<Integer> userIDList = new ArrayList<>();

        System.out.println();
        for (Users userObj : userRecords) {
           System.out.println("Type "+userObj.getUserID()+" for "+userObj.getFullname()+" (Current Grade : "+userObj.getGradeLevel()+")");
            if (!userIDList.contains(userObj.getUserID())) {
                userIDList.add(userObj.getUserID());
            }
        }
        
        System.out.print("\nEnter User ID to check reservations : ");
        String userID = sc.nextLine();
        
        if(!validateInput(userID) || userID.equalsIgnoreCase("")){
            System.out.println("\nUser ID is required and must be valid");
            return;
        }
        
        //Check user exists or not
        boolean isUserFound = Users.isUserFound(Integer.parseInt(userID));
        if(!isUserFound){
            System.out.println("\nUser ID is not valid");
            return;
        }
        
         
        System.out.println("\n**********************************************************************************************************************************************************************");
        System.out.printf("%-17s %-10s %-20s %-10s %-12s %-10s %-12s %-12s %-12s %-12s %-10s %-20s \n","ReservationID","SlotNo","Lesson","Day",
                "ClassGrade","Slot","ClassOn","Teacher","ReservedBy","UserGrade","Status","ReservedOn");
        System.out.println("***********************************************************************************************************************************************************************");

        for(Reservations obj : uniqueReservations){
            if(obj.getUserID() == Integer.parseInt(userID)){
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
        
        updateReservationsMenu();
    }
    

    //my ratings
    public static void myRatings(){
        
        Scanner sc = new Scanner(System.in);
      
        ArrayList<Ratings> ratingsRecords = Ratings.getRatingRecords();
        ArrayList<Reservations> reservationsRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
        
        Set<Ratings> uniqueRatings = new HashSet<>();

        for(Ratings obj : ratingsRecords){
            String uniqueData = String.valueOf(obj.getReservationID()) + String.valueOf(obj.getUserID());
            if(!uniqueRatings.contains(uniqueData)){
                uniqueRatings.add(obj);
            }
        }
        
        if(uniqueRatings.isEmpty()){
            System.out.println("\nNo Record Found");
            return;
        }
        
        //take User detail
        ArrayList<Users> userRecords = Users.getUserRecords();
        
        List<Integer> userIDList = new ArrayList<>();

        System.out.println();
        for (Users userObj : userRecords) {
           System.out.println("Type "+userObj.getUserID()+" for "+userObj.getFullname()+" (Current Grade : "+userObj.getGradeLevel()+")");
            if (!userIDList.contains(userObj.getUserID())) {
                userIDList.add(userObj.getUserID());
            }
        }
        
        System.out.print("\nEnter User ID to check given ratings to teachers : ");
        String userID = sc.nextLine();
        
        if(!validateInput(userID) || userID.equalsIgnoreCase("")){
            System.out.println("\nUser ID is required and must be valid");
            return;
        }
        
        //Check user exists or not
        boolean isUserFound = Users.isUserFound(Integer.parseInt(userID));
        if(!isUserFound){
            System.out.println("\nUser ID is not valid");
            return;
        }
        
        System.out.println("\n**************************************************************************************************************************************************************************************************");
        System.out.printf("%-17s %-10s %-30s %-10s %-12s %-10s %-12s %-15s %-15s %-10s %-20s %-20s \n","ReservationID","SlotNo","Lesson","Day",
                "ClassGrade","Slot","ClassOn","Given To","Given By","Rating","Review","AttendedOn");
        System.out.println("**************************************************************************************************************************************************************************************************");

        for(Ratings ratingObj : uniqueRatings){
            if(ratingObj.getUserID() == Integer.parseInt(userID)){
                for(Reservations obj : reservationsRecords){
                    if(ratingObj.getReservationID().equalsIgnoreCase(obj.getReservationID())){
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
                         System.out.printf("%-17s %-10s %-30s %-10s %-12s %-10s %-12s %-15s %-15s %-10s %-20s %-20s \n",obj.getReservationID(),obj.getSlotNo(),
                                   lesson,day,classGrade,slot,classOn,teacher,reservedBy,
                                   ratingObj.getRatings(),ratingObj.getReview(), ratingObj.getAttendedOn());
                    }
                }
            }
        }
    }
 
    
    //Rserve Class
    private static void reserveClass(){
        
        //Take slot no
        if(Reservations.CHANGE_RESERVATION == 0){
            System.out.print("\nEnter Slot No to Reserve Class : ");
        }else{
            System.out.print("\nEnter Slot No to Change Class : ");        
        }
        
        Scanner sc = new Scanner(System.in);
        String slotNo = sc.nextLine();
        
        if(!validateInput(slotNo) || slotNo.equalsIgnoreCase("")){
            System.out.println("\nSlot No is required and must be valid");
            return;
        }
        
        //Check slotNo exists or not
        boolean isSlotFound = Timetables.isSlotFound(slotNo);
        if(!isSlotFound){
            System.out.println("\nSlot No is not valid");
            return;
        }         
        if(Reservations.CHANGE_RESERVATION == 0){
            //take User detail
            ArrayList<Users> userRecords = Users.getUserRecords();

            List<Integer> userIDList = new ArrayList<>();

            System.out.println();
            for (Users userObj : userRecords) {
               System.out.println("Type "+userObj.getUserID()+" for "+userObj.getFullname()+" (Current Grade : "+userObj.getGradeLevel()+")");
                if (!userIDList.contains(userObj.getUserID())) {
                    userIDList.add(userObj.getUserID());
                }
            }

            System.out.print("\nEnter User ID to Reserve Class : ");
            String userID = sc.nextLine();

            if(!validateInput(userID) || userID.equalsIgnoreCase("")){
                System.out.println("\nUser ID is required and must be valid");
                return;
            }

            //Check user exists or not
            boolean isUserFound = Users.isUserFound(Integer.parseInt(userID));
            if(!isUserFound){
                System.out.println("\nUser ID is not valid");
                return;
            }
            
             //if duplicate reservation
            boolean isDuplicate = Reservations.isDuplicateReservation(slotNo,userID);
            if(isDuplicate){
                System.out.println("\nDuplicate Reservation !");
                return;
            }
            
             //if seats full
            int remaining = Timetables.getRemainingSeats(slotNo);
            if(remaining < 1){
                System.out.println("\nSeats are already full for selected SlotNo !");
                return;
            }
            
            //Is Higher or Lower Grade Level
            int classGrade = Timetables.getClassGrade(Integer.parseInt(slotNo));
            int userGrade = Users.getUserGrade(Integer.parseInt(userID));

            if(((userGrade+1) != classGrade && (userGrade) != classGrade) || classGrade < userGrade){
                System.out.println("\nYou have to reserve class that is same as your current grade level or one level higher than your current level");
                return;
            }
            
            //Add Reservation
            Reservations.addReservation(slotNo, userID);
            System.out.println("\nSuccess : Reservation Done by you");
        }else{
            String reservationID = Reservations.RESERVATION_ID;
                        
            //get userID
            ArrayList<Reservations> reservationRecords = Reservations.getReservationRecords();
             
            String userID = "";
            for(Reservations obj : reservationRecords){
                if(obj.getReservationID().equalsIgnoreCase(reservationID)){
                    userID = String.valueOf(obj.getUserID());
                    break;
                }
            }
            
            //Is Attended cancelled or attended
            boolean isAlreadyCancelledOrAttended = Reservations.isAllowedToChangeOrCancelOrAttend(reservationID);
            if(isAlreadyCancelledOrAttended){
                System.out.println("\nReservation is either attended or cancelled by you");
                return;
            }
        
        
            //if duplicate reservation
            boolean isDuplicate = Reservations.isDuplicateReservation(slotNo,userID);
            if(isDuplicate){
                System.out.println("\nDuplicate Reservation !");
                return;
            }
            
             //if seats full
            int remaining = Timetables.getRemainingSeats(slotNo);
            if(remaining < 1){
                System.out.println("\nSeats are already full for selected SlotNo !");
                return;
            }
            
            
            //Is Higher or Lower Grade Level
            int classGrade = Timetables.getClassGrade(Integer.parseInt(slotNo));
            int userGrade = Users.getUserGrade(Integer.parseInt(userID));

            if(((userGrade+1) != classGrade && (userGrade) != classGrade) || classGrade < userGrade){
                System.out.println("\nYou have to reserve class that is same as your current grade level or one level higher than your current level");
                return;
            }
                                    
            //Update  Reservation
            Reservations.updateReservation(slotNo, reservationID);
            System.out.println("\nSuccess : Reservation Updated by you");
            Reservations.CHANGE_RESERVATION = 0;
            Reservations.RESERVATION_ID = "";
            return;
        }
    }


    //Update Reservations Menu
    private static void updateReservationsMenu(){
        
        int selectedOption;
        do {
            selectedOption = updateReservationsMenuOption();
            switch (selectedOption) {
                case 1 -> {
                            updateReservation();
                            return;
                        }
                case 2 -> {
                            cancelReservation();
                            return;
                          }
                case 3 -> { 
                            attendReservation();
                            return;
                          }
                case 4 -> {
                            return;
                        }
                default -> System.out.println("\nPlease enter a valid choice (1-4)");
            }
        } while (selectedOption != 4);
        return;
    }
    

    //Update Reservations Menu Option
    private static int updateReservationsMenuOption(){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\nDo you want to do any of the below operation : ");
        System.out.println("1. Change Reservation");
        System.out.println("2. Cancel Reservation");
        System.out.println("3. Attend Reservation");
        System.out.println("4. Return to Main Menu");
        System.out.print("\nEnter Option : ");

        String menuOption = sc.nextLine();
        while (menuOption.equals("") || !validateInput(menuOption))
        {
            System.out.print("\nEnter Valid Option ");
            menuOption = sc.nextLine();
        }
        return Integer.parseInt(menuOption);
    }

    
    //Update Reservations
    private static void updateReservation(){
        Scanner sc = new Scanner(System.in);

        //Take ReservationID
        System.out.print("\nEnter Reservation ID to Change Class : ");
        String reservationID = sc.nextLine();
        
        if(reservationID.equalsIgnoreCase("")){
            System.out.println("\nReservationID is required and must be valid");
            return;
        }
        
        //Check ReservationID  exists or not
        boolean isReservationFound = Reservations.isReservationFound(reservationID);
        if(!isReservationFound){
            System.out.println("\nReservationID is not valid");
            return;
        }
        
        //Is Attended cancelled or attended
        boolean isAlreadyCancelledOrAttended = Reservations.isAllowedToChangeOrCancelOrAttend(reservationID);
        if(isAlreadyCancelledOrAttended){
            System.out.println("\nReservation is either attended or cancelled by you");
            return;
        }
        
        changeReservationTimetableMenu(reservationID);
        return;
    }
        
        
    //Update Reservations Timetable Menu 
    private static void changeReservationTimetableMenu(String reservationID){
        
        Reservations.CHANGE_RESERVATION = 1;
        Reservations.RESERVATION_ID = reservationID;
        
        int selectedOption;
        do {
            selectedOption = changeReservationTimetableMenuOption();
            switch (selectedOption) {
                case 1 -> timetableByDay();
                case 2 -> timetableByGradeLevel();
                case 3 -> timetableByTeacher();
                case 4 -> {
                        Reservations.CHANGE_RESERVATION = 0;
                        Reservations.RESERVATION_ID = "";
                            return;
                        }
                default -> System.out.println("\nPlease enter a valid choice (1-4)");
            }
        } while (selectedOption != 4);
        return;
    }
    
        
    //Update Reservations Timetable Menu Option
    private static int changeReservationTimetableMenuOption(){
        
         Scanner sc = new Scanner(System.in);

        System.out.println("\n\nSelect Option from below menu to filter timetable to change class : ");
        System.out.println("1. Timetable By Day");
        System.out.println("2. Timetable By Grade Level");
        System.out.println("3. Timetable By Coach");
        System.out.println("4. Return to Main Menu");
        System.out.print("\nEnter Option : ");
        String menuOption = sc.nextLine();
        while (menuOption.equals("") || !validateInput(menuOption))
        {
            System.out.print("\nEnter Valid Option ");
            menuOption = sc.nextLine();
        }
        return Integer.parseInt(menuOption);
    }
    
        
    //Cancel Reservations
    private static void cancelReservation(){
        Scanner sc = new Scanner(System.in);

        //Take ReservationID
        System.out.print("\nEnter Reservation ID to Cancel Class : ");
        String reservationID = sc.nextLine();
        
        if(reservationID.equalsIgnoreCase("")){
            System.out.println("\nReservationID is required and must be valid");
            return;
        }
        
        //Check ReservationID  exists or not
        boolean isReservationFound = Reservations.isReservationFound(reservationID);
        if(!isReservationFound){
            System.out.println("\nReservationID is not valid");
            return;
        }
        
        //Is Attended cancelled or attended
        boolean isAlreadyCancelledOrAttended = Reservations.isAllowedToChangeOrCancelOrAttend(reservationID);
        if(isAlreadyCancelledOrAttended){
            System.out.println("\nReservation is either attended or cancelled by you");
            return;
        }
        
        
        //Update status of reservation
        int slotNo = 0;
        ArrayList<Reservations> reservationRecords = Reservations.getReservationRecords();
        for(Reservations obj : reservationRecords){
            if(obj.getReservationID().equalsIgnoreCase(reservationID)){
                obj.setReservationStatus("Cancelled");
                slotNo = obj.getSlotNo();
                break;
            }
        }
        
        //Increase seats after cancelling class       
        Timetables.incSeats(slotNo);
        
        System.out.println("\nSuccess : Class Cancelled by you");
        return;
    }
    

    //Attend Reservations
    private static void attendReservation(){
        Scanner sc = new Scanner(System.in);

        //Take ReservationID
        System.out.print("\nEnter Reservation ID to Attend Class : ");
        String reservationID = sc.nextLine();
        
        if(reservationID.equalsIgnoreCase("")){
            System.out.println("\nReservationID is required and must be valid");
            return;
        }
        
        //Check ReservationID  exists or not
        boolean isReservationFound = Reservations.isReservationFound(reservationID);
        if(!isReservationFound){
            System.out.println("\nReservationID is not valid");
            return;
        }
        
        //Is Attended cancelled or attended
        boolean isAlreadyCancelledOrAttended = Reservations.isAllowedToChangeOrCancelOrAttend(reservationID);
        if(isAlreadyCancelledOrAttended){
            System.out.println("\nReservation is either attended or cancelled by you");
            return;
        }
        
        Reservations.printReservationDetail(reservationID);
        
        //Take Review & rating
        System.out.print("\nWrite a review for the teacher : ");
        String review = sc.nextLine();
                
        if(review.equalsIgnoreCase("")){
            do{
                System.out.print("\nYou have to write few lines for the teacher to attend class : ");
                review = sc.nextLine();
            }while(review.equalsIgnoreCase(""));
        }
        
        
        System.out.print("\nEnter Rating (1 to 5) : ");
        System.out.println("\n\n1 for Very dissatisfied");
        System.out.println("2 for Dissatisfied");
        System.out.println("3 for Ok");
        System.out.println("4 for Satisfied");
        System.out.println("5 for Very Satisfied");
        System.out.print("\n\nEnter your rating : ");
        String rating = sc.nextLine();
                
        if(rating.equalsIgnoreCase("") || !validateInput(rating)){
            do{
                System.out.print("\nRating must be valid integer value (1 to 5) : ");
                rating = sc.nextLine();
            }while(rating.equalsIgnoreCase("") || !validateInput(rating));
        }
        
        //Add rating
        Ratings.addRating(reservationID,review,rating);
        
        //Update status of reservation
        int slotNo = 0;
        int userID = 0;
        ArrayList<Reservations> reservationRecords = Reservations.getReservationRecords();
        for(Reservations obj : reservationRecords){
            if(obj.getReservationID().equalsIgnoreCase(reservationID)){
                slotNo = obj.getSlotNo();
                userID = obj.getUserID();
                obj.setReservationStatus("Attended");
                break;
            }
        }        
        //Increase seats after attending class
        Timetables.incSeats(slotNo);
        
        //Update grade level if attended higher level class
        int classGrade = Timetables.getClassGrade(slotNo);
        int userGrade = Users.getUserGrade(userID);
        ArrayList<Users> userRecords = Users.getUserRecords();
        if(classGrade > userGrade){
            for(Users obj : userRecords){
                if(obj.getUserID() == userID){
                    obj.setGradeLevel(userGrade+1);
                    break;
                }
            }
        }
                
        System.out.println("\nSuccess : You have attended class");
    }
    
   
}
