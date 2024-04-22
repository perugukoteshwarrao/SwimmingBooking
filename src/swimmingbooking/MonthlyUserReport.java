
package swimmingbooking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MonthlyUserReport implements MonthlyReports{

    @Override
    public void monthlyReports(){
        Scanner  sc = new Scanner(System.in);
        System.out.print("\nEnter Month Number : ");
        String inputMonth = sc.nextLine();
        
        if(inputMonth.equalsIgnoreCase("") || !SwimmingBooking.validateInput(inputMonth) || (Integer.parseInt(inputMonth) < 1 
                || Integer.parseInt(inputMonth) > 12)){
            do{
                System.out.print("\nEnter Valid Month Number (1 to 12) : ");
                inputMonth = sc.nextLine();
            }while(inputMonth.equalsIgnoreCase("") || !SwimmingBooking.validateInput(inputMonth) || (Integer.parseInt(inputMonth) < 1 
                    || Integer.parseInt(inputMonth) > 12));
        }
        

        ArrayList<Reservations> reservationsRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Users> userRecords = Users.getUserRecords();
       
        System.out.println();

        HashMap<Integer, int[]> userData = new HashMap<>();
        
        Set<String> uniqueLearnerCode = new HashSet<>();             
        for (int j = 0; j < reservationsRecords.size(); j++) {


            for (int i = 0; i < userRecords.size(); i++) {


                String monthNumber = "";
                for (Timetables timetableObj : timetableRecords) {

                    if(timetableObj.getSlotNo() == reservationsRecords.get(j).getSlotNo()){

                        //Parse timetable classOn field
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");                

                        try {
                            LocalDateTime parsedDateTime = LocalDate.parse(timetableObj.getClassOn(), formatter).atStartOfDay();
                            monthNumber = parsedDateTime.format(DateTimeFormatter.ofPattern("M"));
                        } catch (DateTimeParseException e) {
                            System.out.println("Error parsing datetime: " + e.getMessage());
                        }
                        break;
                    }
                }

                String uniqueKey = String.valueOf(userRecords.get(i).getUserID()) + reservationsRecords.get(j).getReservationID();

                if (!uniqueLearnerCode.contains(uniqueKey)) {
                    uniqueLearnerCode.add(uniqueKey);

                    if (userRecords.get(i).getUserID() == reservationsRecords.get(j).getUserID() && monthNumber.equalsIgnoreCase(inputMonth)) {

                        int uniqueCode = userRecords.get(i).getUserID();
                        int[] counter = userData.getOrDefault(uniqueCode, new int[3]);

                        if (reservationsRecords.get(j).getReservationStatus().equalsIgnoreCase("Booked") 
                                || reservationsRecords.get(j).getReservationStatus().equalsIgnoreCase("Changed")) {
                            counter[0]++;
                        }
                        if (reservationsRecords.get(j).getReservationStatus().equalsIgnoreCase("Attended")) {
                            counter[1]++;
                        }
                        if (reservationsRecords.get(j).getReservationStatus().equalsIgnoreCase("Cancelled")) {
                            counter[2]++;
                        }
                        userData.put(uniqueCode, counter);
                    }
                }
            }
        }
        
        if(!userData.isEmpty()){
            for (Map.Entry<Integer, int[]> entry : userData.entrySet()) {
                int userID = entry.getKey();
                int[] counter = entry.getValue();

                //User details
                String userFullName = "";
                for (int i = 0; i < userRecords.size(); i++) {
                   if(userRecords.get(i).getUserID() == userID){
                       userFullName = userRecords.get(i).getFullname();
                   }
                }

                System.out.println("\nUser : "+ userFullName+"\n");
                
                System.out.println("\nBooked Lessons : "+counter[0]);
                System.out.println("Attended Lessons : "+counter[1]);
                System.out.println("Cancelled Lessons : "+counter[2]);
                
                System.out.println("\nLesson Details as follows : ");
                 
                System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("%-13s %-30s %-20s %-25s %-12s\n",
                        "SlotNo", "LessonName", "Slot", "ClassOn",  "Status");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                for (int j = 0; j < reservationsRecords.size(); j++) {
                    
                    if(reservationsRecords.get(j).getUserID() == userID){
                        

                        //Lesson Grade Level
                        String lessonname = "";
                        String slot = "";
                        String classOn = "";
                        int slotNo = 0;
                        for (Timetables timetableObj : timetableRecords) {
                            if(timetableObj.getSlotNo() == reservationsRecords.get(j).getSlotNo()){
                                lessonname = timetableObj.getLessonName();
                                slotNo = timetableObj.getSlotNo();
                                slot = timetableObj.getSlot();
                                classOn = timetableObj.getClassOn();
                            }
                        }
                        System.out.printf("%-13s %-30s %-20s %-25s %-12s\n",
                        slotNo, lessonname, slot, classOn,  reservationsRecords.get(j).getReservationStatus());
                    }
                }               
            }
        }else{
             System.out.println("No Record Found");
        }
    }
}
