package utils;

import main.teacher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.time.Duration;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Map; 


public class functionsUtils {

    public static int CheckValidatedIntegerInput(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid integer.");
        }
    }

    public static float getValidatedFloatInput(String input) {
        try {
            return Float.parseFloat(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid float.");
        }
    }

    public static List<teacher> readTeachersFromCSV(String filePath) {
        List<teacher> teacherList = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            br.readLine();
    
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 12) {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    String department = fields[2].trim();
                    String course = fields[3].trim();
                    String day = fields[4].trim();
                    String time = fields[5].trim();
                    float payrate = Float.parseFloat(fields[6].trim());
                    float hoursPerDay = Float.parseFloat(fields[7].trim());
                    float totalHoursPerWeek = Float.parseFloat(fields[8].trim());
                    float totalHoursPerMonth = Float.parseFloat(fields[9].trim());
                    float grossSalary = Float.parseFloat(fields[10].trim());
                    float netSalary = Float.parseFloat(fields[11].trim());

                    teacherList.add(new teacher(id, name, department, course, day, time, payrate, hoursPerDay, totalHoursPerWeek, totalHoursPerMonth,grossSalary, netSalary));
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing the ID field: " + e.getMessage());
        }
    
        return teacherList;
    }

    // Helper method to append teacher data to the CSV file
    public static void appendTeacherToCSV(teacher newTeacher) {
        String filePath = "professors_timetable.csv";

        try {
            // Calculate hours per day and total hours per week
            long hoursPerDay = calculateHoursPerDay(newTeacher.getTime());
            int daysPerWeek = newTeacher.getDay().split("/").length; // Count selected days
            long totalHoursPerWeek = hoursPerDay * daysPerWeek;

            // Update teacher object with calculated hours
            newTeacher.setHoursPerDay(hoursPerDay);
            newTeacher.setTotalHoursPerWeek(totalHoursPerWeek);

            // Calculate total hours per month (assuming 4 weeks per month)
            float totalHoursPerMonth = totalHoursPerWeek * 4; // Assuming 4 weeks in a month
            newTeacher.setTotalHoursPerMonth(totalHoursPerMonth);

            // Calculate gross salary (payrate * totalHoursPerMonth)
            float grossSalary = newTeacher.getPayrate() * totalHoursPerMonth;
            newTeacher.setGrossSalary(grossSalary);

            // Calculate net salary (assuming 10% deduction for simplicity)
            float netSalary = grossSalary * 0.90f; // 10% tax deduction
            newTeacher.setNetSalary(netSalary);

            // Write the teacher details to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(newTeacher.getId() + ","
                        + newTeacher.getName() + ","
                        + newTeacher.getDepartment() + ","
                        + newTeacher.getCourse() + ","
                        + newTeacher.getDay() + ","
                        + newTeacher.getTime() + ","
                        + newTeacher.getPayrate() + ","
                        + newTeacher.getHoursPerDay() + ","
                        + newTeacher.getTotalHoursPerWeek() + ","
                        + newTeacher.getTotalHoursPerMonth() + ","
                        + newTeacher.getGrossSalary() + ","
                        + newTeacher.getNetSalary());
                writer.newLine(); // Move to the next line after appending
                System.out.println("Teacher added to file: " + newTeacher.getName());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

     public static long calculateHoursPerDay(String time) {
        if (time == null || !time.contains("-")) {
            throw new IllegalArgumentException("Invalid time format. Expected format: 10AM-12PM.");
        }
    
        try {
            // Trim spaces and ensure the input is in uppercase
            time = time.trim().toUpperCase();
    
            // Debug log for input verification
            System.out.println("Parsed time input: " + time);
    
            // Split the time into start and end times
            String[] timeParts = time.split("-");
            if (timeParts.length != 2) {
                throw new IllegalArgumentException("Invalid time format. Expected format: 10AM-12PM.");
            }
    
            // Parse start and end times using DateTimeFormatter with Locale.US
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ha", Locale.US); // Explicitly set Locale.US
            LocalTime startTime = LocalTime.parse(timeParts[0].trim(), formatter);
            LocalTime endTime = LocalTime.parse(timeParts[1].trim(), formatter);
    
            // Calculate the duration in hours
            long hours = Duration.between(startTime, endTime).toHours();
            if (hours <= 0) {
                throw new IllegalArgumentException("Invalid time range. End time must be after start time.");
            }
    
            return hours;
        } catch (DateTimeParseException e) {
            // Log and rethrow the exception for invalid time formats
            System.err.println("Error parsing time: " + e.getMessage());
            throw new IllegalArgumentException("Invalid time format. Ensure it's in the format 10AM-12PM.");
        }
    }
    
    public static List<teacher> searchTeacherFromCSV(String idInput, String name) {
        List<teacher> searchResults = new ArrayList<>();
        String filePath = "professors_timetable.csv";  // Path to your CSV file
        int id = -1;

        // Validate and parse the ID input
        if (!idInput.isEmpty()) {
            try {
                id = Integer.parseInt(idInput.trim());
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid integer for ID.");
                return searchResults;  // Return empty results if ID is invalid
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header line if it exists
            reader.readLine();

            // Read through each line of the CSV file
            while ((line = reader.readLine()) != null) {
                String[] teacherData = line.split(","); // Split by comma

                // Ensure the line has the expected number of columns (id, name, department, course, day, time, payrate, hoursPerDay, totalHoursPerWeek)
                if (teacherData.length == 12) {
                    int teacherId = Integer.parseInt(teacherData[0].trim());
                    String teacherName = teacherData[1].trim();

                    // Check if ID and/or Name matches
                    boolean idMatches = (id != -1 && teacherId == id);
                    boolean nameMatches = (!name.isEmpty() && teacherName.equalsIgnoreCase(name));

                    // Add to search results based on the search criteria
                    if ((id != -1 && name.isEmpty() && idMatches) || 
                        (id == -1 && !name.isEmpty() && nameMatches) || 
                        (id != -1 && !name.isEmpty() && idMatches && nameMatches)) {
                        // Teacher found, add to search results
                        teacher foundTeacher = new teacher(
                                teacherId,
                                teacherName,
                                teacherData[2].trim(),
                                teacherData[3].trim(),
                                teacherData[4].trim(),
                                teacherData[5].trim(),
                                Float.parseFloat(teacherData[6].trim()),
                                Float.parseFloat(teacherData[7].trim()),
                                Float.parseFloat(teacherData[8].trim()),
                                Float.parseFloat(teacherData[9].trim()),
                                Float.parseFloat(teacherData[10].trim()),
                                Float.parseFloat(teacherData[11].trim())
                        );
                        searchResults.add(foundTeacher);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return searchResults;
    }

public static void deleteTeacherByIdOrName(int id, String name) {
    boolean teacherFound = false;
    String filePath = "professors_timetable.csv"; // Path to your CSV file

    try {
        // Read all lines from the file into a list
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        // Check if the file contains a header and retain it
        List<String> updatedLines = new ArrayList<>();
        if (!lines.isEmpty()) {
            updatedLines.add(lines.get(0)); // Add header row
        }

        // Iterate through the file's lines and remove the matching teacher
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] teacherData = line.split(","); // Split by comma

            if (teacherData.length == 12) {
                int teacherId = Integer.parseInt(teacherData[0].trim());
                String teacherName = teacherData[1].trim();

                // Check if ID or Name matches
                boolean idMatches = (id != -1 && teacherId == id);
                boolean nameMatches = (!name.isEmpty() && teacherName.equalsIgnoreCase(name));

                if (idMatches || nameMatches) {
                    teacherFound = true;
                    System.out.println("Teacher with ID " + teacherId + " and Name " + teacherName + " has been deleted.");
                    continue; // Skip this line to delete the teacher
                }
            }
            updatedLines.add(line); // Add non-matching lines to the updated list
        }

        // Write the updated lines back to the file
        Files.write(Paths.get(filePath), updatedLines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

        if (!teacherFound) {
            System.out.println("No teacher found with ID: " + id + " or Name: " + name);
        } else {
            System.out.println("The CSV file has been updated successfully.");
        }

    } catch (IOException e) {
        System.err.println("Error processing the CSV file: " + e.getMessage());
    }
}

    public static List<teacher> displayTeachersFromCSV(String filePath) {
        List<teacher> teacherList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 12) {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    String department = fields[2].trim();
                    String course = fields[3].trim();
                    String day = fields[4].trim();
                    String time = fields[5].trim();
                    float payrate = Float.parseFloat(fields[6].trim());
                    float hoursPerDay = Float.parseFloat(fields[7].trim());
                    float totalHoursPerWeek = Float.parseFloat(fields[8].trim());
                    float totalHoursPerMonth = Float.parseFloat(fields[9].trim());
                    float grossSalary = Float.parseFloat(fields[10].trim());
                    float netSalary = Float.parseFloat(fields[11].trim());

                    teacherList.add(new teacher(id, name, department, course, day, time, payrate, hoursPerDay, totalHoursPerWeek, totalHoursPerMonth, grossSalary, netSalary));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("The file does not exist: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing the ID field: " + e.getMessage());
        }

        return teacherList;
    }

    public static void updateTeacherExtraHoursInCSV(teacher teacherItem, Map<Integer, Float> extraHoursMap) {
        // Load the list of teachers from the CSV
        List<teacher> teacherList = readTeachersFromCSV("professors_timetable.csv");
    
        // Iterate over the list and find the matching teacher by ID
        for (teacher t : teacherList) {
            if (t.getId() == teacherItem.getId()) {
                // Get the extra hours for the teacher
                float extraHours = extraHoursMap.getOrDefault(t.getId(), 0f);
                
                // Calculate the base monthly hours and add the extra hours
                float baseHours = t.getHoursPerDay() * 24;  // Assume base is hours per day * 24 days in a month
                float totalHoursMonthly = baseHours + extraHours;
        
                // Calculate the extra pay, gross salary, and net salary
                float extraPay = extraHours * t.getPayrate() * 1.5f;  // Assuming 1.5x rate for extra hours
                float grossSalary = (baseHours * t.getPayrate()) + extraPay;
                float netSalary = grossSalary - (grossSalary * 0.05f);  // Assuming 5% deduction for taxes
        
                // Update the teacher object with the new values
                t.setTotalHoursPerMonth(totalHoursMonthly);
                t.setGrossSalary(grossSalary);
                t.setNetSalary(netSalary);
                break;
            }
        }
    
        // Save the updated teacher data back to the CSV
        saveTeachersToCSV(teacherList, "professors_timetable.csv");
    }
    

        // // Utility function to save teacher data to CSV
        private static void saveTeachersToCSV(List<teacher> teacherList, String filePath) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                // Write the header
                writer.write("ID,Name,Department,Course,Day,Time,Payrate,HoursPerDay,TotalHoursPerWeek,TotalHoursPerMonth,GrossSalary,NetSalary\n");
    
                // Write each teacher's data
                for (teacher t : teacherList) {
                    writer.write(String.format("%d,%s,%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f\n",
                            t.getId(), t.getName(), t.getDepartment(), t.getCourse(), t.getDay(), t.getTime(),
                            t.getPayrate(), t.getHoursPerDay(), t.getTotalHoursPerWeek(), t.getTotalHoursPerMonth(),
                            t.getGrossSalary(), t.getNetSalary()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            // Update bonus and recalculate the teacher's gross and net salary
            public static void updateTeacherBonusInCSV(teacher teacherItem, float bonusPercentage, Map<Integer, Float> extraHoursMap) {
                // Load the list of teachers from the CSV
                List<teacher> teacherList = readTeachersFromCSV("professors_timetable.csv");
            
                // Iterate over the list and find the matching teacher by ID
                for (teacher t : teacherList) {
                    if (t.getId() == teacherItem.getId()) {
                        // Calculate the base monthly hours and the gross salary
                        float baseHours = t.getHoursPerDay() * 24;  // Assume base is hours per day * 24 days in a month
                        float extraHours = extraHoursMap.getOrDefault(t.getId(), 0f);
                        float extraPay = extraHours * t.getPayrate() * 1.5f;
                        float grossSalary = (baseHours * t.getPayrate()) + extraPay;
            
                        // Apply the bonus to the gross salary
                        float bonus = (bonusPercentage / 100) * grossSalary;
                        grossSalary += bonus;
            
                        // Recalculate the net salary after the bonus
                        float netSalary = grossSalary - (grossSalary * 0.05f);  // Assuming 5% deduction for taxes
            
                        // Update the teacher object with the new values
                        t.setGrossSalary(grossSalary);
                        t.setNetSalary(netSalary);
                        break;
                    }
                }
            
                // Save the updated teacher data back to the CSV
                saveTeachersToCSV(teacherList, "professors_timetable.csv");
            }
            
            
}


// Commit 14
