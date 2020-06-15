package kr.co.inogard.enio.api.commons;

public class EnioContext {
	public static ThreadLocal<EnioContext> local=new ThreadLocal<EnioContext>();
	private String id,pwd;
	public EnioContext(String id,String pwd) {
		this.id=id;
		this.pwd=pwd;
	}
	public String getEnioId() {
		return this.id;
	}
	public String getEnioPwd() {
		return this.pwd;
	}	
}
