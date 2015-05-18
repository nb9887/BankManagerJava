// 유심히보기
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

// 질문 BankAccount "", 123
// 송금입금.
// 전체 거래내역에서 왜 size가 계속 0인지. BankManager하고도 비교해봄 - > 아마 객체가 달라서
// 

class ClientManager 
{
	private int getClientIndex(ClientList clientList, String name)
	{
		for(int i =0;i<clientList.getClientList().size();i++) 
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
				return i;	
		}
		
		return Constants.NAME_ERROR;
	}
	
	private int getBankAccountIndex(ClientList clientList, String name, String bankAccNum)
	{
		for(int i =0;i<clientList.getClientList().size();i++) 
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{
				for(int j = 0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
					{
						return j;
					}
				} 
			}
		}
		return -4;
	}	
	
	private void showBankNumber(ClientList clientList, String name, int clientIndex)
	{
		//if(name.compareTo(clientList.getClientList().get(clientIndex).getName())==0)
		//{
			System.out.println("=====계좌를 출력합니다=====");
			
			for(int j = 0;j<clientList.getClientList().get(clientIndex).getBankAccList().size();j++)
			{
				System.out.println("계좌 ("+j+") :"+clientList.getClientList().get(clientIndex).getBankAccList().get(j).getBankAccNum());
			} 	
		//}
	}
	
	//입금
	public void deposit(ClientList clientList, String name)
	{	
		int clientIndex = getClientIndex(clientList, name);
		depositMoney(clientList, name , clientIndex);
	}
		
	private void depositMoney(ClientList clientList, String name, int clientIndex)
	{	
		Scanner s = new Scanner(System.in);
		showBankNumber(clientList, name, clientIndex);
		System.out.println("=====계좌 입력해주세요=====");
		String bankAccNum = s.nextLine();
		int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);
	//	if(bankAccNum.compareTo(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum())==0) 
	//	{
			System.out.println("입금을 시작합니다");
			System.out.print("입금액 : "); 
			int money = s.nextInt();
			s.nextLine();
			System.out.println("해당계좌에 " + money + "원을 입금하시겠습니까? (Y/N)");
			String answer = s.nextLine();
		
			if (answer.compareTo("Y") == 0 || answer.compareTo("y") == 0) 
			{
				checkBalance(clientList, money, bankAccNum, clientIndex, bankAccIndex);
				System.out.println(money + "원을 입금했습니다");
			}

			else if (answer.compareTo("N") == 0 || answer.compareTo("n") == 0)
			{
				System.out.println("얼마를 입금하시겠습니까?");
				System.out.println("입금액을 다시 입력하세요");
				money = s.nextInt();
				s.nextLine();
				System.out.println("해당계좌에 " + money + "원을 입금하시겠습니까? (Y/N)");
				answer = s.nextLine();
				checkBalance(clientList, money, bankAccNum, clientIndex, bankAccIndex);
				System.out.println(money + "원을 입금했습니다");
			} 
			else
				System.out.println("다시 입력하세요");
	//	}
	}
	

		//Deposit 잔액조회
	private int checkBalance(ClientList clientList, int money, String bankAccNum, int clientIndex , int bankAccIndex)
	{
		Scanner s = new Scanner(System.in);
		
		String name = clientList.getClientList().get(clientIndex).getName();
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).addMoney(money);
		System.out.println("통장 잔고 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
		System.out.print("날짜를 입력하세요 : ");
		String enterDate = s.nextLine();
		getDepositLog(clientList, enterDate, name, money, bankAccNum, clientIndex, bankAccIndex);
		return money;
	
	}
		
	
	private void getDepositLog(ClientList clientList, String date, String name, int money, String bankAccNum, int clientIndex, int bankAccIndex)
	{
		if(bankAccNum.compareTo(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum())==0)
		{
			MoneyLog log = new MoneyLog(Constants.DEPOSIT, date, name, money);
			clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
			return;
		}
		
	}

	//Overloading
	private void getDepositLog(ClientList clientList, String date, String name, int money, String bankAccNum)
	{
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())==0)
			{
				for(int j = 0; j <clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
					{
						MoneyLog log = new MoneyLog(Constants.DEPOSIT, date, name, money);
						clientList.getClientList().get(i).getBankAccList().get(j).transactionLogList().add(log);
					}
				}
			}
		}
	}
		
	//인출계좌 출력
	public void withdraw(ClientList clientList, String name)
	{	
		int clientIndex = getClientIndex(clientList, name);
		showBankNumber(clientList, name, clientIndex);
		Scanner s = new Scanner(System.in);
		System.out.println("계좌를 선택하세요 ");
	    String bankAccNum = s.nextLine();
	    
	    int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);
		int money = withdrawMoney(clientList ,name, bankAccNum, clientIndex, bankAccIndex);	
		
		if(money < clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance())
		{
			System.out.println("날짜입력 하세요");
			String date = s.nextLine();
			System.out.println("checking withdraw");
			getWithdrawLog(clientList, date, name, money, bankAccNum, clientIndex, bankAccIndex);
			return;
		}	
	}
	

	private int withdrawMoney(ClientList clientList, String name, String bankAccNum, int clientIndex, int bankAccIndex) 
	{
		Scanner s = new Scanner(System.in);

		System.out.println("통장 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
	    System.out.println("얼마 인출하시겠습니까?");
	    int money = s.nextInt();
	    s.nextLine();
	        
	    if(money>clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance())
	    {
	      	System.out.println("잔액이 부족합니다. 다시 입력해주세요");
			
	    }
	    else
	    {
	    	System.out.println("해당계좌에서" + money + "원을 인출하시겠습니까? (Y/N)");
	        String answer = s.nextLine();
		        
	        if(answer.compareTo("Y")==0||answer.compareTo("y")==0)
			{
				System.out.println(money +"원을 인출 했습니다");		
				clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).withdrawMoney(money);
				System.out.println("출금 후 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
				return money;
	        }
	        else
	        {
	          		System.out.println("다시 입력해주세요");
	           		System.out.println("통장 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
			        System.out.println("얼마 인출하시겠습니까?");
			        money = s.nextInt();
			        System.out.println(money +"원을 인출 했습니다");		
					clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).withdrawMoney(money);
					System.out.println("출금 후 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
						
					return money;
	        }	
		}   	
	    return 0;
	}
	
	private void getWithdrawLog(ClientList clientList, String date, String name, int money, String bankAccNum, int clientIndex, int bankAccIndex)
	{
		if(bankAccNum.compareTo(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum())==0)
		{
			MoneyLog log = new MoneyLog(Constants.WITHDRAW, date, name, money);
			clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
			System.out.println("성공적으로 로그를 추가했습니다");
			return;
		}
	}
	
	//계좌이체
	public void transfer(ClientList clientList, String name)
	{
		int clientIndex = getClientIndex(clientList, name);
		showBankNumber(clientList, name, clientIndex);
		System.out.println("계좌이체할 계좌를 입력하세요");
		Scanner s = new Scanner(System.in);
		System.out.print("선택 : "); 			
		String bankAccNum = s.nextLine();
		
		System.out.println("날짜입력 하세요");
		String date = s.nextLine();
		String receiver = "";
		int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);
		
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())!=0)
			{
				for(int j =0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())!=0)
					{
						receiver = clientList.getClientList().get(i).getName();
					}
				}
			}
		}	
		
		int money = withdrawMoney(clientList, name, bankAccNum, clientIndex, bankAccIndex); //돈 인출
		addTransferLog(clientList, date, name, receiver, money, bankAccNum, clientIndex, bankAccIndex);
		
		bankAccNum = findReceiverBankAcc(clientList, name); // 수취인 은행계좌번호 출력
		
//		System.out.println("clientIndex"+clientIndex);
//		System.out.println("bankAccIndex"+bankAccIndex);
		//찍어보면 보내는사람의 clientIndex 및 계좌번호가 찍혀서 새로 for문을 해주는게 좋을듯
		
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			for(int j =0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
			{
				if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
				{	
					clientList.getClientList().get(i).getBankAccList().get(j).addMoney(money);
					getDepositLog(clientList,date,receiver,money,bankAccNum);
					System.out.println(receiver+"님이" + money + "원을 받았습니다");
					return;
				}
			}
		}
	}
		
	
	private void addTransferLog(ClientList clientList, String date, String name, String receiver, int money, String bankAccNum, int clientIndex, int bankAccIndex)
	{
		if(bankAccNum.compareTo(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum())==0)
		{
			MoneyLog log = new MoneyLog(Constants.TRANSFER, date, name, receiver, money);
			clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
			return;
		}			
	}
	
	private String findReceiverBankAcc(ClientList clientList, String name)
	{	
		Scanner s = new Scanner(System.in);
		System.out.println("=========================");
		System.out.println("송금할 상대방의 모든 계좌를 출력합니다");
		
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())!=0)
			{
				
				for(int j =0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{	
					System.out.println("계좌 ("+j+") :"+clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum());
				}
			}
		}
		
		System.out.println("계좌이체할 계좌를 입력하세요");
		System.out.print("선택 : "); 
		String bankAccNum = s.nextLine();
		return bankAccNum;
	}
	
	public void showBalance(ClientList clientList, String name)
	{
		System.out.println("계좌를 잔액 순으로 출력합니다.");
		int clientIndex = getClientIndex(clientList, name);
		
		Collections.sort(clientList.getClientList().get(clientIndex).getBankAccList(), new NameAscCompare());
		System.out.println(clientList.getClientList().get(clientIndex).getBankAccList());
		System.out.println("총 잔액 : " + addTotal(clientList,name, clientIndex));

	}
	
	private int addTotal(ClientList clientList, String name, int clientIndex)
	{
		int total = 0;
	
		for(int j =0;j<clientList.getClientList().get(clientIndex).getBankAccList().size();j++)
		{
			total+= clientList.getClientList().get(clientIndex).getBankAccList().get(j).getBalance();	 
		}
		return total;
	}
	
	public void showStatement(ClientList clientList, String name)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("====================");
		System.out.println("| 1. Print All     |");
		System.out.println("| 2. Print by Date |");
		System.out.println("====================");
		System.out.print("선택하세요 : "); 
		int choice = s.nextInt();
		
		switch(choice)
		{
			case Constants.PRINT_ALL:
				showAllStatement(clientList, name);
				break;
			case Constants.PRINT_DATE:
				sortByDate(clientList);
				//java에서 제공하는 date참조
				break;
		}
		
		
	}
	
	
	
	private void showAllStatement(ClientList clientList, String name)
	{
		System.out.println("모든 거래내역을 출력합니다");
		
		int clientIndex = getClientIndex(clientList, name);
		//log 출력

		for(int j =0 ; j<clientList.getClientList().get(clientIndex).getBankAccList().size();j++)
		{
			System.out.println("계좌번호 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(j).getBankAccNum());
			String bankAccNum = clientList.getClientList().get(clientIndex).getBankAccList().get(j).getBankAccNum();
			int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);	
	
			if(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size()==0)
				System.out.println("출력할 로그가 없습니다");
			for(int k = 0; k<clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size();k++)
			{			
				clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().get(k).showMoneyLog();							
			}					
		}				
	}
	
	private void sortByDate(ClientList clientList)
	{
		LocalDate now = LocalDate.now();
		LocalDate seven = now.minusDays(7);
		LocalDate three = now.minusDays(3);
		LocalDate thirty = now.minusDays(30);
		
		System.out.println("now" + now);
		System.out.println("three " + three);
	}
	
	static class NameAscCompare implements Comparator<BankAccount> 
	{
		 
		@Override
		public int compare(BankAccount arg0, BankAccount arg1) 
		{
			return String.valueOf(arg0.getBalance()).compareTo(String.valueOf(arg1.getBalance()));
		}
 
	}
	
}

