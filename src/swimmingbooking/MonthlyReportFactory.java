
package swimmingbooking;

public class MonthlyReportFactory {
    
    
    public static MonthlyReports generateReport(int option) {
        switch (option) {
            case 5 -> {
                return new MonthlyUserReport();
            }
            case 6 -> {
                return new MonthlyRatingReport();
            }
            default -> throw new IllegalArgumentException("Invalid report option");
        }
    }
}
