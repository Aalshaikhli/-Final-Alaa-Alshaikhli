import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class Jdbcdemo
{
public static void main(String[] args)
{
int choice;
//Word Storing Section.
//A do while loop to take multiple words from the user.
do{
//A scanner object to take input word from user.
Scanner in = new Scanner(System.in);
//Try catch to handle checked exception like sqlexception and classnotfound exception.
try
{
//Asking user to enter a word.
System.out.println("Enter a word:");
String word = in.next();
//Loading the jdbc driver.
Class.forName("com.mysql.jdbc.Driver");
//Establishing Connection.
Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordoccurences","root","123456");
//Creating statement.
PreparedStatement ps = con.prepareStatement("insert into word (words) values(?)");
//Setting the word entered by the user in the query.
ps.setString(1,word);
//Executing the query.
ps.executeUpdate();
//Showing the database table word after addition of the user word.
System.out.println("Database after addition of your word");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("select * from word");
while(rs.next()){
System.out.println(rs.getString(1));
}

con.close();
}
catch(ClassNotFoundException |SQLException c){
System.out.println(c.getMessage());
}
System.out.println("Do you want to enter more word(1 for yes)");
choice = in.nextInt();
}while(choice==1);
//Calculating Word Frequency Section.
System.out.println("Frequency of each word present in the database is:");
//Creating a map to store the word and corresponding frequency.
Map<String,Integer> frequency = new LinkedHashMap<>();
//Same as word storing section accessing the database and get the resultset.
try{
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordoccurences","root","123456");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("Select * from word");
while(rs.next()){
//Getting the word from the database.
String s = rs.getString(1);
//Checking if the word already present in map or not.
if(frequency.get(s)==null){
//If not present then adding the word with frequency 1 in the map.
frequency.put(s,1);
}
else{
//Else increasing the frequency if it's already present in the map.
frequency.put(s,frequency.get(s)+1);
}
}
//Closing the database connection.
con.close();
}
catch(ClassNotFoundException | SQLException s){
System.out.println(s.getMessage());
}
//Printing the words with it's frequency.
Set<String> key = frequency.keySet();
for(String k:key){
System.out.println("Word: "+k+" frequency: "+frequency.get(k));
}
}
}

