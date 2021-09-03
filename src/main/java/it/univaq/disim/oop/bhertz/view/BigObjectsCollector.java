package it.univaq.disim.oop.bhertz.view;

public class BigObjectsCollector <A, B, C> {
	private A objectA;
	private B objectB;
	private C objectC;
	
	public BigObjectsCollector() {}
	
	public BigObjectsCollector(A a, B b, C c) {
		this.setObjectA(a);
		this.setObjectB(b);
		this.setObjectC(c);
	}

	public A getObjectA() {
		return objectA;
	}

	public void setObjectA(A objectA) {
		this.objectA = objectA;
	}

	public B getObjectB() {
		return objectB;
	}

	public void setObjectB(B objectB) {
		this.objectB = objectB;
	}

	public C getObjectC() {
		return objectC;
	}

	public void setObjectC(C objectC) {
		this.objectC = objectC;
	}

	
}
