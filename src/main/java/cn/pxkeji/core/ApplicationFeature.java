package cn.pxkeji.core;
public class ApplicationFeature implements RequestFeature{
    private String applicationId;
    
	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}