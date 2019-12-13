package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;



import Ex1.Monom;

import Ex1.Polynom;

class PolynomTest {

	@Test

	void testPolynomString() {

		Polynom p =new Polynom("2x+3x^4+6");

		assertTrue(p!=null);

	}

	@Test

	void testF() {

		Polynom p =new Polynom("2x+3x^4+6");

		assertEquals(255,p.f(3));

	}

	@Test

	void testAddPolynom_able() {

		Polynom p =new Polynom("3x^2+5x+2");

		Polynom p1 =new Polynom("6x^3+7x^2+2");

		 p.add(p1);

		assertEquals(new Polynom("6x^3+10x^2+5x+4"),p);

	}

	@Test

	void testAddMonom() {

		Monom p=new Monom("3x^2");

		 Polynom p1 =new Polynom("6x^3+7x^2+2");

		 p1.add(p);

		 assertEquals(new Polynom("6x^3+10x^2+2"),p1);

	}

	@Test

	void testSubstract() {

		Polynom p =new Polynom("3x^3+5x^2-3");

		Polynom p1 =new Polynom("5x^3+2x^2-1");

		p.substract(p1);

		assertEquals(new Polynom("-2x^3+3x^2-2"), p);

	}

	@Test

	void testMultiplyPolynom_able() {

		Polynom p =new Polynom("3x^2+5x");

		Polynom p1 =new Polynom("2x+2");

		p.multiply(p1);

		assertEquals(new Polynom("6x^3+16x^2+10x"), p);

	}

	@Test

	void testEqualsObject() {

		Polynom p =new Polynom("3x^2+5x");

		Polynom p1 =new Polynom("2x+2");

		Polynom p2 =new Polynom("3x^3+5x^2-3");

		Polynom p3 =new Polynom("3x^3+5x^2-3");

		assertTrue((!p.equals(p1))&& (p2.equals(p3)));

	}

	@Test

	void testIsZero() {

		Polynom  p=new Polynom("0x^3+0x+0");

		assertTrue(p.isZero());

	}

	@Test

	void testRoot() {

		Polynom  p=new Polynom("x^2-5");

		assertEquals(2.234375, p.root(-1, 5, 0.01));

	}

	@Test

	void testCopy() {

		Polynom p2 =new Polynom("3x^3+5x^2-3");
		
		assertEquals(p2.copy(),p2);

	}

	@Test

	void testDerivative() {
		Polynom p2 =new Polynom("3x^3+5x^2-3");
		assertEquals(new Polynom("9x^2+10x"), p2.derivative());

	}

	@Test

	void testArea() {

		Polynom  p=new Polynom("x^2-5");
		assertEquals(20.00999999999939, p.area(-1, 5, 0.01));

	}

	@Test

	void testMultiplyMonom() {

		Polynom p2 =new Polynom("3x^3+5x^2-3");

		Monom p= new Monom("3x^2");

		p2.multiply(p);

		assertEquals(new Polynom("9x^5+15x^4-9x^2"),p2 );

	}

	@Test

	void testToString() {

		Polynom p=new Polynom("5x^4+4x");

		Polynom p1=new Polynom("4x+5x^4");

		p.toString();

		p1.toString();

		assertEquals(p1, p);

	}

}