package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}

	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	public void SetC(double x) {
		this._coefficient=x;
	}
	public void SetP(int x) {
		this._power=x;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	public Monom(String s) { 
		s=s.trim();
		if(s.isEmpty()) {
			this._coefficient=0;
			this._power=0;
		}
		
		boolean isX=false;
		boolean Cflag=false;
		boolean Pflag=false;
		int start=0,end=0;
		if(s.charAt(0)=='+') {
			s=s.substring(1);
			s=s.trim();
			}
		if(s.charAt(0)=='-' && s.charAt(1)=='x') {
			this._coefficient=-1;
			Cflag=true;
			s=s.substring(1);
			s=s.trim();
		}
		double j=0;
		char x='x';
		for (int k = 0; k < s.length(); k++) {
			if(s.charAt(k)==x)
				isX=true;
		}
        
		if(!Cflag && !isX && IsCoeff(s)) {
			this._coefficient=Double.parseDouble(s);
			this._power=0;
			return;
		}
		if(!Cflag && s.charAt(0)==x) {
			this._coefficient=1;
			Cflag=true;
			if(s.length()==1) {
				this._power=1;
				return;
			}
		}
		
		while(s.length()>end+1&& s.charAt(end+1)!=x && !Cflag) {
			end++;
		}
	
		if(!Cflag && IsCoeff(s.substring(start,end+1))) {
				if(s.charAt(0)=='-' && s.charAt(1)==x) {
					j=-1;
					Cflag=true;
					this._coefficient=j;
				}
				if(!Cflag) {
					end++;
					j=Double.parseDouble(s.substring(start,end));
					this._coefficient=j;
					Cflag=true;
					s=s.substring(end);
					if(s.length()>0)
						s=s.trim();
					if(s.equals("x")) {
						this._power=1;
						return;
					}
				}
			if(!isX) {
				this._power=0;
				return;
			}
		}

		if(!Cflag) {
			throw new RuntimeException("ERR,coefficient shuold be a applicable number");
		}
		if(isX && s.length()==1) {
			this._power=1;
			Pflag=true;
			return;
		}
		if(s.equals("x^")) {
			throw new RuntimeException("ERR,Power shuold be a applicable number");
		}
		if(s.length()>0)
			s=s.trim();
		int xin=Xindex(s);
		char[] c=s.substring(xin+2,s.length()).toCharArray();
		if(isX && !Pflag) {
			if(isPow(c) || c.length==1) {
				if(c.length!=1) {
					j=Double.parseDouble(s.substring(0+3,s.length()));
				}
				if(c.length==1) {
					j=Character.getNumericValue(c[0]);
				}
				this._power=(int)j;
				Pflag=true;
				return;
			}
			
			if(!Pflag) {
				throw new RuntimeException("ERR,Power shuold be a applicable number");
			}
		}
	}

	private boolean isPow(char[] c) {

		if(c.length>=3 && ((c[0]=='0' && c[1]=='.') || (c[0]=='-' && c[1]=='0'))) 
			return false;

		if((c.length>0 && c[0]!='^') || c.length==0)
			return false;

		for (int i = 1; i < c.length; i++) {
			boolean flag=false;
			int num=0;
			while(num<10 && !flag) {
				if((int)c[i]-48==num || (c[i]=='.' && i>1 && i<c.length-1 && NumOfPoints(c))) {
					flag=true;
				}
				num++;
			}
			if(!flag)  
				return false;
		}
		return true;
	}

	private boolean IsCoeff(String s) {
		if(s.length()>0)
			s=s.trim();
		for (int i = 0; i < s.length(); i++) {

			if(i==0 && s.charAt(0)=='-') {
				i++;
			}
			if(s.charAt(i)!='.' && s.charAt(i)!='+'&& s.charAt(i)!='-') {
			if(i<s.length() && Character.getNumericValue(s.charAt(i))<0||Character.getNumericValue(s.charAt(i))>'9')
				return false;
			}
			if(s.charAt(0)=='.' || s.charAt(s.length()-1)=='.' || !NumOfPoints(s.toCharArray()) || !NumOfPows(s.toCharArray()))
				return false;
		}
		return true;
	}

	private boolean NumOfPoints(char[] c) {
		int count=0;
		for (int i = 0; i < c.length; i++) {
			if(c[i]=='.')
				count++;
		}
		if(count<=1)
			return true;
		return false;
	}
	private boolean NumOfPows(char[] c) {
		int count=0;
		for (int i = 0; i < c.length; i++) {
			if(c[i]=='^')
				count++;
		}
		if(count<=1)
			return true;
		return false;
	}

	public void add(Monom m) {
		if(this._power!=m._power && !(this._power==0 && this._coefficient==0)) {
			throw new RuntimeException("ERR,Monom power should be equals to:"+ this._power);
		}
		if(this._power==m.get_power())
			this._coefficient=this._coefficient+m._coefficient;
		else
			this._coefficient=this._coefficient+m._coefficient;
		this._power=m._power;
	}

	public void multiply(Monom d) {
		this._coefficient=this._coefficient*d._coefficient;
		this._power=this._power+d._power;
	}

	public String toString() {
		String Str="";
		if(this.get_coefficient()>=0) {
			Str = ""+this.get_coefficient() +"x^" + this.get_power();	
		}
		else {
			Str = this.get_coefficient() +"x^" + this.get_power();	
		}
		return Str;
	}
	public void sub(Monom m) {
		if(this._power!=m._power) {
			throw new RuntimeException("ERR,Monom power should be equals to:"+ this._power);
		}
		this._coefficient=this._coefficient-m._coefficient;
	}

	public boolean equals(Monom m) {
		double x=Math.abs(this._coefficient-m._coefficient);
		if(x>0.0000001 || this._power!=m._power)
			return false;
		return true;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************


	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	@Override
	public function initFromString(String s) {
		s=s.trim();
		if(s.isEmpty()) {
			this._coefficient=0;
			this._power=0;
		}
		
		boolean isX=false;
		boolean Cflag=false;
		boolean Pflag=false;
		int start=0,end=0;
		if(s.charAt(0)=='+') {
			s=s.substring(1);
			s=s.trim();
			}
		if(s.charAt(0)=='-' && s.charAt(1)=='x') {
			this._coefficient=-1;
			Cflag=true;
			s=s.substring(1);
			s=s.trim();
		}
		double j=0;
		char x='x';
		for (int k = 0; k < s.length(); k++) {
			if(s.charAt(k)==x)
				isX=true;
		}
        
		if(!Cflag && !isX && IsCoeff(s)) {
			this._coefficient=Double.parseDouble(s);
			this._power=0;
			function newMonom=new Monom(this._coefficient,this._power);
			return newMonom;
		}
		if(!Cflag && s.charAt(0)==x) {
			this._coefficient=1;
			Cflag=true;
			if(s.length()==1) {
				this._power=1;
				function newMonom=new Monom(this._coefficient,this._power);
				return newMonom;			}
		}
		
		while(s.length()>end+1&& s.charAt(end+1)!=x && !Cflag) {
			end++;
		}
	
		if(!Cflag && IsCoeff(s.substring(start,end+1))) {
				if(s.charAt(0)=='-' && s.charAt(1)==x) {
					j=-1;
					Cflag=true;
					this._coefficient=j;
				}
				if(!Cflag) {
					end++;
					j=Double.parseDouble(s.substring(start,end));
					this._coefficient=j;
					Cflag=true;
					s=s.substring(end);
					if(s.length()>0)
						s=s.trim();
					if(s.equals("x")) {
						this._power=1;
						function newMonom=new Monom(this._coefficient,this._power);
						return newMonom;
					}
				}
			if(!isX) {
				this._power=0;
				function newMonom=new Monom(this._coefficient,this._power);
				return newMonom;
			}
		}

		if(!Cflag) {
			throw new RuntimeException("ERR,coefficient shuold be a applicable number");
		}
		if(isX && s.length()==1) {
			this._power=1;
			Pflag=true;
			function newMonom=new Monom(this._coefficient,this._power);
			return newMonom;
		}
		if(s.equals("x^")) {
			throw new RuntimeException("ERR,Power shuold be a applicable number");
		}
		if(s.length()>0)
			s=s.trim();
		int xin=Xindex(s);
		char[] c=s.substring(xin+2,s.length()).toCharArray();
		if(isX && !Pflag) {
			if(isPow(c) || c.length==1) {
				if(c.length!=1) {
					j=Double.parseDouble(s.substring(0+3,s.length()));
				}
				if(c.length==1) {
					j=Character.getNumericValue(c[0]);
				}
				this._power=(int)j;
				Pflag=true;
				function newMonom=new Monom(this._coefficient,this._power);
				return newMonom;
			}
			
			if(!Pflag) {
				throw new RuntimeException("ERR,Power shuold be a applicable number");
			}
		}
		function newMonom=new Monom(this._coefficient,this._power);
		return newMonom;
	}

	@Override
	public function copy() {
		// TODO Auto-generated method stub
		function newMonom=new Monom(this._coefficient,this._power);
		return newMonom;
	}

 public int Xindex(String s) {
	 int ans=0;
	 for (int i = 0; i < s.length(); i++) {
		if(s.charAt(i)=='x') 
			ans=i;
	}
	 return ans;
 }
}
