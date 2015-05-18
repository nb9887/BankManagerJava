import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class FileHandler 
{
	public FileHandler()
	{
		
	}
	public void loadFile(ClientList clientList)
	{
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try
		{
			fis = new FileInputStream("clientList.txt");
			ois = new ObjectInputStream(fis);
			
			try {
				  while (true) {
				    Client e = (Client)ois.readObject();
				    clientList.getClientList().add(e);
				  }
			}
			catch (EOFException e)
			{
				
			}
		}
		catch(Exception e)
		{
		}
		finally
		{
			if(fis != null)
			{
				try {
					fis.close();
				}
				catch(IOException e)
				{
					
				}
			}
			if(ois != null)
			{
				try {
					ois.close();
				}
				catch(IOException e)
				{
					
				}
			}
		}
	}
	
	public void saveFile(ClientList clientList)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try
		{
			fos = new FileOutputStream("clientList.txt");
			oos = new ObjectOutputStream(fos);
			
			for(int i=0 ; i<clientList.getClientList().size() ; i++)
			{
				oos.writeObject(clientList.getClientList().get(i));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (fos != null)
			{
				try {
					fos.close();
				}
				catch (IOException e)
				{
					
				}
			}
			if (oos != null)
			{
				try {
					oos.close();
				}
				catch (IOException e)
				{
					
				}
			}
		}
	}
}
