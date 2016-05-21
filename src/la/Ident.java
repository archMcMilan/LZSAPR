package la;

public class Ident
{
	private String idn;
	private int num;
	private boolean init;
	

	public Ident(String idn,int num)
	{
		this.idn=idn;
		this.num=num;
		this.init=false;
	}
	public String getIdn() {
		return idn;
	}
	
	public void setIdn(String idn) {
		this.idn = idn;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public boolean isInit() {
		return init;
	}
	public void setInit(boolean init) {
		this.init = init;
	}
}