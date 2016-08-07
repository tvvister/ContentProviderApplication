package tvvister.contentproviderapplication.data;

import java.util.HashMap;
import java.util.Map;



public class Cover {

    private String small;
    private String big;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The small
     */
    public String getSmall() {
        return small;
    }

    /**
     *
     * @param small
     * The small
     */
    public void setSmall(String small) {
        this.small = small;
    }

    /**
     *
     * @return
     * The big
     */
    public String getBig() {
        return big;
    }

    /**
     *
     * @param big
     * The big
     */
    public void setBig(String big) {
        this.big = big;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
