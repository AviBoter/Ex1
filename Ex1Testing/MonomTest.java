package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;



import Ex1.Monom;

import Ex1.Polynom;

class MonomTest {

	@Test

	void testDerivative() {

		Monom m =new Monom(2,4);

		assertEquals(new Monom(8,3),m.derivative());

	}

	@Test

	void testF() {

		Monom m =new Monom(2,4);

		assertEquals(32,m.f(2));

	}

	@Test

	void testIsZero() {

		Monom m =new Monom(0,0);

		assertTrue( m.isZero());

	}

	@Test

	void testMonomString() {

		Monom m =new Monom("2x^5");

		assertTrue(m.get_coefficient()==2 && m.get_power()==5);

	}

	@Test

	void testAdd() {

		Monom m =new Monom("2x^5");

		Monom m1 =new Monom("5x^5");

		m.add(m1);

		assertTrue(m.get_coefficient()==7 && m.get_power()==5);

	}

	@Test

	void testMultipy() {

		Monom m =new Monom("2x^5");

		Monom m1 =new Monom("5x^5");

		m.multiply(m1);

		assertTrue(m.get_coefficient()==10 && m.get_power()==10);

	}

	@Test

	void testToString() {

		Monom m =new Monom("2x^5");

		assertTrue(m.toString().equals("2.0x^5"));

	}

	@Test

	void testCopy() {

		Monom m =new Monom("2x^5");

		assertEquals(m,m.copy());

	}

	@Test

	void testEqualsObject() {

		Monom m =new Monom("2x^5");

		Polynom p =new Polynom("2x^5");

		assertEquals(m,p);
	}
}