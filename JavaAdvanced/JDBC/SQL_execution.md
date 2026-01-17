## Simple and Prepared Statements

### Simple Statements

**Simple Statements** are used to execute static SQL queries in JDBC. They are created using the `createStatement` method of the `Connection` interface. Simple statements are typically used for executing SQL queries that do not require parameters.

Example of creating and using a Simple Statement:

```java

Connection connection = DriverManager.getConnection(url, user, password);

Statement statement = connection.createStatement();

String query = "SELECT * FROM users";

ResultSet resultSet = statement.executeQuery(query);

while (resultSet.next()) {

    int id = resultSet.getInt("id");

    String name = resultSet.getString("name");

    String email = resultSet.getString("email");

    System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);

}

```

### Prepared Statements

**Prepared Statements** are used for executing parameterized SQL queries. They are created using the `prepareStatement` method of the `Connection` interface. Prepared statements are precompiled, which can improve performance, especially for repeated execution of similar queries. They also help prevent SQL injection attacks.

Example of creating and using a Prepared Statement:

```java

Connection connection = DriverManager.getConnection(url, user, password);

String query = "SELECT * FROM users WHERE id = ?";

PreparedStatement preparedStatement = connection.prepareStatement(query);

preparedStatement.setInt(1, 1); // Setting parameter value

ResultSet resultSet = preparedStatement.executeQuery();

while (resultSet.next()) {

    int id = resultSet.getInt("id");

    String name = resultSet.getString("name");

    String email = resultSet.getString("email");

    System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);

}

```

## SQL Injection

**SQL Injection** is a type of security vulnerability that occurs when an attacker is able to insert or "inject" malicious SQL code into a query. This can happen if the application constructs SQL queries directly from user input without proper validation or escaping. SQL injection can lead to unauthorized access to database data, data manipulation, and other security issues.

### Example of SQL Injection Vulnerability

```java

String userId = request.getParameter("userId");

String query = "SELECT * FROM users WHERE id = " + userId;

Statement statement = connection.createStatement();

ResultSet resultSet = statement.executeQuery(query);

```

If `userId` is set to `1 OR 1=1`, the query becomes:

```sql

SELECT * FROM users WHERE id = 1 OR 1=1

```

This query will return all rows from the `users` table because the condition `1=1` is always true.

### Preventing SQL Injection with Prepared Statements

Prepared statements help prevent SQL injection by separating the SQL code from the data, ensuring that user input is treated as data rather than executable code.

```java

String userId = request.getParameter("userId");

String query = "SELECT * FROM users WHERE id = ?";

PreparedStatement preparedStatement = connection.prepareStatement(query);

preparedStatement.setInt(1, Integer.parseInt(userId));

ResultSet resultSet = preparedStatement.executeQuery();

```

In this example, the user input is properly parameterized and cannot alter the SQL code.

## Callable Statements

**Callable Statements** are used to execute stored procedures in the database. They are created using the `prepareCall` method of the `Connection` interface. Callable statements can accept input parameters and return output parameters or result sets.

### Example of Using a Callable Statement

Assume we have a stored procedure in the database defined as follows:

```sql

CREATE PROCEDURE getUserById(IN userId INT, OUT userName VARCHAR(50), OUT userEmail VARCHAR(50))

BEGIN

    SELECT name, email INTO userName, userEmail FROM users WHERE id = userId;

END;

```

We can call this stored procedure from a Java application using a Callable Statement:

```java

Connection connection = DriverManager.getConnection(url, user, password);

String sql = "{CALL getUserById(?, ?, ?)}";

CallableStatement callableStatement = connection.prepareCall(sql);

callableStatement.setInt(1, 1); // Setting input parameter

callableStatement.registerOutParameter(2, Types.VARCHAR); // Registering output parameters

callableStatement.registerOutParameter(3, Types.VARCHAR);

callableStatement.execute();

String userName = callableStatement.getString(2); // Retrieving output parameters

String userEmail = callableStatement.getString(3);

System.out.println("Name: " + userName + ", Email: " + userEmail);

```

In this example:

- The `CALL getUserById(?, ?, ?)` SQL statement is used to invoke the stored procedure.

- Input and output parameters are set using the `setInt` and `registerOutParameter` methods, respectively.

- The `execute` method runs the stored procedure, and output parameters are retrieved using the `getString` method.