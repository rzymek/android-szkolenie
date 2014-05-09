
package tumblr.viewer.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Photo {

    @Expose
    private String caption;
    @Expose
    private List<Alt_size> alt_sizes = new ArrayList<Alt_size>();
    @Expose
    private Original_size original_size;
    @Expose
    private Exif exif;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Alt_size> getAlt_sizes() {
        return alt_sizes;
    }

    public void setAlt_sizes(List<Alt_size> alt_sizes) {
        this.alt_sizes = alt_sizes;
    }

    public Original_size getOriginal_size() {
        return original_size;
    }

    public void setOriginal_size(Original_size original_size) {
        this.original_size = original_size;
    }

    public Exif getExif() {
        return exif;
    }

    public void setExif(Exif exif) {
        this.exif = exif;
    }

}
