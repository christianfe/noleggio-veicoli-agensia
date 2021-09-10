package it.univaq.disim.oop.bhertz.controller;

public class ObjectsCollector <A, B> {
	private A objectA;
	private B objectB;
	
	public ObjectsCollector() {}
	
	public ObjectsCollector(A a, B b) {
		this.objectA = a;
		this.objectB = b;
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
	

}
