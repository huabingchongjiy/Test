package teacherwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.tree.TreePath;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

/*class MyThread extends Thread{
	
public void run() {
	//Jesen:此处实现的功能只是每隔30分钟扫描邮箱，且只有9次
	for(int i = 1;i<10;i++){
		//System.out.println("i");
		try{
			String user = "shenxiaoya3@163.com";//邮箱的用户名
			String password = "1234554321";//邮箱的密码
			
			Properties prop = System.getProperties();
			prop.put("mail.store.protocol", "imap");
			prop.put("mail.imap.host", "imap.163.com");
			
			Session session = Session.getInstance(prop);
			
			int total = 0;
			IMAPStore store = (IMAPStore) session.getStore("imap");//使用imap会话机制，连接服务器
			store.connect(user,password);
			IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			//获取总邮件数
			total = folder.getMessageCount();
			System.out.println("共有邮件：" + total + "封");
			//得到收件箱文件夹信息，获取邮件列表
			System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------");
			Message[] messages = folder.getMessages();
			int messageNumber = 0;
			for (Message message : messages){
				System.out.println("发送时间：" + message.getSentDate());
				System.out.println("主题：" + message.getSubject());
				//System.out.println("内容：" + message.getContent());
				Flags flags = message.getFlags();
				if (flags.contains(Flags.Flag.SEEN)) {
					System.out.println("这是一份已读邮件");
				}
				else {
					System.err.println("未读邮件");
				}
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------------");
				//通过邮件的MessageNumber在收件箱里面获取邮件
				messageNumber = message.getMessageNumber();
			}
			Message message = folder.getMessage(messageNumber);
			//释放资源
			if(folder != null){
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}
			Thread.sleep(1000*60*30);	
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
		
}
	
}*/
class Tasksending extends TimerTask{
	//用Timeer替换了Thread
	
	int i=0;//用来判断30分钟发一次邮件
	
	public void run() {
		// TODO Auto-generated method stub
		try{
			
			String user = "shenxiaoya3@163.com";//邮箱的用户名
			String password = "1234554321";//邮箱的密码
			
			Properties prop = System.getProperties();
			prop.put("mail.store.protocol", "imap");
			prop.put("mail.imap.host", "imap.163.com");
			
			Session session = Session.getInstance(prop);
			
			
			int total = 0;
		    
			IMAPStore store = (IMAPStore) session.getStore("imap");//使用imap会话机制，连接服务器
			store.connect(user,password);
			IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			//获取总邮件数
			total = folder.getMessageCount();
			System.out.println("共有邮件：" + total + "封");
			//得到收件箱文件夹信息，获取邮件列表
			System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------");
			Message[] messages = folder.getMessages();
			int messageNumber = 0;
			for (Message message : messages){
				System.out.println("发送时间：" + message.getSentDate());
				System.out.println("主题：" + message.getSubject());
				//System.out.println("内容：" + message.getContent());
				Flags flags = message.getFlags();
				if (flags.contains(Flags.Flag.SEEN)) {
					System.out.println("这是一份已读邮件");
				}
				else {
					System.out.println("未读邮件");
				}
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------------");
				//通过邮件的MessageNumber在收件箱里面获取邮件
				messageNumber = message.getMessageNumber();
			}
			//如果存在未读邮件
			if(folder.getUnreadMessageCount()> 0){
				i = i + 1;
				if(i%2==0){//判断是否满足30分钟。
					Sendmail sd = new Sendmail();
					sd.run();
					i = 0;
				}	
			}else{
				i = 0;
			}
			Message message = folder.getMessage(messageNumber);
			//释放资源
			if(folder != null){
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}	
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
//发送邮件给24963386@qq.com
class Sendmail {
	public void run() throws Exception{
		Properties properties = new Properties();
		properties.setProperty("mail.host","smtp.163.com");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		//发送邮件
		//1、创建session,
		Session session = Session.getInstance(properties);
		//2、通过session得到transport对象
		Transport ts = session.getTransport();
		//3、设置用户名和密码正确连接上邮箱服务器，发送邮件
		ts.connect("smtp.163.com", "shenxiaoya3@163.com", "1234554321");
		//4、创建邮件
		Message message = createSimpleMain(session);
		//5、发送邮件
		ts.sendMessage(message, message.getAllRecipients());
	}
	
	public static MimeMessage createSimpleMain(Session session)throws Exception{
		//创建邮箱对象
		MimeMessage message = new MimeMessage(session);
		//指明邮箱的发件人
		message.setFrom(new InternetAddress("shenxiaoya3@163.com"));
		//收件人
		message.setRecipient(Message.RecipientType.TO,new InternetAddress("591014118@qq.com") );
		//邮箱标题
		message.setSubject("邮件提醒");
		//邮箱的文本内容
		message.setContent("你的163邮箱中有未读邮件，记得查看","text/html;charset=UTF-8");
		return message;
		
	}
}



public class Email {

	
	/**
	 * 程序改进：
	 * 1.每隔15分钟扫描邮箱一次,可以考虑使用Timer或者ScheduledExecutorService
	 * 2.当发现shenxiaoya3@163.com邮箱中有超过30分钟未被读取的邮件，请发一封邮件给24963386@qq.com
	 * 
	 */
	public static void main(String[] args) throws Exception{
		/*MyThread m = new MyThread();
		m.start();
		*/
		//System.out.println("test change!");
		Timer timer = new Timer();
		Tasksending tasksending = new Tasksending();
		timer.schedule(tasksending, 0, 1000*60*1);//15分钟
		
		
		
		
	}
	
	
	
}

