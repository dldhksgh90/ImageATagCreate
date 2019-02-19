package Date;

public class AreaVo {
	private float x;
	private float y;
	private float width;
	private float height;
/*	private double persentX;
	private double persentY;
	private double persentWidth;
	private double persentHeight;*/
	private int num;
	private float imageWidth;
	private float imageHeight;
	private AreaPoint mAreaPoint;
	
	public AreaVo() {
		mAreaPoint= new AreaPoint();
	}
	public AreaPoint getPoint() {
		mAreaPoint.persentX = x / imageWidth * 100f;
		mAreaPoint.persentY = y / imageHeight * 100f;
		mAreaPoint.persentWidth = width / imageWidth * 100f;
		mAreaPoint.persentHeight = height / imageHeight * 100f;
		return mAreaPoint;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public float getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(float imageWidth) {
		this.imageWidth = imageWidth;
	}
	public float getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(float imageHeight) {
		this.imageHeight = imageHeight;
	}
	
	

	public class AreaPoint{
		public double persentX;
		public double persentY;
		public double persentWidth;
		public double persentHeight;
	}

}
