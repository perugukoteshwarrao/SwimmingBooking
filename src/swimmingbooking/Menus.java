
package swimmingbooking;

import java.util.Scanner;
import static swimmingbooking.SwimmingBooking.validateInput;


public class Menus {
 
    
    
    //Menu Options
    public static int adminMenuOptions(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n\n(Admin Menu) ");
        System.out.println("Select Option from below menu : ");
        System.out.println("1. Timetable");
        System.out.println("2. Users");
        System.out.println("3. Reservations");
        System.out.println("4. Ratings");
        System.out.println("5. Monthly Learner Report");
        System.out.println("6. Monthly Rating Report");
        System.out.println("7. Add New Learner");
        System.out.println("8. Return to Main Menu");
        System.out.print("\nEnter Option : ");
        String menuOption = sc.nextLine();
        while (menuOption.equals("") || !validateInput(menuOption))
        {
            System.out.print("\nEnter Valid Option ");
            menuOption = sc.nextLine();
        }
        return Integer.parseInt(menuOption);
    }
    
    
     
    //User Menu Options
    public static int userMenuOptions(){
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n(User Menu) ");
        System.out.println("Select Option from below menu : ");
        System.out.println("1. Timetable By Day");
        System.out.println("2. Timetable By Grade Level");
        System.out.println("3. Timetable By Coach");
        System.out.println("4. My Reservations");
        System.out.println("5. My Ratings");
        System.out.println("6. Return to Main Menu");
        System.out.print("\nEnter Option : ");
        String menuOption = sc.nextLine();
        while (menuOption.equals("") || !validateInput(menuOption))
        {
            System.out.print("\nEnter Valid Option ");
            menuOption = sc.nextLine();
        }
        return Integer.parseInt(menuOption);
    }
 
    
      //Admin Menu
    public static void adminMenu(){     
        
        int selectedOption;
        do {
            selectedOption = adminMenuOptions();
            switch (selectedOption) {
                case 1 -> SwimmingBooking.timetable();
                case 2 -> Users.users();
                case 3 -> SwimmingBooking.reservations();
                case 4 -> SwimmingBooking.ratings();
                case 5 ->{
                        MonthlyReports report = MonthlyReportFactory.generateReport(selectedOption);
                        report.monthlyReports();
                       }
                case 6 -> {
                        MonthlyReports report = MonthlyReportFactory.generateReport(selectedOption);
                        report.monthlyReports();
                        }
                case 7 -> Users.addLearner();
                case 8 -> {
                            return;
                        }
                default -> System.out.println("\nPlease enter a valid choice (1-8)");
            }
        } while (selectedOption != 8);
    }
    
   
    //User Menu
    public static void userMenu(){     
        int selectedOption;
        do {
            selectedOption = userMenuOptions();
            switch (selectedOption) {
                case 1 -> SwimmingBooking.timetableByDay();
                case 2 -> SwimmingBooking.timetableByGradeLevel();
                case 3 -> SwimmingBooking.timetableByTeacher();
                case 4 -> SwimmingBooking.myReservations();
                case 5 -> SwimmingBooking.myRatings();
                case 6 -> {
                            return;
                        }
                default -> System.out.println("\nPlease enter a valid choice (1-6)");
            }
        } while (selectedOption != 6);
    }
 
    
    //Show Main Menu
    public static int showMenuFunctions(){     
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\nSelect Option from below menu : ");
        System.out.println("1. Use System as Admin");
        System.out.println("2. Use System as User");
        System.out.println("3. Exit Program");
        System.out.print("\nEnter Option : ");

        String menuOption = sc.nextLine();
        while (menuOption.equals("") || !validateInput(menuOption))
        {
            System.out.print("\nEnter Valid Option ");
            menuOption = sc.nextLine();
        }
        return Integer.parseInt(menuOption);
    }
 
    
}
