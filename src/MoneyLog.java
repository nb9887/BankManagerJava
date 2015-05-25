import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class MoneyLog implements Serializable
{
	
	private String type; // 입금 or 출금
	private Date time;
//	private String time; // 시간
	private String owner; //송금자
	private String receiver; //수신자
	private int money; //잔액
	
	public MoneyLog(String type, Date time, String owner, int money)
	{
		this.type = type;
		this.time = time;
		this.owner = owner;
		this.receiver = "";
		this.money = money;
	}
	
	public MoneyLog(String type, Date time, String owner, String receiver, int money) //계좌이체 
	//누가 받았는지
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
		System.out.println("종류 : " + this.type);
		System.out.println("시간 : " + this.time);
		System.out.println("계좌주인 : " + this.owner);
		if(receiver!="")
			System.out.println("수취인 : " + this.receiver);
		System.out.println("돈 : " + this.money);
		System.out.println("===========================");
				
	}
		
	
}
