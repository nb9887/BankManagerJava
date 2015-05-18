import java.io.Serializable;
import java.util.ArrayList;

class BankAccount implements Serializable
{
	protected String bankAccNum;
	protected int balance;
	private ArrayList<MoneyLog> transactionLogList; 

	public BankAccount(String bankAccNum, int balance)
	{
		this.bankAccNum = bankAccNum;
		this.balance = 0;
		transactionLogList = new ArrayList<MoneyLog>();
	}
	
	public ArrayList<MoneyLog> transactionLogList()
	{
		return transactionLogList;
	}
	
	public String getBankAccNum()
	{
		return bankAccNum;
	}
	
	public int addMoney(int money)
	{
		balance += money; 
		return balance;
	}
	
	public int withdrawMoney(int money) //ÀÜ¾×Á¶È¸
	{
		balance-=money;
		return balance;
	}
	
	public int getBalance()
	{
		return balance;
	}
}

class CheckingAccount extends BankAccount
{
	public CheckingAccount(String bankAccNum, int balance)
	{
		super(bankAccNum,balance);
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append("°èÁÂ¹øÈ£ : ").append(bankAccNum).append(", ");
		str.append("°èÁÂÁ¾·ù : ").append(Constants.CHECKING_ACCOUNT).append(", ");
		str.append("ÀÜ¾× : ").append(balance);
		
		return str.toString();
	}
}

class MinusAccount extends BankAccount
{
	private int credit;// °í°´ÀÌ °¡Á®¾ßÇÒ °Í
	
	public MinusAccount(String bankAccNum, int balance, int credit)
	{
		super(bankAccNum, balance);
		this.credit = credit;
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		
		str.append("°èÁÂ¹øÈ£ : ").append(bankAccNum).append(", ");
		str.append("°èÁÂÁ¾·ù : ").append(Constants.MINUS_ACCOUNT).append(", ");
		str.append("°í°´ÀÇ ½Å¿ëµî±Ş : ").append(credit).append(", ");
		str.append("ÀÜ¾× : ").append(balance);
		
		return str.toString();
	}
}