import java.text.ParseException;
import java.util.Scanner;

interface TELLER
{
	int OPENACCOUNT = 1, CLOSEACCOUNT = 2, SHOWCLIENT = 3, EXIT = 4;
}

interface CLIENT_
{
	int DEPOSIT = 1, WITHDRAW = 2, TRANSFER = 3, SHOW_BALANCE = 4, SHOW_STATEMENT = 5, EXIT = 6;
}

class MenuViewer 
{
	public void printInitialMenu()
	{
		System.out.println("==================");
		System.out.println("|    1. Teller   |");
		System.out.println("|    2. Client   |");
		System.out.println("|    3. Exit     |");
		System.out.println("==================");
		System.out.print("| Pick :  ");
		
		
	}
	
	public void printTellerMenu(ClientList clientList)
	{
		Scanner input = new Scanner(System.in);
		BankManager bankManager = new BankManager();
		//TellerMenu 积己矫 ClientList啊 货肺 积己
		System.out.println("=====================");
		System.out.println("| 1. OPEN ACCOUNT   |");
		System.out.println("| 2. CLOSE ACCOUNT  |");
		System.out.println("| 3. SHOW CLIENT    |");
		System.out.println("| 4. EXIT           |");
		System.out.println("=====================");
		System.out.print("| Pick : ");
	
		int choice = input.nextInt();

		switch(choice)
		{
		case TELLER.OPENACCOUNT:
			bankManager.enrollCustomerAndBankAcc(clientList);
			break;
		case TELLER.CLOSEACCOUNT:
			bankManager.closeAccount(clientList);
			break;
		case TELLER.SHOWCLIENT:
			bankManager.showClient(clientList);
			break;
		case TELLER.EXIT:
			break;
		}
		
	}
	
	public void printClientMenu(ClientList clientList, String name) throws Exception
	{
		ClientManager clientManager = new ClientManager();
		Scanner input = new Scanner(System.in);
		
		System.out.println("========================");
		System.out.println("|    1. DEPOSIT        |");
		System.out.println("|    2. WITHDRAW       |");
		System.out.println("|    3. TRANSFER       |");
		System.out.println("|    4. SHOW BALANCE   |");
		System.out.println("|    5. SHOW STATEMENT |");
		System.out.println("|    6. EXIT           |");
		System.out.println("========================");
		System.out.print("| Pick : ");
	
		int choice = input.nextInt();
		
		switch(choice)
		{
		case CLIENT_.DEPOSIT:
			clientManager.deposit(clientList, name);
			break;
		case CLIENT_.WITHDRAW:
			clientManager.withdraw(clientList, name);
			break;
		case CLIENT_.TRANSFER:
			clientManager.transfer(clientList, name);
			break;
		case CLIENT_.SHOW_BALANCE:
			clientManager.showBalance(clientList, name);
			break;
		case CLIENT_.SHOW_STATEMENT:
			clientManager.showStatement(clientList , name);
			break;
		case CLIENT_.EXIT:
			break;
		}
	}
	
	
}
