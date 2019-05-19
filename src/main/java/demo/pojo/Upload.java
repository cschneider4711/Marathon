package demo.pojo;

public class Upload {
	
	private String contentType = "UNKNOWN";
	private String name = "UNKNOWN";
	private byte[] data;
	
	public Upload(String contentType, String name, byte[] data) {
		setContentType(contentType);
		setName(name);
		setData(data);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	
	
}
