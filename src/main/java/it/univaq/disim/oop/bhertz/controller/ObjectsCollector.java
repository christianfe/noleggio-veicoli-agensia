package it.univaq.disim.oop.bhertz.controller;

public class ObjectsCollector<A, B> {
	/*
	 * In alcune viste si è presentata la necessità di passare più di un oggetto al
	 * controllore, tale classe risolve tale problema raccogliendo 2 oggetti di tipo
	 * generico dentro un unico oggetto. Con tale soluzione il ViewDispatcher non ha
	 * necessitato di ulteriori modifiche.
	 */
	private A objectA;
	private B objectB;

	public ObjectsCollector() {
	}

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
