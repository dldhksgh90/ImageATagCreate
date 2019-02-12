package Date;

public class ScrollDate {
	public double height;
	public double width;
	public double orgHeight;
	public double orgWidth;
	public double x;
	public double y;
	public double scrollWidth;
	public double scrollHeight;
	public ScrollDate(double x,double y,double width,double height,double orgWidth,double orgHeight,double scrollWidth, double scrollHeight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.orgWidth = orgWidth;
		this.orgHeight = orgHeight;
		this.scrollWidth = scrollWidth;
		this.scrollHeight = scrollHeight;
	}
}
