import java.text.ParseException;
import java.util.Scanner;
import java.util.Random;


interface BIG_MENU
{
	int TELLER = 1, CLIENT = 2, EXIT = 3;
}

class BankMain
{

	public static void main(String[] args) throws Exception
	{
		
		MenuViewer menuViewer = new MenuViewer();
		ClientList clientList = new ClientList();
		FileHandler fileHandler = new FileHandler();
		Scanner input = new Scanner(System.in);
		
		fileHandler.loadFile(clientList); // 파일핸들러로 뺴줘서 
		while(true)
		{
			menuViewer.printInitialMenu();
			int choice = input.nextInt();
			
			if(choice == BIG_MENU.EXIT)
				break;
				
			
			switch(choice)
			{
				case BIG_MENU.TELLER:
					menuViewer.printTellerMenu(clientList);
					break;
				case BIG_MENU.CLIENT :
					if(clientList.getClientList().size()==0)
					{
						System.out.println("고객이 아무도 없습니다");
						break;
					}
					System.out.println("Enter Name : ");
					input.nextLine();
					String name = input.nextLine();
					for(int i =0;i<clientList.getClientList().size();i++)
					{
						if(name.compareTo(clientList.getClientList().get(i).getName())==0)
						{
							menuViewer.printClientMenu(clientList, name);
							break;
						}
						else if(i==clientList.getClientList().size()-1&&name.compareTo(clientList.getClientList().get(i).getName())!=0)
						{
							System.out.println("고객이 존재하지 않습니다");
							break;
						}
					}
			}
				
		}
		fileHandler.saveFile(clientList);
	}
}
