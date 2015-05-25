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
		System.out.println("고객 정보 등록을 선택하셨습니다.");
		System.out.print("이름을 입력하세요 : ");
		String name = s.nextLine();
		
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{
				System.out.println("Already exists. Getting address and phoneNum automatically");
				System.out.println("Enter BankAccountNumber");
				
				ArrayList<BankAccount> getAcc = clientList.getClientList().get(i).getBankAccList(); // 불러오기//
				BankAccount bankAccount = MakeAccount(); //새로운 계좌 생성
					
				getAcc.add(bankAccount); // 계좌 생성한것 추가
				//1,2 + 3

				System.out.println(name+"님이 가지고 있는 계좌 수 : " + clientList.getClientList().get(i).getBankAccList().size());			
				return;
			}
			
		}
		
		System.out.print("Enter Address : ");
		String address = s.nextLine();
		System.out.print("Enter PhoneNumber : ");
		String phoneNumber = s.nextLine();
		
		BankAccount bankAccount = MakeAccount(); 
		
		Client e = new Client(name, address, phoneNumber); //고객 생성
		e.getBankAccList().add(bankAccount); //생성된 계좌번호 넣어주고
		clientList.getClientList().add(e); //고객리스트에 만든고객 등록
		
	}
	public void closeAccount(ClientList clientList)
	{
		Scanner s = new Scanner(System.in);
		
		if(clientList.getClientList().size() == 0)
			System.out.println("아무도 없습니다.");
		else if(clientList.getClientList().size()!=0)
		{
			System.out.println("이름을 입력하세요 : ");
			String name = s.nextLine();
		
			closeAccount(clientList, name);
		}
	}
	
	private void closeAccount(ClientList clientList, String name)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("계좌 해약버튼을 누르셨습니다");
		
		removeAccountInfo(clientList,name);
	}
	
	private void removeAccountInfo(ClientList clientList, String name)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("내 계좌를 출력합니다");
		for(int i =0 ; i< clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{
				for(int j = 0 ;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					System.out.println("계좌 ("+j+") :" + clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum());
				}
				break;
			}
		}
		System.out.println("삭제할 계좌를 입력하세요");
		String bankAccNum = s.nextLine();
		
		
		for(int i = 0; i<clientList.getClientList().size();i++)
		{
			for(int j = 0 ; j <clientList.getClientList().get(i).getBankAccList().size();j++)
			{
				if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
				{
					if(clientList.getClientList().get(i).getBankAccList().get(j).getBalance()==0)
					{
						System.out.println("계좌를 삭제합니다");
						clientList.getClientList().get(i).getBankAccList().remove(j);
						if(clientList.getClientList().get(i).getBankAccList().size()==0)
						{
							System.out.println("계좌가 없으므로 고객도 삭제합니다");
							clientList.getClientList().remove(i);
							return;
						}
					}
					else if(clientList.getClientList().get(i).getBankAccList().get(j).getBalance()!=0)
					{
						System.out.println("계좌에 잔액이 남아있습니다. 잔액을 먼저 비워주세요");
						break;
					}
				}
			}
		}
	}//for문끝나고나서 계좌가 0개면 고객도 삭제
	
	private BankAccount MakeAccount()
	{
		int credit = 8;
	//	String bankAccNum = null;
		int balance = 0;
		Scanner s = new Scanner(System.in);
		System.out.println("======계좌종류=====");
		System.out.println("|  1. 일반 계좌         |");
		System.out.println("|  2. 마이너스 계좌   |");
		System.out.println("=================");
		System.out.print("선택 : "); 
		int choice = s.nextInt();
		BankAccount account = null;
		
		String bankAccNum = null;
		switch(choice)
		{
			case BANK_MENU.CHECKING_ACCOUNT : 
				System.out.println("잠시만 기다려 주세요. 계좌를 개설 중입니다...");
				System.out.println("일반 계좌번호입니다");

				bankAccNum = getAccountNumber(ACCOUNT_NUMBER.CHECKING_ACCOUNT);
				System.out.println("계좌번호 : " + bankAccNum);
				account = new CheckingAccount(bankAccNum,balance);
				
				break;
				
			case BANK_MENU.MINUS_ACCOUNT :
				System.out.println("잠시만 기다려 주세요. 계좌를 개설 중입니다...");
				System.out.println("마이너스 계좌번호입니다");
				
				bankAccNum = getAccountNumber(ACCOUNT_NUMBER.MINUS_ACCOUNT);
				System.out.println("계좌번호 : "  + bankAccNum);
		 		account = new MinusAccount(bankAccNum, balance, credit);
				break;
			
		}
		return account;
	}
	
	public void showClient(ClientList clientList)
	{
		System.out.println("직원 정보 출력을 선택하셨습니다.");
		if(clientList.getClientList().size() == 0)
			System.out.println("아무도 없습니다.");
		else if(clientList.getClientList().size()!=0)
			showClientMenu(clientList);
	
	}
	
	private void showClientMenu(ClientList clientList)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("==================");
		System.out.println("| 1. 직원정보 전체출력  |");
		System.out.println("| 2. 개인정보 출력        |");
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
		System.out.println("직원정보 전체 출력을 선택하셨습니다");
		Collections.sort(clientList.getClientList(), new NameAscCompare());
		for (Client temp : clientList.getClientList()) {
			System.out.println(temp);
		}
	}
	
	private void showMyInfo(ClientList clientList)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("개인정보 출력을 선택하셨습니다");
		System.out.println("이름을 입력하세요");
		String name = s.nextLine();
	
		for(int i = 0 ; i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{	//객체를 넘겨주면 toString함수가 호출되				
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
