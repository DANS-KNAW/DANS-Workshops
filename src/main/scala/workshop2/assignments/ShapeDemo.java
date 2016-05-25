package workshop2.assignments;

public class ShapeDemo {

	public static void main(String[] args) {
		Shape rectangle = new Rectangle(2, 1);
		Shape circle = new Circle(1);

		System.out.println(rectangle.circumference());
		System.out.println(rectangle.area());

		System.out.println(circle.circumference());
		System.out.println(circle.area());
	}

	// 3a. Implementation in Java with the operations as abstract methods on `Shape`
	//     Note: the static keywords are here to get it all working in a single file
	static abstract class Shape {
		abstract double circumference();
		abstract double area();
	}

	static class Rectangle extends Shape {
		double w, h;
		public Rectangle(double w, double h) {
			this.w = w;
			this.h = h;
		}

		@Override
		public double circumference() {
			return 2 * (w + h);
		}

		@Override
		public double area() {
			return w * h;
		}
	}

	static class Circle extends Shape {
		double r;
		public Circle(double r) {
			this.r = r;
		}

		@Override
		public double circumference() {
			return 2 * Math.PI * r;
		}

		@Override
		public double area() {
			return Math.PI * Math.pow(r, 2.0);
		}
	}
}
