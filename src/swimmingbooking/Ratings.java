
package swimmingbooking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Ratings {
    
    private String reservationID;
    private String review;
    private int ratings;
    private int teacherID;
    private int  userID;
    private String attendedOn;

   public static ArrayList <Ratings> ratingRecords = new ArrayList<>();

    public Ratings(String reservationID, String review, int ratings, int teacherID, int userID, String attendedOn) {
        this.reservationID = reservationID;
        this.review = review;
        this.ratings = ratings;
        this.teacherID = teacherID;
        this.userID = userID;
        this.attendedOn = attendedOn;
    }

    public String getReservationID() {
        return reservationID;
    }

    public String getReview() {
        return review;
    }

    public int getRatings() {
        return ratings;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getUserID() {
        return userID;
    }

   

    public String getAttendedOn() {
        return attendedOn;
    }

    public static ArrayList<Ratings> getRatingRecords() {
        return ratingRecords;
    }
    
    
    //Add Rating
    public static void addRating(String reservationID, String review, String rating){
        ArrayList<Reservations> reservationRecords = Reservations.getReservationRecords();
        ArrayList<Timetables> timetableRecords = Timetables.getTimetableRecords();
        
        int userID = 0;
        int teacherID = 0;
        for(Reservations obj : reservationRecords){
            if(obj.getReservationID().equalsIgnoreCase(reservationID)){
                for(Timetables timetableObj : timetableRecords){
                    if(timetableObj.getSlotNo() == obj.getSlotNo()){
                        teacherID = timetableObj.getTeacher();
                    }
                }
                userID = obj.getUserID();
            }
        }
        
        // Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMM, yyyy h:mma");
        String attendedOn = currentDateTime.format(dateFormatter);
        
        Ratings obj = new Ratings(reservationID,review,Integer.parseInt(rating),teacherID,userID,attendedOn);
        ratingRecords.add(obj);
    }
    
    
    
}
