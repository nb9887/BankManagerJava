//	clientList.getClientList().get(i).addBankAccList(bankAccount);	
			
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
interface BANK_MENU
{
	int CHECKING_ACCOUNT = 1, MINUS_ACCOUNT = 2;
}

interface ACCOUNT_NUMBER
{
	String CHECKING_ACCOUNT = "001-", MINUS_ACCOUNT = "002-";
}

interface SHOW_CLIENT
{
	int SHOW_ALL = 1, SHOW_ME = 2;
}

interface CLOSE_ACCOUNT
{
	int REMOVE_ACCOUNT = 1;
}

class BankManager 
{
	public void enrollCustomerAndBankAcc(ClientList clientList)
	{	
		Scanner s = new Scanner(System.in);
		System.out.println("�� ���� ����� �����ϼ̽��ϴ�.");
		System.out.print("�̸��� �Է��ϼ��� : ");
		String name = s.nextLine();
		
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{
				System.out.println("Already exists. Getting address and phoneNum automatically");
				System.out.println("Enter BankAccountNumber");
				
				ArrayList<BankAccount> getAcc = clientList.getClientList().get(i).getBankAccList(); // �ҷ�����//
				BankAccount bankAccount = MakeAccount(); //���ο� ���� ����
					
				getAcc.add(bankAccount); // ���� �����Ѱ� �߰�
				//1,2 + 3

				System.out.println(name+"���� ������ �ִ� ���� �� : " + clientList.getClientList().get(i).getBankAccList().size());			
				return;
			}
			
		}
		
		System.out.print("Enter Address : ");
		String address = s.nextLine();
		System.out.print("Enter PhoneNumber : ");
		String phoneNumber = s.nextLine();
		
		BankAccount bankAccount = MakeAccount(); 
		
		Client e = new Client(name, address, phoneNumber); //�� ����
		e.getBankAccList().add(bankAccount); //������ ���¹�ȣ �־��ְ�
		clientList.getClientList().add(e); //������Ʈ�� ����� ���
		
	}
	public void closeAccount(ClientList clientList)
	{
		Scanner s = new Scanner(System.in);
		
		if(clientList.getClientList().size() == 0)
			System.out.println("�ƹ��� �����ϴ�.");
		else if(clientList.getClientList().size()!=0)
		{
			System.out.println("�̸��� �Է��ϼ��� : ");
			String name = s.nextLine();
		
			closeAccount(clientList, name);
		}
	}
	
	private void closeAccount(ClientList clientList, String name)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("���� �ؾ��ư�� �����̽��ϴ�");
		
		removeAccountInfo(clientList,name);
	}
	
	private void removeAccountInfo(ClientList clientList, String name)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("�� ���¸� ����մϴ�");
		for(int i =0 ; i< clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{
				for(int j = 0 ;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					System.out.println("���� ("+j+") :" + clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum());
				}
				break;
			}
		}
		System.out.println("������ ���¸� �Է��ϼ���");
		String bankAccNum = s.nextLine();
		
		
		for(int i = 0; i<clientList.getClientList().size();i++)
		{
			for(int j = 0 ; j <clientList.getClientList().get(i).getBankAccList().size();j++)
			{
				if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
				{
					if(clientList.getClientList().get(i).getBankAccList().get(j).getBalance()==0)
					{
						System.out.println("���¸� �����մϴ�");
						clientList.getClientList().get(i).getBankAccList().remove(j);
						if(clientList.getClientList().get(i).getBankAccList().size()==0)
						{
							System.out.println("���°� �����Ƿ� ���� �����մϴ�");
							clientList.getClientList().remove(i);
							return;
						}
					}
					else if(clientList.getClientList().get(i).getBankAccList().get(j).getBalance()!=0)
					{
						System.out.println("���¿� �ܾ��� �����ֽ��ϴ�. �ܾ��� ���� ����ּ���");
						break;
					}
				}
			}
		}
	}//for���������� ���°� 0���� ���� ����
	
	private BankAccount MakeAccount()
	{
		int credit = 8;
	//	String bankAccNum = null;
		int balance = 0;
		Scanner s = new Scanner(System.in);
		System.out.println("======��������=====");
		System.out.println("|  1. �Ϲ� ����         |");
		System.out.println("|  2. ���̳ʽ� ����   |");
		System.out.println("=================");
		System.out.print("���� : "); 
		int choice = s.nextInt();
		BankAccount account = null;
		
		String bankAccNum = null;
		switch(choice)
		{
			case BANK_MENU.CHECKING_ACCOUNT : 
				System.out.println("��ø� ��ٷ� �ּ���. ���¸� ���� ���Դϴ�...");
				System.out.println("�Ϲ� ���¹�ȣ�Դϴ�");

				bankAccNum = getAccountNumber(ACCOUNT_NUMBER.CHECKING_ACCOUNT);
				System.out.println("���¹�ȣ : " + bankAccNum);
				account = new CheckingAccount(bankAccNum,balance);
				
				break;
				
			case BANK_MENU.MINUS_ACCOUNT :
				System.out.println("��ø� ��ٷ� �ּ���. ���¸� ���� ���Դϴ�...");
				System.out.println("���̳ʽ� ���¹�ȣ�Դϴ�");
				
				bankAccNum = getAccountNumber(ACCOUNT_NUMBER.MINUS_ACCOUNT);
				System.out.println("���¹�ȣ : "  + bankAccNum);
		 		account = new MinusAccount(bankAccNum, balance, credit);
				break;
			
		}
		return account;
	}
	
	public void showClient(ClientList clientList)
	{
		System.out.println("���� ���� ����� �����ϼ̽��ϴ�.");
		if(clientList.getClientList().size() == 0)
			System.out.println("�ƹ��� �����ϴ�.");
		else if(clientList.getClientList().size()!=0)
			showClientMenu(clientList);
	
	}
	
	private void showClientMenu(ClientList clientList)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("==================");
		System.out.println("| 1. �������� ��ü���  |");
		System.out.println("| 2. �������� ���        |");
		System.out.println("==================");
		System.out.print("| Pick : "); 
		int choice = s.nextInt();
		
		switch(choice)
		{
			case SHOW_CLIENT.SHOW_ALL :
				showAllInfo(clientList);
				break;
			case SHOW_CLIENT.SHOW_ME :
				showMyInfo(clientList);
				break;
		}
	}
	
	private void showAllInfo(ClientList clientList)
	{
		System.out.println("�������� ��ü ����� �����ϼ̽��ϴ�");
		Collections.sort(clientList.getClientList(), new NameAscCompare());
		for (Client temp : clientList.getClientList()) {
			System.out.println(temp);
		}
	}
	
	private void showMyInfo(ClientList clientList)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("�������� ����� �����ϼ̽��ϴ�");
		System.out.println("�̸��� �Է��ϼ���");
		String name = s.nextLine();
	
		for(int i = 0 ; i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{	//��ü�� �Ѱ��ָ� toString�Լ��� ȣ���				
				System.out.println(clientList.getClientList().get(i).toString());
				System.out.println("===================");
			}
		}
	}
	
	private String getAccountNumber(String checkingacc)
	{
		int first_digit = getFirst(100,999);
		int second_digit = getSecond(1000,9999);
		return (checkingacc+String.valueOf(first_digit) +"-"+String.valueOf(second_digit));
	}
	
	static class NameAscCompare implements Comparator<Client> {
		 
		@Override
		public int compare(Client arg0, Client arg1) {
			return arg0.getName().compareTo(arg1.getName());
		}
 
	}
	
	private int getFirst(int n1, int n2)
	{
		return (int) (Math.random() * (n2 - n1 + 1)) + n1;
	}
	
	private int getSecond(int n1, int n2)
	{
		return (int) (Math.random() * (n2 - n1 + 1) + n1);
	}

}
