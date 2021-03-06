// 유심히보기
//http://kin.naver.com/qna/detail.nhn?d1id=1&dirId=1040202&docId=203964054&qb=7J6Q67CUIOykhOygleumrA==&enc=utf8&section=kin&rank=1&search_sort=0&spq=0&pid=SSxcywpySDwssvuiHcGsssssstZ-164522&sid=zsL2pwA4MH11aYzWQJq%2Bog%3D%3D
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

// 질문 BankAccount "", 123
// 송금입금.
// 전체 거래내역에서 왜 size가 계속 0인지. BankManager하고도 비교해봄 - > 아마 객체가 달라서
// transfer에서 receiver에 관한 객체를 반환시 MoneyLog다 바꾸어 주어야함
// transfer에서 맨처음 보내는사람의 clientIndex 및 계좌번호가 찍혀서 receiver모듈화함.

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
		int clientIndex = getClientIndex(clientList, name);

		for(int j = 0;j<clientList.getClientList().get(clientIndex).getBankAccList().size();j++)
		{
			if(bankAccNum.compareTo(clientList.getClientList().get(clientIndex).getBankAccList().get(j).getBankAccNum())==0)
			{
					return j;
			}
		} 
		return Constants.BANK_ACCOUNT_ERROR;
	}	
	
	private void showBankNumber(ClientList clientList, String name, int clientIndex)
	{
		System.out.println("=====계좌를 출력합니다=====");
			
		for(int j = 0;j<clientList.getClientList().get(clientIndex).getBankAccList().size();j++)
		{
			System.out.println("계좌 ("+j+") :"+clientList.getClientList().get(clientIndex).getBankAccList().get(j).getBankAccNum());
		} 	
		
	}
	
	//입금
	public void deposit(ClientList clientList, String name)
	{	
		Scanner s = new Scanner(System.in);
		int clientIndex = getClientIndex(clientList, name);
		
		showBankNumber(clientList, name, clientIndex); //계좌 출력
		System.out.println("은행계좌번호를 입력해주세요");
		String bankAccNum = s.nextLine();
		
		int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);
		int money = depositMoney(clientList, name , clientIndex, bankAccNum, bankAccIndex); //출금
		if(money == Constants.WRONG_DEPOSIT_INPUT)
		{
			System.out.println("입금이 잘못되었습니다");
			return; 
		}
		if(bankAccIndex == Constants.BANK_ACCOUNT_ERROR)
		{
			System.out.println("잘못된 계좌 번호입니다");
			return;
		}
		
		checkBalance(clientList, money, clientIndex, bankAccIndex); //잔액조회
		addDepositLog(clientList, name, money, clientIndex, bankAccIndex);//로그추가
	}
		
	private int depositMoney(ClientList clientList, String name, int clientIndex, String bankAccNum, int bankAccIndex)
	{	
		Scanner s = new Scanner(System.in);
		
		System.out.println("입금을 시작합니다");
		System.out.print("입금액 : "); 
		int money = s.nextInt();
		s.nextLine();
		System.out.println("해당계좌에 " + money + "원을 입금하시겠습니까? (Y/N)");
		String answer = s.nextLine();
		while(true)
		{
			if (answer.compareTo("Y") == 0 || answer.compareTo("y") == 0) 
			{
				System.out.println(money + "원을 입금했습니다");
				break;
			}
			else if (answer.compareTo("N") == 0 || answer.compareTo("n") == 0)
			{
				System.out.println("얼마를 입금하시겠습니까?");
				System.out.println("입금액을 다시 입력하세요");
				money = s.nextInt();
				s.nextLine();
				System.out.println("해당계좌에 " + money + "원을 입금하시겠습니까? (Y/N)");
				answer = s.nextLine();
			}
			else
				return Constants.WRONG_DEPOSIT_INPUT;
			
		}
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).addMoney(money);
		return money;
	}
	
	private Date getTodayDate()
	{
		//http://alvinalexander.com/java/simpledateformat-convert-string-to-date-formatted-parse
		Scanner s = new Scanner(System.in);
		String expectedPattern = "yyyyMMdd";
	    Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
	    try
	    {
	      String userInput = s.nextLine();
	      date = formatter.parse(userInput);
	      
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return date;
	}
	
	private Date getWeekDate()
	{
		Scanner s = new Scanner(System.in);
		Calendar cal = Calendar.getInstance();
		String expectedPattern = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		Date date = null;
		System.out.println("입력");
		String userInput = s.nextLine();
		
		try
		{
			cal.setTime( formatter.parse( userInput ) );
			cal.add( Calendar.DATE, -7 );	
			date = cal.getTime();
			
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		
		return date;
	}
	
	private Date getMonthDate()
	{
		Scanner s = new Scanner(System.in);
		Calendar cal = Calendar.getInstance();
		String expectedPattern = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		Date date = null;
		System.out.println("입력");
		String userInput = s.nextLine();
		
		try
		{
			cal.setTime( formatter.parse( userInput ) );
			cal.add( Calendar.DATE, -30 );	
			date = cal.getTime();
			
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		
		return date;
	}

		//Deposit 잔액조회
	private void checkBalance(ClientList clientList, int money, int clientIndex , int bankAccIndex)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("통장 잔고 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());	
	}
	
	private void addDepositLog(ClientList clientList, String name, int money, int clientIndex, int bankAccIndex)
	{
		Scanner s = new Scanner(System.in);
		System.out.print("날짜를 입력하세요 : ");
		Date date = getTodayDate();
		MoneyLog log = new MoneyLog(Constants.DEPOSIT, date, name, money);
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
	}
	
	private void addDepositLog(ClientList clientList, String name, Date date, int money, int clientIndex, int bankAccIndex)
	{
		Scanner s = new Scanner(System.in);
		
		MoneyLog log = new MoneyLog(Constants.DEPOSIT, date, name, money);
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
	}
	
	//인출계좌 출력
	public void withdraw(ClientList clientList, String name)
	{	
		Scanner s = new Scanner(System.in);
		int clientIndex = getClientIndex(clientList, name);
		
		showBankNumber(clientList, name, clientIndex);
		System.out.println("계좌를 선택하세요 ");
	    String bankAccNum = s.nextLine();
	    
	    int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);
	    
	    if(bankAccIndex == Constants.BANK_ACCOUNT_ERROR)
		{
			System.out.println("잘못된 계좌 번호입니다");
			return;
		}
	    
	    int money = withdrawMoney(clientList ,name, bankAccNum, clientIndex, bankAccIndex);	 //인출

	    if(money == Constants.WITHDRAW_MONEY_ERROR)
	    {
	    	System.out.println("출금이 잘못되었습니다");
	    	return;
	    }
		
	    addWithdrawLog(clientList, name, money, bankAccNum, clientIndex, bankAccIndex); //로그추가
		
	}
	

	private int withdrawMoney(ClientList clientList, String name, String bankAccNum, int clientIndex, int bankAccIndex) 
	{
		Scanner s = new Scanner(System.in);

		System.out.println("통장 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
	    System.out.println("얼마 인출하시겠습니까?");
	    
	    int money = s.nextInt();
	    s.nextLine();
	        
	    System.out.println("해당계좌에서" + money + "원을 인출하시겠습니까? (Y/N)");
        String answer = s.nextLine();
    
        while(true)
        {
        	if(answer.compareTo("Y")==0||answer.compareTo("y")==0)
        	{
        		if(money>clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance())
            	{
        			while(true)
        			{
        				System.out.println("통장 잔액"+ clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
	        			System.out.println("잔액이 부족합니다. 다시 입력해주세요");	
		        		money = s.nextInt();
		        		System.out.println("해당계좌에서" + money + "원을 인출하시겠습니까? (Y/N)");
		        		s.nextLine();
		        		answer = s.nextLine();
		        		break;
        			}
            	}
        		else if(money<=clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance())
        		{
        			System.out.println(money +"원을 인출 했습니다");		
    				clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).withdrawMoney(money);
    				System.out.println("출금 후 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
    				break;
        		}
        	}
        	
        	else if(answer.compareTo("N")==0||answer.compareTo("n")==0)
        	{
        		System.out.println("다시 입력해주세요");
        		System.out.println("통장 잔액 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBalance());
        		System.out.println("얼마 인출하시겠습니까?");
        		money = s.nextInt();
        		s.nextLine();
        		System.out.println("해당계좌에서" + money + "원을 인출하시겠습니까? (Y/N)");
        		answer = s.nextLine();
        		
        	}
        }
       
        return money;
	}
	
	private void addWithdrawLog(ClientList clientList, String name, int money, String bankAccNum, int clientIndex, int bankAccIndex)
	{
		Scanner s = new Scanner(System.in);

		System.out.println("날짜입력 하세요");
		Date date = getTodayDate();
//		String date = s.nextLine();
		MoneyLog log = new MoneyLog(Constants.WITHDRAW, date, name, money);
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
		System.out.println("성공적으로 로그를 추가했습니다");
	}
	
	//계좌이체
	public void transfer(ClientList clientList, String name)
	{
		int clientIndex = getClientIndex(clientList, name);
		Scanner s = new Scanner(System.in);
		
		showBankNumber(clientList, name, clientIndex);
		
		System.out.println("계좌이체할 계좌를 입력하세요");
		System.out.print("선택 : "); 			
		String bankAccNum = s.nextLine();
		
		int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);
		
		if(bankAccIndex == Constants.BANK_ACCOUNT_ERROR)
		{
			System.out.println("잘못된 계좌 번호입니다");
			return;
		}
		
		int money = withdrawMoney(clientList, name, bankAccNum, clientIndex, bankAccIndex); //돈 인출
		
		if(money == Constants.WITHDRAW_MONEY_ERROR)
		{
			System.out.println("인출이 잘못됐습니다");
			return;
		}
		
		bankAccNum = getReceiverBankAccount(clientList, name); // 수취인 은행계좌번호 출력

		String receiver = getReceiverName(clientList, name, bankAccNum);
		if(receiver == Constants.RECEIVER_NAME_ERROR)
			System.out.println("잘못된 수취인 이름입니다");
		Date date = addTransferLog(clientList, name, receiver, money, bankAccNum, clientIndex, bankAccIndex);
		
		clientIndex = getReceiverIndex(clientList, name, bankAccNum);
		if(clientIndex == Constants.RECEIVER_INDEX_ERROR)
			System.out.println("잘못된 수취인 인덱스입니다");
		
		bankAccIndex = getReceiverBankAccountIndex(clientList, name, bankAccNum);
		if(bankAccIndex == Constants.RECEIVER_BANK_ACCOUNT_INDEX_ERROR)
			System.out.println("잘못된 은행계좌 인덱스입니다");
		
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).addMoney(money);
		addDepositLog(clientList, name, date, money, clientIndex, bankAccIndex);
		System.out.println(receiver+"님이" + money + "원을 받았습니다");
	}
	
	private int getReceiverIndex(ClientList clientList, String name, String bankAccNum)
	{
		int receiverIndex = 0;
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())!=0)
			{
				for(int j =0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
					{
						receiverIndex = i;
						return receiverIndex;
					}
				}
			}
		}
		return Constants.RECEIVER_INDEX_ERROR;
	}
	
	private int getReceiverBankAccountIndex(ClientList clientList, String name, String bankAccNum)
	{
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())!=0)
			{
				for(int j =0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
					{
						return j;
					}
				}
			}
		}
		return Constants.RECEIVER_BANK_ACCOUNT_INDEX_ERROR;
	}
		
	private String getReceiverName(ClientList clientList, String name, String bankAccNum)
	{
		String receiver = "";
		for(int i =0;i<clientList.getClientList().size();i++)
		{
			if(name.compareTo(clientList.getClientList().get(i).getName())!=0)
			{
				for(int j =0;j<clientList.getClientList().get(i).getBankAccList().size();j++)
				{
					if(bankAccNum.compareTo(clientList.getClientList().get(i).getBankAccList().get(j).getBankAccNum())==0)
					{
						receiver = clientList.getClientList().get(i).getName();
						return receiver;
					}
				}
			}
		}
		return Constants.RECEIVER_NAME_ERROR;
	}
	private Date addTransferLog(ClientList clientList, String name, String receiver, int money, String bankAccNum, int clientIndex, int bankAccIndex)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("날짜를 입력하세요");
	//	String date = s.nextLine();
		Date date = getTodayDate();
		MoneyLog log = new MoneyLog(Constants.TRANSFER, date, name, receiver, money);
		clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().add(log);
		return date;
				
	}
	
	private String getReceiverBankAccount(ClientList clientList, String name)
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
	
	public void showStatement(ClientList clientList, String name) throws Exception
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
				showSortByDateMenu(clientList, name);
				break;
		}
		
		
	}
	
	private void showAllStatement(ClientList clientList, String name)
	{
		System.out.println("모든 거래내역을 출력합니다");
		
		int clientIndex = getClientIndex(clientList, name);

		for(int i =0 ; i<clientList.getClientList().get(clientIndex).getBankAccList().size();i++)
		{
			System.out.println("계좌번호 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(i).getBankAccNum());
			String bankAccNum = clientList.getClientList().get(clientIndex).getBankAccList().get(i).getBankAccNum();
			int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);	
	
			if(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size()==0)
				System.out.println("출력할 로그가 없습니다");
			for(int j = 0; j<clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size();j++)
			{			
				clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().get(j).showMoneyLog();							
			}					
		}				
	}
	
		
	private void showSortByDateMenu(ClientList clientList, String name) throws Exception
	{
		Scanner s = new Scanner(System.in);
		System.out.println("=================");
		System.out.println("| 1. 오늘 거래 출력    |");
		System.out.println("| 2. 일주일 거래 출력 |");
		System.out.println("| 3. 한달 거래 출력    |");
		System.out.println("=================");
		System.out.println("select : ");
		int choice = s.nextInt();
		switch(choice)
		{
			case Constants.PRINT_TODAY_STATEMENT:
				printTodayStatement(clientList,name);
				break;
			case Constants.PRINT_WEEK_STATEMENT:
				printWeeklyStatement(clientList, name);
				break;
			case Constants.PRINT_MONTH_STATEMENT:
				printMonthlyStatement(clientList, name);
				break;
			
		}
	}
	
	private void printTodayStatement(ClientList clientList, String name) throws Exception
	{
		System.out.println("오늘 거래를 출력합니다");
		int clientIndex = getClientIndex(clientList, name);
		System.out.println("오늘의 날짜를 입력하세요");
		Date date = getTodayDate();
		
		for(int i=0;i<clientList.getClientList().get(clientIndex).getBankAccList().size();i++)
		{
			String bankAccNum = clientList.getClientList().get(clientIndex).getBankAccList().get(i).getBankAccNum();
			int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);	
			
			for(int j = 0;j<clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size();j++)
			{
				if(clientList.getClientList().get(clientIndex).getBankAccList().get(i).transactionLogList().get(j).getDate().compareTo(date)==0)
				{
					System.out.println("계좌번호 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum());
					clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().get(j).showMoneyLog();		
					return;
				}
			}
		}	
	}
	
	private void printWeeklyStatement(ClientList clientList, String name) throws Exception
	{
		System.out.println("일주일 거래를 출력합니다");
		int clientIndex = getClientIndex(clientList, name);
		System.out.println("오늘의 날짜를 입력하세요");
		Date date = getWeekDate();
		
		for(int i=0;i<clientList.getClientList().get(clientIndex).getBankAccList().size();i++)
		{
			String bankAccNum = clientList.getClientList().get(clientIndex).getBankAccList().get(i).getBankAccNum();
			int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);	
			
			for(int j = 0;j<clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size();j++)
			{
				if(clientList.getClientList().get(clientIndex).getBankAccList().get(i).transactionLogList().get(j).getDate().after(date))
				{
					System.out.println("계좌번호 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum());
					Collections.sort(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList(), new NameAscCompare2());
					clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().get(j).showMoneyLog();		
				}
			}
		}	
	}
	
	private void printMonthlyStatement(ClientList clientList, String name) throws Exception
	{
		System.out.println("한 달 거래를 출력합니다");
		int clientIndex = getClientIndex(clientList, name);
		System.out.println("오늘의 날짜를 입력하세요");
		Date date = getMonthDate();
		
		for(int i=0;i<clientList.getClientList().get(clientIndex).getBankAccList().size();i++)
		{
			String bankAccNum = clientList.getClientList().get(clientIndex).getBankAccList().get(i).getBankAccNum();
			int bankAccIndex = getBankAccountIndex(clientList, name, bankAccNum);	
			
			for(int j = 0;j<clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().size();j++)
			{
				if(clientList.getClientList().get(clientIndex).getBankAccList().get(i).transactionLogList().get(j).getDate().after(date))
				{
					System.out.println("계좌번호 : " + clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).getBankAccNum());
					Collections.sort(clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList(), new NameAscCompare2());
					clientList.getClientList().get(clientIndex).getBankAccList().get(bankAccIndex).transactionLogList().get(j).showMoneyLog();		
				}
			}
		}	
	}
	
	static class NameAscCompare implements Comparator<BankAccount> 
	{
		@Override
		public int compare(BankAccount arg0, BankAccount arg1) 
		{
			return String.valueOf(arg0.getBalance()).compareTo(String.valueOf(arg1.getBalance()));
		}
 
	}
	
	static class NameAscCompare2 implements Comparator<MoneyLog>
	{
		@Override
		public int compare(MoneyLog arg0, MoneyLog arg1)
		{
			return String.valueOf(arg0.getDate()).compareTo(String.valueOf(arg1.getDate()));
		}
	}
	
}


