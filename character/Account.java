package character;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class Account {
	public String account;
	public String name;
	public String password;
	public char character;// 學生:'s' 教授:'p' 管理員:'m'

	public Account(String account, String password, String name,char character) {
		this.account = account;
		this.name = name;
		this.password = password;
		this.character = character;
	}
	public abstract boolean changePassword(String oldPassword, String newPassword) throws FileNotFoundException, IOException;


}
