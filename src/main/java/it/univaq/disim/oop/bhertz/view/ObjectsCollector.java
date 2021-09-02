package it.univaq.disim.oop.bhertz.view;

public class ObjectsCollector {
	private Object objectA;
	private Object objectB;
	
	public ObjectsCollector() {}
	
	public ObjectsCollector(Object a, Object b) {
		this.objectA = a;
		this.objectB = b;
	}

	public Object getObjectA() {
		return objectA;
	}

	public void setObjectA(Object objectA) {
		this.objectA = objectA;
	}

	public Object getObjectB() {
		return objectB;
	}

	public void setObjectB(Object objectB) {
		this.objectB = objectB;
	}
	

}
