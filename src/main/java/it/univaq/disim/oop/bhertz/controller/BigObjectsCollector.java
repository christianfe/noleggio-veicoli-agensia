package it.univaq.disim.oop.bhertz.controller;

public class BigObjectsCollector<A, B, C> {
	/*
	 * Questa classe è semplicemente un'estensione della classe ObjectsCollector.
	 * Viene utilizzata in alcuni controllori per passare tre invece di due
	 * parametri. Avremmo potuto riutilizzare un object collector anche in quel
	 * caso, ma abbiamo deciso di creare un big objects collector per una maggiore
	 * leggibilità del codice
	 */
	private A objectA;
	private B objectB;
	private C objectC;

	public BigObjectsCollector() {
	}

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
