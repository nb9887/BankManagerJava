//import java.io.Serializable;
import java.io.Serializable;
import java.util.ArrayList;

class Client implements Serializable
{
	private String name;
	private String address;
	private String phoneNum;
	private int maxLoan; //대출한도
	private ArrayList<BankAccount> bankAccList; 
	
	public Client(String name, String address, String phoneNum)
	{
		setName(name);
		setAddress(address);
		setPhoneNum(phoneNum);
		bankAccList = new ArrayList<BankAccount>();	
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setPhoneNum(String phoneNum)
	{
		this.phoneNum = phoneNum;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public String getPhoneNum()
	{
		return phoneNum;
	}
	
	public ArrayList<BankAccount> getBankAccList()
	{
		return bankAccList;
	}
	
	public String getClientName(int i)
	{
		return name;
	}
	
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		System.out.println("=========================");
		str.append("이름 : ").append(name).append(", ");
		str.append("주소 : ").append(address).append(", ");
		str.append("전화번호 : ").append(phoneNum).append(", ");
		for(int i =0; i<bankAccList.size(); i++)
		{
			str.append("계좌번호 : ").append(bankAccList.get(i).getBankAccNum()).append(", ");
			str.append("계좌 잔액 : ").append(bankAccList.get(i).getBalance());
		}
		return str.toString();
	}
	

}
