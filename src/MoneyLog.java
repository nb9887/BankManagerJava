import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class MoneyLog implements Serializable
{
	
	private String type; // �Ա� or ���
	private Date time;
//	private String time; // �ð�
	private String owner; //�۱���
	private String receiver; //������
	private int money; //�ܾ�
	
	public MoneyLog(String type, Date time, String owner, int money)
	{
		this.type = type;
		this.time = time;
		this.owner = owner;
		this.receiver = "";
		this.money = money;
	}
	
	public MoneyLog(String type, Date time, String owner, String receiver, int money) //������ü 
	//���� �޾Ҵ���
	{
		this.type = type;
		this.time = time;
		this.owner = owner;
		this.receiver = receiver;
		this.money = money;
	}

	public Date getDate()
	{
		return this.time;
	}
	
	/*	
	public String getDate()
	{
		return this.time;
	}
	*/
	public String getName()
	{
		return this.owner;
	}
	
	public void showMoneyLog()
	{	
		System.out.println("���� : " + this.type);
		System.out.println("�ð� : " + this.time);
		System.out.println("�������� : " + this.owner);
		if(receiver!="")
			System.out.println("������ : " + this.receiver);
		System.out.println("�� : " + this.money);
		System.out.println("===========================");
				
	}
		
	
}
