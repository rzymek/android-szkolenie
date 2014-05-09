
package tumblr.viewer.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Exif {

    @SerializedName("Camera")
    @Expose
    private String camera;
    @SerializedName("ISO")
    @Expose
    private Long iSO;
    @SerializedName("Aperture")
    @Expose
    private String aperture;
    @SerializedName("Exposure")
    @Expose
    private String exposure;
    @SerializedName("FocalLength")
    @Expose
    private String focalLength;

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public Long getISO() {
        return iSO;
    }

    public void setISO(Long iSO) {
        this.iSO = iSO;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

}
