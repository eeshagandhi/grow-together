import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class Sell extends Frame 
{
	Button sellBookButton;
	TextField s_idText, b_idText,dayText,profitText;
	TextArea errorText;
	Connection connection;
	Statement statement;
	public Sell() 
	{
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB();
	}

	public void connectToDB() 
    {
		try 
		{
		  connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","eesha","eeshasoham");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void buildGUI() 
	{		
		//Handle Insert Account Button
		sellBookButton = new Button("Sell Book");
		sellBookButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
				  			  
				  String query= "INSERT INTO sells VALUES(" + s_idText.getText() + ", " + "'" + b_idText.getText() + ", " + "'" + dayText.getText()+", " + "'" + profitText.getText()+ ")";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});

	
		s_idText = new TextField(15);
		b_idText = new TextField(15);
		dayText = new TextField(15);
		profitText = new TextField(15);
		
		
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Seller ID:"));
		first.add(s_idText);
		first.add(new Label("Book ID:"));
		first.add(b_idText);
		first.add(new Label("Day:"));
		first.add(dayText);
		first.add(new Label("Profit:"));
		first.add(profitText);
		
		
		first.setBounds(125,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(sellBookButton);
        	second.setBounds(125,220,150,100); 
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,320,300,200);
		
		
		
		setLayout(null);

		add(first);
		add(second);
		add(third);
		
	    
		setTitle("Selling a book");
		Color clr = new Color(230, 190, 250);
		setBackground(clr); 
		setFont(new Font("Cambria", Font.BOLD, 15)); 
		setSize(500, 600);
		setVisible(true);
	}

	

	private void displaySQLErrors(SQLException e) 
	{
		errorText.append("\nSQLException: " + e.getMessage() + "\n");
		errorText.append("SQLState:     " + e.getSQLState() + "\n");
		errorText.append("VendorError:  " + e.getErrorCode() + "\n");
	}

	

	public static void main(String[] args) 
	{
		Sell s = new Sell();

		s.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		
		s.buildGUI();
	}
}
