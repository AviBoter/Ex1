package Ex1Testing;
import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;



import Ex1.ComplexFunction;

import Ex1.Polynom;

import Ex1.function;

class ComplexFunctionTest {
	Polynom p1 =new Polynom("2x^3+3x^6");
	Polynom p2 =new Polynom("3x^6");

	@Test

	void testComplexFunctionStringFunctionFunction() {

		ComplexFunction cf1= new ComplexFunction("plus",p1,p2);

		function cf2=cf1.initFromString(cf1.toString());

		assertEquals(cf1,cf2);
	}

	@Test

	void testF() {

		ComplexFunction cf1= new ComplexFunction(p2);

		assertEquals(192,cf1.f(2));

	}

	@Test

	void testInitFromString() {

		ComplexFunction cf1= new ComplexFunction(p1);

		function cf2=cf1.initFromString("plus(2x^3,3x^6)");

		assertEquals(cf2.f(2),p1.f(2));
	}

	@Test

	void testPlus() {

		ComplexFunction cf1= new ComplexFunction(p2);

		ComplexFunction cf2= new ComplexFunction(p1);

		cf1.plus(cf2);

		assertEquals(cf1.f(2),p2.f(2)+p1.f(2));

	}

	@Test

	void testMul() {

		ComplexFunction cf1= new ComplexFunction(p2);

		ComplexFunction cf2= new ComplexFunction(p1);

		cf1.mul(cf2);

		assertEquals(cf1.f(2),p2.f(2)*p1.f(2));

	}

	@Test

	void testDiv() {

		ComplexFunction cf1= new ComplexFunction(p2);

		ComplexFunction cf2= new ComplexFunction(p1);

		cf1.div(cf2);

		assertEquals(cf1.f(2),p2.f(2)/p1.f(2));

	}

	@Test

	void testMax() {

		ComplexFunction cf1= new ComplexFunction(p2);

		ComplexFunction cf2= new ComplexFunction(p1);

		cf1.max(cf2);

		assertEquals(cf1.f(2),Math.max(p2.f(2),p1.f(2)));

	}

	@Test

	void testMin() {

		ComplexFunction cf1= new ComplexFunction(p2);

		ComplexFunction cf2= new ComplexFunction(p1);

		cf1.min(cf2);

		assertEquals(cf1.f(2),Math.min(p2.f(2),p1.f(2)));

	}

	@Test

	void testComp() {

		ComplexFunction cf1= new ComplexFunction(p2);

		ComplexFunction cf2= new ComplexFunction(p1);

		cf1.comp(cf2);

		assertEquals(cf1.f(2),p2.f(p1.f(2)));

	}

	@Test

	void testLeft() {

		ComplexFunction cf1= new ComplexFunction("plus",p2,p1);

		assertEquals(cf1.left().f(2),p2.f(2));

	}

	@Test

	void testRight() {

		ComplexFunction cf1= new ComplexFunction("plus",p2,p1);

		assertEquals(cf1.right().f(2),p1.f(2));

	}

	@Test

	void testEqualsObject() {

		ComplexFunction cf1= new ComplexFunction(p1);

		ComplexFunction cf2= new ComplexFunction(p2);

		Polynom p3=new Polynom("2x^3");

		cf2.plus(p3);

		assertTrue(cf1.equals(cf2));

	}

}