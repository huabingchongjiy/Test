package teacherwork;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class unlookSend {
	String user;
	String password;
	public unlookSend() {
		user = new person().user;
		password = new person().password;
	}
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
		ts.connect("smtp.163.com", user, password);
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
	public static void main(String[] args) {
		//

	}

}
