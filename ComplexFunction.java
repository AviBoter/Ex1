package Ex1;

public class ComplexFunction implements complex_function {

	BTNode root;
	private static int count=0;

	public ComplexFunction() {
		this.root=new BTNode();
	}
	public ComplexFunction(BTNode r) {
		this.root=r;
	}
	public ComplexFunction(function f) {
		this.root=new BTNode();
		this.root.f=f;
	}
	public ComplexFunction(String Str,function f1,function f2) {
		this.root=new BTNode();
		if(Str=="div" || Str=="max" || Str=="mul" || Str=="min" || Str=="plus" || Str=="comp") {
			if(Str=="div") {this.root.o=Operation.Divid;}
			if(Str=="max") {this.root.o=Operation.Max;}
			if(Str=="mul") {this.root.o=Operation.Times;}
			if(Str=="min") {this.root.o=Operation.Min;}
			if(Str=="plus") {this.root.o=Operation.Plus;}
			if(Str=="comp") {this.root.o=Operation.Comp;}
		}
		BTNode left=new BTNode();
		left.f=f1;
		this.root.setLeft(left);
		BTNode right=new BTNode();
		right.f=f2;
		this.root.setRight(right);
	}
	public ComplexFunction(Operation Op,function f1,function f2) {
		this.root=new BTNode();
		this.root.o=Op;
		BTNode left=new BTNode();
		left.f=f1;
		this.root.setLeft(left);
		BTNode right=new BTNode();
		right.f=f2;
		this.root.setRight(right);
	}

	public void plus(function f1) {
		BTNode newNode=new BTNode();
		newNode.right=new BTNode();
		newNode.right.f=f1;
		newNode.o=Operation.Plus;
		newNode.left=this.root;
		this.root=newNode;

	}

	public void mul(function f1) {
		BTNode newNode=new BTNode();
		newNode.right=new BTNode();
		newNode.right.f=f1;
		newNode.o=Operation.Times;
		newNode.left=this.root;
		this.root=newNode;
	}
	public void div(function f1) {
		BTNode newNode=new BTNode();
		newNode.right=new BTNode();
		newNode.right.f=f1;
		newNode.o=Operation.Divid;
		newNode.left=this.root;
		this.root=newNode;
	}
	public void max(function f1) {
		BTNode newNode=new BTNode();
		newNode.right=new BTNode();
		newNode.right.f=f1;
		newNode.o=Operation.Max;
		newNode.left=this.root;
		this.root=newNode;
	}

	private double OperAction(Operation o,function f1,function f2,double x) {
		double ans=0;
		if(o!=null && o.equals(Operation.Comp)) {
			ans=f1.f(f2.f(x));
		}
		if(o!=null && o.equals(Operation.Divid)) {
			ans=f1.f(x)/f2.f(x);
		}
		if(o!=null && o.equals(Operation.Max)) {
			ans=Math.max(f1.f(x), f2.f(x));
		}
		if(o!=null && o.equals(Operation.Min)) {
			ans=Math.min(f1.f(x), f2.f(x));
		}
		if(o!=null && o.equals(Operation.Plus)) {
			ans=f1.f(x)+f2.f(x);
		}
		if(o!=null && o.equals(Operation.Times)) {
			ans=f1.f(x)*f2.f(x);
		}
		return ans;
	}
	private double OperAction(Operation o,function f,double x,BTNode run) {
		double ans=0;
		function newf=new ComplexFunction(run);

		if(o!=null && o.equals(Operation.Comp)) {
			ans=ans+newf.f(f(x));
		}
		if(o!=null && o.equals(Operation.Divid)) {
			ans=ans+(newf.f(x)/f.f(x));
		}
		if(o!=null && o.equals(Operation.Max)) {
			ans+=Math.max(newf.f(x), f.f(x));
		}
		if(o!=null && o.equals(Operation.Min)) {
			ans=Math.min(newf.f(x), f.f(x));
		}
		if(o!=null && o.equals(Operation.Plus)) {
			ans=ans+(newf.f(x)+f.f(x));
		}
		if(o!=null && o.equals(Operation.Times)) {
			ans+=newf.f(x)*f.f(x);
		}
		return ans;
	}
	private double OperAction(Operation o, BTNode left, BTNode right,double x) {
		
		function newfl=new ComplexFunction(left);
		function newfr=new ComplexFunction(right);
		double ans=0;
		if(o!=null && o.equals(Operation.Comp)) {
			ans=ans+newfl.f(newfr.f(x));
		}
		if(o!=null && o.equals(Operation.Divid)) {
			ans=ans+(newfl.f(x)/newfr.f(x));
		}
		if(o!=null && o.equals(Operation.Max)) {
			ans+=Math.max(newfl.f(x), newfr.f(x));
		}
		if(o!=null && o.equals(Operation.Min)) {
			ans=Math.min(newfl.f(x), newfr.f(x));
		}
		if(o!=null && o.equals(Operation.Plus)) {
			ans=ans+(newfl.f(x)+newfr.f(x));
		}
		if(o!=null && o.equals(Operation.Times)) {
			ans+=newfl.f(x)*newfr.f(x);
		}
		return ans;
	}

	@Override
	public double f(double x) {

		BTNode run=this.root;
		double ans = 0;
		if(run.f!=null && isPolynom(run.FtoString())) {
			System.out.println(run.FtoString());
			ans=ans+run.f.f(x);
			return ans;
		}
		if(run.o!=Operation.None && run.left.f!=null && run.right.f!=null) {
			ans+=OperAction(run.o,run.left.f,run.right.f,x);
		}
		if(run.o!=Operation.None && run.left.f==null && run.right.f!=null) {
			ans=ans+OperAction(run.o,run.right.f,x,run.left);
		}
		if(run.o!=Operation.None && run.left.f!=null && run.right.f==null) {
			ans=ans+OperAction(run.o,run.left.f,x,run.right);
		}
		if(run.o!=Operation.None && run.left.f==null && run.right.f==null) {
			ans=ans+OperAction(run.o,run.left,run.right,x);
		}

		return ans;
	}

	public boolean equals(function other) {
		if(other.getClass()!=this.getClass())
			return false;
		for (int i = -5; i <= 5; i++) {
			if(this.f(i)!=other.f(i))
				return false;
		}
		return true;
	}

	@Override
	public function initFromString(String s) {
		BTNode run=this.root;
		s=s.trim();
		if(!isValidStr(s)) 
			throw new RuntimeException("ERR,String isn't Valid"); 
		if(isPolynom(s)) {
			ComplexFunction p=new ComplexFunction(new Polynom());
			p.root=run;
			if(!run.o.equals(Operation.None) && run.f!=null){
				run.f=null;
			}
			function newf1=new ComplexFunction(p);
			return newf1;
		}
		//"div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)"

		boolean runleft=false,runright=false;
		while(s.length()!=0) {
			for (int i = 0; i < s.length(); i++) {
				if(s.charAt(0)=='(') {
					s=s.substring(1);
					s=s.substring(0,s.length()-1);

				}
				if(runleft && runright) {
					run=run.left;
					runleft=runright=false;
				}

				if(s.charAt(i)==',' && isPolynom(s.substring(0,i)) && !runleft && run.left.f==null) {
					run.left = new BTNode();
					function f=new Polynom(s.substring(1,i));
					run.left.FsetData(f);
					runleft=true;
					s=s.substring(i+1);
					i=0;
				}
				if((s.charAt(i)==')' || i==s.length()-1) && isPolynom(s.substring(0,i)) && runleft && run.right.f==null) {
					run.right = new BTNode();
					run.right.f=new Polynom(s.substring(0,i+1));
					runright=true;
					if(s.charAt(i)==')')
						s=s.substring(i+1);
					i++;
					if(s.length()==0) {
						ComplexFunction p=new ComplexFunction(new Polynom());
						p.root=run;
						if(!run.o.equals(Operation.None) && run.f!=null){
							run.f=null;
						}
						function newf1=new ComplexFunction(p);
						return newf1;
					}
					i=0;
				}

				if(i<s.length()-5 && (s.substring(i,i+3).equals("div") || s.substring(i,i+3).equals("max") || s.substring(i,i+3).equals("mul") || s.substring(i,i+3).equals("min"))) {
					while(!run.o.equals(Operation.None) || run.f!=null) { 
						if(run.left==null) {
							run.left=new BTNode();
						}
						run=run.left;
					}
					run.o=StrToOper(s.substring(i,i+3));
					s=s.substring(i+3);
					if(run.left==null) {
						run.left=new BTNode();
					}
					int pindiv=PinDivid(s);
					int Po=PoClose(s);
					initFromString(s.substring(1,pindiv),run.left);
					Charout(s,Po);
					s=s.substring(pindiv);


					if(run.right==null) {
						run.right=new BTNode();
					}
					initFromString(s,run.right);
					s=s.substring(s.length());

				}
				if(i<s.length()-6 && (s.substring(i,i+4).equals("plus") || s.substring(i,i+4).equals("comp"))) {

					while(!run.o.equals(Operation.None) || run.f!=null) { 
						if(run.left==null) {
							run.left=new BTNode();
						}
						run=run.left;
					}
					run.o=StrToOper(s.substring(i,i+4));
					s=s.substring(i+4);
					i=i+4;
					if(run.left==null) {
						run.left=new BTNode();
					}
					int pindiv=PinDivid(s);
					int Po=PoClose(s);
					initFromString(s.substring(1,pindiv),run.left);
					Charout(s,Po);
					s=s.substring(pindiv);


					if(run.right==null) {
						run.right=new BTNode();
					}
					initFromString(s.substring(0,s.length()),run.right);
					s=s.substring(s.length());

				}
			}
		}
		ComplexFunction p=new ComplexFunction(new Polynom());
		p.root=run;
		function newf1=new ComplexFunction(run);
		return newf1;
	}


	private String Charout(String s, int Po) {
		String s1=s.substring(0,Po);
		String s2=s.substring(Po+1,s.length());
		s=s1+s2;
		return s;

	}
	private void initFromString(String s, BTNode run) {
		if(isPolynom(s)) {
			while(s.charAt(s.length()-1)==')' || s.charAt(s.length()-1)==',' || s.charAt(s.length()-1)=='(') {
				s=s.substring(0,s.length()-1);
			}
			while(s.charAt(0)==',' || s.charAt(0)=='(') {
				s=s.substring(1,s.length());
			}
			run.f=new Polynom(s.substring(0));
			return;
		}
		//div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.298x +5.0),-1.0x^4 +2.4x^2 +3.1)
		if(s.charAt(0)==',' || s.charAt(0)==')' ) {
			s=s.substring(1);
		}
		boolean runleft=false,runright=false;
		while(s.length()!=0) {
			for (int i = 0; i < s.length(); i++) {
				if(runleft && runright) {
					if(run.left==null) 
						run.left=new BTNode();
					run=run.left;
					runleft=runright=false;
				}
				if(isPolynom(s)) {
					if(isPolynom(s)) {
						if(s.charAt(0)==',')
							s=s.substring(1);
						root.f=new Polynom(s);
						return;
					}
				}
				if((s.charAt(i)==',' || s.charAt(i)==')') && isPolynom(s.substring(0,i)) && !runleft) {
					if(run.left==null) 
						run.left=new BTNode();
					run.left.f=new Polynom(s.substring(1,i));
					runleft=true;
					s=s.substring(i+1);
					if(s.length()!=0)
						i++;
					if(s.length()==0)
						break;
				}
				if(s.charAt(i)==')' && isPolynom(s.substring(0,i)) && runleft && !runright) {
					if(run.right==null) {
						run.right=new BTNode();
					}
					run.right.f=new Polynom(s.substring(0,i));
					runright=true;
					s=s.substring(i+1);
					if(s.length()!=0)
						i++;
					if(s.length()==0)
						break;
				}
				if(s.charAt(i)==',') {
					int pindex=PinDivid(s);  
					initFromString(s.substring(1,pindex),run.left);
					initFromString(s.substring(pindex,s.length()),run.right);
					s=s.substring(s.length());

				}
				if(i<s.length()-5 && (s.substring(i,i+3).equals("div") || s.substring(i,i+3).equals("max") || s.substring(i,i+3).equals("mul") || s.substring(i,i+3).equals("min"))) {
					while(!run.o.equals(Operation.None)) { 
						if(run.left==null) {
							run.left=new BTNode();
						}
						run=run.left;
					}
					run.o=StrToOper(s.substring(i,i+3));
					s=s.substring(i+3);
					if(run.left==null) {
						run.left=new BTNode();
					}
					int pindiv=PinDivid(s);
					int Po=PoClose(s);
					initFromString(s.substring(1,pindiv),run.left);
					Charout(s,Po);
					s=s.substring(pindiv);


					if(run.right==null) {
						run.right=new BTNode();
					}
					if(s.charAt(0)==',') {
						s=s.substring(1);
					}
					initFromString(s.substring(0,s.length()),run.right);
					s=s.substring(s.length());

				}
				if(i<s.length()-6 && (s.substring(i,i+4).equals("plus") || s.substring(i,i+4).equals("comp"))) {

					while(!run.o.equals(Operation.None)) { 
						if(run.left==null) {
							run.left=new BTNode();
						}
						run=run.left;
					}
					run.o=StrToOper(s.substring(i,i+4));
					s=s.substring(i+4);
					if(run.left==null) {
						run.left=new BTNode();
					}
					int pindiv=PinDivid(s);
					int Po=PoClose(s);
					initFromString(s.substring(1,pindiv),run.left);
					s=Charout(s,Po);
					s=s.substring(pindiv);

					if(run.right==null) {
						run.right=new BTNode();
					}
					if(s.charAt(0)==',') {
						s=s.substring(1);
					}
					initFromString(s.substring(0,s.length()),run.right);
					s=s.substring(s.length());
				}
			}
		}
	}
	//div(min(x-2,x),3-x)//
	public String toString() {
		BTNode run=this.root;
		String ans="";
		if(run.f!=null) {
			ans=ans+run.f.toString();
			return ans;
		}
		if(run.o!=Operation.None) {
			if(!ans.isEmpty())
				ans=ans+"(";
			ans=ans+run.OtoString()+"(";
		}

		if(run.left!=null) {
			if(run.left.f!=null)
				ans=ans+run.left.toString()+",";
			else
				ans=ans+run.left.toString();
		}

		if(run.right!=null) {
			if(ans.charAt(ans.length()-1)==')') 
				ans+=",";
			if(run.right.FtoString()!=null) 
				ans=ans+run.right.FtoString()+")";
			else
				ans=ans+run.right.toString();
		}

		return ans;
	}

	private int PinDivid(String s) {
		int Scount=0,Ecount=0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='(')
				Scount++;
			if(s.charAt(i)==')') {
				Ecount++;
			}
			if(Scount-1==Ecount && s.charAt(i)==',') {
				return i;
			}
		}
		return s.length()-1;
	}



	private int PoClose(String s) {
		int Scount=0,Ecount=0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)=='(')
				Scount++;
			if(s.charAt(i)==')') {
				Ecount++;
			}
			if(Scount==Ecount) {
				return i;
			}
		}
		return s.length()-1;
	}

	private boolean isPolynom(String s) {
		for (int i = 0; i < s.length(); i++) {

			if(i<s.length()-4 && (s.substring(i,i+3).equals("div") || s.substring(i,i+3).equals("max") || s.substring(i,i+3).equals("mul") || s.substring(i,i+3).equals("min"))) {
				return false;
			}
			if(i<s.length()-5 && (s.substring(i,i+4).equals("plus") || s.substring(i,i+4).equals("comp"))) {
				return false;
			}
		}
		return true;
	}

	private Operation StrToOper(String substring) {
		Operation Oper=Operation.None;
		if(substring.equals("mul")) 
			Oper=Operation.Times;

		if(substring.equals("plus")) 
			Oper=Operation.Plus;

		if(substring.equals("div"))
			Oper=Operation.Divid;

		if(substring.equals("max"))
			Oper=Operation.Max;
		if(substring.equals("min"))
			Oper=Operation.Min;
		if(substring.equals("comp"))
			Oper=Operation.Comp;

		return Oper;
	}
	public String StrLessSpaces(String s) {

		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)==' ') {
				String s1=s.substring(0,i);
				String s2=s.substring(i+1,s.length());
				s=s1+s2;
			}
		}
		return s;
	}

	private boolean isValidStr(String s) {
		int NumOfPa=0,NumOfPo=0,NumOfOper=0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i)==',')
				NumOfPo++;
			if(s.charAt(i)=='(' || s.charAt(i)==')')
				NumOfPa++;
			if(i<s.length()-4 && (s.substring(i,i+3)=="div" || s.substring(i,i+3)=="max" || s.substring(i,i+3)=="mul" || s.substring(i,i+3)=="min" || s.substring(i,i+4)=="plus" || s.substring(i,i+4)=="comp")) {
				NumOfOper++;
			}
			if(NumOfPo!=0 && NumOfOper!=0 &&  NumOfPa!=0 && (NumOfPo!=NumOfOper || NumOfPa!=NumOfPo*2))
				return false;
		}
		return true;
	}

	@Override

	public function copy() {
		function copyf=new ComplexFunction();
		copyf=this.root.f;
		return copyf;
	}

	@Override
	public void min(function f1) {

		BTNode newNode=new BTNode();
		newNode.right=new BTNode();
		newNode.right.f=f1;
		newNode.o=Operation.Min;
		newNode.left=this.root;
		this.root=newNode;

	}

	@Override
	public void comp(function f1) {

		BTNode newNode=new BTNode();
		newNode.right=new BTNode();
		newNode.right.f=f1;
		newNode.o=Operation.Comp;
		newNode.left=this.root;
		this.root=newNode;

	}

	@Override
	public function left() {
		return root.left.f;
	}

	@Override
	public function right() {
		return root.right.f;
	}

	@Override
	public Operation getOp() {
		return root.o;
	}

	class BTNode{
		Operation o;
		function f;
		BTNode left, right;
		int size;

		public BTNode() {
			this.f = null;
			this.o=Operation.None;
			this.left = null;
			this.right = null;

		}

		public BTNode(function data){
			this.f =new ComplexFunction(data);
			this.o=Operation.None;
			this.left = null;
			this.right = null;

		}
		public BTNode(Operation data){
			this.o = data;
			this.f=null;
			this.left = null;
			this.right = null;

		}

		public function FgetData() {
			return this.f;
		}
		public Operation OgetData() {
			return this.o;
		}
		public void FsetData(function f2) {
			System.out.println(f2);
			this.f =f2;
		}
		public void OsetData(Operation data) {
			this.o = data;
		}
		public BTNode getLeft() {
			return left;
		}
		public void setLeft(BTNode left) {
			this.left = left;
		}
		public BTNode getRight() {
			return right;
		}
		public void setRight(BTNode right) {
			this.right = right;
		}
		public int size(BTNode root) {
			count=0;
			sizev(root);
			return count;
		}
		private void sizev(BTNode root) {
			if (root==null)
				return;

			count++;
			sizev(root.left);
			sizev(root.right);
			return;
		}
		public String toString() {
			if(this.f!=null)
				return this.FtoString();
			String ans=OtoString()+"(";
			ans=ans+this.left.toString();
			if(ans.charAt(ans.length()-1)!='(')
				ans=ans+",";
			ans=ans+this.right.toString();
			if(ans.charAt(ans.length()-1)!='(')
				ans=ans+")";

			return ans;
		}
		public String FtoString(){
			Polynom p=new Polynom(this.f.toString());
			return p.toString();}

		public String OtoString(){
			if(this.o==Operation.Divid)
				return "div";
			if(this.o==Operation.Max)
				return "max";
			if(this.o==Operation.Times)
				return "mul";
			if(this.o==Operation.Min) 
				return "mul";
			if(this.o==Operation.Plus) 
				return "plus";
			if(this.o==Operation.Comp) 
				return "comp";
			else
				return "none";
		}
	}
}

