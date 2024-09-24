//Create a PHP script to connect to a MySQL database named "student" and insert a new record into a table called "student_info" with fields for student ID, name, email and course.

<?php
$conn = mysqli_connect("localhost", "username", "password", "student");

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$student_id = 1001;
$name = "John Doe";
$email = "john@example.com"; 
$course = "Web Development";

$sql = "INSERT INTO student_info (student_id, name, email, course) 
        VALUES ('$student_id', '$name', '$email', '$course')";

if (mysqli_query($conn, $sql)) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>



//Create a PHP form to collect student information (name, email, course) and insert it into the "student_info" table in the database.
//HTML Form (student_form.html):

<form action="insert_student.php" method="post">
    Name: <input type="text" name="name"><br>
    Email: <input type="email" name="email"><br>
    Course: <input type="text" name="course"><br>
    <input type="submit" value="Submit">
</form>

//PHP Script (insert_student.php):

<?php
$conn = mysqli_connect("localhost", "username", "password", "student");

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$name = $_POST['name'];
$email = $_POST['email'];
$course = $_POST['course'];

$sql = "INSERT INTO student_info (name, email, course) 
        VALUES ('$name', '$email', '$course')";

if (mysqli_query($conn, $sql)) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}

mysqli_close($conn);
?>



//Create a PHP script to retrieve all records from the "student_info" table and display them in an HTML table.

<?php
$conn = mysqli_connect("localhost", "username", "password", "student");

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT * FROM student_info";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    echo "<table border='1'>
    <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
    <th>Course</th>
    </tr>";
    
    while($row = mysqli_fetch_assoc($result)) {
        echo "<tr>";
        echo "<td>" . $row['student_id'] . "</td>";
        echo "<td>" . $row['name'] . "</td>";
        echo "<td>" . $row['email'] . "</td>";
        echo "<td>" . $row['course'] . "</td>";
        echo "</tr>";
    }
    echo "</table>";
} else {
    echo "0 results";
}

mysqli_close($conn);
?>



//Create a PHP script that allows searching for a student by name in the "student_info" table and displays the result.
//HTML Form (search_form.html):

<form action="search_student.php" method="post">
    Search by name: <input type="text" name="search_name">
    <input type="submit" value="Search">
</form>

//PHP Script (search_student.php):
<?php
$conn = mysqli_connect("localhost", "username", "password", "student");

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$search_name = $_POST['search_name'];

$sql = "SELECT * FROM student_info WHERE name LIKE '%$search_name%'";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
    while($row = mysqli_fetch_assoc($result)) {
        echo "ID: " . $row["student_id"]. " - Name: " . $row["name"]. " - Email: " . $row["email"]. " - Course: " . $row["course"]. "<br>";
    }
} else {
    echo "0 results";
}

mysqli_close($conn);
?>

