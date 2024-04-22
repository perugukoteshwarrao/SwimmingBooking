
package swimmingbooking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class MonthlyRatingReport implements MonthlyReports{
    
    @Override
    public void monthlyReports(){
        Scanner  sc = new Scanner(System.in);
        System.out.print("\nEnter Month Number : ");
        String inputMonth = sc.nextLine();
        
        if(inputMonth.equalsIgnoreCase("") || !SwimmingBooking.validateInput(inputMonth) || (Integer.parseInt(inputMonth) < 1 || 
                Integer.parseInt(inputMonth) > 12)){
            do{
                System.out.print("\nEnter Valid Month Number (1 to 12) : ");
                inputMonth = sc.nextLine();
            }while(inputMonth.equalsIgnoreCase("") || !SwimmingBooking.validateInput(inputMonth) || (Integer.parseInt(inputMonth) < 1
                    || Integer.parseInt(inputMonth) > 12));
        }
        
        
        ArrayList<Ratings> ratingsRecords = Ratings.getRatingRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        ArrayList<Teachers> teacherRecords = Teachers.getTeacherRecords();
        
         
        HashMap<String, Integer> ratingRecords = new HashMap<>();
        HashMap<String, Integer> totalRecords = new HashMap<>();
        HashMap<String, Double> calculatedRating = new HashMap<>();
       
        System.out.println();
       
        for (Ratings obj : ratingsRecords){
            for (Timetables timetableObj : timetableRecords){
                
                 //Parse timetable classOn field
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");                
                
                String monthNumber = "";
                try {
                    LocalDateTime parsedDateTime = LocalDate.parse(timetableObj.getClassOn(), formatter).atStartOfDay();
                    monthNumber = parsedDateTime.format(DateTimeFormatter.ofPattern("M"));
                } catch (DateTimeParseException e) {
                    System.out.println("Error parsing datetime: " + e.getMessage());
                }
                    
                if(timetableObj.getTeacher() == obj.getTeacherID() && monthNumber.equalsIgnoreCase(inputMonth)){
                    int teacher = obj.getTeacherID();
                    int sumrating = obj.getRatings();
                    
                    String teachername = "";
                    for(Teachers tObj : teacherRecords){
                        if(tObj.getTeacherID() == teacher){
                            teachername = tObj.getTeacherName();
                        }
                    }

                    ratingRecords.put(teachername, ratingRecords.getOrDefault(teachername, 0) + sumrating);
                    totalRecords.put(teachername, totalRecords.getOrDefault(teachername, 0) + 1);
                }
            }
        }

        for (String teachername : ratingRecords.keySet()) {
            int ratingsSum = ratingRecords.get(teachername);
            int total = totalRecords.get(teachername);

            if (total > 0) {
                double calavgRate = (double) ratingsSum / total;
                double rounfRating = Math.round(calavgRate * 10.0) / 10.0; 
                calculatedRating.put(teachername, rounfRating);
            }
        }
        if(!calculatedRating.isEmpty()){
            System.out.println("\n**********************************************");
            System.out.printf("%-20s %-15s \n", "Teacher", "AvgRating");
            System.out.println("**********************************************");
            for (String teachername : calculatedRating.keySet()) {
                double averageRating = calculatedRating.get(teachername);
                System.out.printf("%-20s %-15s \n", teachername, averageRating);
            }
        }else{
            System.out.println("No Record Found");
        }
    }
}
