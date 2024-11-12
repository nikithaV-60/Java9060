//    100523729060
//    V NIKITHA 
/*
     This program is used to calculate DOB or Age w.r.t a reference date, based on what the user gives as input .
     i.e. if DOB is given it calculates Age in years, months and days & DOB when the age is given 
 */
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AgeCalculator
 {
    public static void main(String[] args) 
    {
        // Validating command line arguments
        if (args.length < 4)
        {
            System.out.println("Please provide: <DOB/AGE> <referenceDate> <format> <delimiter>");
            return;
        }

        try 
        {
            String input = args[0];         // First argument: DOB=27-02-2001 or AGE=22-04-20
            String refDate = args[1];       // Second argument: Reference date
            String format = args[2];        // Third argument: Date format
            String delimiter = args[3];     // Fourth argument: Delimiter (-, /, etc.)

            // Create date formatter based on user format and delimiter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.replace("dlc", delimiter));

            if (input.startsWith("DOB=")) 
            {
                // Calculate age if DOB is provided
                LocalDate birthDate = LocalDate.parse(input.substring(4), formatter);
                LocalDate referenceDate = LocalDate.parse(refDate, formatter);
                calculateAge(birthDate, referenceDate);
            } 
            else if (input.startsWith("AGE=")) 
            {
                // Calculate DOB if age is provided
                LocalDate referenceDate = LocalDate.parse(refDate, formatter);
                calculateDOB(input.substring(4), referenceDate, delimiter, formatter);
            } 
            else 
            {
                System.out.println("Input must start with 'DOB=' or 'AGE='");
            }
        } catch (DateTimeParseException e) 
        {
            System.out.println("Invalid date format!");
        } catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Calculate and print age
    private static void calculateAge(LocalDate birthDate, LocalDate referenceDate) 
    {
        Period age = Period.between(birthDate, referenceDate);
        System.out.printf("Age is %d years, %d months, %d days%n", 
            age.getYears(), age.getMonths(), age.getDays());
    }

    // Calculate and print DOB
    private static void calculateDOB(String ageInput, LocalDate referenceDate, String delimiter, DateTimeFormatter formatter) 
    {
        // Split age into components (days-months-years)
        String[] ageParts = ageInput.split(delimiter);
        
        // Convert age components to numbers
        int days = Integer.parseInt(ageParts[0]);
        int months = Integer.parseInt(ageParts[1]);
        int years = Integer.parseInt(ageParts[2]);

        // Calculate DOB by subtracting age from reference date
        LocalDate dob = referenceDate.minusYears(years).minusMonths(months).minusDays(days);
        
        System.out.println("DOB: " + dob.format(formatter));
    }
}
