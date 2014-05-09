
package tumblr.viewer.json;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Original_size {

    @Expose
    private Long width;
    @Expose
    private Long height;
    @Expose
    private String url;

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
