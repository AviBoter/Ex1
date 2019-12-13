package Ex1Testing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Functions_GUI;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.Range;
import Ex1.function;
import Ex1.functions;

class Functions_GUITest {
	public static void main(String[] a) {
		functions data = FunctionsFactory();
		String file = "function_file.txt";
		String file2 = "function_file2.txt";
		try {
			data.saveToFile(file);
			Functions_GUI data2 = new Functions_GUI();
			data2.initFromFile(file);
			data2.saveToFile(file2);
		}
		catch(Exception e) {e.printStackTrace();}
	}
	@Test
	void testInitFromFile() throws IOException {
		Functions_GUI fg1=new Functions_GUI();
		fg1.initFromFile("function_file.txt");
		assertTrue(fg1.isEmpty());
	}
	@Test
	void testSaveToFile() throws IOException {
		Functions_GUI fg1=new Functions_GUI();
		fg1.initFromFile("function_file.txt");
		fg1.saveToFile("function_file2.txt");
	}
	@Test
	void testDrawFunctions() throws IOException {
		Functions_GUI fg1=new Functions_GUI();
		fg1.initFromFile("function_file.txt");
		fg1.drawFunctions("GUI_params.txt");
	}
	@Test
	void testDrawFunctionsIntIntRangeRangeInt() throws IOException {
		int w=1000, h=600, res=300;
		Range rx = new Range(-10,10);
		Range ry = new Range(-5,15);
		Functions_GUI fg1=new Functions_GUI();
		fg1.initFromFile("function_file.txt");
		fg1.drawFunctions(w,h, rx, ry, res);
	}
	public static functions FunctionsFactory() {
		functions ans = new Functions_GUI();
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		
		for(int i=1;i<s3.length;i++) {

			cf3.mul(new Polynom(s3[i]));
		}
		ComplexFunction cf = new ComplexFunction(Operation.Plus, p1,p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"),cf3);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		ans.add(cf5.copy());
		ans.add(cf6.copy());
		Iterator<function> iter = ans.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);

		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		ans.add(max);
		ans.add(min);		
		return ans;
	}
}