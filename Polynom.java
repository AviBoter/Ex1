package Ex1;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;


/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{
	public ArrayList<Monom> Polynom=new ArrayList<Monom>();
	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		Monom m=new Monom(0,0);
		Polynom.add(0,m);
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {

		Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			Monom m = new Monom(matcher.group(1));
			Polynom.add(m);
		}
		this.ReOrder();
	}

	@Override
	public double f(double x) {
		double ans=0;
		for (int i = 0; i < Polynom.size(); i++) {
			ans=ans+(double)this.Polynom.get(i).f(x);
		}
		return ans;
	}

	@Override
	public void add(Polynom_able p1) {
		Polynom_able temp= new Polynom();
		temp=(Polynom)p1.copy();
		Iterator<Monom> t1 = temp.iteretor();
		while(t1.hasNext()) {
			this.Polynom.add(t1.next());
		}
		this.ReOrder();
	}


	private void ReOrder() {
		for (int i = 0; i < this.Polynom.size()-1; i++) {
			for (int j = i+1; j < this.Polynom.size(); j++) {
				if(Polynom.get(i).get_power() == Polynom.get(j).get_power()) {
					Polynom.get(i).add(Polynom.get(j));
					Polynom.remove(j);
					j--;
				}
			}
		}
		int k=this.Polynom.size();
		while(k>0) {
			for ( int i= 0; i < this.Polynom.size(); i++) {
				if(Polynom.get(i).get_coefficient()== 0) {
					Polynom.remove(i);
				}
			}
			k--;
		}
	}
	@Override
	public void add(Monom m1) {
		for(int i=0 ; i < Polynom.size() ; i++) {
			if(Polynom.get(i).get_power() == m1.get_power()) {
				Monom m=new Monom(Polynom.get(i).get_coefficient()+m1.get_coefficient(), m1.get_power());
				Polynom.set(i,m);
				return;
			}
		}
		Polynom.add(m1);
		return;

	}


	@Override
	public void substract(Polynom_able p1) {
		// TODO Auto-generated method stub
		Polynom_able temp=new Polynom();
		temp=(Polynom)p1.copy();
		Iterator<Monom> it = temp.iteretor();
		while(it.hasNext()) {
			Monom m = new Monom("0");
			m.add(it.next());
			m.SetC(m.get_coefficient()*-1);
			m.SetP(m.get_power());
			this.Polynom.add(m);
		} 
		this.ReOrder();
	}


	@Override
	public void multiply(Polynom_able p1) {
		Polynom temp=new Polynom();
		temp=(Polynom)(p1);
		Monom m =new Monom(0,0);
		Polynom answer=new Polynom();
		for (int i = 0; i <temp.Polynom.size(); i++) {
			for (int j = 0; j <this.Polynom.size(); j++) {
				m= new Monom(temp.Polynom.get(i));
				m.multiply(this.Polynom.get(j));
				answer.add(m);
			}
		}
		this.Polynom= new ArrayList<Monom>(answer.Polynom);
		this.ReOrder();
	}

	@Override
	public boolean equals(Object p1) {
		Iterator<Monom> t = this.iteretor();
		Polynom p2 = new Polynom();
		boolean pe=false;
		if(p1 instanceof Polynom) {
			 p2=(Polynom)p1;
			pe=true;
		}
		if(p1 instanceof Monom) {
			Monom m=new Monom((Monom)p1);
			return (this.Polynom.get(0).equals(m));
		}
		while(pe && t.hasNext()) {
			Iterator<Monom> p = p2.iteretor();
			Monom temp=t.next();
			boolean flag=false;
			while(p.hasNext()) {
				if(temp.equals(p.next())) {
					flag=true;
				}
			}
			if(!flag)
				return false; 
		}
		return true;	
	}

	@Override
	public boolean isZero() {
		// TODO Auto-generated method stub
		if(this.Polynom.size()==0)
			return true;
		return false;
	}

	@Override
	public double root(double x0, double x1, double eps) {
		if(eps<=0)
			throw new RuntimeException("ERR: eps must be positive number");
		if(f(x1)*f(x0) >0)
			throw new RuntimeException("ERROR: Try to apply differnt parameters");
		if(f(x0)*f(x1)==0) {
			if(x0==0)
				return x0;
			return x1;
		}
        double t=(x0+x1)/2.0;
        while(Math.abs(f(t))>eps) {
        	if(f(t)*f(x0)<0)
        		x1=t;
        	else
        		x0=t;
        	t=(x0+x1)/2.0;
        }
        return t;
	}

	public Polynom_able copy() {
		Polynom p= new Polynom();
		Iterator<Monom> it=this.iteretor();
		while(it.hasNext()) {
			p.add(new Monom(it.next()));
		}
		return p;		
	}

	@Override
	public Polynom_able derivative() {
		// TODO Auto-generated method stub
		Polynom_able Temp=new Polynom();
		/*Iterator<Monom> it = this.Polynom.iterator();
		while(it.hasNext()) {
			Temp.add(it.next().derivative());
		} */
		for (int i = 0; i < Polynom.size(); i++) {   
			Temp.add(this.Polynom.get(i).derivative()); 
		}

		return Temp;
	}
//24.220325999999282, p.area(-1, 5, 0.01)
	@Override
	public double area(double x0, double x1, double eps) {
		double ans=0;
		while(x0<x1) {
			if(f(x0)>0)
			ans=f(x0)+eps;
			x0=x0+eps;
		}
		return ans;
	}

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return this.Polynom.iterator();
	}

	@Override
	public void multiply(Monom m1) {
		for (int j = 0; j < Polynom.size(); j++) {
			double SetC=this.Polynom.get(j).get_coefficient()*m1.get_coefficient();
			int SetP=this.Polynom.get(j).get_power()+m1.get_power();
			this.Polynom.set(j, new Monom(SetC,SetP));
		}
		this.ReOrder();
	}

	public String toString() {
		if(this.Polynom.size()==0) 
			return "null";
		String str="";
		for(int i= 0;i<this.Polynom.size();i++) {
			if(Polynom.get(i).toString().contains("-"))
			str+=Polynom.get(i).toString();
			else
			str+="+"+Polynom.get(i).toString();
		}
		return str;
	}
	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		return null;
	}
}
