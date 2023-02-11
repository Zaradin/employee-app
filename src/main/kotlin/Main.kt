import java.math.BigDecimal
import java.math.RoundingMode

import Employee
import mu.KotlinLogging

//Globol Variable for holding employees
var employees = EmployeeAPI()

val logger = KotlinLogging.logger {}


fun main(args: Array<String>){
    logger.info { "Launching Employee App" }
    start()
}


fun menu() : Int {
    logger.info { "Printing Menu" }
    print(""" 
         |  ** Employee Menu System (Made By Josh Crotty) **
         |   1. Add Employee
         |   2. List All Employees
         |   3. Search Employees 
         |   4. Print Payslip for Employee
         |   -----------------------------
         |     ** Extra Functionality **
         |   -----------------------------
         |   5. Highest paid Employees
         |   6. Edit Employee Details
         |   7. Delete an Employee by ID
         |   -99 Import dummy data
         |  -1. Exit
         |       
         |Enter Option : """.trimMargin())
    return readLine()!!.toInt()
}

fun start() {
    var input: Int

    do {
        input = menu()
        when (input) {
            1 -> add()
            2 -> list()
            3 -> search()
            4 -> paySlip()
            5 -> employees.highestPaidEmployees()
            6 -> editEmployeeInfo()
            7 -> deleteEmployee()
            -99 -> dummyData()
            -1 -> println("Exiting App")
            else -> println("Invalid Option")
        }
        println()
    } while (input != -1)
}

fun list(){
    employees.findAll()
        .forEach{ println(it) }
}

internal fun getEmployeeById(): Employee? {
    print("Enter the employee id to search by: ")
    val employeeID = readLine()!!.toInt()
    return employees.findOne(employeeID)
}


fun search() {
    val employee = getEmployeeById()
    if (employee == null)
        println("No employee found")
    else
        println(employee)
}

fun paySlip(){
    logger.info { "Printing Employee Payslip" }
    val employee = getEmployeeById()
    if (employee != null)
        println(employee.getPaySlipRounding())
}

fun deleteEmployee(){
    logger.info { "Delete employee by ID" }

    println("*List of employees*")
    employees.findAll()
        .forEach{ println("""
            ID: ${it.employeeID}
            Name: ${it.getFullName()}
        """.trimIndent())}

    println("Please enter the ID of the employee you would like to remove: ")

    var empID = readln().toInt()

    add()
    employees.deleteEmployeeByID(empID)
}

// Edit employees information
fun editEmployeeInfo(){
    // List employees with thier ID and Name so the user can pick an optin to edit
    println("*List of employees*")
    employees.findAll()
        .forEach{ println("""
            ID: ${it.employeeID}
            Name: ${it.getFullName()}
            -----------------------------
        """.trimIndent())}

    // Call API function to make the changes
    employees.editEmployee()
}

fun dummyData() {
    employees.create(Employee("Joe", "Soap", 'm', 0, 35655.43, 31.0, 7.5, 2000.0, 25.6))
    employees.create(Employee("Joan", "Murphy", 'f', 0, 54255.13, 32.5, 7.0, 1500.0, 55.3))
    employees.create(Employee("Mary", "Quinn", 'f', 0, 75685.41, 40.0, 8.5, 4500.0, 0.0))
    employees.create(Employee("Josh", "Crotty", 'm', 0, 87345.67, 40.0, 8.5, 5000.0, 36.0))
}

fun add(){
    print("Enter first name: ")
    val firstName = readLine().toString()
    print("Enter surname: ")
    val surname = readLine().toString()
    print("Enter gender (m/f): ")
    val gender = readLine()!!.toCharArray()[0]
    print("Enter gross salary: ")
    val grossSalary = readLine()!!.toDouble()
    print("Enter PAYE %: ")
    val payePercentage = readLine()!!.toDouble()
    print("Enter PRSI %: ")
    val prsiPercentage = readLine()!!.toDouble()
    print("Enter Annual Bonus: ")
    val annualBonus= readLine()!!.toDouble()
    print("Enter Cycle to Work Deduction: ")
    val cycleToWorkMonthlyDeduction= readLine()!!.toDouble()

    employees.create(Employee(firstName, surname, gender, 0, grossSalary, payePercentage, prsiPercentage, annualBonus, cycleToWorkMonthlyDeduction))
}


