//import java.io.Serializable;
import java.io.Serializable;
import java.util.ArrayList;

class Client implements Serializable
{
	private String name;
	private String address;
	private String phoneNum;
	private int maxLoan; //�����ѵ�
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
		str.append("�̸� : ").append(name).append(", ");
		str.append("�ּ� : ").append(address).append(", ");
		str.append("��ȭ��ȣ : ").append(phoneNum).append(", ");
		for(int i =0; i<bankAccList.size(); i++)
		{
			str.append("���¹�ȣ : ").append(bankAccList.get(i).getBankAccNum()).append(", ");
			str.append("���� �ܾ� : ").append(bankAccList.get(i).getBalance());
		}
		return str.toString();
	}
	

}
